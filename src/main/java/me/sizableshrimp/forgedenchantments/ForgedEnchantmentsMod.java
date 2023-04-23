package me.sizableshrimp.forgedenchantments;

import me.sizableshrimp.forgedenchantments.client.ClientConfig;
import me.sizableshrimp.forgedenchantments.init.BlockInit;
import me.sizableshrimp.forgedenchantments.init.GlobalLootModifierSerializerInit;
import me.sizableshrimp.forgedenchantments.init.ItemInit;
import me.sizableshrimp.forgedenchantments.init.RecipeSerializerInit;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ForgedEnchantmentsMod.MODID)
public class ForgedEnchantmentsMod {
    public static final String MODID = "forged_enchantments";
    public static final Logger LOGGER = LogManager.getLogger();

    public ForgedEnchantmentsMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockInit.VANILLA_BLOCKS.register(modBus);
        ItemInit.VANILLA_ITEMS.register(modBus);
        GlobalLootModifierSerializerInit.GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(modBus);
        RecipeSerializerInit.RECIPE_SERIALIZERS.register(modBus);

        ClientConfig.register();
    }
}
