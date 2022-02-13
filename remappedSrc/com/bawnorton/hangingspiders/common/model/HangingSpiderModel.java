package com.bawnorton.hangingspiders.common.model;

import com.bawnorton.hangingspiders.HangingSpiders;
import com.bawnorton.hangingspiders.common.entity.HangingSpider;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HangingSpiderModel extends AnimatedGeoModel<HangingSpider> {

    @Override
    public Identifier getModelLocation(HangingSpider object) {
        return new Identifier(HangingSpiders.MODID, "geo/hangingspider.geo.json");
    }

    @Override
    public Identifier getTextureLocation(HangingSpider object) {
        return new Identifier(HangingSpiders.MODID, "textures/entity/hangingspider/hangingspider.png");
    }

    @Override
    public Identifier getAnimationFileLocation(HangingSpider animatable) {
        return new Identifier(HangingSpiders.MODID, "animations/hangingspider.animation.json");
    }
}
