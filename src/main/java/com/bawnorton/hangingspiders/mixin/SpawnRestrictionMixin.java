package com.bawnorton.hangingspiders.mixin;

import com.bawnorton.hangingspiders.util.TypeChecker;
import net.minecraft.entity.SpawnRestriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(SpawnRestriction.class)
public class SpawnRestrictionMixin {
    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/SpawnRestriction;register(Lnet/minecraft/entity/EntityType;Lnet/minecraft/entity/SpawnRestriction$Location;Lnet/minecraft/world/Heightmap$Type;Lnet/minecraft/entity/SpawnRestriction$SpawnPredicate;)V"))
    private static void changeSpiderSpawnResitrctions(Args args) {
        if (TypeChecker.isSpider(args.get(0)))
            args.set(1, SpawnRestriction.Location.NO_RESTRICTIONS);
    }
}
