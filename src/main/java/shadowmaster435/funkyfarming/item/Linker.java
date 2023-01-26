package shadowmaster435.funkyfarming.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import shadowmaster435.funkyfarming.block.entity.PylonEntity;
import shadowmaster435.funkyfarming.init.FFBlocks;

import java.util.Objects;

public class Linker extends Item {
    public Linker(Settings settings) {
        super(settings);
    }



    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        NbtCompound compound = context.getStack().getOrCreateNbt();

        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        if (world.getBlockState(pos).getBlock() == FFBlocks.PYLON && compound != null) {
            if (compound.getBoolean("firstuse")) {
                compound.putInt("x0", (int) context.getBlockPos().getX());
                compound.putInt("y0", (int) context.getBlockPos().getY());
                compound.putInt("z0", (int) context.getBlockPos().getZ());
                compound.putBoolean("firstuse", false);
            } else  {


            PylonEntity pos1 = (PylonEntity) world.getBlockEntity(new BlockPos(compound.getInt("x0"), compound.getInt("y0"), compound.getInt("z0")));
            PylonEntity pos2 = (PylonEntity) world.getBlockEntity(context.getBlockPos());

                if (pos1 != null && pos2 != null) {

                    pos1.xlist.add(context.getBlockPos().getX());
                    pos1.ylist.add(context.getBlockPos().getY());
                    pos1.zlist.add(context.getBlockPos().getZ());
                    pos2.xlist.add(compound.getInt("x0"));
                    pos2.ylist.add(compound.getInt("y0"));
                    pos2.zlist.add(compound.getInt("z0"));


                }
                compound.putBoolean("firstuse", true);
            }

            return ActionResult.SUCCESS;
        } else  {
            return ActionResult.FAIL;
        }

    }
}
