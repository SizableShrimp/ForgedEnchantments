package me.sizableshrimp.forgedenchantments.init;

import me.sizableshrimp.forgedenchantments.ForgedEnchantmentsMod;
import me.sizableshrimp.forgedenchantments.glm.DeleteEnchantedBooksLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GlobalLootModifierSerializerInit {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, ForgedEnchantmentsMod.MODID);

    public static final RegistryObject<DeleteEnchantedBooksLootModifier.Serializer> DELETE_ENCHANTED_BOOKS = LOOT_MODIFIER_SERIALIZERS.register("delete_enchanted_books",
            DeleteEnchantedBooksLootModifier.Serializer::new);
}
