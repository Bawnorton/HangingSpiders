package com.bawnorton.hangingspiders.common.registry;

import com.bawnorton.hangingspiders.HangingSpiders;
import com.bawnorton.hangingspiders.common.entity.HangingSpider;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityRegister {
    public static final EntityType<HangingSpider> HANGING_SPIDER = Registry.register(Registry.ENTITY_TYPE,
            85, "hangingspiders:spider",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HangingSpider::new)
                    .dimensions(EntityDimensions.changing(1.4F, 0.9F))
                    .build());

    @SuppressWarnings("all") // contract warning with FabricDefaultAttributeRegistry#register
    public static void init() {
        FabricDefaultAttributeRegistry.register(HANGING_SPIDER, HangingSpider.createSpiderAttributes());

        Identifier identifier = new Identifier(HangingSpiders.MODID + ":hangingspider");
        Registry.register(Registry.ITEM,
                921, "hangingspiders:spider_spawn_egg",
                new SpawnEggItem((EntityType<? extends MobEntity>) HANGING_SPIDER,
                        0x352E28, 0xAB0E0E,
                        (new Item.Settings()).group(ItemGroup.MISC)));
    }
}
