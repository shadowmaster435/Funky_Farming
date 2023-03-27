package shadowmaster435.funkyfarming.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import shadowmaster435.funkyfarming.rendering.SpecialQuad;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class QuadGrid {
    public float pixelSize;
    public static CopyOnWriteArrayList<QuadGrid> grids = new CopyOnWriteArrayList<>();
    public CopyOnWriteArrayList<SpecialQuad> quads = new CopyOnWriteArrayList<>();

    public CopyOnWriteArrayList<SpecialQuad> quads2 = new CopyOnWriteArrayList<>();


    public static boolean remove = false;
    public QuadGrid(float pixelSize) {
        this.pixelSize = pixelSize;
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, RenderLayer renderLayer, float tickdelta, Direction.Axis axis) {
        for (SpecialQuad quad : this.quads2) {
            float offset = quad.offset;
            Vector3f finalPosMin;
            Vector3f finalPosMax;
            switch (axis) {
                case X -> {
                    finalPosMin = new Vector3f(offset, quad.gridPos.x * this.pixelSize, quad.gridPos.y * this.pixelSize);
                    finalPosMax = new Vector3f(offset, (quad.gridPos.x + 1) * this.pixelSize, (quad.gridPos.y + 1) * this.pixelSize);
                }
                case Y -> {
                    finalPosMin = new Vector3f(quad.gridPos.x * this.pixelSize, offset, quad.gridPos.y * this.pixelSize);
                    finalPosMax = new Vector3f((quad.gridPos.x + 1) * this.pixelSize, offset,(quad.gridPos.y + 1) * this.pixelSize);
                }
                case Z -> {
                    finalPosMin = new Vector3f(quad.gridPos.y * this.pixelSize, quad.gridPos.x * this.pixelSize, offset);
                    finalPosMax = new Vector3f((quad.gridPos.y + 1) * this.pixelSize, (quad.gridPos.x + 1) * this.pixelSize, offset);
                }
                default -> throw new RuntimeException("Invalid Axis");
            }
            quad.renderColorGrid(matrixStack, vertexConsumers, finalPosMin, finalPosMax, tickdelta, renderLayer);
        }
    }

    public void render(BlockPos pos, MatrixStack stack, float tickdelta, VertexFormat.DrawMode drawMode, VertexFormat format) {
        if (MinecraftClient.getInstance().world != null) {
            for (SpecialQuad quad : this.quads) {
                if (!(quad.time <= 0)) {

                    Direction.Axis axis = quad.axis;
                    float offset = quad.offset;
                    Vector3f finalPosMin;
                    Vector3f finalPosMax;
                   /* switch (axis) {
                        case X -> {
                            finalPosMin = new Vector3f(offset + pos.getX(), quad.gridPos.x / this.pixelSize + pos.getY(), quad.gridPos.y / this.pixelSize + pos.getZ());
                            finalPosMax = new Vector3f(offset + pos.getX(), (quad.gridPos.x + 1) / this.pixelSize + pos.getY(), (quad.gridPos.y + 1) / this.pixelSize + pos.getZ());
                        }
                        case Y -> {
                            finalPosMin = new Vector3f(quad.gridPos.x / this.pixelSize + pos.getX(), offset + pos.getY(), quad.gridPos.y / this.pixelSize + pos.getZ());
                            finalPosMax = new Vector3f((quad.gridPos.x + 1) / this.pixelSize + pos.getX(), offset + pos.getY(), (quad.gridPos.y + 1) / this.pixelSize + pos.getZ());
                        }
                        case Z -> {
                            finalPosMin = new Vector3f(quad.gridPos.y / this.pixelSize + pos.getX(), quad.gridPos.x / this.pixelSize + pos.getY(), offset + pos.getZ());
                            finalPosMax = new Vector3f((quad.gridPos.y + 1) / this.pixelSize + pos.getX(), (quad.gridPos.x + 1) / this.pixelSize + pos.getY(), offset + pos.getZ());
                        }

                        default -> throw new RuntimeException("Invalid Axis");
                    }*/
                    finalPosMin = new Vector3f(quad.gridPos.x / this.pixelSize + pos.getX(), pos.getY(), pos.getZ() + quad.gridPos.y / this.pixelSize );
                    finalPosMax = new Vector3f((quad.gridPos.x + 1) / this.pixelSize + pos.getX(), pos.getY(), pos.getZ() + quad.gridPos.y / this.pixelSize );

                    quad.renderColorGrid(stack.peek().getPositionMatrix(), stack, finalPosMin, finalPosMax, tickdelta, drawMode, format);
                }
            }
        }
    }
    public void renderBlockEntity(BlockPos pos, MatrixStack stack, float tickdelta, VertexConsumerProvider provider) {
        if (MinecraftClient.getInstance().world != null) {
            for (SpecialQuad quad : this.quads) {
                if (!(quad.time <= 0)) {

                    Vector3f finalPosMin;
                    Vector3f finalPosMax;

                    finalPosMin = new Vector3f(quad.gridPos.x / this.pixelSize, 0, quad.gridPos.y / this.pixelSize );
                    finalPosMax = new Vector3f((quad.gridPos.x + 1) / this.pixelSize, 0, quad.gridPos.y / this.pixelSize );
                    quad.renderColorGrid(stack, finalPosMin, finalPosMax, tickdelta, provider);
                }
            }
        }
    }
    public Vector2i posFromPoint(BlockHitResult result, Vec3d sourceBlock, VoxelShape shape) {
        Vec3d sourcePos = new Vec3d( result.getPos().x, result.getPos().z, result.getPos().y);
        Vec3d sourceBpos = new Vec3d(result.getBlockPos().getX(), result.getBlockPos().getZ(), result.getBlockPos().getY());
        Vec3d truePoint = MathUtil.absVec3d(sourcePos.subtract(sourceBpos));
        Direction.Axis face = result.getSide().getAxis();
        Vector2i finalGridPos = new Vector2i(0, 0);

      /*  switch (face) {
            case X -> finalGridPos = new Vector2i(0, (int) Math.ceil(truePoint.x * 16) , (int) Math.ceil(Math.ceil(truePoint.z * 16) / 16));
            case Y -> finalGridPos = new Vector2i((int) Math.ceil(truePoint.x * 16), 0, (int) Math.ceil(Math.ceil(truePoint.z * 16) / 16));
            case Z -> finalGridPos = new Vector2i((int) Math.ceil(truePoint.x * 16), (int) Math.ceil(Math.ceil(truePoint.z * 16) / 16), 0);
            default -> throw new RuntimeException("Null Direction!");
        }*/
        finalGridPos = new Vector2i((int) Math.ceil(truePoint.x * this.pixelSize), (int) Math.ceil(truePoint.z * this.pixelSize));
        return finalGridPos;

    }

    public Vector2i posFromPointBlockEntity(Vector2f pos) {
        return new Vector2i((int) Math.ceil(pos.getX() * 16), (int) Math.ceil(pos.getY() * 16));

    }
    public float offsetVal(Vec3d point, Vec3d sourcePoint, BlockPos sourceBlock, VoxelShape shape) {
        Vec3d truePoint = MathUtil.absVec3d(point.subtract(MathUtil.bpToVec3d(sourceBlock)));
        Direction.Axis face = Objects.requireNonNull(shape.raycast(point, sourcePoint, sourceBlock)).getSide().getAxis();
        double finalGridPos;
        switch (face) {
            case X -> finalGridPos = Math.abs(truePoint.x);
            case Y -> finalGridPos = Math.abs(truePoint.y);
            case Z -> finalGridPos = Math.abs(truePoint.z);
            default -> throw new RuntimeException("Null Direction!");
        }
        return (float) finalGridPos;
    }

    public void tick() {
        for (SpecialQuad quad : this.quads) {
            quad.tick();

        }
    }

    public static void tickAll() {
        for (QuadGrid grid : QuadGrid.grids) {
            grid.tick();

        }
        if (grids.size() == 0) {
            grids.add(new QuadGrid(16));
        }

    }

    public static void debugTick() {
        for (QuadGrid grid : QuadGrid.grids) {
            System.out.println(grid.quads);

        }
    }

    public void addQuadFromPos(Vec3d pos, Vector3f rgb, int lifespan) {

        this.quads.add(new SpecialQuad(posFromPointBlockEntity(new Vector2f((float) pos.x, (float) pos.z)), rgb, this, lifespan, Direction.Axis.Y));

    }
    public void addQuadFromRayCast(Vector3f rgb, int lifespan) {

        try {
            assert MinecraftClient.getInstance().world != null;
            BlockHitResult hitResult = MiscUtil.getBlockHitResult();
            VoxelShape hitShape = MiscUtil.getBlockHitResultShape(MinecraftClient.getInstance().world);
            assert MinecraftClient.getInstance().player != null;
            this.quads.add(new SpecialQuad(this.posFromPoint(hitResult, MinecraftClient.getInstance().cameraEntity.getPos(), hitShape), rgb, this, lifespan, hitResult.getSide().getAxis()));
        } catch (Exception ignored) {

        }

    }




}
