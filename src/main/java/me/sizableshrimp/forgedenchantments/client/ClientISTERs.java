package me.sizableshrimp.forgedenchantments.client;

import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;

public class ClientISTERs {
    public static ItemStackTileEntityRenderer getEnchantedBookISTER() {
        return new ForgedEnchantmentISTER();
    }
}
