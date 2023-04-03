package shadowmaster435.funkyfarming.block.entity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.codec.DecoderException;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import shadowmaster435.funkyfarming.block.entity.TesterBlockEntity;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.init.FFShaders;
import shadowmaster435.funkyfarming.rendering.DynamicTexture;
import shadowmaster435.funkyfarming.util.ExtendedDirection;
import shadowmaster435.funkyfarming.rendering.QuadGrid;

import java.nio.ByteBuffer;
import java.util.Objects;

public class TesterRenderer implements BlockEntityRenderer<TesterBlockEntity> {


    public static final Identifier tex = new Identifier("funkyfarming:textures/block/ct_test.png");

    public final DynamicTexture dyn = new DynamicTexture(16, 16, 20, 16);;

    public TesterRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    public final QuadGrid grid = new QuadGrid(16);
    @Override
    public void render(TesterBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        assert MinecraftClient.getInstance().world != null;
        if (!entity.isRemoved()) {
            this.dyn.setTick((int) MinecraftClient.getInstance().world.getTime());
            this.dyn.renderQuad(matrices, entity.getPos());
        } else {
            this.dyn.remove();
        }
        matrices.pop();
    }
    
    
    public void tlQuad(Matrix4f matrices, TesterBlockEntity entity, Direction side) {
        declutterMethod(matrices, entity, side, "tl");
    }
    
    public void trQuad(Matrix4f matrices, TesterBlockEntity entity, Direction side) {
        declutterMethod(matrices, entity, side, "tr");
    }
    
    public void blQuad(Matrix4f matrices, TesterBlockEntity entity, Direction side) {
        declutterMethod(matrices, entity, side, "bl");
    }

    public void brQuad(Matrix4f matrices, TesterBlockEntity entity, Direction side) {
        declutterMethod(matrices, entity, side, "br");
    }
    public void declutterMethod(Matrix4f matricies, TesterBlockEntity entity, Direction side, String corner) {
        Vector3f quadOffset = this.quadOffset(corner, side);
        Vector3f maxoffs = quadOffset.add(0.5f, 0.5f, 0.5f);
        for (ExtendedDirection extendedDirection : ExtendedDirection.ctFilter(corner, side)) {
            BlockPos pos = extendedDirection.offset(entity.getPos(), extendedDirection);
            boolean test = this.logic(pos, Objects.requireNonNull(entity.getWorld()), corner, extendedDirection);

            if (pos != entity.getPos() && entity.getWorld() != null) {
                Vector4f uv = this.uv(corner, test);
                finishRender(matricies, new Vector3f(0, 0, 0), quadOffset, uv.mul(2f), side.getAxis());
            }
        }
    }


    public void finishRender(Matrix4f mat4, Vector3f min, Vector3f max, Vector4f uv, Direction.Axis axis) {


        RenderSystem.enableTexture();
        RenderSystem.enableDepthTest();
        RenderSystem.disableCull();
        Tessellator tessellator =  Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        RenderSystem.setShaderTexture(0, tex);
        builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        if (axis == Direction.Axis.X) {

            builder.vertex(mat4, min.x, min.y, min.z).texture(uv.x, uv.y).color(255, 255, 255, 255).next();
            builder.vertex(mat4, min.x, min.y, max.z).texture(uv.z, uv.y).color(255, 255, 255, 255).next();
            builder.vertex(mat4, min.x, max.y, max.z).texture(uv.z, uv.w).color(255, 255, 255, 255).next();
            builder.vertex(mat4, min.x, max.y, min.z).texture(uv.x, uv.w).color(255, 255, 255, 255).next();
        }
        if (axis == Direction.Axis.Y) {

            builder.vertex(mat4, min.x, min.y, min.z).texture(uv.x, uv.y).color(255, 255, 255, 255).next();
            builder.vertex(mat4, min.x, min.y, max.z).texture(uv.z, uv.y).color(255, 255, 255, 255).next();
            builder.vertex(mat4, max.x, min.y, max.z).texture(uv.z, uv.w).color(255, 255, 255, 255).next();
            builder.vertex(mat4, max.x, min.y, min.z).texture(uv.x, uv.w).color(255, 255, 255, 255).next();
        }
        if (axis == Direction.Axis.Z) {

            builder.vertex(mat4, min.x, min.y, min.z).texture(uv.x, uv.y).color(255, 255, 255, 255).next();
            builder.vertex(mat4, max.x, min.y, min.z).texture(uv.z, uv.y).color(255, 255, 255, 255).next();
            builder.vertex(mat4, max.x, max.y, min.z).texture(uv.z, uv.w).color(255, 255, 255, 255).next();
            builder.vertex(mat4, min.x, max.y, min.z).texture(uv.x, uv.w).color(255, 255, 255, 255).next();
        }
        tessellator.draw();
        RenderSystem.disableTexture();


    }


    public boolean logic(BlockPos pos, World world, String corner, ExtendedDirection direction) {
        if (Objects.equals(corner, "bl")) {
        }
        return world.getBlockState(pos).getBlock() == FFBlocks.TESTER;
    }




    public Vector4f uv(String corner, boolean b) {
        float minX = 0;
        float minY = 0;
        float maxX = 0;
        float maxY = 0;
        switch (corner) {
            case "bl" -> {

                minX = (b) ? 0.5f : 0;
                minY = (b) ? 0.5f : 0;
                maxX = (b) ? 0.75f : 0.25f;
                maxY = (b) ? 0.75f : 0.25f;
            }
            case "br" -> {
                minX = (b) ? 0.75f : 0.25f;
                minY = (b) ? 0.5f : 0;
                maxX = (b) ? 1f : 0.5f;
                maxY = (b) ? 0.75f : 0.25f;
            }
            case "tl" -> {
                minX = (b) ? 0.5f : 0;
                minY = (b) ? 0.75f : 0.5f;
                maxX = (b) ? 0.75f : 0.25f;
                maxY = (b) ? 1f : 0.75f;
            }
            case "tr" -> {
                minX = (b) ? 0.75f : 0.5f;
                minY = (b) ? 0.75f : 0.5f;
                maxX = (b) ? 1f : 0.75f;
                maxY = (b) ? 1f : 0.75f;
            }
        }
        return new Vector4f(minX, minY, maxX, maxY);
    }


    public Vector3f quadOffset(String corner, Direction side) {
        float x = 0;
        float y = 0;
        float z = 0;
        float minOffsetH = 0;
        float minOffsetV = 0;
        switch (corner) {
            case "bl" -> {
                minOffsetH = 0;
                minOffsetV = 0;
            }
            case "br" -> {
                minOffsetH = 0.5f;
                minOffsetV = 0;
            }
            case "tl" -> {
                minOffsetH = 0;
                minOffsetV = 0.5f;
            }
            case "tr" -> {
                minOffsetH = 0.5f;
                minOffsetV = 0.5f;
            }
        }

        switch (side) {
            case UP -> {
                x = 0.5f;
                y = 1;
                z = 0.5f;
            }
            case DOWN -> {
                x = 0.5f;
                y = 0;
                z = 0.5f;
            }
            case NORTH -> {
                x = 0.5f;
                y = 0.5f;
                z = 0;
            }
            case SOUTH -> {
                x = 0.5f;
                y = 0.5f;
                z = 1;
            }
            case EAST -> {
                x = 1;
                y = 0.5f;
                z = 0.5f;
            }
            case WEST -> {
                x = 0;
                y = 0.5f;
                z = 0.5f;
            }
        }
        return new Vector3f(x, y, z);
    }



    
}
