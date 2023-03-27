package shadowmaster435.funkyfarming.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.util.TransferUtil;

import java.util.List;

public class EnergyVoidEntity extends BlockEntity {

 //   public final GravitySphere sphere;

    public EnergyVoidEntity(BlockPos pos, BlockState state) {
        super(FFBlocks.ENERGY_VOID_ENTITY, pos, state);
      //  this.sphere = new GravitySphere(5, this.getPos());
     //   GravitySphere.spheres.add(sphere);
    }

/*
    public GravitySphere getSphere() {
        return this.sphere;
    }
*/

    public static void tick(World world, BlockPos pos, BlockState state, EnergyVoidEntity be) {
       /* if (!GravitySphere.spheres.contains(be.sphere)) {
            GravitySphere.spheres.add(be.sphere);
        }
        for (Entity entity : be.sphere.getEntitiesInRadius(world)) {

            if (be.sphere.inside(entity.getPos())) {

                System.out.println(entity.getVelocity());
                entity.setBoundingBox(be.sphere.getRotatedBoundingBox(entity));
                RenderUtil.cameraRot = be.sphere.CameraRotation;
            }
        }*/
        try {


    if (world.getBlockEntity(pos.offset(Direction.UP)) instanceof PylonEntity) {
        List<BlockPos> transferpoints = TransferUtil.getTransferPoints(world, (PylonEntity) world.getBlockEntity(pos.offset(Direction.UP)),50);

        for (BlockPos vec3i: transferpoints) {
            if (world.getBlockState(vec3i).getBlock() == FFBlocks.GENERATOR) {
                if (world.getBlockEntity(vec3i) instanceof GeneratorEntity generatorEntity) {
                    if (generatorEntity != null) {
                        if (generatorEntity.getPropertyDelegate().get(1) > 0) {
                            generatorEntity.getPropertyDelegate().set(1, generatorEntity.getPropertyDelegate().get(1) - 20);
                        }
                    }
                }
            }
        }
    }
        } catch (Exception ignored) {

        }

    }
}
