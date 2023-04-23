package me.sizableshrimp.forgedenchantments.util;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import me.sizableshrimp.forgedenchantments.item.ForgedEnchantedBookItem;
import me.sizableshrimp.forgedenchantments.recipe.IForgedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class RecipeUtil {
    @Nullable
    public static ItemStack findForgedRecipe(Level level, String upgradeType, int upgradeLevel) {
        for (Recipe<?> recipe : level.getRecipeManager().getRecipes()) {
            if (recipe instanceof IForgedRecipe forgedRecipe) {
                ItemStack result = forgedRecipe.getResultItem();
                if (!result.hasTag())
                    continue;

                if (ForgedEnchantedBookItem.getType(result).equals(upgradeType) && upgradeLevel == ForgedEnchantedBookItem.getLevel(result))
                    return result;
            }
        }

        return null;
    }

    @Nullable
    public static ItemStack findForgedRecipe(Level level, Object2IntMap<Enchantment> enchantments) {
        for (Recipe<?> recipe : level.getRecipeManager().getRecipes()) {
            if (recipe instanceof IForgedRecipe forgedRecipe) {
                ItemStack result = forgedRecipe.getResultItem();
                if (!result.hasTag())
                    continue;

                if (enchantments.equals(ForgedEnchantmentUtil.getEnchantmentsMap(result)))
                    return result;
            }
        }

        return null;
    }
}
