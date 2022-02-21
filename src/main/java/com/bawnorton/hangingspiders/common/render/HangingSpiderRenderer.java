package com.bawnorton.hangingspiders.common.render;

import com.bawnorton.hangingspiders.common.entity.HangingSpider;
import com.bawnorton.hangingspiders.common.model.HangingSpiderModel;
import com.bawnorton.hangingspiders.common.model.SpiderEyesLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HangingSpiderRenderer extends GeoEntityRenderer<HangingSpider> {

    public HangingSpiderRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new HangingSpiderModel());
        this.addLayer(new SpiderEyesLayer(this));
    }

    @Override
    public void render(HangingSpider entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
}
