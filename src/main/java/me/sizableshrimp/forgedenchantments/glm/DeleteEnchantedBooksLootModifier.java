package me.sizableshrimp.forgedenchantments.glm;

import com.google.gson.JsonObject;
import me.sizableshrimp.forgedenchantments.item.ForgedEnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class DeleteEnchantedBooksLootModifier extends LootModifier {
    protected DeleteEnchantedBooksLootModifier(ILootCondition[] conditions) {
        super(conditions);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.removeIf(itemStack -> !ForgedEnchantedBookItem.isForgedEnchantment(itemStack));

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<DeleteEnchantedBooksLootModifier> {
        @Override
        public DeleteEnchantedBooksLootModifier read(ResourceLocation location, JsonObject json, ILootCondition[] lootConditions) {
            return new DeleteEnchantedBooksLootModifier(lootConditions);
        }

        @Override
        public JsonObject write(DeleteEnchantedBooksLootModifier instance) {
            return this.makeConditions(instance.conditions);
        }
    }
}
