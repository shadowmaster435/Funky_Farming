package shadowmaster435.funkyfarming.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.util.FuseSide;

public class GlycerootBlock extends Block {
    public GlycerootBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(IGNITED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.0625, 0, 1, 0.8125, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.8125, 0.0625, 0.9375, 0.9375, 0.9375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375));

        return shape;
    }

    public static BooleanProperty IGNITED;

    static {
        IGNITED = BooleanProperty.of("ignited");
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.scheduleBlockTick(pos, this, 1);
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(IGNITED);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBlockState(pos).get(IGNITED))  {
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 4, World.ExplosionSourceType.BLOCK);

            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }

        world.scheduleBlockTick(pos, this, 1);

        super.scheduledTick(state, world, pos, random);
    }
}
