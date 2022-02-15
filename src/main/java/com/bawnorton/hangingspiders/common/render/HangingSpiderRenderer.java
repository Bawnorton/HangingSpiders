package com.bawnorton.hangingspiders.common.render;

import com.bawnorton.hangingspiders.common.entity.HangingSpider;
import com.bawnorton.hangingspiders.common.model.HangingSpiderModel;
import com.bawnorton.hangingspiders.common.model.SpiderEyesLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.*;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.HashMap;

public class HangingSpiderRenderer extends GeoEntityRenderer<HangingSpider> {

    public HangingSpiderRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new HangingSpiderModel());
        this.addLayer(new SpiderEyesLayer(this));
    }

    @Override
    public void render(HangingSpider entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        if(entity.isUpsideDown()) {
            stack.multiply(new Quaternion(Vec3f.POSITIVE_X, 180,true));
            stack.translate(0, -0.9F, 0);
        }
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
}
