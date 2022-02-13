package com.bawnorton.hangingspiders.common.model;

import com.bawnorton.hangingspiders.HangingSpiders;
import com.bawnorton.hangingspiders.common.entity.HangingSpider;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class SpiderEyesLayer extends GeoLayerRenderer<HangingSpider> {
    private final Identifier SPIDER_EYES;
    private final Identifier SPIDER_MODEL;

    public SpiderEyesLayer(IGeoRenderer<HangingSpider> entityRendererIn) {
        super(entityRendererIn);
        SPIDER_EYES = new Identifier(HangingSpiders.MODID, "textures/entity/hangingspider/spider_eyes.png");
        SPIDER_MODEL = new Identifier(HangingSpiders.MODID, "geo/hangingspider.geo.json");
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, HangingSpider entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getRenderer().render(getEntityModel().getModel(SPIDER_MODEL),
                entity, partialTicks, RenderLayer.getEyes(SPIDER_EYES),
                matrixStackIn, bufferIn, bufferIn.getBuffer(RenderLayer.getEyes(SPIDER_EYES)),
                packedLightIn, OverlayTexture.packUv(0, OverlayTexture.getV(entity.hurtTime > 0)),
                1F, 1F, 1F, 1F);
    }
}
