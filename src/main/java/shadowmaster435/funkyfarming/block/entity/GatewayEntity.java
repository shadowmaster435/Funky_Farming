package shadowmaster435.funkyfarming.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;
import shadowmaster435.funkyfarming.block.GatewayAmp;
import shadowmaster435.funkyfarming.block.Generator;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.util.ImplementedInventory;
import shadowmaster435.funkyfarming.util.PillarProperty;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Arrays;
import java.util.Objects;

public class GatewayEntity extends BlockEntity implements GeoBlockEntity, ImplementedInventory {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }
    public int animtimer = 1;

    public int checkCoolDown = 10;
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation EMPTY = RawAnimation.begin().thenLoop("empty");

    private static final RawAnimation ACTIVATING = RawAnimation.begin().thenPlay("activating").thenLoop("activatedstage1");
    private static final RawAnimation STAGE1TO2 = RawAnimation.begin().thenPlay("stage1to2").thenLoop("activatedstage2");
    private static final RawAnimation STAGE2TOBREAKING = RawAnimation.begin().thenPlayAndHold("stage2tobreaking");

    public boolean ampsPresent = false;
    public GatewayEntity(BlockPos pos, BlockState state) {
        super(FFBlocks.GATEWAY_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, GatewayEntity be) {
        if (be.animtimer > 260) {
            be.animtimer = 1;
            be.getItems().set(0, ItemStack.EMPTY);
        } else {
            if (be.ampsPresent) {
                be.animtimer++;
            }
        }
        if (be.checkCoolDown >= 20) {
            be.ampsPresent = be.checkForAmps(pos, world);
            be.checkCoolDown = 0;

        } else  {
            be.checkCoolDown++;
        }
        if (be.ampsPresent) {
            be.setAmpState(be.getItems().get(0).getItem() == Items.ENDER_PEARL, world, pos);
        }
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("animtimer", animtimer);
        Inventories.readNbt(nbt, items);

        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {

        super.readNbt(nbt);
        Inventories.writeNbt(nbt, items);
        animtimer = createNbt().getInt("animtimer");

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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> {
            GatewayEntity entity = (GatewayEntity) Objects.requireNonNull(state.getAnimatable().getWorld()).getBlockEntity(state.getAnimatable().pos);
            if (entity != null && entity.ampsPresent) {
                if (entity.animtimer < 40) {
                    return state.setAndContinue(ACTIVATING);
                } else if (entity.animtimer <= 165 && entity.animtimer >= 40) {
                    return state.setAndContinue(STAGE1TO2);
                } else if (entity.animtimer > 165 && entity.animtimer < 260) {
                    return state.setAndContinue(STAGE2TOBREAKING);
                } else {
                    return state.setAndContinue(IDLE);
                }
            } else  {
                if (entity != null && entity.getItems().get(0).getItem() == Items.ENDER_PEARL) {
                    return state.setAndContinue(IDLE);
                } else {
                    return state.setAndContinue(EMPTY);
                }
            }
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public boolean checkForAmps(BlockPos pos, World world) {
        BlockState state = FFBlocks.GATEWAY_AMP.getDefaultState();
        int successCount = 0;
        for (AmpCheckDirections direction : AmpCheckDirections.cardinal()) {
            if (world.getBlockState(pos.add(direction.getPos())).getBlock() == FFBlocks.PEARLSTONE) {
                if (world.getBlockState(pos.add(direction.getPos()).offset(Direction.UP)).getBlock() == FFBlocks.PEARLSTONE) {
                    if (world.getBlockState(pos.add(direction.getPos()).offset(Direction.UP, 2)).getBlock() == FFBlocks.PEARLSTONE_PILLAR) {
                        if (world.getBlockState(pos.add(direction.getPos()).offset(Direction.UP, 3)).getBlock() == FFBlocks.PEARLSTONE) {
                            if (world.getBlockState(pos.add(direction.getPos()).offset(Direction.UP, 4)).getBlock() == FFBlocks.PEARLSTONE) {
                                ++successCount;
                            }
                        }
                    }
                }
            } else {
                if (world.getBlockState(pos.add(direction.getPos())).getBlock() == FFBlocks.GATEWAY_AMP) {
                    if (world.getBlockState(pos.add(direction.getPos()).offset(Direction.UP)).getBlock() == FFBlocks.GATEWAY_AMP) {
                        if (world.getBlockState(pos.add(direction.getPos()).offset(Direction.UP, 2)).getBlock() == FFBlocks.GATEWAY_AMP) {
                            if (world.getBlockState(pos.add(direction.getPos()).offset(Direction.UP, 3)).getBlock() == FFBlocks.GATEWAY_AMP) {
                                if (world.getBlockState(pos.add(direction.getPos()).offset(Direction.UP, 4)).getBlock() == FFBlocks.GATEWAY_AMP) {
                                    ++successCount;
                                }
                            }
                        }
                    }
                }
            }
        }
        for (AmpCheckDirections direction : AmpCheckDirections.diagonal()) {
            if (world.getBlockState(pos.add(direction.getPos())).getBlock() == FFBlocks.PEARLSTONE) {
                if (world.getBlockState(pos.add(direction.getPos()).offset(Direction.UP)).getBlock() == FFBlocks.PEARLSTONE_PILLAR) {
                    if (world.getBlockState(pos.add(direction.getPos()).offset(Direction.UP, 2)).getBlock() == FFBlocks.PEARLSTONE) {
                        ++successCount;
                    }
                }
            } else {
                if (world.getBlockState(pos.add(direction.getPos())).getBlock() == FFBlocks.GATEWAY_AMP) {
                    if (world.getBlockState(pos.add(direction.getPos()).offset(Direction.UP)).getBlock() == FFBlocks.GATEWAY_AMP) {
                        if (world.getBlockState(pos.add(direction.getPos()).offset(Direction.UP, 2)).getBlock() == FFBlocks.GATEWAY_AMP) {
                            ++successCount;
                        }
                    }
                }
            }
        }
        return successCount >= 8;
    }

    public void setAmpState(boolean active, World world, BlockPos pos) {
        BlockState state = FFBlocks.GATEWAY_AMP.getDefaultState();

        if (checkForAmps(pos, world)) {
            for (AmpCheckDirections direction : AmpCheckDirections.cardinal()) {
                world.setBlockState(pos.add(direction.getPos()), state.with(GatewayAmp.PART, PillarProperty.BOTTOM).with(GatewayAmp.ACTIVE, active), 0);
                world.setBlockState(pos.add(direction.getPos()).offset(Direction.UP), state.with(GatewayAmp.PART, PillarProperty.CENTER).with(GatewayAmp.ACTIVE, active), 0);
                world.setBlockState(pos.add(direction.getPos()).offset(Direction.UP, 2), state.with(GatewayAmp.PEARLED, true).with(GatewayAmp.ACTIVE, active), 0);
                world.setBlockState(pos.add(direction.getPos()).offset(Direction.UP, 3), state.with(GatewayAmp.PART, PillarProperty.CENTER).with(GatewayAmp.ACTIVE, active), 0);
                world.setBlockState(pos.add(direction.getPos()).offset(Direction.UP, 4), state.with(GatewayAmp.PART, PillarProperty.TOP).with(GatewayAmp.ACTIVE, active), 0);
            }
            for (AmpCheckDirections direction : AmpCheckDirections.diagonal()) {
                world.setBlockState(pos.add(direction.getPos()), state.with(GatewayAmp.PART, PillarProperty.BOTTOM).with(GatewayAmp.ACTIVE, active), 0);
                world.setBlockState(pos.add(direction.getPos()).offset(Direction.UP), state.with(GatewayAmp.PEARLED, true).with(GatewayAmp.ACTIVE, active), 0);
                world.setBlockState(pos.add(direction.getPos()).offset(Direction.UP, 2), state.with(GatewayAmp.PART, PillarProperty.TOP).with(GatewayAmp.ACTIVE, active), 0);
            }
        }
    }

    public enum AmpCheckDirections {
        NORTH(new BlockPos(0,0,-4), true),
        SOUTH(new BlockPos(0,0,4), true),
        EAST(new BlockPos(4,0,0), true),
        WEST(new BlockPos(-4,0,0), true),
        NORTHEAST(new BlockPos(3,0,-3), false),
        NORTHWEST(new BlockPos(-3,0,-3), false),
        SOUTHEAST(new BlockPos(3,0,3), false),
        SOUTHWEST(new BlockPos(-3,0,3), false);
        private final BlockPos pos;

        private static final AmpCheckDirections[] ALL = AmpCheckDirections.values();

        private final boolean isCardinal;



        public static final AmpCheckDirections[] cardinal() {
            return Arrays.stream(ALL).filter((cardinal) -> cardinal.isCardinal).toArray(AmpCheckDirections[]::new);
        }
        public static final AmpCheckDirections[] diagonal() {
            return Arrays.stream(ALL).filter((cardinal) -> !cardinal.isCardinal).toArray(AmpCheckDirections[]::new);
        }
        AmpCheckDirections(BlockPos pos, boolean caridnal) {
            this.pos = pos;
            this.isCardinal = caridnal;
        }

        public BlockPos getPos() {
            return pos;
        }
    }


}
