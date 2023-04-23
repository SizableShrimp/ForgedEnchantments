package me.sizableshrimp.forgedenchantments.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;

public class ForgedEnchantmentShapelessRecipeSerializer extends ShapelessRecipe.Serializer {
    @Override
    public ShapelessRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        String s = GsonHelper.getAsString(json, "group", "");
        NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(json, "ingredients"));

        if (nonnulllist.isEmpty()) {
            throw new JsonParseException("No ingredients for shapeless recipe");
        } else if (nonnulllist.size() > 3 * 3) {
            throw new JsonParseException("Too many ingredients for shapeless recipe the max is " + (3 * 3));
        }

        ItemStack itemstack = ForgedEnchantmentShapedRecipeSerializer.getItemStack(json);
        return new ForgedShapelessRecipe(recipeId, s, itemstack, nonnulllist);
    }

    @Override
    public ShapelessRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
         String s = buffer.readUtf();
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
            nonnulllist.add(Ingredient.fromJson(pIngredientArray.get(i)));
        }

        return nonnulllist;
    }
}
