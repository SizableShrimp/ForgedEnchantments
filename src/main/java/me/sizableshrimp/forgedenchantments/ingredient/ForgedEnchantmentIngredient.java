package me.sizableshrimp.forgedenchantments.ingredient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.sizableshrimp.forgedenchantments.ForgedEnchantmentsMod;
import me.sizableshrimp.forgedenchantments.item.ForgedEnchantedBookItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class ForgedEnchantmentIngredient extends Ingredient {
    private final String type;
    private final int level;

    public ForgedEnchantmentIngredient(String type, int level) {
        super(Stream.of(new Ingredient.ItemValue(new ItemStack(Items.ENCHANTED_BOOK))));
        this.type = type;
        this.level = level;
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        if (stack == null || !stack.is(Items.ENCHANTED_BOOK) || !stack.hasTag())
            return false;

        return ForgedEnchantedBookItem.getLevel(stack) == this.level && ForgedEnchantedBookItem.getType(stack).equals(this.type);
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public JsonElement toJson() {
        JsonObject json = new JsonObject();

        json.addProperty("type", Serializer.NAME.toString());
        json.addProperty("upgrade_type", this.type);
        json.addProperty("level", this.level);

        return json;
    }

    public static class Serializer implements IIngredientSerializer<ForgedEnchantmentIngredient> {
        public static final ResourceLocation NAME = new ResourceLocation(ForgedEnchantmentsMod.MODID, "forged_enchantment");
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {}

        @Override
        public ForgedEnchantmentIngredient parse(FriendlyByteBuf buffer) {
            return new ForgedEnchantmentIngredient(buffer.readUtf(), buffer.readVarInt());
        }

        @Override
        public ForgedEnchantmentIngredient parse(JsonObject json) {
            return new ForgedEnchantmentIngredient(GsonHelper.getAsString(json, "upgrade_type"), GsonHelper.getAsInt(json, "level"));
        }

        @Override
        public void write(FriendlyByteBuf buffer, ForgedEnchantmentIngredient ingredient) {
            buffer.writeUtf(ingredient.type);
            buffer.writeVarInt(ingredient.level);
        }
    }
}
