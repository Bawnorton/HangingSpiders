package com.bawnorton.hangingspiders.client;

import com.bawnorton.hangingspiders.common.registry.EntityRegister;
import com.bawnorton.hangingspiders.common.render.HangingSpiderRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class HangingSpidersClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityRegister.HANGING_SPIDER, HangingSpiderRenderer::new);
    }
}
