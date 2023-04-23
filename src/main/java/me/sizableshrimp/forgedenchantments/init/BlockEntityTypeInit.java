package me.sizableshrimp.forgedenchantments.init;

import me.sizableshrimp.forgedenchantments.ForgedEnchantmentsMod;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityTypeInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ForgedEnchantmentsMod.MODID);

    public static final RegistryObject<BlockEntityType<EnchantmentTableBlockEntity>> ENCHANTING_TABLE = BLOCK_ENTITY_TYPES.register("enchanting_table",
            () -> BlockEntityType.Builder.of(EnchantmentTableBlockEntity::new, BlockInit.ENCHANTING_TABLE.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "enchanting_table")));
}
