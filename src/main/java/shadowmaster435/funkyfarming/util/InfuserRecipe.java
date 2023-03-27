package shadowmaster435.funkyfarming.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

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

    public boolean test(List<ItemStack> itemStacks) {
        List<ItemStack> tempList = this.items;
        if (!tempList.contains(this.middleItem)) {
            tempList.add(this.middleItem);
        }
        List<ItemStack> temp1 = new ArrayList<>();
        List<ItemStack> middleRemoved = this.items;
        Item providedmiddleItem = Items.AIR;

        middleRemoved.remove(this.middleItem);
        List<Item> previtems = new ArrayList<>();
        previtems.add(Items.AIR);
        for (ItemStack stack : itemStacks) {
            List<ItemStack> tempList1 = itemStacks.stream().filter(s -> s.getItem() == stack.getItem() && s.getItem() != this.middleItem.getItem()).toList();
            int stackSize = 0;
            for (int i = 0; i < tempList1.size(); ++i) {
                ++stackSize;
            }
            ItemStack stack1 = new ItemStack(stack.getItem(), stackSize);
            if (!previtems.contains( stack1.getItem())) {
                temp1.add(temp1.size() - 1, stack1.copy());
            }
            if (temp1.size() > 0) {
                previtems.add(stack1.getItem());
            }
            if (stack.getItem() == this.middleItem.getItem()) {
                providedmiddleItem = stack.getItem();
            }
        }
        Item item = this.middleItem.getItem();

        int b = 0;
        for (int i = 0; i < temp1.size(); ++i) {
            if (this.items.get(i).getItem() == temp1.get(i).getItem() && this.items.get(i).getCount() == temp1.get(i).getCount()) {
                ++b;
            }
        }
        System.out.println(b);


        return providedmiddleItem == item && b >= temp1.size();
    }

}
