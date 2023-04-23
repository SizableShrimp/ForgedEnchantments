package me.sizableshrimp.forgedenchantments.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.sizableshrimp.forgedenchantments.ForgedEnchantmentsMod;
import me.sizableshrimp.forgedenchantments.item.ForgedEnchantedBookItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ForgedEnchantmentISTER extends ItemStackTileEntityRenderer {
    public static final ModelResourceLocation DEFAULT_MODEL = new ModelResourceLocation(new ResourceLocation(ForgedEnchantmentsMod.MODID, "default_enchanted_book"), "inventory");
    // private static final ItemModelGenerator ITEM_MODEL_GENERATOR = new ItemModelGenerator();
    // private final Map<String, IBakedModel> bakedModelCache = new HashMap<>();
    private final Map<String, ModelResourceLocation> resourceLocationCache = new HashMap<>();

    @Override
    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack poseStack, IRenderTypeBuffer buffer, int packedLight, int packedOverlay) {
        poseStack.popPose();

        int level = ForgedEnchantedBookItem.getLevel(stack);
        ModelResourceLocation modelLoc = DEFAULT_MODEL;
        if (level > 0) {
            String type = ForgedEnchantedBookItem.getType(stack);
            if (!type.isEmpty()) {
                String modelStr = ClientConfig.INSTANCE.getModelMapping(type, level);
                if (modelStr != null && !modelStr.isEmpty())
                    modelLoc = this.resourceLocationCache.computeIfAbsent(modelStr, ModelResourceLocation::new);
            }
        }

        // String modelStr = stack.hasTag() ? stack.getTag().getString("Model") : "";
        //
        // ModelResourceLocation modelLoc;
        // if (!modelStr.isEmpty()) {
        //     modelLoc = this.resourceLocationCache.computeIfAbsent(modelStr, ModelResourceLocation::new);
        // } else {
        //     modelLoc = DEFAULT_MODEL;
        // }

        IBakedModel bakedModel = Minecraft.getInstance().getModelManager().getModel(modelLoc);

        // String textureStr = stack.getTag().getString("Texture");
        // IBakedModel bakedModel = this.bakedModelCache.computeIfAbsent(textureStr, k -> {
        //     ModelLoader modelLoader = ModelLoader.instance();
        //     Function<RenderMaterial, TextureAtlasSprite> textureGetter = modelLoader.getSpriteMap()::getSprite;
        //     HashMap<String, Either<RenderMaterial, String>> textureMap = new HashMap<>();
        //     textureMap.put("layer0", Either.left(new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(k))));
        //     BlockModel blockModel = new BlockModel(new ResourceLocation("builtin/generated"), new ArrayList<>(), textureMap, true, null, ItemCameraTransforms.NO_TRANSFORMS, new ArrayList<>());
        //     blockModel.parent = ModelBakery.GENERATION_MARKER;
        //
        //     return ITEM_MODEL_GENERATOR.generateBlockModel(textureGetter, blockModel).bake(modelLoader, blockModel, textureGetter, ModelRotation.X0_Y0, stack.getItem().getRegistryName(), false);
        // });

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        itemRenderer.render(stack, transformType, false, poseStack, buffer, packedLight, packedOverlay, bakedModel);

        poseStack.pushPose();
    }
}
