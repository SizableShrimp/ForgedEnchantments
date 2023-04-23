package me.sizableshrimp.forgedenchantments.recipe;

import me.sizableshrimp.forgedenchantments.init.RecipeSerializerInit;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class ForgedShapedRecipe extends ShapedRecipe implements IForgedRecipe {
    public ForgedShapedRecipe(ResourceLocation id, String group, int width, int height, NonNullList<Ingredient> recipeItems, ItemStack result) {
        super(id, group, width, height, recipeItems, result);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.FORGED_ENCHANTMENT_SHAPED.get();
    }
}
