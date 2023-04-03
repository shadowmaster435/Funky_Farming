package shadowmaster435.funkyfarming.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.util.ImplementedInventory;

public class InfusionPedestalEntity extends BlockEntity implements ImplementedInventory {

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public BlockPos infuserPos;

    public boolean animating = false;

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }
    public int animtimer = 1;

    public InfusionPedestalEntity(BlockPos pos, BlockState state) {
        super(FFBlocks.PEDESTAL_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, InfusionPedestalEntity be) {
        try {
            if (world.getBlockEntity(be.infuserPos) instanceof InfuserEntity entity) {
                be.animtimer = entity.animtimer;
                be.setAnimating(entity.animating);
            }
        } catch (Exception ignored) {
        }
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("animtimer", animtimer);
        Inventories.readNbt(nbt, items);

        if (this.infuserPos != null) {
            nbt.putInt("infuserx", infuserPos.getX());
            nbt.putInt("infusery", infuserPos.getY());
            nbt.putInt("infuserz", infuserPos.getZ());
        }
        super.writeNbt(nbt);
    }
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.writeNbt(nbt, items);
        if (nbt.get("infuserx") != null && nbt.get("infusery") != null && nbt.get("infuserz") != null) {
            this.infuserPos = new BlockPos(nbt.getInt("infuserx"), nbt.getInt("infusery"), nbt.getInt("infuserz"));
        }
        animtimer = createNbt().getInt("animtimer");
    }

    public void setAnimating(boolean animating) {
        this.animating = animating;
    }
}
