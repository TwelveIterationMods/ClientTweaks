package net.blay09.mods.clienttweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.blay09.mods.balm.Balm;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.tweak.HideOwnEffectParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "maxUpStep", at = @At("RETURN"), cancellable = true)
    public void maxUpStep(CallbackInfoReturnable<Float> cir) {
        final var config = Balm.config().getActiveConfig(ClientTweaksConfig.class);
        if (config != null && config.mobility.disableStepAssist) {
            final var baseMaxStep = ((LivingEntity) (Object) this).getAttributeBaseValue(Attributes.STEP_HEIGHT);
            final var modifiedMaxStep = cir.getReturnValue();
            cir.setReturnValue((float) Math.min(baseMaxStep, modifiedMaxStep));
        }
    }

    @WrapOperation(method = "tickEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    public void addEffectParticle(Level level, ParticleOptions particle, double x, double y, double z, double xd, double yd, double zd, Operation<Void> original) {
        if (!HideOwnEffectParticles.shouldSuppressFor((LivingEntity) (Object) this)) {
            original.call(level, particle, x, y, z, xd, yd, zd);
        }
    }

}
