package shadowmaster435.funkyfarming.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;
import shadowmaster435.funkyfarming.block.entity.GeneratorEntity;
import shadowmaster435.funkyfarming.block.entity.WorkTableEntity;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.util.FuseSide;
import shadowmaster435.funkyfarming.util.MiscUtil;
import shadowmaster435.funkyfarming.util.SideProperty;
import shadowmaster435.funkyfarming.util.WorkTableSide;

public class WorkTable extends BlockWithEntity {

    public static final EnumProperty<WorkTableSide> SIDE;

    public static final BooleanProperty ANIMATING;


    public WorkTable(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(SIDE, WorkTableSide.LEFT).with(ANIMATING, true));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            //This will call the createScreenHandlerFactory method from BlockWithEntity, which will return our blockEntity cast to
            //a namedScreenHandlerFactory. If your block class does not extend BlockWithEntity, it needs to implement createScreenHandlerFactory.
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

            if (screenHandlerFactory != null && !player.isSneaking()) {
                //With this call the server will request the client to open the appropriate Screenhandler
                player.openHandledScreen(screenHandlerFactory);
            }
        }


        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing());
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {

        return world.getBlockState(pos.offset(this.rotated(state.get(Properties.HORIZONTAL_FACING), state, 1))).isReplaceable();
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BlockPos offsetPos = pos.offset(this.rotated(state.get(Properties.HORIZONTAL_FACING), state, 1));
        BlockState offsetState = world.getBlockState(offsetPos);
        if (offsetState.isReplaceable()) {
            world.setBlockState(offsetPos, this.getDefaultState().with(SIDE, WorkTableSide.RIGHT).with(Properties.HORIZONTAL_FACING, state.get(Properties.HORIZONTAL_FACING)).with(ANIMATING, true));
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.getBlockState(pos.offset(this.rotated(state.get(Properties.HORIZONTAL_FACING), state, 1))).getBlock() == FFBlocks.WORKTABLE) {
            world.breakBlock(pos.offset(this.rotated(state.get(Properties.HORIZONTAL_FACING), state, 1)), false);
        }
    }

    static {
        SIDE = EnumProperty.of("side", WorkTableSide.class);
        ANIMATING = BooleanProperty.of("animating");

    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(SIDE, Properties.HORIZONTAL_FACING, ANIMATING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return (state.get(SIDE) == WorkTableSide.LEFT) ? this.blShape(state) : this.brShape(state);
    }

    public VoxelShape blShape(BlockState state){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.03125, 0, 0.78125, 0.21875, 0.0625, 0.96875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0.8125, 0.1875, 0.875, 0.9375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.03125, 0, 0.03125, 0.21875, 0.0625, 0.21875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.375, 0.1875, 0.1875, 0.5, 0.8125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.1875, 0.875, 0.1875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.875, 0, 1, 1, 1));

        return MiscUtil.rotateHorizontal(shape, state.get(Properties.HORIZONTAL_FACING));
    }

    public VoxelShape brShape(BlockState state){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.03125, 0, 0.78125, 0.21875, 0.0625, 0.96875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0.8125, 0.1875, 0.875, 0.9375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.03125, 0, 0.03125, 0.21875, 0.0625, 0.21875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.375, 0.1875, 0.1875, 0.5, 0.8125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.1875, 0.875, 0.1875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.875, 0, 1, 1, 1));
        if (!state.get(ANIMATING) && !state.get(SIDE).equals(WorkTableSide.RIGHT)) {
            return MiscUtil.rotateHorizontal(shape, this.rotated(state.get(Properties.HORIZONTAL_FACING), state, 2));

        } else {
            return VoxelShapes.empty();
        }
    }
    public Direction result;

    public Direction rotated(Direction direction, BlockState state, int times) {
        if (times > 0) {
            switch (direction) {
                case NORTH -> result = Direction.EAST;
                case EAST -> result = Direction.SOUTH;
                case SOUTH -> result = Direction.WEST;
                case WEST -> result = Direction.NORTH;
            }
            this.rotated(result, state, times - 1);
        }
        return result;
    }
    public static Direction resultStatic;
    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
    public static Direction rotatedStatic(Direction direction, BlockState state, int times) {
        if (times > 0) {
            switch (direction) {
                case NORTH -> resultStatic = Direction.EAST;
                case EAST -> resultStatic = Direction.SOUTH;
                case SOUTH -> resultStatic = Direction.WEST;
                case WEST -> resultStatic = Direction.NORTH;
            }
            rotatedStatic(resultStatic, state, times - 1);
        }
        return resultStatic;
    }
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WorkTableEntity(pos, state);
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {

        return (state.get(ANIMATING)) ? BlockRenderType.INVISIBLE : BlockRenderType.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {

        return checkType(type, FFBlocks.WORKTABLE_ENTITY, WorkTableEntity::tick);
    }
}
