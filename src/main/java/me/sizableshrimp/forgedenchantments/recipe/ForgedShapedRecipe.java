package me.sizableshrimp.forgedenchantments.recipe;

import me.sizableshrimp.forgedenchantments.init.RecipeSerializerInit;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class ForgedShapedRecipe extends ShapedRecipe implements IForgedRecipe {
    public ForgedShapedRecipe(ResourceLocation id, String group, int width, int height, NonNullList<Ingredient> recipeItems, ItemStack result) {
        super(id, group, width, height, recipeItems, result);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.FORGED_ENCHANTMENT_SHAPED.get();
    }
}
