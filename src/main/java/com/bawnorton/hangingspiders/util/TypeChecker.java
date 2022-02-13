package com.bawnorton.hangingspiders.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.Objects;

public class TypeChecker {
    public static boolean isSpider(EntityType<? extends Entity> entityType) {
        return Objects.equals(String.valueOf(entityType), "entity.minecraft.spider");
    }
}
