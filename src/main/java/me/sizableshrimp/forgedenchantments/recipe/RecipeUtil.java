package me.sizableshrimp.forgedenchantments.recipe;

import me.sizableshrimp.forgedenchantments.item.ForgedEnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RecipeUtil {
    @Nullable
    public static ItemStack findForgedRecipe(World world, String type, int level) {
        for (IRecipe<?> recipe : world.getRecipeManager().getRecipes()) {
            if (recipe instanceof IForgedRecipe) {
                ItemStack result = recipe.getResultItem();
                if (!result.hasTag())
                    continue;

                if (ForgedEnchantedBookItem.getType(result).equals(type) && level == ForgedEnchantedBookItem.getLevel(result))
                    return result;
            }
        }

        return null;
    }
}
