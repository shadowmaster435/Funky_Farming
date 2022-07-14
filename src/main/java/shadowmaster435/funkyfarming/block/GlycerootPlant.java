package shadowmaster435.funkyfarming.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class GlycerootPlant extends CropBase {
    public GlycerootPlant(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.getAge(state) < 3 && this.getAge(state) != 0) {
            if (random.nextInt(3) != 0) {
                if (world.getBaseLightLevel(pos, 0) >= 9) {
                    int i = this.getAge(state);
                    world.setBlockState(pos, state.with(AGE, i + 1), 2);
                }
            }
        }
    }

    public VoxelShape stage0(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 0.1875, 0.625));

        return shape;
    }
    public VoxelShape stage3(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0, 0, 1, 0.5625, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.5625, 0.0625, 0.9375, 0.6875, 0.9375));

        return shape;
    }

    public VoxelShape stage2(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0.375, 0.375, 0.625, 0.4375, 0.625));

        return shape;
    }

    public VoxelShape stage1(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 0.25, 0.625));

        return shape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (this.getAge(state)) {
            case 1 -> { return stage1(); }
            case 2 -> { return stage2(); }
            case 3 -> { return stage3(); }
            default -> { return stage0(); }
        }
    }

}
