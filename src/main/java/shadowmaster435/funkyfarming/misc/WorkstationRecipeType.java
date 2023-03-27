package shadowmaster435.funkyfarming.misc;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class WorkstationRecipeType implements Recipe<CraftingInventory> {

    //You can add as many inputs as you want here.
    //It is important to always use Ingredient, so you can support tags.
    private final Ingredient toolA;
    private final Ingredient toolB;

    private final Ingredient toolC;

    private final List<Ingredient> GridIngredients;
    private final ItemStack result;
    private final Identifier id;

    public WorkstationRecipeType(Identifier id, ItemStack result, Ingredient toolA, Ingredient toolB, Ingredient toolC, List<Ingredient> ingredients) {
        this.id = id;
        this.toolA = toolA;
        this.toolB = toolB;
        this.toolC = toolC;
        this.GridIngredients = ingredients;
        this.result = result;
    }

    public Ingredient getToolA() {
        return this.toolA;
    }

    public Ingredient getToolB() {
        return this.toolB;
    }

    public Ingredient getToolC() {
        return this.toolC;
    }

    public List<Ingredient> getGridIngredients() {
        return this.GridIngredients;
    }

    @Override
    public ItemStack getOutput() {
        return this.result;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return null;
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        int successCount = 0;
        for (Ingredient ingredient : this.GridIngredients) {
            if (ingredient.test(inventory.getStack(this.GridIngredients.indexOf(ingredient) + 3))) {
                successCount++;
            }

        }
        return successCount >= 9;
    }

    public ItemStack craft(CraftingInventory inv) {
        return this.getOutput().copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }
}