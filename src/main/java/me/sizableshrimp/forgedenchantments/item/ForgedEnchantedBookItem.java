package me.sizableshrimp.forgedenchantments.item;

import me.sizableshrimp.forgedenchantments.client.ClientItemGroupFiller;
import me.sizableshrimp.forgedenchantments.client.ForgedEnchantedBookBEWLR;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.function.Consumer;

public class ForgedEnchantedBookItem extends EnchantedBookItem {
    public ForgedEnchantedBookItem(Properties properties) {
        super(properties);
    }

    public static boolean isForgedEnchantment(ItemStack itemStack) {
        return itemStack.hasTag() && itemStack.getTag().contains("UpgradeType", Tag.TAG_STRING);
    }

    public static String getType(ItemStack itemStack) {
        return itemStack.hasTag() ? itemStack.getTag().getString("UpgradeType") : "";
    }

    public static int getLevel(ItemStack itemStack) {
        return itemStack.hasTag() && itemStack.getTag().contains("UpgradeLevel", Tag.TAG_INT) ? itemStack.getTag().getInt("UpgradeLevel") : -1;
    }

    public static void copyStyle(ItemStack from, MutableComponent to) {
        to.setStyle(from.getItem().getName(from).getStyle());
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return !stack.hasTag() || !ForgedEnchantedBookItem.isForgedEnchantment(stack) || stack.getTag().getBoolean("Glint");
    }

    @Override
    public Component getName(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("UpgradeName", Tag.TAG_STRING))
            return Component.Serializer.fromJson(stack.getTag().getString("UpgradeName"));

        return super.getName(stack);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (FMLEnvironment.dist == Dist.CLIENT)
            ClientItemGroupFiller.fillEnchantedBooks(group, items);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private ForgedEnchantedBookBEWLR renderer;

            @Override
            public ForgedEnchantedBookBEWLR getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new ForgedEnchantedBookBEWLR();

                return this.renderer;
            }
        });
    }
}
