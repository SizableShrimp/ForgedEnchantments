package me.sizableshrimp.forgedenchantments.events;

import com.google.common.collect.ImmutableSet;
import me.sizableshrimp.forgedenchantments.ForgedEnchantmentsMod;
import me.sizableshrimp.forgedenchantments.ingredient.ForgedEnchantmentIngredient;
import me.sizableshrimp.forgedenchantments.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = ForgedEnchantmentsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void onRegisterTileEntityTypes(RegistryEvent<TileEntityType<?>> event) {
        try {
            Field field = ObfuscationReflectionHelper.findField(TileEntityType.class, "field_223046_I");
            Set<Block> validBlocks = new HashSet<>((Set<Block>) field.get(TileEntityType.ENCHANTING_TABLE));
            validBlocks.add(BlockInit.ENCHANTING_TABLE.get());
            field.set(TileEntityType.ENCHANTING_TABLE, ImmutableSet.copyOf(validBlocks));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public static void onRegisterRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        CraftingHelper.register(ForgedEnchantmentIngredient.Serializer.NAME, ForgedEnchantmentIngredient.Serializer.INSTANCE);
    }
}
