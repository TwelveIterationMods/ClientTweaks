package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
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
        final var config = Balm.getConfig().getActiveConfig(ClientTweaksConfigData.class);
        if (config != null && config.tweaks.disableStepAssist) {
            final var baseMaxStep = ((LivingEntity) (Object) this).getAttributeBaseValue(Attributes.STEP_HEIGHT);
            final var modifiedMaxStep = cir.getReturnValue();
            cir.setReturnValue((float) Math.min(baseMaxStep, modifiedMaxStep));
        }
    }

}
