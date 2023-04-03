package shadowmaster435.funkyfarming.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
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

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) != null && world.getBlockEntity(pos) instanceof InfusionPedestalEntity pedestalEntity) {
            ItemStack stack = pedestalEntity.getItems().get(0);
            if (stack.getItem() == Items.AIR) {
                pedestalEntity.getItems().set(0, new ItemStack(player.getStackInHand(hand).getItem(), 1));
                if (!player.isCreative()) {
                    player.getStackInHand(hand).decrement(1);
                }
            } else {
                world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, stack));
                pedestalEntity.getItems().set(0, ItemStack.EMPTY);
            }
            pedestalEntity.markDirty();
            return ActionResult.SUCCESS;
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

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return makeShape();
    }

    public VoxelShape makeShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.1875, 0.75));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0.1875, 0.3125, 0.6875, 0.8125, 0.6875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.8125, 0.1875, 0.8125, 1, 0.8125));

        return shape;
    }
}
