package me.sizableshrimp.forgedenchantments.mixin;

import me.sizableshrimp.forgedenchantments.item.ForgedEnchantedBookItem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractRepairContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.text.StringTextComponent;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;

@Mixin(RepairContainer.class)
public abstract class RepairContainerMixin extends AbstractRepairContainer {
    @Shadow
    private String itemName;

    private RepairContainerMixin(@Nullable ContainerType<?> type, int containerId, PlayerInventory playerInventory, IWorldPosCallable access) {
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
                StringTextComponent hoverName = new StringTextComponent(this.itemName);
                if (ForgedEnchantedBookItem.isForgedEnchantment(itemstack))
                    ForgedEnchantedBookItem.copyStyle(itemstack, hoverName);
                itemstack.setHoverName(hoverName);
            }
        }

        this.createResult();
    }

    @SuppressWarnings({"InvalidMemberReference", "MixinAnnotationTarget", "InvalidInjectorMethodSignature", "UnresolvedMixinReference"})
    @Redirect(method = "createResult", at = @At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/util/text/StringTextComponent;"))
    private StringTextComponent forgedenchantments$redirectCreateResult_HoverNameChanged(String text) {
        ItemStack leftStack = this.inputSlots.getItem(0);
        StringTextComponent name = new StringTextComponent(text);

        if (ForgedEnchantedBookItem.isForgedEnchantment(leftStack))
            ForgedEnchantedBookItem.copyStyle(leftStack, name);

        return name;
    }
}
