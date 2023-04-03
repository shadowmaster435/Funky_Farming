package shadowmaster435.funkyfarming.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import javax.annotation.Nullable;
import java.util.*;

public class InfuserRecipe {


    public List<ItemStack> items = new ArrayList<>();

    public ItemStack middleItem;


    public ItemStack result;


    public InfuserRecipe(List<ItemStack> items, ItemStack middleItems, ItemStack result) {
        this.items = items;
        this.middleItem = middleItems;

        this.result = result;
    }
    public InfuserRecipe() {
    }
    public static InfuserRecipe builder() {
        return new InfuserRecipe();
    }

    public InfuserRecipe item(Item item, int count) {

        this.items.add(new ItemStack(item, count));
        return this;
    }

    public InfuserRecipe catalyst(Item item) {
        this.middleItem = new ItemStack(item);
        return this;
    }


    public InfuserRecipe build(Item result, int count) {

        return new InfuserRecipe(this.items, this.middleItem, new ItemStack(result, count));
    }

    public boolean test(List<ItemStack> itemStacks, ItemStack istack) {

        try {
            HashMap<Item, Integer> stackSizes = new HashMap<>();
            HashMap<String, ItemStack> holder = new HashMap<>();
            HashSet<Item> itemHolder = new HashSet<>();

            for (ItemStack itemStack : itemStacks) {
                itemHolder.add(itemStack.getItem());
                stackSizes.putIfAbsent(itemStack.getItem(), 0);

                if (stackSizes.containsKey(itemStack.getItem())) {
                    stackSizes.replace(itemStack.getItem(), stackSizes.get(itemStack.getItem()) + 1);
                }
            }
            for (Item item : itemHolder) {
                if (holder.containsKey(item.getName().getString())) {
                    holder.replace(item.getName().getString(), new ItemStack(item, stackSizes.get(item)));
                } else {
                    holder.put(item.getName().getString(), new ItemStack(item, stackSizes.get(item)));
                }
            }
            int successCount = 0;
            for (ItemStack stack : holder.values()) {
                for (ItemStack itemStack : items) {
                    if (compareStacks(stack, itemStack)) {
                        ++successCount;
                    }
                }
            }
            return successCount == holder.size() && istack.getItem() == this.middleItem.getItem();
        } catch (Exception e) {

            return false;
        }

    }

    private boolean compareStacks(ItemStack first, ItemStack second) {
        return first.getItem() == second.getItem() && first.getCount() == second.getCount();
    }



}
