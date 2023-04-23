package me.sizableshrimp.forgedenchantments.client;

import me.sizableshrimp.forgedenchantments.item.ForgedEnchantedBookItem;
import me.sizableshrimp.forgedenchantments.recipe.IForgedRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ClientItemGroupFiller {
    public static void fillEnchantedBooks(ItemGroup group, NonNullList<ItemStack> items) {
        boolean addAll = group == ItemGroup.TAB_SEARCH;
        if (!addAll && group.getEnchantmentCategories().length == 0)
            return;

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null)
            return;

        List<ItemStack> toAdd = new ArrayList<>();

        for (IRecipe<?> recipe : minecraft.level.getRecipeManager().getRecipes()) {
            if (recipe instanceof IForgedRecipe) {
                ItemStack result = recipe.getResultItem();
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(result);
                boolean add = addAll;
                if (!add && enchantments.size() == 1) {
                    Enchantment enchantment = enchantments.keySet().iterator().next();
                    for (EnchantmentType enchantmentCategory : group.getEnchantmentCategories()) {
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
