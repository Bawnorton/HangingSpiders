package com.bawnorton.hangingspiders.common.entity;

import com.bawnorton.hangingspiders.HangingSpiders;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class HangingSpider extends SpiderEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private static final TrackedData<Boolean> ATTACKING;
    private static final TrackedData<Boolean> UPSIDE_DOWN;

    public HangingSpider(EntityType<? extends HangingSpider> entityType, World world) {
        super(entityType, world);
        this.lookControl = new LookControl(this);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new HangingSpider.SmartPounceAtTargetGoal(this, 0.4F));
        this.goalSelector.add(4, new HangingSpider.AttackGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new HangingSpider.TargetGoal<>(this, PlayerEntity.class));
        this.targetSelector.add(3, new HangingSpider.TargetGoal<>(this, IronGolemEntity.class));
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos up = this.getBlockPos().up();
        BlockPos down = this.getBlockPos().down();
        this.dataTracker.set(UPSIDE_DOWN, false);
        if(world.getBlockState(up).isSolidBlock(world, up) && !world.getBlockState(down).isSolidBlock(world, down)) {
            this.dataTracker.set(UPSIDE_DOWN, true);
        }
    }

    @Override
    public boolean isClimbingWall() {
        return super.isClimbingWall() && !this.isUpsideDown();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(UPSIDE_DOWN, false);
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

    static {
        ATTACKING = DataTracker.registerData(HangingSpider.class, TrackedDataHandlerRegistry.BOOLEAN);
        UPSIDE_DOWN = DataTracker.registerData(HangingSpider.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    private static class TargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
        public TargetGoal(HangingSpider spider, Class<T> targetEntityClass) {
            super(spider, targetEntityClass, true);
        }

        @Override
        protected double getFollowRange() {
            return ((HangingSpider) this.mob).isUpsideDown() ? 64D : 32D;
        }

        @Override
        public boolean shouldContinue() {
            if (super.shouldContinue()) {
                LivingEntity target = this.mob.getTarget();
                if (((HangingSpider) this.mob).isUpsideDown() && target != null) {
                    BlockPos targetPos = target.getBlockPos();
                    BlockPos spiderPos = this.mob.getBlockPos();
                    double dX = Math.abs(targetPos.getX() - spiderPos.getX());
                    double dZ = Math.abs(targetPos.getZ() - spiderPos.getZ());
                    double dY = spiderPos.getY() - targetPos.getY();
                    double d = Math.sqrt(MathHelper.square(dX) + MathHelper.square(dZ));
                    return !(d < 25D) && dY < 50 && dY > 0;
                }
            }
            return true;
        }

        @Override
        public boolean canStart() {
            if (((HangingSpider) this.mob).isUpsideDown() && target != null) {
                BlockPos blockAbove = target.getBlockPos().up();
                while (!target.world.getBlockState(blockAbove).isAir()) {
                    if (blockAbove.getY() == target.world.getHeight()) break;
                    blockAbove = blockAbove.up();
                }
                HangingSpiders.LOGGER.info(String.valueOf(blockAbove));
                if (target.world.getBlockState(blockAbove).isAir()) {
                    return false;
                }
            }
            float f = this.mob.getBrightnessAtEyes();
            return !(f >= 0.5F) && super.canStart();
        }
    }

    private static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(HangingSpider spider) {
            super(spider, 1.0D, true);
        }

        public boolean canStart() {
            return super.canStart() && !this.mob.hasPassengers();
        }

        public boolean shouldContinue() {
            float f = this.mob.getBrightnessAtEyes();
            if (f >= 0.5F && this.mob.getRandom().nextInt(100) == 0) {
                this.mob.setTarget(null);
                return false;
            } else {
                return super.shouldContinue();
            }
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return 4.0F + entity.getWidth();
        }
    }

    private static class SmartPounceAtTargetGoal extends PounceAtTargetGoal {
        private final HangingSpider spider;

        public SmartPounceAtTargetGoal(HangingSpider spider, float velocity) {
            super(spider, velocity);
            this.spider = spider;
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !spider.isUpsideDown();
        }
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationController<?> controller = event.getController();
        controller.setAnimationSpeed(1D);
        if (event.isMoving()) {
            String animationName = "walk";
            if (this.isUpsideDown()) animationName = "upsidedown" + animationName;
            controller.setAnimationSpeed(1.3);
            controller.setAnimation(new AnimationBuilder()
                    .addAnimation(animationName, true));
        } else {
            String animationName = "idle";
            if (this.isUpsideDown()) animationName = "upsidedown" + animationName;
            controller.setAnimation(new AnimationBuilder()
                    .addAnimation(animationName, true));
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
