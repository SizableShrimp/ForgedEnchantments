package me.sizableshrimp.forgedenchantments.mixin;

import me.sizableshrimp.forgedenchantments.item.ForgedEnchantedBookItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
    @Shadow
    private String itemName;

    private AnvilMenuMixin(@Nullable MenuType<?> type, int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(type, containerId, playerInventory, access);
    }

    @Overwrite
    public void setItemName(String newName) {
        this.itemName = newName;
        if (this.getSlot(2).hasItem()) {
            ItemStack itemstack = this.getSlot(2).getItem();
            if (StringUtils.isBlank(newName)) {
                itemstack.resetHoverName();
            } else {
                MutableComponent hoverName = Component.literal(this.itemName);
                if (ForgedEnchantedBookItem.isForgedEnchantment(itemstack))
                    ForgedEnchantedBookItem.copyStyle(itemstack, hoverName);
                itemstack.setHoverName(hoverName);
            }
        }

        this.createResult();
    }

    @Redirect(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;literal(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;"))
    private MutableComponent forgedenchantments$redirectCreateResult_HoverNameChanged(String text) {
        ItemStack leftStack = this.inputSlots.getItem(0);
        MutableComponent name = Component.literal(text);

        if (ForgedEnchantedBookItem.isForgedEnchantment(leftStack))
            ForgedEnchantedBookItem.copyStyle(leftStack, name);

        return name;
    }
}
