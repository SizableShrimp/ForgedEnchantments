package me.sizableshrimp.forgedenchantments.client;

import com.electronwill.nightconfig.core.Config;
import com.google.common.collect.ImmutableList;
import me.sizableshrimp.forgedenchantments.ForgedEnchantmentsMod;
import net.minecraft.ResourceLocationException;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ClientConfig {
    public static final ClientConfig INSTANCE;
    private static final ForgeConfigSpec spec;

    // public final ForgeConfigSpec.ConfigValue<List<String>> modelSearchPaths;
    public final ForgeConfigSpec.ConfigValue<Config> modelMappings;

    static {
        Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        INSTANCE = specPair.getLeft();
        spec = specPair.getRight();
    }

    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, spec);
    }

    private ClientConfig(ForgeConfigSpec.Builder builder) {
        // this.modelSearchPaths = builder
        //         .comment("A list of resource locations paths to search for models. Paths are relative to assets directory. Example: minecraft:models/item")
        //         .define("modelSearchPaths", ImmutableList.of(), o -> o instanceof List);

        Config defaultModelMappings = Config.inMemory();
        defaultModelMappings.add(ImmutableList.of("example", "1"), "minecraft:apple#inventory");
        this.modelMappings = builder
                .comment("A mapping of upgrade types and levels to their model path. Format is [modelMappings.(upgrade type)] (level) = \"(model)\"",
                        "NOTE: You must restart the game to add new models; otherwise, they will not load and show as purple/black.")
                .define("modelMappings", defaultModelMappings, this::validateModelMappings);
    }

    @Nullable
    public String getModelMapping(String type, int level) {
        Config typeMappings = this.modelMappings.get().get(type);

        if (typeMappings == null)
            return null;

        return typeMappings.get(String.valueOf(level));
    }

    private boolean validateModelMappings(Object obj) {
        if (!(obj instanceof Config config))
            return false;

        for (Map.Entry<String, Object> entry : config.valueMap().entrySet()) {
            String upgradeType = entry.getKey();

            if (entry.getValue() instanceof Config levelMappings) {
                for (Map.Entry<String, Object> levelEntry : levelMappings.valueMap().entrySet()) {
                    int level;
                    try {
                        level = Integer.parseInt(levelEntry.getKey());
                    } catch (NumberFormatException e) {
                        ForgedEnchantmentsMod.LOGGER.warn("Invalid level \"{}\" for upgrade type \"{}\" in client config; not an integer", levelEntry.getKey(), upgradeType);
                        return false;
                    }

                    if (levelEntry.getValue() instanceof String modelStr) {
                        try {
                            new ModelResourceLocation(modelStr);
                        } catch (ResourceLocationException e) {
                            ForgedEnchantmentsMod.LOGGER.warn("Invalid entry \"{}\" for upgrade type \"{}\" level {} in client config; not a valid resource location",
                                    levelEntry.getValue(), upgradeType, level);
                        }
                    } else {
                        ForgedEnchantmentsMod.LOGGER.warn("Invalid entry \"{}\" for upgrade type \"{}\" level {} in client config; not a string",
                                levelEntry.getValue(), upgradeType, level);
                    }
                }
            }
        }

        return true;
    }
}
