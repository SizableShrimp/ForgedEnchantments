package me.sizableshrimp.forgedenchantments.client;

import me.sizableshrimp.forgedenchantments.ForgedEnchantmentsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.IMutableSearchTree;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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
        IMutableSearchTree<ItemStack> searchTreeNames = minecraft.getSearchTree(SearchTreeManager.CREATIVE_NAMES);
        IMutableSearchTree<ItemStack> searchTreeTags = minecraft.getSearchTree(SearchTreeManager.CREATIVE_TAGS);

        searchTreeNames.clear();
        searchTreeTags.clear();

        NonNullList<ItemStack> toAdd = NonNullList.create();

        for (Item item : ForgeRegistries.ITEMS) {
            item.fillItemCategory(ItemGroup.TAB_SEARCH, toAdd);
        }

        for (ItemStack itemStack : toAdd) {
            searchTreeNames.add(itemStack);
            searchTreeTags.add(itemStack);
        }

        searchTreeNames.refresh();
        searchTreeTags.refresh();
    }
}
