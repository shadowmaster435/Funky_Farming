package shadowmaster435.funkyfarming.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import shadowmaster435.funkyfarming.block.entity.GatewayEntity;
import shadowmaster435.funkyfarming.block.entity.MechaLillyEntity;
import shadowmaster435.funkyfarming.init.FFBlocks;

public class Gateway extends BlockWithEntity {

    public Gateway(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }


    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GatewayEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getActiveItem().getItem() == Items.ENDER_PEARL) {
            GatewayEntity entity = (GatewayEntity) world.getBlockEntity(pos);
            assert entity != null;
            entity.getItems().set(0, new ItemStack(Items.ENDER_PEARL, 1));
            player.getActiveItem().decrement(1);
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL;
        }
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {

        return checkType(type, FFBlocks.GATEWAY_ENTITY, GatewayEntity::tick);
    }
}
