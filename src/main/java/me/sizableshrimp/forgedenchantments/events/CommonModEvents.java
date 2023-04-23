package me.sizableshrimp.forgedenchantments.events;

import me.sizableshrimp.forgedenchantments.ForgedEnchantmentsMod;
import me.sizableshrimp.forgedenchantments.ingredient.ForgedEnchantmentIngredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = ForgedEnchantmentsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void onRegisterRecipeSerializers(RegisterEvent event) {
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.RECIPE_SERIALIZERS))
            CraftingHelper.register(ForgedEnchantmentIngredient.Serializer.NAME, ForgedEnchantmentIngredient.Serializer.INSTANCE);
    }
}
