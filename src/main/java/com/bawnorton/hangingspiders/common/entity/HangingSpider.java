package com.bawnorton.hangingspiders.common.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.HashMap;

public class HangingSpider extends SpiderEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private static final TrackedData<Boolean> ATTACKING;
    private static final TrackedData<Integer> ROTATION;

    private static final HashMap<Integer, Quaternion> rotations = new HashMap<>() {{
        put(0, new Quaternion(Vec3f.POSITIVE_X, 180,true));
        put(1, new Quaternion(Vec3f.POSITIVE_X, 0,true));
        put(2, new Quaternion(Vec3f.POSITIVE_X, 90,true));
        put(3, new Quaternion(Vec3f.POSITIVE_Z, 90,true));
        put(4, new Quaternion(Vec3f.NEGATIVE_X, 90,true));
        put(5, new Quaternion(Vec3f.NEGATIVE_Z, 90,true));
    }};

    public HangingSpider(EntityType<? extends HangingSpider> entityType, World world) {
        super(entityType, world);
        this.lookControl = new LookControl(this);
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos pos = this.getBlockPos();
        BlockPos[] offsets = new BlockPos[]{pos.up(), pos.down(), pos.north(), pos.east(), pos.south(), pos.west()};
        this.dataTracker.set(ROTATION, 1);
        for(int i = 0; i < offsets.length; i++) {
            BlockPos offset = offsets[i];
            if(world.getBlockState(offset).isSolidBlock(world, offset)) {
                this.dataTracker.set(ROTATION, i);
            }
        }
    }

    @Override
    public void setClimbingWall(boolean climbing) {}

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(ROTATION, 1);
    }

    static {
        ATTACKING = DataTracker.registerData(HangingSpider.class, TrackedDataHandlerRegistry.BOOLEAN);
        ROTATION = DataTracker.registerData(HangingSpider.class, TrackedDataHandlerRegistry.INTEGER);
    }

    @Override
    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }

    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }

    public Quaternion getRotation() {
        return rotations.get(this.dataTracker.get(ROTATION));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationController<?> controller = event.getController();
        controller.setAnimationSpeed(1D);
        if (event.isMoving()) {
            controller.setAnimationSpeed(1.3);
            controller.setAnimation(new AnimationBuilder()
                    .addAnimation("animation.hangingspider.walk", true));
        } else {
            controller.setAnimation(new AnimationBuilder()
                    .addAnimation("animation.hangingspider.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 1, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
