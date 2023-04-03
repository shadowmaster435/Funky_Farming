package shadowmaster435.funkyfarming.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import shadowmaster435.funkyfarming.init.FFItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InfuserRecipes {

    public static final ArrayList<InfuserRecipe> recipes = new ArrayList<>();
    public static final InfuserRecipe linkerRecipe = InfuserRecipe.builder()
            .item(FFItems.COPPER_GEAR, 4)
            .item(FFItems.IRON_GEAR, 4)
            .catalyst(Items.ENDER_PEARL)
            .build(FFItems.LINKER, 1);

    public static void init() {
        recipes.add(linkerRecipe);
    }


}
