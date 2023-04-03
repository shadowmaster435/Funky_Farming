package shadowmaster435.funkyfarming.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import shadowmaster435.funkyfarming.block.entity.InfuserEntity;
import shadowmaster435.funkyfarming.block.entity.TesterBlockEntity;
import shadowmaster435.funkyfarming.init.FFBlocks;

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
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, FFBlocks.TESTER_BLOCK_ENTITY, TesterBlockEntity::tick);
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

}
