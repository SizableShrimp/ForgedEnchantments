package me.sizableshrimp.forgedenchantments.events;

import com.google.common.collect.ImmutableSet;
import me.sizableshrimp.forgedenchantments.ForgedEnchantmentsMod;
import me.sizableshrimp.forgedenchantments.ingredient.ForgedEnchantmentIngredient;
import me.sizableshrimp.forgedenchantments.init.BlockInit;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = ForgedEnchantmentsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void onRegisterBlockEntityTypes(RegisterEvent event) {
        try {
            Field validBlocksField = ObfuscationReflectionHelper.findField(BlockEntityType.class, "f_58915_");
            Set<Block> validBlocks = new HashSet<>();
            validBlocks.add(BlockInit.ENCHANTING_TABLE.get());
            validBlocksField.set(BlockEntityType.ENCHANTING_TABLE, ImmutableSet.copyOf(validBlocks));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public static void onRegisterRecipeSerializers(RegisterEvent event) {
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.RECIPE_SERIALIZERS))
            CraftingHelper.register(ForgedEnchantmentIngredient.Serializer.NAME, ForgedEnchantmentIngredient.Serializer.INSTANCE);
    }
}
