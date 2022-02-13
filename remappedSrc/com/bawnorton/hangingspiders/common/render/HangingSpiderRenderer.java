package com.bawnorton.hangingspiders.common.render;

import com.bawnorton.hangingspiders.common.entity.HangingSpider;
import com.bawnorton.hangingspiders.common.model.HangingSpiderModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HangingSpiderRenderer extends GeoEntityRenderer<HangingSpider> {

    public HangingSpiderRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new HangingSpiderModel());
    }
}
