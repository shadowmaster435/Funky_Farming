package shadowmaster435.funkyfarming.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.joml.*;
import shadowmaster435.funkyfarming.util.MathUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class QuadStrip {

    private final ArrayList<Vector3f> posList;

    private final float width;

    private float randomSpread = 0.0625f;


    public QuadStrip(List<Vector3f> posList, float width) {
        this.posList = new ArrayList<>(posList);
        this.width = width;
    }

    public QuadStrip(List<Vector3f> posList, float width, float randomSpread) {
        this.posList = new ArrayList<>(posList);
        this.width = width;
        this.randomSpread = randomSpread;
    }


    public List<Vector3f> getPosList() {
        return new ArrayList<>(this.posList);
    }

    public float getRandomSpread() {
        return randomSpread;
    }

    public float getWidth() {
        return this.width;
    }

    public void addPos(Vector3f vector3f) {
        this.posList.add(vector3f);
    }

    public void addPos(Vector3f vector3f, int index) {
        this.posList.add(index, vector3f);
    }

    public void removePos(Vector3f vector3f) {
        this.posList.remove(vector3f);
    }
    public void setPos(Vector3f vector3f, int index) {
        this.posList.set(index, vector3f);
    }
    public void removePos(int index) {
        this.posList.remove(index);
    }

    public void render(MatrixStack matrices, float tickDelta, Random random, Identifier texture, Vector4f uv, float rot, Direction.Axis axis) {


        Matrix4f mat = matrices.peek().getPositionMatrix();
        matrices.push();
        if (this.posList.size() > 1) {
            RenderSystem.enableTexture();
            RenderSystem.enableDepthTest();
            RenderSystem.disableCull();
            Tessellator tessellator =  Tessellator.getInstance();
            BufferBuilder builder = tessellator.getBuffer();
            RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
            RenderSystem.setShaderTexture(0, texture);
            builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            for (int i = 0; i < this.posList.size() - 1; ++i) {

                Vector3f firstPos = this.posList.get(i);
                Vector3f nexPos = this.posList.get(i + 1);
                Vector2f zro = new Vector2f(0f,0f);
                Vector2f nextVecR = MathUtil.rotateAround(this.width / 2, new Vector2f(nexPos.x, nexPos.z));
                Vector2f rotationVec = MathUtil.rotateAround(this.width / 2, new Vector2f(firstPos.x, firstPos.z));
                Vector2f randPosOffset = new Vector2f(((random.nextFloat() - 0.5f) * this.randomSpread), ((random.nextFloat() - 0.5f) * this.randomSpread));

                builder.vertex(mat, firstPos.x + randPosOffset.y, firstPos.y, rotationVec.y).texture(uv.x, uv.y).color(255,255,255,255).next();
                builder.vertex(mat, nexPos.x + randPosOffset.x, nexPos.y, nextVecR.y).texture(uv.x, uv.w).color(255,255,255,255).next();
                builder.vertex(mat, nexPos.x + randPosOffset.x, nexPos.y, nextVecR.y).texture(uv.z, uv.w).color(255,255,255,255).next();
                builder.vertex(mat, firstPos.x + randPosOffset.y, firstPos.y, rotationVec.y).texture(uv.z, uv.y).color(255,255,255,255).next();

                switch (axis) {
                    case X -> {
                        builder.vertex(mat, firstPos.x + randPosOffset.y, firstPos.y, rotationVec.y).texture(uv.x, uv.y).color(1f,1f,1f,1f).next();
                        builder.vertex(mat, rotationVec.x + randPosOffset.x, nexPos.y, nextVecR.y).texture(uv.x, uv.w).color(1f,1f,1f,1f).next();
                        builder.vertex(mat, nexPos.x + randPosOffset.x, nexPos.y, nextVecR.y).texture(uv.z, uv.w).color(1f,1f,1f,1f).next();
                        builder.vertex(mat, nexPos.x + randPosOffset.y, firstPos.y, rotationVec.y).texture(uv.z, uv.y).color(1f,1f,1f,1f).next();
                    }
                    case Z -> {
                        builder.vertex(mat, rotationVec.x, nexPos.y, nextVecR.y + randPosOffset.x).texture(uv.x, uv.w).color(1f,1f,1f,1f).next();
                        builder.vertex(mat, nexPos.x, firstPos.y, rotationVec.y + randPosOffset.y).texture(uv.z, uv.y).color(1f,1f,1f,1f).next();

                        builder.vertex(mat, nexPos.x, nexPos.y, nextVecR.y + randPosOffset.x).texture(uv.z, uv.w).color(1f,1f,1f,1f).next();
                        builder.vertex(mat, firstPos.x + randPosOffset.y, firstPos.y, rotationVec.y + randPosOffset.y).texture(uv.x, uv.y).color(1f,1f,1f,1f).next();
                    }
                }
            }
            tessellator.draw();

        } else {
            System.out.println(this.posList.size() + " is too small of a list for a quad strip this shouldn't happen!");
        }
        matrices.pop();

        RenderSystem.disableDepthTest();
        RenderSystem.disableTexture();
    }

}
