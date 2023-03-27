package shadowmaster435.funkyfarming.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import shadowmaster435.funkyfarming.block.WorkTable;
import shadowmaster435.funkyfarming.gui.WorktableScreenHandler;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.util.ImplementedInventory;
import shadowmaster435.funkyfarming.util.WorkTableSide;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class WorkTableEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, GeoBlockEntity {
    public PacketByteBuf buf;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private AnimationController<WorkTableEntity> controller;

    private static final RawAnimation CLOSED = RawAnimation.begin().thenPlayAndHold("closed");
    private static final RawAnimation OPENING = RawAnimation.begin().thenPlay("opening");

    private static final RawAnimation UNANIMATED = RawAnimation.begin().thenPlay("unanimated");


    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(12, ItemStack.EMPTY);
    public WorkTableEntity(BlockPos pos, BlockState state) {
        super(FFBlocks.WORKTABLE_ENTITY, pos, state);

    }


    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {

        packetByteBuf.writeBlockPos(pos);
        this.buf = packetByteBuf;

    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());

    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {

        return new WorktableScreenHandler(syncId, playerInventory, this, this.buf);
    }
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
    }
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    @Override
    public void
    writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, this.inventory);
        super.writeNbt(nbt);

    }

    public Direction getDirFromState(BlockState state) {
        return state.get(Properties.HORIZONTAL_FACING);
    }


    public Direction rotated(Direction direction) {
        Direction result = null;
        switch (direction) {
            case NORTH -> result = Direction.EAST;
            case EAST -> result = Direction.SOUTH;
            case SOUTH -> result = Direction.WEST;
            case WEST -> result = Direction.NORTH;
        }
        return result;
    }

    public AnimationController<WorkTableEntity> getController() {
        return controller;
    }

    public boolean animationDone = false;
    public static void tick(World world, BlockPos pos, BlockState state, WorkTableEntity be) {


        if (be.getController() != null) {


            world.setBlockState(pos, state.with(WorkTable.ANIMATING, be.getController().hasAnimationFinished()));
        }
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> {
            this.controller = state.getController();

            if (state.getAnimatable().getWorld() != null) {

                BlockState cached = state.getAnimatable().getWorld().getBlockState(state.getAnimatable().pos.offset(this.rotated(this.getDirFromState(state.getAnimatable().getCachedState()))));
                BlockPos ofs = pos.offset(this.rotated(this.getDirFromState(state.getAnimatable().getCachedState())));
                if ((state.getAnimatable().getWorld().getBlockState(state.getAnimatable().pos)).getBlock() == FFBlocks.WORKTABLE && state.getAnimatable().getWorld().getBlockState(state.getAnimatable().pos).get(WorkTable.ANIMATING)) {
                    if (cached.isAir() || cached.getBlock() != FFBlocks.WORKTABLE) {
                        return state.setAndContinue(OPENING);
                    } else {
                        return state.setAndContinue(CLOSED);

                    }
                } else {
                    return state.setAndContinue(UNANIMATED);

                }
            } else {
                return state.setAndContinue(CLOSED);
            }

        }));
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

}
