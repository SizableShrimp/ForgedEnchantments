package me.sizableshrimp.forgedenchantments.init;

import me.sizableshrimp.forgedenchantments.item.ForgedEnchantedBookItem;
import me.sizableshrimp.forgedenchantments.item.UselessBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> VANILLA_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<ForgedEnchantedBookItem> ENCHANTED_BOOK = VANILLA_ITEMS.register("enchanted_book", () -> new ForgedEnchantedBookItem(new Item.Properties()
            .stacksTo(1)
            .rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<UselessBlockItem> ENCHANTING_TABLE = VANILLA_ITEMS.register("enchanting_table", () -> new UselessBlockItem(BlockInit.ENCHANTING_TABLE.get(),
            new Item.Properties()));
}
