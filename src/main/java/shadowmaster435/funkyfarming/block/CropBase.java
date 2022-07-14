package shadowmaster435.funkyfarming.block;

import net.minecraft.block.CropBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CropBase extends CropBlock {
    public CropBase(Settings settings) {
        super(settings);
    }

    public List<ItemStack> getItemsFromArea(int x, int y, int z, World world) {
        List<ItemEntity> itemEntity = world.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), new Box(x, y, z, x + 1, y + 1, z + 1), Entity::isOnGround);
        List<ItemStack> itemStacks = new ArrayList<>();
        for (ItemEntity entity : itemEntity) {
            itemStacks.add(entity.getStack());
        }
        return itemStacks;
    }
}
