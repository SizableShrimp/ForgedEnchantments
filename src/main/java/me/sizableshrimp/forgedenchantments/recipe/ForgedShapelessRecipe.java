package me.sizableshrimp.forgedenchantments.recipe;

import me.sizableshrimp.forgedenchantments.init.RecipeSerializerInit;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class ForgedShapelessRecipe extends ShapelessRecipe implements IForgedRecipe {
    public ForgedShapelessRecipe(ResourceLocation id, String group, ItemStack result, NonNullList<Ingredient> ingredients) {
        super(id, group, result, ingredients);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.FORGED_ENCHANTMENT_SHAPELESS.get();
    }
}
