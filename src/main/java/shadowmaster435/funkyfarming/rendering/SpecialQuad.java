package shadowmaster435.funkyfarming.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.joml.*;
import shadowmaster435.funkyfarming.util.MiscUtil;
import shadowmaster435.funkyfarming.util.QuadGrid;

import java.util.ArrayList;
import java.util.List;

public class SpecialQuad {

    public Vector2f pos;
    public Vector2f quadSize;
    public Vector3f rgb;
    public Vector2i gridPos;
    public QuadGrid owner;
    public int time;
    public String tickType;
    public int lifespan;

    public float offset;

    public Direction.Axis axis;

    public static List<SpecialQuad> quads = new ArrayList<>();
    public boolean remove = false;

    public void removeSafe() {
        this.remove = true;
        quads.removeIf(quad -> quad.remove);
    }

    public void removeSafeGrid() {
        this.remove = true;
        this.owner.quads.removeIf(quad -> quad.remove);
    }
    public SpecialQuad(Vector2f pos, Vector2f quadSize, Vector3f rgb) {
        this.quadSize = quadSize;
        this.pos = pos;
        this.rgb = rgb;
    }

    public Vector4f verts;
    public Vector2f uv;
    public float alpha;
    public float zPos;
    public Identifier tex;
    public SpecialQuad(Vector4f verts, Vector2f uv, Identifier tex, float alpha, float zPos, int lifespan) {
        this.verts = verts;
        this.uv = uv;
        this.alpha = alpha;
        this.zPos = zPos;
        this.lifespan = lifespan;
        this.tex = tex;
        quads.add(this);
    }

    public SpecialQuad(Vector2f pos, Vector2f quadSize, Vector3f rgb, int lifespan) {
        this.quadSize = quadSize;
        this.pos = pos;
        this.lifespan = lifespan;
        this.rgb = rgb;
        this.time = lifespan;

    }

    public SpecialQuad(Vector2i gridPos, Vector3f rgb, QuadGrid owner, int lifespan, Direction.Axis axis) {
        this.gridPos = gridPos;
        this.rgb = rgb;
        this.owner = owner;
        this.lifespan = lifespan;
        this.time = lifespan;

        this.axis = axis;
    }
    
    public SpecialQuad(Vector2i gridPos, Vector3f rgb, int lifespan, QuadGrid owner, float offset, Direction.Axis axis) {
        this.gridPos = gridPos;
        this.lifespan = lifespan;
        this.rgb = rgb;
        this.owner = owner;
        this.time = lifespan;
        this.offset = offset;
        this.axis = axis;
    }

    public void renderColorGrid(MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, Vector3f minpos, Vector3f maxpos, float tickDelta, RenderLayer renderLayer) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
        Matrix4f mat4 = matrixStack.peek().getPositionMatrix();
        matrixStack.push();
        vertexConsumer.vertex(mat4, minpos.x, minpos.y, minpos.z).color(this.rgb.x, this.rgb.y, this.rgb.z, this.time - tickDelta / this.lifespan);
        vertexConsumer.vertex(mat4, maxpos.x, minpos.y, minpos.z).color(this.rgb.x, this.rgb.y, this.rgb.z, this.time - tickDelta / this.lifespan);
        vertexConsumer.vertex(mat4, maxpos.x, minpos.y, maxpos.z).color(this.rgb.x, this.rgb.y, this.rgb.z, this.time - tickDelta / this.lifespan);
        vertexConsumer.vertex(mat4, minpos.x, minpos.y, maxpos.z).color(this.rgb.x, this.rgb.y, this.rgb.z, this.time - tickDelta / this.lifespan);
        matrixStack.pop();
    }

    public void renderColorGrid(Matrix4f mat4, MatrixStack stack, Vector3f minpos, Vector3f maxpos, float tickDelta, VertexFormat.DrawMode drawMode, VertexFormat format) {
        float camx = (float) MinecraftClient.getInstance().cameraEntity.getCameraPosVec(tickDelta).x;
        float camy = (float) MinecraftClient.getInstance().cameraEntity.getCameraPosVec(tickDelta).y - 1;
        float camz = (float) MinecraftClient.getInstance().cameraEntity.getCameraPosVec(tickDelta).z;
        float playerx = (float) MinecraftClient.getInstance().player.getCameraPosVec(tickDelta).getX();
        float playery = (float) MinecraftClient.getInstance().player.getCameraPosVec(tickDelta).getY();
        float playerz = (float) MinecraftClient.getInstance().player.getCameraPosVec(tickDelta).getZ();
        float distx = camx - playerx;
        float disty = camy - playery;
        float distz = camz - playerz;

        float x = minpos.x - playerx - distx;
        float y = minpos.y - playery - disty;
        float z = minpos.z - playerz - distz;
        Vec3d zpos = MiscUtil.getBlockHitResult().getPos();

        stack.push();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder builder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
            RenderSystem.enableDepthTest();
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            float alphafade = (float)this.time / this.lifespan;
            builder.begin(drawMode, format);
            builder.vertex(mat4, x, y, z).color(this.rgb.x, this.rgb.y, this.rgb.z, alphafade).next();
            builder.vertex(mat4, x + 0.0625f, y, z).color(this.rgb.x, this.rgb.y, this.rgb.z, alphafade).next();
            builder.vertex(mat4, x + 0.0625f, y, z + 0.0625f).color(this.rgb.x, this.rgb.y, this.rgb.z, alphafade).next();
            builder.vertex(mat4, x, y, z + 0.0625f).color(this.rgb.x, this.rgb.y, this.rgb.z, alphafade).next();
            tessellator.draw();
            stack.pop();
    }

    public void renderColorGrid(MatrixStack stack, Vector3f minpos, Vector3f maxpos, float tickDelta, VertexConsumerProvider provider) {
        Matrix4f mat4 = stack.peek().getPositionMatrix();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        stack.push();
        builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        float alphafade = (float)this.time / this.lifespan;
        builder.vertex(mat4, minpos.x, minpos.y, minpos.z).color(this.rgb.x, this.rgb.y, this.rgb.z, alphafade).next();
        builder.vertex(mat4, minpos.x + 0.0625f, minpos.y, minpos.z).color(this.rgb.x, this.rgb.y, this.rgb.z, alphafade).next();
        builder.vertex(mat4, minpos.x + 0.0625f, minpos.y, minpos.z + 0.0625f).color(this.rgb.x, this.rgb.y, this.rgb.z, alphafade).next();
        builder.vertex(mat4, minpos.x, minpos.y, minpos.z + 0.0625f).color(this.rgb.x, this.rgb.y, this.rgb.z, alphafade).next();
        tessellator.draw();
        stack.pop();
    }

    public void renderFrames(Matrix4f mat4, float tickDelta, int frame) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        RenderSystem.setShaderTexture(0, this.tex);
        float vOfs = this.uv.y * frame;
        builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        builder.vertex(mat4, this.verts.x, this.verts.w, this.zPos).texture(0, this.uv.y + vOfs).color(1f, 1f, 1f, this.alpha);
        builder.vertex(mat4, this.verts.z, this.verts.w, this.zPos).texture(this.uv.x, this.uv.y + vOfs).color(1f, 1f, 1f, this.alpha);
        builder.vertex(mat4, this.verts.z,this.verts.y, this.zPos).texture(this.uv.x, vOfs).color(1f, 1f, 1f, this.alpha);
        builder.vertex(mat4, this.verts.x, this.verts.y, this.zPos).texture(0, vOfs).color(1f, 1f, 1f, this.alpha);
        tessellator.draw();

    }

    public void setTime(int time) {
        this.time = time;
    }
    
    public void remove() {
        this.owner.quads.remove(this);
    }
    
    public void tick() {
        if (this.time <= 0) {
            this.remove();
        } else {
            --this.time;
        }
    }

    public void setRgb(Vector3f rgb) {
        this.rgb = rgb;
    }

    public void setPos(Vector2f pos) {
        this.pos = pos;
    }

    public void setGridPos(Vector2i gridPos) {
        this.gridPos = gridPos;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

    public void setOwner(QuadGrid owner) {
        this.owner = owner;
    }



    public void setQuadSize(Vector2f quadSize) {
        this.quadSize = quadSize;
    }

    public void setTickType(String tickType) {
        this.tickType = tickType;
    }
}
