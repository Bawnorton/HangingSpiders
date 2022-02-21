package com.bawnorton.hangingspiders.common.mixin;

import com.bawnorton.hangingspiders.common.entity.HangingSpider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @ModifyArgs(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V", ordinal = 3))
    private void reverseGravity(Args args) {
        Entity entity = ((Entity) (Object) this);
        if (entity instanceof HangingSpider) {
            if(((HangingSpider) entity).isUpsideDown()) {
                entity.setOnGround(true);
                double y = args.get(1);
                if (y < 0) {
                    args.set(1, y * -1);
                }
            }
        }
    }
}
