package shadowmaster435.funkyfarming.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import shadowmaster435.funkyfarming.block.entity.EnergyVoidEntity;
import shadowmaster435.funkyfarming.gravity.GravitySphere;
import shadowmaster435.funkyfarming.init.FFBlocks;

public class EnergyVoid extends BlockWithEntity {

    public EnergyVoid(Settings settings) {
        super(settings);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        EnergyVoidEntity entity = (EnergyVoidEntity) world.getBlockEntity(pos);
        assert entity != null;
        GravitySphere.spheres.remove(entity.getSphere());
        super.onBreak(world, pos, state, player);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EnergyVoidEntity(pos, state);
    }
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {

        return checkType(type, FFBlocks.ENERGY_VOID_ENTITY, EnergyVoidEntity::tick);
    }
}
