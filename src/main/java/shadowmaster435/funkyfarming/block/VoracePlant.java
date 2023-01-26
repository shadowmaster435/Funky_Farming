package shadowmaster435.funkyfarming.block;

import com.mojang.brigadier.StringReader;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.tick.TickPriority;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;
import shadowmaster435.funkyfarming.block.entity.VoracePlantEntity;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.init.FFItems;
import shadowmaster435.funkyfarming.init.FFSounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VoracePlant extends CropBase implements BlockEntityProvider {


    public VoracePlant(Settings settings) {
        super(settings);

    }




    @Override
    public int getMaxAge() {
        return 3;
    }


    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.getBlock() == Blocks.FARMLAND;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.scheduleBlockTick(pos, this, 1, TickPriority.byIndex(1));
        super.onPlaced(world, pos, state, placer, itemStack);
    }
    public static int fedval = 0;
    public List<ItemEntity> getItemsEntitiesFromArea(World world) {

       // return world.getClosestEntity(LivingEntity.class, TargetPredicate.createNonAttackable(), null, x, y, z, new Box(BlockPos.ORIGIN));
            return world.getEntitiesByClass(ItemEntity.class, new Box(BlockPos.ORIGIN), Entity::isOnGround);
    }
    public List<ItemStack> getItemsFromArea(int x, int y, int z, World world) {
        List<ItemEntity> itemEntity = world.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), new Box(x, y, z, x + 1, y + 1, z + 1), Entity::isOnGround);
        List<ItemStack> itemStacks = new ArrayList<>();
        for (ItemEntity entity : itemEntity) {
            itemStacks.add(entity.getStack());
        }
        return itemStacks;
    }
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        assert MinecraftClient.getInstance().world != null;
        List<ItemEntity> entity = getItemsEntitiesFromArea(world);
        if (entity != null) {
            for (ItemEntity ientity : entity) {

            }
        }
        world.scheduleBlockTick(pos, this, 1, TickPriority.byIndex(1));

        super.scheduledTick(state, world, pos, random);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity.getClass() == ItemEntity.class) {

            ItemEntity ientity = (ItemEntity) entity;
        ItemStack stack = ientity.getStack();
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            VoracePlantEntity entity1 = (VoracePlantEntity) world.getBlockEntity(pos);
            if (entity1 != null && entity1.fedval < 20) {

                if (stack.isFood() && stack.getItem() != FFItems.VORACE_BULB) {

                    entity1.fedval = entity1.fedval + Objects.requireNonNull(stack.getItem().getFoodComponent()).getHunger();
                    ientity.remove(Entity.RemovalReason.DISCARDED);
                    world.playSound(MinecraftClient.getInstance().player, pos, SoundEvents.ENTITY_GENERIC_EAT,  SoundCategory.BLOCKS, 1, 1);
                }
                if (entity1.fedval >= 20) {
                    world.setBlockState(pos, state.with(VoracePlant.AGE, 1));
                }
            }
        }
    }
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.getAge(state) < 3 && this.getAge(state) != 0) {
            if (random.nextInt(3) != 0) {
                if (world.getBaseLightLevel(pos, 0) >= 9) {
                    int i = this.getAge(state);
                    world.setBlockState(pos, state.with(AGE, i + 1), 2);
                }
            }
        }
    }
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new VoracePlantEntity(pos, state);
    }



    public VoxelShape stage0(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 0.09375, 0.8125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0, 0.125, 0.6875, 0.09375, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.3125, 0.875, 0.09375, 0.6875));

        return shape;
    }
    public VoxelShape stage3(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 0.0625, 0.625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0.4375, 0.375, 0.625, 0.5, 0.625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0.0625, 0.3125, 0.6875, 0.8125, 0.6875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 0.03125, 0.8125));

        return shape;
    }

    public VoxelShape stage2(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 0.0625, 0.625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0.4375, 0.375, 0.625, 0.5, 0.625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0.0625, 0.3125, 0.6875, 0.4375, 0.6875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.34375, 0.5, 0.34375, 0.65625, 0.8125, 0.65625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 0.03125, 0.8125));

        return shape;
    }

    public VoxelShape stage1(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 0.0625, 0.625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0.4375, 0.375, 0.625, 0.5, 0.625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0.0625, 0.3125, 0.6875, 0.4375, 0.6875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0.5, 0.375, 0.625, 0.75, 0.625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 0.03125, 0.8125));

        return shape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (this.getAge(state)) {
            case 1 -> { return stage1(); }
            case 2 -> { return stage2(); }
            case 3 -> { return stage3(); }
            default -> { return stage0(); }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient && world.getBlockState(pos).get(AGE) == 3) {
            world.playSound(null, pos, FFSounds.VORACE_PICK_EVENT, SoundCategory.BLOCKS, 1, 1);
            world.spawnEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), new ItemStack(FFItems.VORACE_BULB, 1)));
            world.setBlockState(pos, state.with(AGE, 0));
            VoracePlantEntity voracePlantEntity = (VoracePlantEntity) world.getBlockEntity(pos);
            if (voracePlantEntity != null) {
                voracePlantEntity.fedval = 0;
            }
            return ActionResult.success(true);
        } else {
            return ActionResult.success(false);
        }
    }
}
