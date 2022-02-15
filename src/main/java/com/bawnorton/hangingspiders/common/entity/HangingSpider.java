package com.bawnorton.hangingspiders.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
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
    private static final TrackedData<Boolean> UPSIDE_DOWN;

    public HangingSpider(EntityType<? extends HangingSpider> entityType, World world) {
        super(entityType, world);
        this.lookControl = new LookControl(this);
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos up = this.getBlockPos().up();
        this.dataTracker.set(UPSIDE_DOWN, false);
        if(world.getBlockState(up).isSolidBlock(world, up)) {
            this.dataTracker.set(UPSIDE_DOWN, true);
        }
    }

    @Override
    public void setClimbingWall(boolean climbing) {}

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(UPSIDE_DOWN, false);
    }

    static {
        ATTACKING = DataTracker.registerData(HangingSpider.class, TrackedDataHandlerRegistry.BOOLEAN);
        UPSIDE_DOWN = DataTracker.registerData(HangingSpider.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
    @Override
    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }

    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }

    public boolean isUpsideDown() {
        return this.dataTracker.get(UPSIDE_DOWN);
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
