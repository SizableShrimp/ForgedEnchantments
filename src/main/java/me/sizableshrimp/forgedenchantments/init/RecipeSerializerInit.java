package me.sizableshrimp.forgedenchantments.init;

import me.sizableshrimp.forgedenchantments.recipe.ForgedEnchantmentShapedRecipeSerializer;
import me.sizableshrimp.forgedenchantments.recipe.ForgedEnchantmentShapelessRecipeSerializer;
import me.sizableshrimp.forgedenchantments.ForgedEnchantmentsMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializerInit {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ForgedEnchantmentsMod.MODID);

    public static final RegistryObject<ForgedEnchantmentShapedRecipeSerializer> FORGED_ENCHANTMENT_SHAPED = RECIPE_SERIALIZERS.register("forged_enchantment_shaped",
            ForgedEnchantmentShapedRecipeSerializer::new);
    public static final RegistryObject<ForgedEnchantmentShapelessRecipeSerializer> FORGED_ENCHANTMENT_SHAPELESS = RECIPE_SERIALIZERS.register("forged_enchantment_shapeless",
            ForgedEnchantmentShapelessRecipeSerializer::new);
}
