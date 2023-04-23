package me.sizableshrimp.forgedenchantments.init;

import me.sizableshrimp.forgedenchantments.item.ForgedEnchantedBookItem;
import me.sizableshrimp.forgedenchantments.item.UselessBlockItem;
import me.sizableshrimp.forgedenchantments.client.ClientISTERs;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> VANILLA_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<ForgedEnchantedBookItem> ENCHANTED_BOOK = VANILLA_ITEMS.register("enchanted_book", () -> new ForgedEnchantedBookItem(new Item.Properties()
            .stacksTo(1)
            .rarity(Rarity.UNCOMMON)
            .setISTER(() -> ClientISTERs::getEnchantedBookISTER)));
    public static final RegistryObject<UselessBlockItem> ENCHANTING_TABLE = VANILLA_ITEMS.register("enchanting_table", () -> new UselessBlockItem(BlockInit.ENCHANTING_TABLE.get(),
            new Item.Properties()));
}
