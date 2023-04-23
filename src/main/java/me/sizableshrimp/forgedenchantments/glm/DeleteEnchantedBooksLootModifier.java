package me.sizableshrimp.forgedenchantments.glm;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.sizableshrimp.forgedenchantments.item.ForgedEnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class DeleteEnchantedBooksLootModifier extends LootModifier {
    public static final Codec<DeleteEnchantedBooksLootModifier> CODEC = RecordCodecBuilder.create(instance -> codecStart(instance).apply(instance, DeleteEnchantedBooksLootModifier::new));

    private DeleteEnchantedBooksLootModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @NotNull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.removeIf(itemStack -> !ForgedEnchantedBookItem.isForgedEnchantment(itemStack));

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
