package me.sizableshrimp.forgedenchantments.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ForgedEnchantingTableBlock extends EnchantingTableBlock {
    public ForgedEnchantingTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return super.newBlockEntity(p_196283_1_);
    }

    @Override
    public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        return ActionResultType.PASS;
    }

    @Nullable
    @Override
    public INamedContainerProvider getMenuProvider(BlockState state, World level, BlockPos pos) {
        // Don't allow opening at all
        return null;
    }
}
