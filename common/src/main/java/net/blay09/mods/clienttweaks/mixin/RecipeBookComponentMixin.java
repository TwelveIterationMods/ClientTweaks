package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeBookComponent.class)
public class RecipeBookComponentMixin {

    @Shadow
    private int xOffset;

    @Shadow
    private boolean widthTooNarrow;

    @Inject(method = "initVisuals()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/recipebook/RecipeBookComponent;xOffset:I", shift = At.Shift.AFTER))
    void initVisuals(CallbackInfo callbackInfo) {
        if (ClientTweaksConfig.getActive().tweaks.noRecipeBookShifting) {
            widthTooNarrow = true;
            xOffset = 162;
        }
    }

    @Inject(method = "isOffsetNextToMainGUI()Z", at = @At("HEAD"), cancellable = true)
    void isOffsetNextToMainGUI(CallbackInfoReturnable<Boolean> callbackInfo) {
        if (ClientTweaksConfig.getActive().tweaks.noRecipeBookShifting) {
            callbackInfo.setReturnValue(true); // we pretend like we're not shifted to prevent the recipe book from being closed
        }
    }

}
