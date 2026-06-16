package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Hud.class)
public class HudMixin {

    @Inject(method = "willPrioritizeExperienceInfo", at = @At("HEAD"), cancellable = true)
    private void willPrioritizeExperienceInfo(CallbackInfoReturnable<Boolean> cir) {
        if (ClientTweaksConfig.getActive().ui.alwaysPrioritizeExperienceBar) {
            cir.setReturnValue(true);
        }
    }
}
