package shadowmaster435.funkyfarming.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MiscUtil {

    public static BlockHitResult getBlockHitResult(World world, LivingEntity placer) {
        return world.raycast(new RaycastContext(placer.getEyePos(), placer.raycast(4, MinecraftClient.getInstance().getTickDelta(), false).getPos(), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, placer));
    }

    public static boolean dirPositive(Direction direction) {
        return direction == Direction.UP || direction == Direction.SOUTH || direction == Direction.EAST;
    }

    public static boolean IsBlockAtPos(Block block, BlockPos pos, World world) {
        return world.getBlockState(pos).getBlock() == block;
    }

    public static RaycastContext getRaycastContext(World world, LivingEntity placer) {
        return new RaycastContext(placer.getEyePos(), placer.raycast(4, MinecraftClient.getInstance().getTickDelta(), false).getPos(), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, placer);
    }

    public static BlockHitResult getBlockHitResult() {
        BlockHitResult result = (BlockHitResult) MinecraftClient.getInstance().crosshairTarget;
        return result;

    }

    public static VoxelShape getBlockHitResultShape(World world) {
        BlockHitResult result = (BlockHitResult) MinecraftClient.getInstance().crosshairTarget;

        assert result != null;
        return world.getBlockState(result.getBlockPos()).getOutlineShape(world, vec3dToBP(result.getPos()));

    }
    public static BlockPos vec3dToBP(Vec3d vec3d) {
        return new BlockPos((int) vec3d.x, (int) vec3d.y, (int) vec3d.z);
    }

    public static VoxelShape rotate(VoxelShape shape, Direction direction) {
        VoxelShape result = VoxelShapes.empty();
        for (Box box : shape.getBoundingBoxes()) {
            Vec3d min = new Vec3d(box.minX, box.minY, box.minZ);
            Vec3d max = new Vec3d(box.maxX, box.maxY, box.maxZ);
            switch (direction) {
                case UP -> result = VoxelShapes.union(result, VoxelShapes.cuboid(new Box(min.x, min.y, min.z, max.x, max.y, max.z)));
                case DOWN -> result = VoxelShapes.union(result, VoxelShapes.cuboid(new Box(min.x, 0, min.z, max.x, min.y, max.z)));
                case NORTH -> result = VoxelShapes.union(result, VoxelShapes.cuboid(new Box(min.x, min.z, min.y, max.x, max.z, max.y)));
                case SOUTH -> result = VoxelShapes.union(result, VoxelShapes.cuboid(new Box(min.x, max.z, 0, max.x, min.z, min.y)));
                case EAST -> result = VoxelShapes.union(result, VoxelShapes.cuboid(new Box(min.y, min.x, min.z, max.y, max.x, max.z)));
                case WEST -> result = VoxelShapes.union(result, VoxelShapes.cuboid(new Box(0, max.x, min.z, min.y, min.x, max.z)));
            }
        }
        return result;
    }
    public static VoxelShape rotateHorizontal(VoxelShape shape, Direction direction) {
        VoxelShape result = VoxelShapes.empty();
        for (Box box : shape.getBoundingBoxes()) {
            Vec3d min = new Vec3d(box.minX, box.minY, box.minZ);
            Vec3d max = new Vec3d(box.maxX, box.maxY, box.maxZ);
            switch (direction) {

                case SOUTH -> result = VoxelShapes.union(result, VoxelShapes.cuboid(new Box(1 -min.x, min.y, min.z, 1 - max.x, max.y, max.z)));
                case NORTH -> result = VoxelShapes.union(result, VoxelShapes.cuboid(new Box(min.x, min.y,min.z, max.x, max.y, max.z)));
                case EAST -> result = VoxelShapes.union(result, VoxelShapes.cuboid(new Box(min.z, min.y, min.x, max.z, max.y, max.x)));
                case WEST -> result = VoxelShapes.union(result, VoxelShapes.cuboid(new Box( min.z, min.y,1 - min.x, max.z, max.y,1 -  max.x)));

            }
        }
        return result;
    }
    public static VoxelShape rotate(VoxelShape shape, Direction.Axis axis) {
        VoxelShape result = VoxelShapes.empty();
        for (Box box : shape.getBoundingBoxes()) {
            Vec3d min = new Vec3d(box.minX, box.minY, box.minZ);
            Vec3d max = new Vec3d(box.maxX, box.maxY, box.maxZ);
            switch (axis) {
                case Y -> result = VoxelShapes.union(result, VoxelShapes.cuboid(new Box(min.x, min.y, min.z, max.x, max.y, max.z)));
                case Z -> result = VoxelShapes.union(result, VoxelShapes.cuboid(new Box(min.x, min.z, min.y, max.x, max.z, max.y)));
                case X -> result = VoxelShapes.union(result, VoxelShapes.cuboid(new Box(min.y, min.x, min.z, max.y, max.x, max.z)));
            }
        }
        return result;
    }
    public static int RandomIntseed(Random random, int mult) {
        return random.nextInt() * mult;
    }

    public static Vec3d RayLerpPos(double delta, World world, Vec3d start, Vec3d end) {
        double x = MathHelper.lerp(delta, start.x, end.x);
        double y = MathHelper.lerp(delta, start.y, end.y);
        double z = MathHelper.lerp(delta, start.z, end.z);
        return new Vec3d(x, y, z);
    }
    public static Vector3f RayLerpPosF(float delta, World world, Vector3f start, Vector3f end) {
        float x = (float) MathHelper.lerp(delta, start.x, end.x);
        float y = (float) MathHelper.lerp(delta, start.y, end.y);
        float z = (float) MathHelper.lerp(delta, start.z, end.z);
        return new Vector3f(x, y, z);
    }

    public static int RandomInt(int mult) {
        return (int) (Math.random() * mult);
    }

    public static double RandomDoubleSeed(Random random, double mult) {
        return random.nextDouble() * mult;
    }

    public static double RandomDouble(double mult) {
        return Math.random() * mult;
    }

    public static float RandomFloatSeed(Random random, float mult) {
        return random.nextFloat() * mult;
    }

    public static float RandomFloat(float mult) {
        return (float) (Math.random()) * mult;
    }

    public static <T> List<T> Arraytolist(T[] array) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, array);
        return list;
    }
    public static List<Integer> IntArraytolist(int[] array) {
        List<Integer> list = new ArrayList<>();
        System.out.println(Arrays.toString(array));
        for (int i : array) {
            list.add(i);
        }
        return list;
    }
}