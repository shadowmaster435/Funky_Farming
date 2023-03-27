package shadowmaster435.funkyfarming.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import shadowmaster435.funkyfarming.block.Generator;
import shadowmaster435.funkyfarming.init.FFBlocks;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MechaLillyEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    // We statically instantiate our RawAnimations for efficiency, consistency, and error-proofing
    private static final RawAnimation DEFAULTANIM = RawAnimation.begin().thenPlay("spinning").thenLoop("spinning");
    private static final RawAnimation MOTORANIM = RawAnimation.begin().thenPlay("spinningwithmotor").thenLoop("spinningwithmotor");

    public MechaLillyEntity(BlockPos pos, BlockState state) {
        super(FFBlocks.MECHALILLY_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, MechaLillyEntity be) {
        if (world.getBlockState(pos.offset(Direction.Axis.Y, 1)).getBlock() == FFBlocks.GENERATOR) {
            world.setBlockState(pos.offset(Direction.Axis.Y, 1), FFBlocks.GENERATOR.getDefaultState().with(Generator.ACTIVE, true));
        }
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> {
            if (state.getAnimatable().getWorld() != null && state.getAnimatable().getWorld().getBlockState(state.getAnimatable().pos.offset(Direction.Axis.Y, 1)).getBlock() == FFBlocks.GENERATOR) {
                return state.setAndContinue(MOTORANIM);
            } else {
                return state.setAndContinue(DEFAULTANIM);
            }
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}