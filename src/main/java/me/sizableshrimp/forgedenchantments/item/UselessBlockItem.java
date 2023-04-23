package me.sizableshrimp.forgedenchantments.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;

import javax.annotation.Nullable;

public class UselessBlockItem extends BlockItem {
    public UselessBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockItemUseContext context) {
        return null;
    }
}
