package me.sizableshrimp.forgedenchantments.glm;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import me.sizableshrimp.forgedenchantments.item.ForgedEnchantedBookItem;
import me.sizableshrimp.forgedenchantments.util.ForgedEnchantmentUtil;
import me.sizableshrimp.forgedenchantments.util.RecipeUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class ReplaceEnchantedBooksLootModifier extends LootModifier {
    public static final Codec<ReplaceEnchantedBooksLootModifier> CODEC = RecordCodecBuilder.create(instance -> codecStart(instance).apply(instance, ReplaceEnchantedBooksLootModifier::new));

    private ReplaceEnchantedBooksLootModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @NotNull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (ObjectListIterator<ItemStack> iterator = generatedLoot.iterator(); iterator.hasNext(); ) {
            ItemStack itemStack = iterator.next();

            if (itemStack.is(Items.ENCHANTED_BOOK) && !ForgedEnchantedBookItem.isForgedEnchantment(itemStack)) {
                ItemStack result = RecipeUtil.findForgedRecipe(context.getLevel(), ForgedEnchantmentUtil.getEnchantmentsMap(itemStack));

                if (result == null) {
                    iterator.remove();
                } else {
                    iterator.set(result);
                }
            }
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
