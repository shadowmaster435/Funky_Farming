package shadowmaster435.funkyfarming.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import shadowmaster435.funkyfarming.block.entity.TesterBlockEntity;

import java.util.ArrayList;

public class TesterBlock extends BlockWithEntity {

    public ArrayList<Integer> poslist = new ArrayList<>();

    public static DirectionProperty DIRECTION;

    public TesterBlock(Settings settings) {
        super(settings);
    }
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TesterBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

}
