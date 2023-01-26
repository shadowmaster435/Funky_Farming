package shadowmaster435.funkyfarming.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.*;
import shadowmaster435.funkyfarming.block.entity.GatewayEntity;


public class RenderUtil {

    public static float screenshakeamount = 0;

    public static Vec3d cameraRot;



    public static void quad(Vector2f min, Vector2f max, Direction.Axis axis, VertexConsumer consumer, Matrix4f mat4f) {
        if (axis == Direction.Axis.Z) {
            consumer.vertex(mat4f, min.x,  min.y, 0).next();
            consumer.vertex(mat4f, max.x, min.y, 0).next();
            consumer.vertex(mat4f, max.x, max.y, 0).next();
            consumer.vertex(mat4f, min.x, max.y, 0).next();
        } if (axis == Direction.Axis.X) {
            consumer.vertex(mat4f, 0, min.x, min.y).next();
            consumer.vertex(mat4f, 0, max.x, min.y).next();
            consumer.vertex(mat4f, 0, max.x, max.y).next();
            consumer.vertex(mat4f, 0, min.x, max.y).next();
        } else {
            consumer.vertex(mat4f, min.x, 0, min.y).next();
            consumer.vertex(mat4f, max.x, 0, min.y).next();
            consumer.vertex(mat4f, max.x, 0, max.y).next();
            consumer.vertex(mat4f, min.x, 0, max.y).next();
        }
    }
    public static void texturedquad(Vector2f min, Vector2f max, float extraoffset, Direction.Axis axis, Vector2f uv, Identifier tex, VertexConsumer consumers, Matrix4f mat4f, Vector2f extrauv, Vector4f argb) {
        RenderSystem.enableTexture();
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        Tessellator tessellator =  Tessellator.getInstance();
        BufferBuilder consumer = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        RenderSystem.setShaderTexture(0, tex);
        consumer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        if (axis == Direction.Axis.Z) {
            consumer.vertex(mat4f, min.x,  min.y, extraoffset).texture(0 + extrauv.x,0 + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, max.x, min.y, extraoffset).texture(uv.x + extrauv.x,0 + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, max.x, max.y, extraoffset).texture(uv.x + extrauv.x,uv.y + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, min.x, max.y, extraoffset).texture(0 + extrauv.x,uv.y + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
        } if (axis == Direction.Axis.X) {
            consumer.vertex(mat4f, extraoffset, min.y, min.x).texture(0 + extrauv.x,0 + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, extraoffset, min.y, max.x).texture(uv.x + extrauv.x,0 + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, extraoffset, max.y, max.x).texture(uv.x + extrauv.x,uv.y + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, extraoffset, max.y, min.x).texture(0 + extrauv.x,uv.y + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
        } if (axis == Direction.Axis.Y) {
            consumer.vertex(mat4f, min.x, extraoffset, min.y).texture(0 + extrauv.x,0 + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, max.x, extraoffset, min.y).texture(uv.x + extrauv.x,0 + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, max.x, extraoffset, max.y).texture(uv.x + extrauv.x,uv.y + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, min.x, extraoffset, max.y).texture(0 + extrauv.x,uv.y + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
        }
        tessellator.draw();
        RenderSystem.disableDepthTest();
        RenderSystem.disableTexture();
    }
    public static void texturedquadopaqu(Vector2f min, Vector2f max, float extraoffset, Direction.Axis axis, Vector2f uv, Identifier tex, VertexConsumer consumers, Matrix4f mat4f, Vector2f extrauv, Vector4f argb) {
        RenderSystem.enableTexture();
        RenderSystem.enableDepthTest();
        Tessellator tessellator =  Tessellator.getInstance();
        BufferBuilder consumer = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        RenderSystem.setShaderTexture(0, tex);
        consumer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        if (axis == Direction.Axis.Z) {
            consumer.vertex(mat4f, min.x,  min.y, extraoffset).texture(0 + extrauv.x,0 + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, max.x, min.y, extraoffset).texture(uv.x + extrauv.x,0 + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, max.x, max.y, extraoffset).texture(uv.x + extrauv.x,uv.y + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, min.x, max.y, extraoffset).texture(0 + extrauv.x,uv.y + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
        } if (axis == Direction.Axis.X) {
            consumer.vertex(mat4f, extraoffset, min.y, min.x).texture(0 + extrauv.x,0 + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, extraoffset, min.y, max.x).texture(uv.x + extrauv.x,0 + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, extraoffset, max.y, max.x).texture(uv.x + extrauv.x,uv.y + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, extraoffset, max.y, min.x).texture(0 + extrauv.x,uv.y + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
        } if (axis == Direction.Axis.Y) {
            consumer.vertex(mat4f, min.x, extraoffset, min.y).texture(0 + extrauv.x,0 + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, max.x, extraoffset, min.y).texture(uv.x + extrauv.x,0 + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, max.x, extraoffset, max.y).texture(uv.x + extrauv.x,uv.y + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
            consumer.vertex(mat4f, min.x, extraoffset, max.y).texture(0 + extrauv.x,uv.y + extrauv.y).color(argb.x, argb.y, argb.z, argb.w).next();
        }
        tessellator.draw();
        RenderSystem.disableDepthTest();
        RenderSystem.disableTexture();
    }
    public static void cubeallside(Vector3f min, Vector3f max, VertexConsumer consumer, Matrix4f mat4f, Identifier texture, Vector4f argb) {
        for (Direction dir : Direction.values()) {
            switch (dir) {
                case UP -> texturedquad(new Vector2f(min.x, min.z), new Vector2f(max.x, max.z), max.y, Direction.Axis.Y, new Vector2f(1,1), texture, consumer, mat4f, new Vector2f(0, 0), argb);
                case DOWN -> texturedquad(new Vector2f(min.x, min.z), new Vector2f(max.x, max.z), min.y, Direction.Axis.Y, new Vector2f(1,1), texture, consumer, mat4f, new Vector2f(0, 0), argb);
                case NORTH -> texturedquad(new Vector2f(min.x, min.y), new Vector2f(max.x, max.y), min.z, Direction.Axis.Z, new Vector2f(1,1), texture, consumer, mat4f, new Vector2f(0, 0), argb);
                case SOUTH -> texturedquad(new Vector2f(min.x, min.y), new Vector2f(max.x, max.y), max.z, Direction.Axis.Z, new Vector2f(1,1), texture, consumer, mat4f, new Vector2f(0, 0), argb);
                case EAST -> texturedquad(new Vector2f(min.z, min.y), new Vector2f(max.z,  max.y), max.x, Direction.Axis.X, new Vector2f(1,1), texture, consumer, mat4f, new Vector2f(0, 0), argb);
                case WEST -> texturedquad(new Vector2f(min.z, min.y), new Vector2f(max.z,  max.y), min.x, Direction.Axis.X, new Vector2f(1,1), texture, consumer, mat4f, new Vector2f(0, 0), argb);
            }
        }
    }

    public static void cubeshaft(Vector3f min, Vector3f max, VertexConsumer consumer, Matrix4f mat4f, Identifier texture, float uvscroll, float uvscalar, Vector4f argb) {

                texturedquadopaqu(new Vector2f(max.x, max.y), new Vector2f(min.x, min.y), max.x, Direction.Axis.Z, new Vector2f(1, uvscalar * 2), texture, consumer, mat4f, new Vector2f(1, uvscroll * 4 / 1.25f), argb);
        texturedquadopaqu(new Vector2f(min.z, max.y), new Vector2f(max.z, min.y), max.z, Direction.Axis.X, new Vector2f(1,uvscalar * 2), texture, consumer, mat4f, new Vector2f(1, uvscroll * 4 / 1.25f), argb);
        texturedquadopaqu(new Vector2f(min.x, max.y), new Vector2f(max.x, min.y), min.x, Direction.Axis.Z, new Vector2f(1,uvscalar * 2), texture, consumer, mat4f, new Vector2f(1, uvscroll * 4 / 1.25f), argb);

        texturedquadopaqu(new Vector2f(max.z, max.y), new Vector2f(min.z, min.y), min.z, Direction.Axis.X, new Vector2f(1,uvscalar * 2), texture, consumer, mat4f, new Vector2f(1, uvscroll * 4 / 1.25f), argb);


    }



}
