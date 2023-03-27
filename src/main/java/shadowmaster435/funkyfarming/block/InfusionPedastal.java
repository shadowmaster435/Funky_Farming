package shadowmaster435.funkyfarming.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import shadowmaster435.funkyfarming.block.entity.GatewayEntity;
import shadowmaster435.funkyfarming.block.entity.InfuserEntity;
import shadowmaster435.funkyfarming.block.entity.InfusionPedestalEntity;
import shadowmaster435.funkyfarming.init.FFBlocks;

public class InfusionPedastal extends BlockWithEntity {
    public InfusionPedastal(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) != null && world.getBlockEntity(pos) instanceof InfusionPedestalEntity pedestalEntity) {
            if (!player.getStackInHand(hand).isEmpty() || pedestalEntity.getItems().get(0).isEmpty()) {
                pedestalEntity.getItems().set(0, new ItemStack(player.getStackInHand(hand).getItem(), 1));
                player.getStackInHand(hand).decrement(1);
                return ActionResult.SUCCESS;
            } else {
                if (!pedestalEntity.getItems().get(0).isEmpty()) {
                    if (pedestalEntity.animating) {
                        InfuserEntity entity = (InfuserEntity) world.getBlockEntity(pedestalEntity.infuserPos);
                            if (entity != null) {
                                entity.stopAnim = true;
                        }
                    }
                    world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.25, pos.getZ() + 0.5, pedestalEntity.getItems().get(0)));
                    pedestalEntity.getItems().set(0, ItemStack.EMPTY);
                    return ActionResult.SUCCESS;
                } else {
                    return ActionResult.FAIL;
                }
            }
        } else {
            return ActionResult.FAIL;
        }
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, FFBlocks.PEDESTAL_ENTITY, InfusionPedestalEntity::tick);
    }
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new InfusionPedestalEntity(pos, state);
    }
}
