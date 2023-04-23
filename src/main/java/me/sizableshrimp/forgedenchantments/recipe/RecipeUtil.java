package me.sizableshrimp.forgedenchantments.recipe;

import me.sizableshrimp.forgedenchantments.item.ForgedEnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class RecipeUtil {
    @Nullable
    public static ItemStack findForgedRecipe(Level world, String type, int level) {
        for (Recipe<?> recipe : world.getRecipeManager().getRecipes()) {
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
