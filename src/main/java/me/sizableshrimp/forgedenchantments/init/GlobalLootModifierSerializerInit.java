package me.sizableshrimp.forgedenchantments.init;

import com.mojang.serialization.Codec;
import me.sizableshrimp.forgedenchantments.ForgedEnchantmentsMod;
import me.sizableshrimp.forgedenchantments.glm.DeleteEnchantedBooksLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GlobalLootModifierSerializerInit {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS,
            ForgedEnchantmentsMod.MODID);

    public static final RegistryObject<Codec<DeleteEnchantedBooksLootModifier>> DELETE_ENCHANTED_BOOKS = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("delete_enchanted_books",
            () -> DeleteEnchantedBooksLootModifier.CODEC);
}
