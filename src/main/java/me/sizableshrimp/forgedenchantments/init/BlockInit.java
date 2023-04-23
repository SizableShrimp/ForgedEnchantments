package me.sizableshrimp.forgedenchantments.init;

import me.sizableshrimp.forgedenchantments.block.ForgedEnchantingTableBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
    public static final DeferredRegister<Block> VANILLA_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");

    public static final RegistryObject<ForgedEnchantingTableBlock> ENCHANTING_TABLE = VANILLA_BLOCKS.register("enchanting_table", () -> new ForgedEnchantingTableBlock(
            BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED)
                    .requiresCorrectToolForDrops()
                    .strength(5.0F, 1200.0F)));
}
