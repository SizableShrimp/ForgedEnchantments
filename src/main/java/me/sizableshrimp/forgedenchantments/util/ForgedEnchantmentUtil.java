package me.sizableshrimp.forgedenchantments.util;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import me.sizableshrimp.forgedenchantments.item.ForgedEnchantedBookItem;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

public class ForgedEnchantmentUtil {
    public static Object2IntMap<Enchantment> getEnchantmentsMap(ItemStack stack) {
        ListTag listtag = stack.is(Items.ENCHANTED_BOOK) ? ForgedEnchantedBookItem.getEnchantments(stack) : stack.getEnchantmentTags();
        return deserializeEnchantmentsMap(listtag);
    }

    @SuppressWarnings("deprecation")
    public static Object2IntMap<Enchantment> deserializeEnchantmentsMap(ListTag serialized) {
        Object2IntMap<Enchantment> enchantments = new Object2IntOpenHashMap<>();

        for (int i = 0; i < serialized.size(); i++) {
            CompoundTag enchantmentTag = serialized.getCompound(i);

            Registry.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(enchantmentTag))
                    .ifPresent(enchantment -> enchantments.put(enchantment, EnchantmentHelper.getEnchantmentLevel(enchantmentTag)));
        }

        return enchantments;
    }

    public static void setEnchantmentsMap(Object2IntMap<Enchantment> enchantments, ItemStack stack) {
        ListTag listTag = new ListTag();

        for (var entry : enchantments.object2IntEntrySet()) {
            Enchantment enchantment = entry.getKey();

            if (enchantment != null) {
                int level = entry.getIntValue();

                listTag.add(EnchantmentHelper.storeEnchantment(EnchantmentHelper.getEnchantmentId(enchantment), level));

                if (stack.is(Items.ENCHANTED_BOOK)) {
                    EnchantedBookItem.addEnchantment(stack, new EnchantmentInstance(enchantment, level));
                }
            }
        }

        if (listTag.isEmpty()) {
            stack.removeTagKey("Enchantments");
        } else if (!stack.is(Items.ENCHANTED_BOOK)) {
            stack.addTagElement("Enchantments", listTag);
        }
    }
}
