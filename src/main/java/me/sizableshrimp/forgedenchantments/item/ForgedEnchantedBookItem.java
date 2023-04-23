package me.sizableshrimp.forgedenchantments.item;

import me.sizableshrimp.forgedenchantments.client.ClientItemGroupFiller;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class ForgedEnchantedBookItem extends EnchantedBookItem {
    public ForgedEnchantedBookItem(Properties properties) {
        super(properties);
    }

    public static boolean isForgedEnchantment(ItemStack itemStack) {
        return itemStack.hasTag() && itemStack.getTag().contains("UpgradeType", Constants.NBT.TAG_STRING);
    }

    public static String getType(ItemStack itemStack) {
        return itemStack.hasTag() ? itemStack.getTag().getString("UpgradeType") : "";
    }

    public static int getLevel(ItemStack itemStack) {
        return itemStack.hasTag() && itemStack.getTag().contains("UpgradeLevel", Constants.NBT.TAG_INT) ? itemStack.getTag().getInt("UpgradeLevel") : -1;
    }

    public static void copyStyle(ItemStack from, TextComponent to) {
        to.setStyle(from.getItem().getName(from).getStyle());
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return !stack.hasTag() || !ForgedEnchantedBookItem.isForgedEnchantment(stack) || stack.getTag().getBoolean("Glint");
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("UpgradeName", Constants.NBT.TAG_STRING))
            return ITextComponent.Serializer.fromJson(stack.getTag().getString("UpgradeName"));

        return super.getName(stack);
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if (FMLEnvironment.dist == Dist.CLIENT)
            ClientItemGroupFiller.fillEnchantedBooks(group, items);
    }
}
