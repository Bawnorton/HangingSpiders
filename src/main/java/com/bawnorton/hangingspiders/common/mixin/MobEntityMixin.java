package com.bawnorton.hangingspiders.common.mixin;

import com.bawnorton.hangingspiders.common.util.TypeChecker;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
    /**
     * Allow spawning on the underside of blocks
     */
    @Inject(method = "canMobSpawn", at = @At("RETURN"), cancellable = true)
    private static void allowSpawningUnderBlock(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ() && TypeChecker.isSpider(type) && world.getBlockState(pos.up()).allowsSpawning(world, pos.up(), type)) {
            cir.setReturnValue(true);
        }
    }
}
