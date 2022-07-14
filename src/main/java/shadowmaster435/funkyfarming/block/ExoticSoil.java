package shadowmaster435.funkyfarming.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import shadowmaster435.funkyfarming.init.FFBlocks;

public class ExoticSoil extends Block {

    public ExoticSoil(Settings settings) {

        super(settings);
    }




    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hit.getSide().equals(Direction.UP) && world.getBlockState(hit.getBlockPos().offset(Direction.UP)).getBlock() == Blocks.AIR && (HoeItem.class.isAssignableFrom(player.getEquippedStack(EquipmentSlot.MAINHAND).getItem().getClass()) || HoeItem.class.isAssignableFrom(player.getEquippedStack(EquipmentSlot.OFFHAND).getItem().getClass()))) {
            world.setBlockState(pos, FFBlocks.EXOTIC_FARMLAND.getDefaultState());
            world.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS,1,1, false);
            player.getEquippedStack(EquipmentSlot.MAINHAND).damage(1, player, playerEntity -> {});
            return ActionResult.success(true);
        } else {
            return ActionResult.FAIL;
        }
    }


}
