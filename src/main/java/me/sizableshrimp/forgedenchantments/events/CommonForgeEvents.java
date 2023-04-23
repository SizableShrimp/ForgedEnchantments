package me.sizableshrimp.forgedenchantments.events;

import me.sizableshrimp.forgedenchantments.ForgedEnchantmentsMod;
import me.sizableshrimp.forgedenchantments.item.ForgedEnchantedBookItem;
import me.sizableshrimp.forgedenchantments.recipe.RecipeUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Mod.EventBusSubscriber(modid = ForgedEnchantmentsMod.MODID)
public class CommonForgeEvents {
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        if (event.getPlayer() == null)
            return;

        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (left.getItem() != Items.ENCHANTED_BOOK || right.getItem() != Items.ENCHANTED_BOOK)
            return;

        String leftType = ForgedEnchantedBookItem.getType(left);
        String rightType = ForgedEnchantedBookItem.getType(right);
        int leftLevel = ForgedEnchantedBookItem.getLevel(left);
        int rightLevel = ForgedEnchantedBookItem.getLevel(right);

        if (leftType.equals(rightType)) {
            if (leftType.isEmpty())
                return; // Default vanilla behavior

            if (leftLevel == rightLevel) {
                mergeForgedUpgrades(event, left, right, leftType, leftLevel);
                return;
            }
        }

        event.setCanceled(true);
    }

    private static void mergeForgedUpgrades(AnvilUpdateEvent event, ItemStack left, ItemStack right, String upgradeType, int upgradeLevel) {
        left = left.copy();
        Map<Enchantment, Integer> leftEnchantments = EnchantmentHelper.getEnchantments(left);
        Map<Enchantment, Integer> rightEnchantments = EnchantmentHelper.getEnchantments(right);
        boolean rightEnchantedBook = true; // right.getItem() == Items.ENCHANTED_BOOK && !rightEnchantments.isEmpty();
        boolean flag2 = false;
        boolean flag3 = false;
        int extraCost = 0;

        for (Enchantment enchantment1 : rightEnchantments.keySet()) {
            if (enchantment1 != null) {
                int i2 = leftEnchantments.getOrDefault(enchantment1, 0);
                int j2 = rightEnchantments.get(enchantment1);
                j2 = i2 == j2 ? j2 + 1 : Math.max(j2, i2);
                boolean flag1 = enchantment1.canEnchant(left);
                if (event.getPlayer().abilities.instabuild || left.getItem() == Items.ENCHANTED_BOOK) {
                    flag1 = true;
                }

                for (Enchantment enchantment : leftEnchantments.keySet()) {
                    if (enchantment != enchantment1 && !enchantment1.isCompatibleWith(enchantment)) {
                        flag1 = false;
                        ++extraCost;
                    }
                }

                if (!flag1) {
                    flag3 = true;
                } else {
                    flag2 = true;
                    if (j2 > enchantment1.getMaxLevel()) {
                        j2 = enchantment1.getMaxLevel();
                    }

                    leftEnchantments.put(enchantment1, j2);
                    int k3 = 0;
                    switch (enchantment1.getRarity()) {
                        case COMMON:
                            k3 = 1;
                            break;
                        case UNCOMMON:
                            k3 = 2;
                            break;
                        case RARE:
                            k3 = 4;
                            break;
                        case VERY_RARE:
                            k3 = 8;
                    }

                    if (rightEnchantedBook) {
                        k3 = Math.max(1, k3 / 2);
                    }

                    extraCost += k3 * j2;
                    if (left.getCount() > 1) {
                        extraCost = 40;
                    }
                }
            }
        }

        if (flag3 && !flag2) {
            event.setCanceled(true);
            return;
        }

        ItemStack upgradedStack = RecipeUtil.findForgedRecipe(event.getPlayer().level, upgradeType, upgradeLevel + 1);
        if (upgradedStack == null || upgradedStack.isEmpty()) {
            event.setCanceled(true);
            return;
        }
        upgradedStack = upgradedStack.copy();

        int renameCost = 0;
        if (StringUtils.isBlank(event.getName())) {
            if (upgradedStack.hasCustomHoverName()) {
                renameCost = 1;
                extraCost += renameCost;
                upgradedStack.resetHoverName();
            }
        } else if (!event.getName().equals(upgradedStack.getHoverName().getString())) {
            renameCost = 1;
            extraCost += renameCost;
            StringTextComponent newName = new StringTextComponent(event.getName());
            ForgedEnchantedBookItem.copyStyle(upgradedStack, newName);
            upgradedStack.setHoverName(newName);
        }
        if (rightEnchantedBook && !upgradedStack.isBookEnchantable(right))
            upgradedStack = ItemStack.EMPTY;

        int cost = event.getCost() + extraCost;
        event.setCost(cost);
        if (extraCost <= 0) {
            upgradedStack = ItemStack.EMPTY;
        }

        if (renameCost == extraCost && renameCost > 0 && cost >= 40) {
            event.setCost(39);
        }

        if (cost >= 40 && !event.getPlayer().abilities.instabuild) {
            upgradedStack = ItemStack.EMPTY;
        }

        if (upgradedStack.isEmpty()) {
            event.setCanceled(true);
        } else {
            int k2 = upgradedStack.getBaseRepairCost();
            if (!right.isEmpty() && k2 < right.getBaseRepairCost()) {
                k2 = right.getBaseRepairCost();
            }

            if (renameCost != extraCost || renameCost == 0) {
                k2 = RepairContainer.calculateIncreasedRepairCost(k2);
            }

            upgradedStack.setRepairCost(k2);
            EnchantmentHelper.setEnchantments(leftEnchantments, upgradedStack);
            event.setOutput(upgradedStack);
        }
    }
}
