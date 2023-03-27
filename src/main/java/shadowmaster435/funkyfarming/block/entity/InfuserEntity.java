package shadowmaster435.funkyfarming.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import shadowmaster435.funkyfarming.util.InfuserRecipe;
import shadowmaster435.funkyfarming.util.InfuserRecipes;

import java.util.ArrayList;
import java.util.List;

public class InfuserEntity extends BlockEntity implements ImplementedInventory {

    public List<BlockPos> posList = new ArrayList<>();

    public boolean animating = false;

    public boolean stopAnim = false;

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.items;
    }

    public int animtimer = 1;

    public int checkTimer = 0;

    public InfuserEntity(BlockPos pos, BlockState state) {
        super(FFBlocks.INFUSER_ENTITY, pos, state);
    }


    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("animtimer", animtimer);
        Inventories.readNbt(nbt, items);

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
        animtimer = createNbt().getInt("animtimer");
    }

    public static void tick(World world, BlockPos pos, BlockState state, InfuserEntity be) {

        if (be.checkTimer < 20) {
            ++be.checkTimer;
        } else {
            be.checkForPedestals(world, pos);
            List<ItemStack> stacks = be.checkPedestalInventories(world, be);


            for (InfuserRecipe recipe : InfuserRecipes.recipes) {
                if (recipe.test(stacks)) {
                    be.animating = true;
                    for (BlockPos pos1 : be.posList) {
                        if (world.getBlockEntity(pos1) instanceof InfusionPedestalEntity entity) {
                            entity.getItems().get(0).decrement(1);
                            entity.markDirty();
                        }
                    }
                    for (int i = 0; i < be.getItems().size(); ++i) {
                        if (recipe.middleItem == (be.getItems().get(i))) {
                            if (be.getItems().get(i).getCount() > 0) {
                                be.getItems().get(i).decrement(1);
                                be.markDirty();
                            }
                        }
                    }
                }
            }
            be.checkTimer = 0;
        }
        if (be.animating) {
        if (be.animtimer < 30) {
            ++be.animtimer;
        }
            be.animating = false;
            be.animtimer = 0;
        }
        for (BlockPos pos1 : be.posList) {
            InfusionPedestalEntity entity = (InfusionPedestalEntity) world.getBlockEntity(pos1);
            if (entity != null) {
                entity.animating = false;
            }
        }

        if (be.animating) {
            if (be.animtimer < 30) {
                ++be.animtimer;

            }/* else {
                be.checkForPedestals(world, pos);
                List<ItemStack> stacks = be.checkPedestalInventories(world, be);
                for (InfuserRecipe recipe : InfuserRecipes.recipes) {
                    if (recipe.test(stacks)) {
                        be.animating = true;
                        for (BlockPos pos1 : be.posList) {
                            if (world.getBlockEntity(pos1) instanceof InfusionPedestalEntity entity) {
                                entity.getItems().get(0).decrement(1);
                            }
                        }
                        for (int i = 0; i < be.getItems().size(); ++i) {
                            if (recipe.middleItem == (be.getItems().get(i))) {
                                if (be.getItems().get(i).getCount() > 0) {
                                    be.getItems().get(i).decrement(1);
                                }
                            }
                        }
                    }
                }
                be.animtimer = 0;
                if (be.animating) {
                    be.animating = false;
                }
            }
*/        }
    }
    public void checkForPedestals(World world, BlockPos pos) {

        this.posList.clear();
        for (int x = -3; x < 3; ++x) {
            for (int z = -3; z < 3; ++z) {
                boolean isPedestal = (world.getBlockState(pos.add(x, 0, z)).getBlock() == FFBlocks.INFUSION_PEDASTAL);
                if (isPedestal) {
                    InfusionPedestalEntity pedestalEntity = (InfusionPedestalEntity) world.getBlockEntity(pos.add(x,0,z));
                    if (pedestalEntity != null) {
                        this.posList.add(pos.add(x, 0, z));
                        pedestalEntity.infuserPos = pos;
                    }
                }
            }
        }
    }

    public List<ItemStack> checkPedestalInventories(World world, InfuserEntity entity) {
        List<ItemStack> result = new ArrayList<>();
        for (BlockPos pos : entity.posList) {
            if (world.getBlockState(pos).getBlock() == FFBlocks.INFUSION_PEDASTAL && world.getBlockEntity(pos) instanceof InfusionPedestalEntity pedestalEntity) {
                result.add(pedestalEntity.getItems().get(0));
            }
        }
        result.addAll(entity.getItems());
        return result;
    }


}
