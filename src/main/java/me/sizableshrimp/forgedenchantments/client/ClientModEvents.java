package me.sizableshrimp.forgedenchantments.client;

import com.electronwill.nightconfig.core.Config;
import me.sizableshrimp.forgedenchantments.ForgedEnchantmentsMod;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = ForgedEnchantmentsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        ModelLoader.addSpecialModel(ForgedEnchantmentISTER.DEFAULT_MODEL);

        // for (String filter : ClientConfig.INSTANCE.modelSearchPaths.get()) {
        //     ResourceLocation baseLocation = new ResourceLocation(filter);
        //
        //     for (ResourceLocation foundLocation : Minecraft.getInstance().getResourceManager().listResources(baseLocation.getPath(), s -> s.endsWith(".json"))) {
        //         if (baseLocation.getNamespace().equals(foundLocation.getNamespace())) {
        //             String foundPath = foundLocation.getPath().substring(0, foundLocation.getPath().length() - 5);
        //             if (foundPath.startsWith("models/")) {
        //                 foundPath = foundPath.substring(7);
        //                 if (foundPath.startsWith("block/")) {
        //                     foundPath = foundPath.substring(6);
        //                 } else if (foundPath.startsWith("item/")) {
        //                     foundPath = foundPath.substring(5);
        //                 }
        //             }
        //             ModelLoader.addSpecialModel(new ModelResourceLocation(new ResourceLocation(baseLocation.getNamespace(), foundPath), "inventory"));
        //         }
        //     }
        // }

        for (Map.Entry<String, Object> entry : ClientConfig.INSTANCE.modelMappings.get().valueMap().entrySet()) {
            if (entry.getValue() instanceof Config) {
                Config levelMappings = (Config) entry.getValue();

                for (Map.Entry<String, Object> levelEntry : levelMappings.valueMap().entrySet()) {
                    if (levelEntry.getValue() instanceof String) {
                        String modelLoc = (String) levelEntry.getValue();
                        ModelLoader.addSpecialModel(new ModelResourceLocation(modelLoc));
                    }
                }
            }
        }
    }
}
