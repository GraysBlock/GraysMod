package graysblock.graysmod.mixin;

import graysblock.graysmod.entity.effect.GraysModStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Inject(method = "emitGameEvent", at = @At("HEAD"), cancellable = true)
    private void preventGameEvents(RegistryEntry<GameEvent> event, Vec3d emitterPos, GameEvent.Emitter emitter, CallbackInfo ci) {
        if(emitter.sourceEntity() != null) {
            Entity entity = emitter.sourceEntity();
            if(entity instanceof LivingEntity livingEntity && livingEntity.hasStatusEffect(GraysModStatusEffects.SHADOW_FORM)) {
                ci.cancel();
            }
        }
    }
}
