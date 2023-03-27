package shadowmaster435.funkyfarming.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import shadowmaster435.funkyfarming.block.entity.ItemPylonEntity;
import shadowmaster435.funkyfarming.init.FFBlocks;
public class ItemPylon extends BlockWithEntity {


    public static DirectionProperty DIRECTION;

    public ItemPylon(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(DIRECTION, Direction.DOWN));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(DIRECTION);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(DIRECTION, ctx.getSide());
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ItemPylonEntity(pos, state);
    }

    public VoxelShape makeShape(Direction direction){
        VoxelShape down = VoxelShapes.empty();
        down = VoxelShapes.union(down, VoxelShapes.cuboid(0.375, 0.75, 0.375, 0.625, 1, 0.625));
        down = VoxelShapes.union(down, VoxelShapes.cuboid(0.40625, 0.0625, 0.40625, 0.59375, 0.75, 0.59375));
        down = VoxelShapes.union(down, VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 0.0625, 0.625));
        VoxelShape up = VoxelShapes.empty();
        up = VoxelShapes.union(up, VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 0.25, 0.625));
        up = VoxelShapes.union(up, VoxelShapes.cuboid(0.40625, 0.25, 0.40625, 0.59375, 0.9375, 0.59375));
        up = VoxelShapes.union(up, VoxelShapes.cuboid(0.375, 0.9375, 0.375, 0.625, 1, 0.625));

        VoxelShape south = VoxelShapes.empty();
        south = VoxelShapes.union(south, VoxelShapes.cuboid(0.375, 0.375, 0.75, 0.625, 0.625, 1));
        south = VoxelShapes.union(south, VoxelShapes.cuboid(0.40625, 0.40625, 0.0625, 0.59375, 0.59375, 0.75));
        south = VoxelShapes.union(south, VoxelShapes.cuboid(0.375, 0.375, 0, 0.625, 0.625, 0.0625));

        VoxelShape north = VoxelShapes.empty();
        north = VoxelShapes.union(north, VoxelShapes.cuboid(0.375, 0.375, 0, 0.625, 0.625, 0.25));
        north = VoxelShapes.union(north, VoxelShapes.cuboid(0.40625, 0.40625, 0.25, 0.59375, 0.59375, 0.9375));
        north = VoxelShapes.union(north, VoxelShapes.cuboid(0.375, 0.375, 0.9375, 0.625, 0.625, 1));

        VoxelShape west = VoxelShapes.empty();
        west = VoxelShapes.union(west, VoxelShapes.cuboid(0, 0.375, 0.375, 0.25, 0.625, 0.625));
        west = VoxelShapes.union(west, VoxelShapes.cuboid(0.25, 0.40625, 0.40625, 0.9375, 0.59375, 0.59375));
        west = VoxelShapes.union(west, VoxelShapes.cuboid(0.9375, 0.375, 0.375, 1, 0.625, 0.625));

        VoxelShape east = VoxelShapes.empty();
        east = VoxelShapes.union(east, VoxelShapes.cuboid(0.75, 0.375, 0.375, 1, 0.625, 0.625));
        east = VoxelShapes.union(east, VoxelShapes.cuboid(0.0625, 0.40625, 0.40625, 0.75, 0.59375, 0.59375));
        east = VoxelShapes.union(east, VoxelShapes.cuboid(0, 0.375, 0.375, 0.0625, 0.625, 0.625));

        switch (direction) {
            case UP -> {
                return down;
            }
            case NORTH -> {
                return north;
            }
            case SOUTH -> {
                return south;
            }
            case EAST -> {
                return east;
            }
            case WEST -> {
                return west;
            }
            default -> {
                return up;
            }
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.makeShape(state.get(DIRECTION));
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {

        return checkType(type, FFBlocks.ITEM_PYLON_ENTITY, ItemPylonEntity::tick);
    }
    static {
        DIRECTION = DirectionProperty.of("direction");
    }
}
