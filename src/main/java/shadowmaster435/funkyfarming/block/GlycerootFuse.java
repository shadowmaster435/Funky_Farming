package shadowmaster435.funkyfarming.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.joml.Vector3f;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.util.FuseSide;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class GlycerootFuse extends Block {

    public static Block[] blocklist = {FFBlocks.GLYCEROOT_FUSE, FFBlocks.GLYCEROOT_BLOCK};
    public static List<Block> valid = Arrays.stream(blocklist).toList();

    public static final BooleanProperty IGNITED;
    public static final EnumProperty<FuseSide> DOWN;
    public static final EnumProperty<FuseSide> UP;

    public static final EnumProperty<FuseSide> NORTH;
    public static final EnumProperty<FuseSide> SOUTH;
    public static final EnumProperty<FuseSide> EAST;
    public static final EnumProperty<FuseSide> WEST;
    

    static {

        NORTH = EnumProperty.of("north", FuseSide.class);
        SOUTH = EnumProperty.of("south", FuseSide.class);
        EAST = EnumProperty.of("east", FuseSide.class);
        WEST = EnumProperty.of("west", FuseSide.class);
        UP = EnumProperty.of("up", FuseSide.class);
        DOWN = EnumProperty.of("down", FuseSide.class);
        IGNITED = BooleanProperty.of("ignited");

    }

    public static VoxelShape center = VoxelShapes.cuboid(0.25, 0, 0.25, .75, 0.125, 0.75);

    public static VoxelShape north = VoxelShapes.cuboid(0.25, 0, 0, .75, 0.125, 0.25);
    public static VoxelShape south = VoxelShapes.cuboid(0.25, 0, 0.75, .75, 0.125, 1);
    public static VoxelShape east = VoxelShapes.cuboid(0, 0, 0.25, 0.25, 0.125, 0.75);
    public static VoxelShape west = VoxelShapes.cuboid(0.75, 0, 0.25, 1, 0.125, 0.75);

    public static VoxelShape eastup = VoxelShapes.cuboid(0.875, 0, 0.25, 1, 1, 0.75);
    public static VoxelShape westup = VoxelShapes.cuboid(0, 0, 0.25, 0.125, 1, 0.75);
    public static VoxelShape northup = VoxelShapes.cuboid(0.25, 0, 0, 0.75, 1, 0.125);
    public static VoxelShape southup = VoxelShapes.cuboid(0.25, 0, 0.875, 0.75, 1, 1);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape finalshape = center;
        for (Direction dir : Direction.values()) {
            if (world.getBlockState(pos).getBlock() == this) {
                boolean horizontal = dir.getAxis().isHorizontal();
                boolean sideConnected = (world.getBlockState(pos).get(getProperty(dir)) == FuseSide.CONNECTED);
                boolean sideConnectedDownward = world.getBlockState(pos).get(getProperty(dir)) == FuseSide.DOWN;
                boolean sideConnectedUpward = world.getBlockState(pos).get(getProperty(dir)) == FuseSide.UP;
                if (horizontal && (sideConnected || sideConnectedDownward)) {
                    switch (dir) {
                        case NORTH -> finalshape =  VoxelShapes.union(finalshape, north);
                        case SOUTH -> finalshape =  VoxelShapes.union(finalshape, south);
                        case EAST -> finalshape = VoxelShapes.union(finalshape, west);
                        case WEST -> finalshape = VoxelShapes.union(finalshape, east);
                    }
                }

                if (horizontal && sideConnectedUpward) {
                    switch (dir) {
                        case NORTH -> finalshape = VoxelShapes.union(finalshape, VoxelShapes.union(northup, north) );
                        case SOUTH -> finalshape = VoxelShapes.union(finalshape, VoxelShapes.union(southup, south));
                        case WEST -> finalshape = VoxelShapes.union(finalshape, VoxelShapes.union(westup, east));
                        case EAST -> finalshape = VoxelShapes.union(finalshape, VoxelShapes.union(eastup, west));
                    }
                }
            }
        }
        return finalshape;
    }

    public GlycerootFuse(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(NORTH, FuseSide.NONE)
                .with(SOUTH, FuseSide.NONE)
                .with(EAST, FuseSide.NONE)
                .with(WEST, FuseSide.NONE)
                .with(IGNITED, false));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(NORTH, SOUTH, EAST, WEST, IGNITED);
    }

 
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = this.getDefaultState();
        for (Direction dir : Direction.values()) {
            for (int i = 0; i < 3; ++i) {
                int yval;
                switch (i) {
                    case 1 -> yval = -1;
                    case 2 -> yval = 1;
                    default -> yval = 0;
                }
                if (world.getBlockState(pos.offset(dir).add(0, yval,0)).getBlock() == this) {
                    switch (i) {
                        case 1 -> state = state.with(getProperty(dir), FuseSide.DOWN);
                        case 2 -> state = state.with(getProperty(dir), FuseSide.UP);
                        default -> state = state.with(getProperty(dir), FuseSide.CONNECTED);
                    }
                } else {
                    state = this.getDefaultState().with(getProperty(dir), FuseSide.NONE);
                }
            }
        }
        return state;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        world.setBlockState(pos, this.getDefaultState());
        this.updateState(world,pos);
    }
    
    public void updateState(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        for (Direction dir : Direction.values()) {
            for (int i = 0; i < 3; ++i) {
                int yval;
                switch (i) {
                    case 1 -> yval = -1;
                    case 2 -> yval = 1;
                    default -> yval = 0;
                }
                if (world.getBlockState(pos.offset(dir).add(0, yval, 0)).getBlock() == this) {
                    FuseSide side;
                    FuseSide oppositeSide;
                    switch (yval) {
                        case 1 -> {
                            side = FuseSide.UP;
                            oppositeSide = FuseSide.DOWN;
                        }
                        case -1 -> {
                            side = FuseSide.DOWN;
                            oppositeSide = FuseSide.UP;
                        }
                        default -> {
                            side = FuseSide.CONNECTED;
                            oppositeSide = FuseSide.CONNECTED;
                        }
                    }
                    BlockPos offsetPos = pos.offset(dir).add(0, yval, 0);
                    BlockState newOffsetState = world.getBlockState(offsetPos).with(getProperty(dir.getOpposite()), oppositeSide);
                    BlockState newState = state.with(getProperty(dir), side);
                    boolean validBlock = !(newOffsetState.getBlock() == FFBlocks.GLYCEROOT_BLOCK) || world.getBlockState(offsetPos).getBlock() == this;
                    if (yval != 0 && validBlock) {
                        world.setBlockState(pos, newState);
                    } else {
                        if (validBlock) {
                            world.setBlockState(offsetPos, newOffsetState);
                        }
                    }
                    world.scheduleBlockTick(offsetPos, this, 1);
                } else  {
                    // Fix for weird bug
                        if (i == 2 && world.getBlockState(pos).get(WEST) == FuseSide.DOWN && world.getBlockState(pos.add(-1, -1, 0)).getBlock() != this) {
                            world.setBlockState(pos, state);
                        } else {
                            BlockPos offsetPos = pos.offset(dir).add(0, -1, 0);
                            if (world.getBlockState(offsetPos).getBlock() == this) {
                                world.setBlockState(pos, world.getBlockState(pos).with(getProperty(dir), FuseSide.DOWN));

                                world.setBlockState(offsetPos, world.getBlockState(offsetPos).with(getProperty(dir.getOpposite()), FuseSide.UP));
                            }

                        }
                    }
                }
            }
        }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        
        for (Direction dir : Direction.values()) {
            for (int i = 0; i < 3; ++i) {
                int yval;
                switch (i) {
                    case 1 -> yval = -1;
                    case 2 -> yval = 1;
                    default -> yval = 0;
                }
                if (world.getBlockState(pos.offset(dir).add(0, yval,0)).getBlock() == this) {
                    switch (i) {
                        case 1 -> world.setBlockState(pos.offset(dir).add(0, -1, 0), world.getBlockState(pos.offset(dir).add(0, -1, 0)).with(getProperty(dir.getOpposite()), FuseSide.NONE), 1);
                        case 2 -> world.setBlockState(pos.offset(dir).add(0, 1, 0), world.getBlockState(pos.offset(dir).add(0, 1, 0)).with(getProperty(dir.getOpposite()), FuseSide.NONE), 1);
                        default -> world.setBlockState(pos.offset(dir), world.getBlockState(pos.offset(dir)).with(getProperty(dir.getOpposite()), FuseSide.NONE), 1);
                    }
                }
            }
        }
        super.onBroken(world, pos, state);
    }
    
    public void ingite(World world, BlockPos pos, BlockState state) {
        for (int i = 0; i < 5; ++i) {
            world.addParticle(ParticleTypes.SMALL_FLAME, true, pos.getX() + MathHelper.lerp(Math.random(),  0.4, 0.6),  pos.getY() + 0.1, pos.getZ() + MathHelper.lerp(Math.random(), 0.4, 0.6), 0, Math.random() * 0.05, 0);
        }
        world.addParticle(ParticleTypes.LAVA, true, pos.getX()+ 0.5, pos.getY() + 0.25, pos.getZ() + 0.5, 0,0,0);
        for (Direction dir : Direction.values()) {
            for (int i = 0; i < 3; ++i) {
                int yval;
                switch (i) {
                    case 1 -> yval = -1;
                    case 2 -> yval = 1;
                    default -> yval = 0;
                }
                if (world.getBlockState(pos.offset(dir)).getBlock() == FFBlocks.GLYCEROOT_BLOCK) {
                    world.scheduleBlockTick(pos.offset(dir), FFBlocks.GLYCEROOT_BLOCK, 1);
                    world.setBlockState(pos.offset(dir), FFBlocks.GLYCEROOT_BLOCK.getDefaultState().with(IGNITED, true));
                }
                if (world.getBlockState(pos.offset(dir).add(0, yval,0)).getBlock() == this) {
                    FuseSide yaxis;

                    switch (i) {
                        //Set Ignited state based on if a required block is found
                        case 1 -> {

                            if (world.getBlockState(pos.offset(dir).add(0, -1, 0)).getBlock() == this) {
                                world.setBlockState(pos, world.getBlockState(pos).with(IGNITED, true));
                                world.setBlockState(pos.offset(dir).add(0, -1, 0), world.getBlockState(pos.offset(dir).add(0, -1, 0)).with(IGNITED, true));


                                world.scheduleBlockTick(pos.offset(dir).add(0, -1, 0), this, 5);
                            }
                        }
                        case 2 -> {
                            if (world.getBlockState(pos.offset(dir).add(0, 1, 0)).getBlock() == this) {

                                world.setBlockState(pos, world.getBlockState(pos).with(IGNITED, true));
                                world.setBlockState(pos.offset(dir).add(0, 1, 0), world.getBlockState(pos.offset(dir).add(0, 1, 0)).with(IGNITED, true));
                                world.scheduleBlockTick(pos.offset(dir).add(0, 1, 0), this, 5);
                            }
                        }
                        default -> {
                            if (world.getBlockState(pos.offset(dir)).getBlock() == this || world.getBlockState(pos.offset(dir)).getBlock() == FFBlocks.GLYCEROOT_BLOCK) {

                                world.setBlockState(pos, world.getBlockState(pos).with(IGNITED, true));
                                world.setBlockState(pos.offset(dir), world.getBlockState(pos.offset(dir)).with(IGNITED, true));
                                world.scheduleBlockTick(pos, this, 1);
                                world.scheduleBlockTick(pos.offset(dir), this, 5);

                            }
                        }
                    }
                }
            }
        }
    }

    public void activateRootBlock(World world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() == FFBlocks.GLYCEROOT_BLOCK) {
            world.setBlockState(pos, FFBlocks.GLYCEROOT_BLOCK.getDefaultState().with(GlycerootBlock.IGNITED, true));
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getEquippedStack(EquipmentSlot.MAINHAND).getItem() == Items.FLINT_AND_STEEL || player.getEquippedStack(EquipmentSlot.MAINHAND).getItem() == Items.FIRE_CHARGE) {
            if (!player.isCreative()) {
                if (player.getActiveItem().getItem() == Items.FIRE_CHARGE) {
                    player.setStackInHand(hand, new ItemStack(player.getActiveItem().getItem(), player.getActiveItem().getCount() - 1));

                } else {
                    player.getActiveItem().damage(1, player, playerEntity -> {
                    });
                }
            }
            world.setBlockState(pos, world.getBlockState(pos).with(IGNITED, true));
            return ActionResult.success(true);

        } else {
            return ActionResult.success(false);
        }
    }

    public boolean burntimeup = false;

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBlockState(pos).get(IGNITED)) {
            this.ingite(world, pos, state);
            if (this.burntimeup) {
                world.addParticle(ParticleTypes.LAVA, true, pos.getX()+ 0.5, pos.getY() + 0.25, pos.getZ() + 0.5, 0,0,0);

                world.setBlockState(pos, Blocks.AIR.getDefaultState());

            } else  {
                this.burntimeup = true;
            }
        } else {
            this.updateState(world, pos);
        }
        super.scheduledTick(state, world, pos, random);
    }

    public static Vector3f getPosOfs(FuseSide y, Direction facing) {
        Vector3f result = new Vector3f(0,0,0);
        switch (y) {
            case UP -> result.add(0, 1, 0);
            case DOWN -> result.add(0, -1, 0);
        }
        switch (Direction.fromHorizontal(facing.getHorizontal())) {
            case EAST -> result.add(1,0,0);
            case WEST -> result.add(-1,0,0);
            case NORTH -> result.add(0,0,-1);
            case SOUTH -> result.add(0,0,1);
        };
        return result;
    }

    public static EnumProperty<FuseSide> getProperty(Direction facing) {

        return switch (Direction.fromHorizontal(facing.getHorizontal())) {
            case UP -> UP;
            case DOWN -> DOWN;
            case EAST -> EAST;
            case WEST -> WEST;
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
        };
    }
}