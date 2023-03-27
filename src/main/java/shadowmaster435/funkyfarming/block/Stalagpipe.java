package shadowmaster435.funkyfarming.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import shadowmaster435.funkyfarming.init.FFParticles;

public class Stalagpipe extends Block {


    public static BooleanProperty OUTLET;

    public Stalagpipe(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(OUTLET, false));

    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.getBlockState(pos.offset(Direction.UP)).getBlock() == this && world.getBlockState(pos.offset(Direction.UP)).get(OUTLET)) {
            world.setBlockState(pos.offset(Direction.UP), this.getDefaultState().with(OUTLET, false));
        }

    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if ((world.getBlockState(pos).get(OUTLET) || world.getBlockState(pos.offset(Direction.DOWN)).getBlock() == Blocks.AIR) && random.nextFloat() > 0.75) {
            if (world.getBlockState(pos.offset(Direction.DOWN)).getBlock() == Blocks.AIR && !(world.getBlockState(pos).get(OUTLET))) {
                world.addParticle(FFParticles.STALAGDRIP,pos.getX() + MathHelper.lerp(Math.random(), 0.40625f, 0.59375f), pos.getY(),pos.getZ() +  MathHelper.lerp(Math.random(),0.40625f, 0.59375f), 0, 0, 0);

            } else {
                world.addParticle(FFParticles.STALAGDRIP,pos.getX() + MathHelper.lerp(Math.random(), 0.40625f, 0.59375f), pos.getY() + 0.42,pos.getZ() +  MathHelper.lerp(Math.random(),0.40625f, 0.59375f), 0, 0, 0);

            }
        }
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {

    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return (state.get(OUTLET)) ? outletShape() : normalShape();
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        if (ctx.getWorld().getBlockState(ctx.getBlockPos().offset(Direction.DOWN)).getBlock() != this ) {
            return this.getDefaultState().with(OUTLET, true);
        } else {
            return this.getDefaultState().with(OUTLET, false);
        }
    }

    static {

        OUTLET = BooleanProperty.of("outlet");

    }
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OUTLET);
    }
    public VoxelShape normalShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 1, 0.625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.34375, 0.46875, 0.34375, 0.65625, 0.53125, 0.65625));

        return shape;
    }
    public VoxelShape outletShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0.5, 0.375, 0.625, 1, 0.625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.34375, 0.4375, 0.34375, 0.65625, 0.5, 0.65625));

        return shape;
    }

}
