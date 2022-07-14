package shadowmaster435.funkyfarming.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import shadowmaster435.funkyfarming.block.VoracePlant;
import shadowmaster435.funkyfarming.init.FFBlocks;

public class RiftRushEntity extends BlockEntity {
    public int fedval = 0;

    public RiftRushEntity(BlockPos pos, BlockState state) {
        super(FFBlocks.VORACE_PLANT_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("fedval", VoracePlant.fedval);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        VoracePlant.fedval = nbt.getInt("fedval");

        super.readNbt(nbt);
    }
}
