package me.sizableshrimp.forgedenchantments.client;

import me.sizableshrimp.forgedenchantments.ForgedEnchantmentsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ForgedEnchantmentsMod.MODID, value = Dist.CLIENT)
public class ClientForgeEvents {
    @SubscribeEvent
    public static void onRecipesUpdated(RecipesUpdatedEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        NonNullList<ItemStack> toAdd = NonNullList.create();

        for (Item item : ForgeRegistries.ITEMS) {
            item.fillItemCategory(CreativeModeTab.TAB_SEARCH, toAdd);
        }

        minecraft.populateSearchTree(SearchRegistry.CREATIVE_NAMES, toAdd);
        minecraft.populateSearchTree(SearchRegistry.CREATIVE_TAGS, toAdd);
    }
}
