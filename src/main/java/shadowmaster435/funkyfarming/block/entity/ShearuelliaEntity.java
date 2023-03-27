package shadowmaster435.funkyfarming.block.entity;

import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import shadowmaster435.funkyfarming.init.FFBlocks;

import java.util.List;
import java.util.Map;

public class ShearuelliaEntity extends BlockEntity {

    public ShearuelliaEntity(BlockPos pos, BlockState state) {
        super(FFBlocks.SHEARUELLIA_ENTITY, pos, state);
    }
    private static final Map<DyeColor, ItemConvertible> DROPS;

    public static boolean checkSheep = false;


    public static void tick(World world, BlockPos pos, BlockState state, ShearuelliaEntity be) {
        be.shearSheep();
    }

    public void shearSheep() {
        if (this.world != null) {
            if (checkSheep) {
                List<PassiveEntity> entities = world.getEntitiesByType(TypeFilter.instanceOf(PassiveEntity.class), new Box(-5 + this.getPos().getX(),  this.getPos().getY(), -5 + this.getPos().getZ(), 5 + this.getPos().getX(), this.getPos().getY() +1, 5 + this.getPos().getZ()), (PassiveEntity e) -> {
                    boolean b = e instanceof SheepEntity;
                    boolean r = e.isBaby();
                    return r && b;
                });
                for (PassiveEntity entity : entities) {
                    if (entity instanceof SheepEntity entity1) {
                        if (!entity1.isSheared()) {
                            Vec3d pos = entity1.getPos();
                            int randomDrop = (Math.floor(world.getRandom().nextFloat()) > 0.6f) ? 1 : 2;

                            this.world.spawnEntity(new ItemEntity(this.world, pos.x, pos.y, pos.z, new ItemStack(DROPS.get(entity1.getColor()), 1 + randomDrop)));
                            this.world.playSound( null, this.pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1F, (float) (1 + (Math.random() * 0.1)));
                            break;
                        }
                    }
                }
            }
            checkSheep = false;
        }
    }
    static {
        DROPS =  Util.make(Maps.newEnumMap(DyeColor.class), (map) -> {
            map.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
            map.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
            map.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
            map.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
            map.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
            map.put(DyeColor.LIME, Blocks.LIME_WOOL);
            map.put(DyeColor.PINK, Blocks.PINK_WOOL);
            map.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
            map.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
            map.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
            map.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
            map.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
            map.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
            map.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
            map.put(DyeColor.RED, Blocks.RED_WOOL);
            map.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
        });
    }


}
