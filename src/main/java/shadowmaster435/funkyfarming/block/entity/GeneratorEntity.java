package shadowmaster435.funkyfarming.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import shadowmaster435.funkyfarming.gui.GeneratorScreenHandler;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.util.ImplementedInventory;
import shadowmaster435.funkyfarming.util.MiscUtil;

public class GeneratorEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private int progressBar;

    private int energy = 0;


    public GeneratorEntity(BlockPos pos, BlockState state) {
        super(FFBlocks.GENERATOR_ENTITY, pos, state);
    }
    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            if (index == 0) {
                return progressBar;
            } else {
                return energy;
            }
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                progressBar = value;
            } else  {
                energy = value;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };


    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());

    }

    public PropertyDelegate getPropertyDelegate() {
        return propertyDelegate;
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {

        return new GeneratorScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
        this.energy = nbt.getInt("energy");
    }

    @Override
    public void
    writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putInt("energy", this.energy);
        super.writeNbt(nbt);

    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
    public static void tick(World world, BlockPos pos, BlockState state, GeneratorEntity be) {
        if(!world.isClient && world.getBlockState(pos.offset(Direction.Axis.Y, -1)).getBlock() == FFBlocks.MECHALILLY) {
            if (be.propertyDelegate.get(0) < 31) {
                be.propertyDelegate.set(0, be.propertyDelegate.get(0) + 1);

            } else {

                be.propertyDelegate.set(0, 0);
            }
            if (be.propertyDelegate.get(1) <= 10000) {
                if (be.getPropertyDelegate().get(0) >= 31) {
                    be.propertyDelegate.set(1, Math.min(be.propertyDelegate.get(1) + 300, 10000));
                }
            } else  {
                be.propertyDelegate.set(1, 10000);
            }
        }
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(pos);
    }
}
