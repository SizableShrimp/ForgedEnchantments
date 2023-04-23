package me.sizableshrimp.forgedenchantments.init;

import me.sizableshrimp.forgedenchantments.block.ForgedEnchantingTableBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> VANILLA_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");

    public static final RegistryObject<ForgedEnchantingTableBlock> ENCHANTING_TABLE = VANILLA_BLOCKS.register("enchanting_table", () -> new ForgedEnchantingTableBlock(
            AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_RED)
                    .requiresCorrectToolForDrops()
                    .strength(5.0F, 1200.0F)));
}
