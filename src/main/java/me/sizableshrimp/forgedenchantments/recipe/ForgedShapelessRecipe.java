package me.sizableshrimp.forgedenchantments.recipe;

import me.sizableshrimp.forgedenchantments.init.RecipeSerializerInit;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class ForgedShapelessRecipe extends ShapelessRecipe implements IForgedRecipe {
    public ForgedShapelessRecipe(ResourceLocation id, String group, ItemStack result, NonNullList<Ingredient> ingredients) {
        super(id, group, result, ingredients);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.FORGED_ENCHANTMENT_SHAPELESS.get();
    }
}
