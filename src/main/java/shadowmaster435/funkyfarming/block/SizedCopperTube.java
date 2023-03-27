package shadowmaster435.funkyfarming.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.util.FastFunction;
import shadowmaster435.funkyfarming.util.MiscUtil;
import shadowmaster435.funkyfarming.util.TubeSide;

import java.util.Objects;

public class SizedCopperTube extends Block {


    public static final EnumProperty<TubeSide> NORTH;
    public static final EnumProperty<TubeSide> SOUTH;
    public static final EnumProperty<TubeSide> EAST;
    public static final EnumProperty<TubeSide> WEST;
    public static final EnumProperty<TubeSide> UP;
    public static final EnumProperty<TubeSide> DOWN;





    public SizedCopperTube(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(NORTH, TubeSide.NONE)
                .with(SOUTH, TubeSide.NONE)
                .with(EAST, TubeSide.NONE)
                .with(WEST, TubeSide.NONE)
                .with(UP, TubeSide.NONE)
                .with(DOWN, TubeSide.NONE)
        );
    }

    static {
        NORTH = EnumProperty.of("north", TubeSide.class);
        SOUTH = EnumProperty.of("south", TubeSide.class);
        EAST = EnumProperty.of("east", TubeSide.class);
        WEST = EnumProperty.of("west", TubeSide.class);
        UP = EnumProperty.of("up", TubeSide.class);
        DOWN = EnumProperty.of("down", TubeSide.class);
    }


    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> stateManager) {
        stateManager.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {

        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = this.getDefaultState();
        Direction direction = ctx.getSide().getOpposite();
        ItemStack held = Objects.requireNonNull(ctx.getPlayer()).getStackInHand(ctx.getHand());
        boolean isTubes = world.getBlockState(pos.offset(direction)).getBlock() == FFBlocks.MEDUIM_COPPER_TUBE && this == FFBlocks.SMALL_COPPER_TUBE;
        BlockState offsetState = world.getBlockState(pos.offset(direction));
        try {
            TubeSide tubeSide = offsetState.get(getProperty(direction.getOpposite()));
            boolean isNone = tubeSide == TubeSide.NONE;

            FastFunction function = new FastFunction(pos).addBool(isTubes).addBool(isNone);

            if (isTubes) {



                try {
                    if (direction != Direction.NORTH && direction != Direction.SOUTH) {

                        if (isNone) {
                            function.executeIfTesterTrue().setBlock(offsetState.with(getProperty(direction.getOpposite()), TubeSide.byName(this.getSize(held))));
                        }
                    }
                    else {
                        if (isNone) {
                            function.executeIfTesterTrue().setBlock(offsetState.with(getProperty(direction), TubeSide.byName(this.getSize(held))));
                        }
                    }

                } catch (Exception e) {
                    function.printError(e);
                }

            }
            if (!this.onAxis(direction)) {
                boolean isTubes2 = offsetState.getBlock().equals(this) && this.isntBigger(direction, state, offsetState) && !offsetState.getBlock().equals(FFBlocks.COPPER_TUBE.getDefaultState().getBlock());

                if (isTubes2) {
                    if (direction != Direction.NORTH && direction != Direction.SOUTH) {
                        world.setBlockState(pos.offset(direction), offsetState.with(getProperty(direction.getOpposite()), TubeSide.byName(this.getSize(held))));

                    } else {
                        world.setBlockState(pos.offset(direction), offsetState.with(getProperty(direction), TubeSide.byName(this.getSize(held))));

                    }
                }

            }
            state = state.with(getProperty(direction), TubeSide.byName(this.getSize(held)));
            state = state.with(getProperty(direction.getOpposite()), TubeSide.byName(this.getSize(held)));

        } catch (Exception ignored) {

        }

        return state;
    }

    public String getSize(ItemStack stack) {
        String result = "";

        if (stack.getItem() == FFBlocks.MEDUIM_COPPER_TUBE.asItem()) {
            result = "medium";
        }
        if (stack.getItem() == FFBlocks.SMALL_COPPER_TUBE.asItem()) {
            result = "small";
        }
        return result;
    }

    public boolean onAxis(Direction direction) {
        return !this.getDefaultState().get(getProperty(direction)).equals(TubeSide.NONE) && !this.getDefaultState().get(getProperty(direction.getOpposite())).equals(TubeSide.NONE);
    }

    public boolean isntBigger(Direction direction, BlockState origin, BlockState offset) {
        int originint = 0;
        int offsetint = 0;
        switch (origin.get(getProperty(direction))) {
            case MEDIUM -> originint = 2;
            case SMALL -> originint = 1;
        }
        switch (offset.get(getProperty(direction))) {
            case MEDIUM -> offsetint = 2;
            case SMALL -> offsetint = 1;
        }
        return originint <= offsetint;
    }
    public static EnumProperty<TubeSide> getProperty(Direction facing) {

        return switch (facing) {
            case UP -> SizedCopperTube.UP;
            case DOWN -> SizedCopperTube.DOWN;
            case EAST -> SizedCopperTube.EAST;
            case WEST -> SizedCopperTube.WEST;
            case NORTH -> SizedCopperTube.NORTH;
            case SOUTH -> SizedCopperTube.SOUTH;
        };
    }

    public static Direction fromPropery(EnumProperty<TubeSide> sideEnumProperty) {
        Direction direction = null;
        if (Objects.equals(sideEnumProperty.getName(), "up")) {
            direction = Direction.UP;
        }
        if (Objects.equals(sideEnumProperty.getName(), "down")) {
            direction = Direction.DOWN;
        }
        if (Objects.equals(sideEnumProperty.getName(), "north")) {
            direction = Direction.NORTH;
        }
        if (Objects.equals(sideEnumProperty.getName(), "south")) {
            direction = Direction.SOUTH;
        }
        if (Objects.equals(sideEnumProperty.getName(), "east")) {
            direction = Direction.EAST;
        }
        if (Objects.equals(sideEnumProperty.getName(), "west")) {
            direction = Direction.WEST;
        }
        return direction;
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();

        return this.makeShape(state);
    }

    public VoxelShape makeShape(BlockState state) {
        VoxelShape shape = VoxelShapes.empty();

      //  VoxelShape largeLimb = VoxelShapes.cuboid(0.125, 0.875, 0.125, 0.875, 1, 0.875);
        VoxelShape mediumLimb = VoxelShapes.cuboid(0.25, 0.6875, 0.25, 0.75, 1, 0.75);
        VoxelShape smallLimb = VoxelShapes.cuboid(0.3125, 0.6875, 0.3125, 0.6875, 1, 0.6875);

        for (Direction dir : Direction.values()) {

            if (Objects.equals(state.get(getProperty(dir)).asString(), "medium")) {
                shape = VoxelShapes.union(shape, MiscUtil.rotate(mediumLimb, dir));
            }


            if (Objects.equals(state.get(getProperty(dir)).asString(), "small")) {
                shape = VoxelShapes.union(shape, MiscUtil.rotate(smallLimb, dir));
            }

        }
        return shape;
    }


}
