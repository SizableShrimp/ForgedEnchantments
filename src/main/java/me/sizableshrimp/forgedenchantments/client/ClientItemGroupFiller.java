package me.sizableshrimp.forgedenchantments.client;

import me.sizableshrimp.forgedenchantments.item.ForgedEnchantedBookItem;
import me.sizableshrimp.forgedenchantments.recipe.IForgedRecipe;
import me.sizableshrimp.forgedenchantments.util.ForgedEnchantmentUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClientItemGroupFiller {
    public static void fillEnchantedBooks(CreativeModeTab group, NonNullList<ItemStack> items) {
        boolean addAll = group == CreativeModeTab.TAB_SEARCH;
        if (!addAll && group.getEnchantmentCategories().length == 0)
            return;

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null)
            return;

        List<ItemStack> toAdd = new ArrayList<>();

        for (Recipe<?> recipe : minecraft.level.getRecipeManager().getRecipes()) {
            if (recipe instanceof IForgedRecipe forgedRecipe) {
                ItemStack result = forgedRecipe.getResultItem();
                var enchantments = ForgedEnchantmentUtil.getEnchantmentsMap(result);
                boolean add = addAll;
                if (!add && enchantments.size() == 1) {
                    Enchantment enchantment = enchantments.keySet().iterator().next();
                    for (EnchantmentCategory enchantmentCategory : group.getEnchantmentCategories()) {
                        if (enchantmentCategory == enchantment.category) {
                            add = true;
                            break;
                        }
                    }
                }

                if (add)
                    toAdd.add(result);
            }
        }

        if (!toAdd.isEmpty()) {
            toAdd.sort(Comparator.comparing(ForgedEnchantedBookItem::getType).thenComparingInt(ForgedEnchantedBookItem::getLevel));
            items.addAll(toAdd);
        }
    }
}
