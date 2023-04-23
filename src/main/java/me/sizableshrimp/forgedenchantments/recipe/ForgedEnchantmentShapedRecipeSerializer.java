package me.sizableshrimp.forgedenchantments.recipe;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.Map;
import java.util.Set;

public class ForgedEnchantmentShapedRecipeSerializer extends ShapedRecipe.Serializer {
    @Override
    public ShapedRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        String s = GsonHelper.getAsString(json, "group", "");
        Map<String, Ingredient> map = keyFromJson(GsonHelper.getAsJsonObject(json, "key"));
        String[] astring = shrink(patternFromJson(GsonHelper.getAsJsonArray(json, "pattern")));
        int i = astring[0].length();
        int j = astring.length;
        NonNullList<Ingredient> nonnulllist = dissolvePattern(astring, map, i, j);
        ItemStack itemstack = getItemStack(json);

        return new ForgedShapedRecipe(recipeId, s, i, j, nonnulllist, itemstack);
    }

    @Override
    public ShapedRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        int i = buffer.readVarInt();
        int j = buffer.readVarInt();
        String s = buffer.readUtf();
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

        for (int k = 0; k < nonnulllist.size(); ++k) {
            nonnulllist.set(k, Ingredient.fromNetwork(buffer));
        }

        ItemStack itemstack = buffer.readItem();
        return new ForgedShapedRecipe(recipeId, s, i, j, nonnulllist, itemstack);
    }

    @SuppressWarnings("deprecation")
    static ItemStack getItemStack(JsonObject json) {
        JsonObject result = GsonHelper.getAsJsonObject(json, "result");
        if (!result.has("item"))
            result.addProperty("item", "minecraft:enchanted_book");
        ItemStack itemstack = CraftingHelper.getItemStack(result, true);

        String type = GsonHelper.getAsString(result, "upgrade_type");
        int upgradeLevel = GsonHelper.getAsInt(result, "level");
        CompoundTag tag = itemstack.getOrCreateTag();
        tag.putString("UpgradeType", type);
        tag.putInt("UpgradeLevel", upgradeLevel);

        for (JsonElement enchantmentJson : GsonHelper.getAsJsonArray(result, "enchantments")) {
            JsonObject enchantmentJsonObject = GsonHelper.convertToJsonObject(enchantmentJson, "enchantment");
            String enchantmentId = GsonHelper.getAsString(enchantmentJsonObject, "id");
            Enchantment enchantment = Registry.ENCHANTMENT.getOptional(new ResourceLocation(enchantmentId)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + enchantmentId + "'"));
            int level = enchantmentJsonObject.getAsJsonPrimitive("level").getAsInt();

            EnchantedBookItem.addEnchantment(itemstack, new EnchantmentInstance(enchantment, level));
        }

        // String modelId = JSONUtils.getAsString(result, "model", "");
        // new ModelResourceLocation(modelId); // Validate it
        // if (!modelId.isEmpty()) {
        //     tag.putString("Model", modelId);
        // }

        if (result.has("name")) {
            MutableComponent name = MutableComponent.Serializer.fromJson(result.get("name"));
            tag.putString("UpgradeName", MutableComponent.Serializer.toJson(name));
        }

        tag.putBoolean("Glint", GsonHelper.getAsBoolean(result, "glint", false));

        return itemstack;
    }

    private static NonNullList<Ingredient> dissolvePattern(String[] pPattern, Map<String, Ingredient> pKeys, int pPatternWidth, int pPatternHeight) {
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(pPatternWidth * pPatternHeight, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(pKeys.keySet());
        set.remove(" ");

        for (int i = 0; i < pPattern.length; ++i) {
            for (int j = 0; j < pPattern[i].length(); ++j) {
                String s = pPattern[i].substring(j, j + 1);
                Ingredient ingredient = pKeys.get(s);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
                }

                set.remove(s);
                nonnulllist.set(j + pPatternWidth * i, ingredient);
            }
        }

        if (!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return nonnulllist;
        }
    }

    private static String[] shrink(String... pToShrink) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;

        for (int i1 = 0; i1 < pToShrink.length; ++i1) {
            String s = pToShrink[i1];
            i = Math.min(i, firstNonSpace(s));
            int j1 = lastNonSpace(s);
            j = Math.max(j, j1);
            if (j1 < 0) {
                if (k == i1) {
                    ++k;
                }

                ++l;
            } else {
                l = 0;
            }
        }

        if (pToShrink.length == l) {
            return new String[0];
        } else {
            String[] astring = new String[pToShrink.length - l - k];

            for (int k1 = 0; k1 < astring.length; ++k1) {
                astring[k1] = pToShrink[k1 + k].substring(i, j + 1);
            }

            return astring;
        }
    }

    private static int firstNonSpace(String pEntry) {
        int i;
        for (i = 0; i < pEntry.length() && pEntry.charAt(i) == ' '; ++i) {
        }

        return i;
    }

    private static int lastNonSpace(String pEntry) {
        int i;
        for (i = pEntry.length() - 1; i >= 0 && pEntry.charAt(i) == ' '; --i) {
        }

        return i;
    }

    private static String[] patternFromJson(JsonArray pPatternArray) {
        String[] astring = new String[pPatternArray.size()];
        if (astring.length > 3) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, " + 3 + " is maximum");
        } else if (astring.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for (int i = 0; i < astring.length; ++i) {
                String s = GsonHelper.convertToString(pPatternArray.get(i), "pattern[" + i + "]");
                if (s.length() > 3) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, " + 3 + " is maximum");
                }

                if (i > 0 && astring[0].length() != s.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }

                astring[i] = s;
            }

            return astring;
        }
    }

    static Map<String, Ingredient> keyFromJson(JsonObject pKeyEntry) {
        Map<String, Ingredient> map = Maps.newHashMap();

        for (Map.Entry<String, JsonElement> entry : pKeyEntry.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + (String) entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }
}
