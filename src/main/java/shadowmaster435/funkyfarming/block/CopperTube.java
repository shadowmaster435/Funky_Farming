package shadowmaster435.funkyfarming.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CopperTube extends Block {




    public static final EnumProperty<Direction.Axis> AXIS;

    public CopperTube(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()

                .with(AXIS, Direction.Axis.Y));
    }

    static {

        AXIS = Properties.AXIS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(AXIS);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        Direction direction = ctx.getSide();

        return this.getDefaultState().with(AXIS, Objects.requireNonNull(Direction.byName(direction.getName())).getAxis());
    }
}
