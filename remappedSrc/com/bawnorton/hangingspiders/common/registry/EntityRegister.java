package com.bawnorton.hangingspiders.common.registry;

import com.bawnorton.hangingspiders.HangingSpiders;
import com.bawnorton.hangingspiders.common.entity.HangingSpider;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityRegister {
    public static final EntityType<HangingSpider> HANGING_SPIDER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(HangingSpiders.MODID + ":hangingspider"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HangingSpider::new)
                    .dimensions(EntityDimensions.changing(2F, 0.5F))
                    .build());

    @SuppressWarnings("all") // contract warning with FabricDefaultAttributeRegistry#register
    public static void init() {
        FabricDefaultAttributeRegistry.register(HANGING_SPIDER, HangingSpider.createSpiderAttributes());
    }
}
