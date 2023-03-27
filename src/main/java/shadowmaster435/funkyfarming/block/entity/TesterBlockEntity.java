package shadowmaster435.funkyfarming.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import shadowmaster435.funkyfarming.block.VoracePlant;
import shadowmaster435.funkyfarming.init.FFBlocks;

public class TesterBlockEntity extends BlockEntity {


    public TesterBlockEntity(BlockPos pos, BlockState state) {
        super(FFBlocks.TESTER_BLOCK_ENTITY, pos, state);
    }


}
