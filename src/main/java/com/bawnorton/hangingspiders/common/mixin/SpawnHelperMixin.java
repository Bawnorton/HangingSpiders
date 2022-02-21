package com.bawnorton.hangingspiders.common.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.SpawnHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.bawnorton.hangingspiders.common.registry.EntityRegister.HANGING_SPIDER;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {

	@Inject(method="createMob", at = @At("RETURN"), cancellable = true)
	private static void modifyMob(ServerWorld world, EntityType<?> type, CallbackInfoReturnable<MobEntity> cir) {
		if(cir.getReturnValue() instanceof SpiderEntity) {
			cir.setReturnValue(HANGING_SPIDER.create(world));
		}
	}
}
