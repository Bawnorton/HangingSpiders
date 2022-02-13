package com.bawnorton.hangingspiders.mixin;

import com.bawnorton.hangingspiders.util.TypeChecker;
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
public class MobEntityMixin {
    @Inject(method = "canMobSpawn", at = @At("RETURN"), cancellable = true)
    private static void allowBlockContactSpawning(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ() && TypeChecker.isSpider(type)) {
            BlockPos[] offsetPostions = new BlockPos[]{pos.up(), pos.north(), pos.east(), pos.south(), pos.west()};
            for(BlockPos offsetPostion: offsetPostions) {
                if (world.getBlockState(offsetPostion).allowsSpawning(world, offsetPostion, type)) {
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }
}
