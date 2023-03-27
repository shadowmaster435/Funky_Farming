package shadowmaster435.funkyfarming.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import shadowmaster435.funkyfarming.block.entity.ItemPylonEntity;
import shadowmaster435.funkyfarming.block.entity.PylonEntity;
import shadowmaster435.funkyfarming.init.FFBlocks;

import java.util.Objects;

public class Linker extends Item {
    public Linker(Settings settings) {
        super(settings);
    }

    public BlockPos posPrev = new BlockPos(0,0,0);


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        NbtCompound compound = context.getStack().getOrCreateNbt();
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();

        if (world.getBlockState(pos).getBlock() == FFBlocks.PYLON || world.getBlockState(pos).getBlock() == FFBlocks.ITEM_PYLON && compound != null) {
            if (!compound.getBoolean("firstuse") || !compound.contains("firstuse")) {
                compound.putInt("x0", context.getBlockPos().getX());
                compound.putInt("y0", context.getBlockPos().getY());
                compound.putInt("z0", context.getBlockPos().getZ());
                posPrev = context.getBlockPos();
                compound.putBoolean("firstuse", true);
            } else {
                if (world.getBlockState(pos).getBlock() == FFBlocks.PYLON) {

                    PylonEntity pos1 = (PylonEntity) world.getBlockEntity(new BlockPos(compound.getInt("x0"), compound.getInt("y0"), compound.getInt("z0")));
                    PylonEntity pos2 = (PylonEntity) world.getBlockEntity(context.getBlockPos());


                    if (pos1 != null && pos2 != null) {
                        if (!pos.equals(posPrev)) {

                            if (!((pos1.getXlist().contains(pos.getX()) && pos1.getYlist().contains(pos.getY()) && pos1.getZlist().contains(pos.getZ())) && (pos2.getXlist().contains(posPrev.getX()) && pos2.getYlist().contains(posPrev.getY()) && pos2.getZlist().contains(posPrev.getZ())))) {

                                pos1.getXlist().add(context.getBlockPos().getX());
                                pos1.getYlist().add(context.getBlockPos().getY());
                                pos1.getZlist().add(context.getBlockPos().getZ());
                                pos2.getXlist().add(compound.getInt("x0"));
                                pos2.getYlist().add(compound.getInt("y0"));
                                pos2.getZlist().add(compound.getInt("z0"));



                                compound.remove("firstuse");
                                compound.remove("x0");
                                compound.remove("y0");
                                compound.remove("z0");
                                pos1.markDirty();
                                pos2.markDirty();
                            } else {

                                Objects.requireNonNull(context.getPlayer()).sendMessage(Text.of("Can't Link A Position Twice"), true);
                                compound.putBoolean("firstuse", false);

                            }
                        } else {

                            Objects.requireNonNull(context.getPlayer()).sendMessage(Text.of("Can't Link To The Same Position"), true);
                            compound.putBoolean("firstuse", false);

                        }
                    }
                }

            }
            return ActionResult.SUCCESS;
        } else  {

            return ActionResult.FAIL;
        }

    }
}
