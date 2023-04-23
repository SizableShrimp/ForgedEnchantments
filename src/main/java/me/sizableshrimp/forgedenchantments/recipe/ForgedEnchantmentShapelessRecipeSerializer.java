package me.sizableshrimp.forgedenchantments.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class ForgedEnchantmentShapelessRecipeSerializer extends ShapelessRecipe.Serializer {
    @Override
    public ShapelessRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        String s = JSONUtils.getAsString(json, "group", "");
        NonNullList<Ingredient> nonnulllist = itemsFromJson(JSONUtils.getAsJsonArray(json, "ingredients"));
        if (nonnulllist.isEmpty()) {
            throw new JsonParseException("No ingredients for shapeless recipe");
        } else if (nonnulllist.size() > 3 * 3) {
            throw new JsonParseException("Too many ingredients for shapeless recipe the max is " + (3 * 3));
        }

        ItemStack itemstack = ForgedEnchantmentShapedRecipeSerializer.getItemStack(json);
        return new ForgedShapelessRecipe(recipeId, s, itemstack, nonnulllist);
    }

    @Override
    public ShapelessRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
         String s = buffer.readUtf(32767);
         int i = buffer.readVarInt();
         NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

         for(int j = 0; j < nonnulllist.size(); ++j) {
            nonnulllist.set(j, Ingredient.fromNetwork(buffer));
         }

         ItemStack itemstack = buffer.readItem();
         return new ForgedShapelessRecipe(recipeId, s, itemstack, nonnulllist);
    }

    private static NonNullList<Ingredient> itemsFromJson(JsonArray pIngredientArray) {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();

        for (int i = 0; i < pIngredientArray.size(); ++i) {
            Ingredient ingredient = Ingredient.fromJson(pIngredientArray.get(i));
            if (!ingredient.isEmpty()) {
                nonnulllist.add(ingredient);
            }
        }

        return nonnulllist;
    }
}
