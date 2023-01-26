package shadowmaster435.funkyfarming.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.util.PillarProperty;

public class GatewayAmp extends Block {

    public static final BooleanProperty PEARLED;
    public static final BooleanProperty ACTIVE;

    public static final EnumProperty<PillarProperty> PART;




    public GatewayAmp(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(ACTIVE, false)
                .with(PEARLED, false)
                .with(PART, PillarProperty.CENTER));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        int successCount = 0;
        for(int i = -5; i < 5; ++i) {
            if (world.getBlockState(pos.add(0, i, 0)).getBlock() == this) {
                ++successCount;

                world.breakBlock(pos.add(0, i, 0), true, null);
            }
        }
        ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(FFBlocks.PEARLSTONE, successCount - 1));

        ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(FFBlocks.PEARLSTONE_PILLAR, 1));

        super.onBreak(world, pos, state, player);
    }


    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {

        for(int i = -5; i < 5; ++i) {
            if (world.getBlockState(pos.add(0, i, 0)).getBlock() == this) {
                world.breakBlock(pos.add(0, i, 0), true, null);
            }
        }
        if (this.shouldDropItemsOnExplosion(explosion)) {
            if (world.getBlockState(pos).get(PEARLED)) {
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(FFBlocks.PEARLSTONE_PILLAR, 1));
            } else {
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(FFBlocks.PEARLSTONE, 1));
            }
        }
        super.onDestroyedByExplosion(world, pos, explosion);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE, PEARLED, PART);
    }

    public VoxelShape makeBottomShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0, 0, 1, 0.1875, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.1875, 0.125, 0.875, 1, 0.875));

        return shape;
    }

    public VoxelShape makeTopShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.8125, 0, 1, 1, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 0.8125, 0.875));

        return shape;
    }

    public VoxelShape makeCenterShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 1, 0.875));

        return shape;
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();

        if (state.get(PEARLED) || state.get(PART) == PillarProperty.CENTER) {
            shape = this.makeCenterShape();
        }
        if (state.get(PART) == PillarProperty.TOP) {
            shape = this.makeTopShape();
        }
        if (state.get(PART) == PillarProperty.BOTTOM) {
            shape = this.makeBottomShape();
        }
        return shape;
    }

    static {
        ACTIVE = BooleanProperty.of("active");
        PEARLED = BooleanProperty.of("pearled");
        PART = EnumProperty.of("part", PillarProperty.class);

    }
}
