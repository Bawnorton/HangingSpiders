package com.bawnorton.hangingspiders.mixin;

import com.bawnorton.hangingspiders.common.entity.HangingSpider;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerEntityManager;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static com.bawnorton.hangingspiders.common.registry.EntityRegister.HANGING_SPIDER;

@Mixin(ServerEntityManager.class)
public class ServerEntityManagerMixin {
	@SuppressWarnings("unchecked")
	@ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerEntityManager;addEntity(Lnet/minecraft/world/entity/EntityLike;Z)Z"), method="addEntity(Lnet/minecraft/world/entity/EntityLike;)Z")
	private <T extends EntityLike> T addEntity(T entity) {
		if (entity instanceof SpiderEntity) {
			if (!(entity instanceof CaveSpiderEntity)) {
				SpiderEntity spiderEntity = (SpiderEntity) entity;
				PlayerEntity player = spiderEntity.world.getPlayers().get(0);
				HangingSpider hangingSpider = new HangingSpider(HANGING_SPIDER, spiderEntity.world);
				hangingSpider.setPos(player.getX(), spiderEntity.getBlockY(), player.getZ());
				return (T) hangingSpider;
			}
		}
		return entity;
	}
}
