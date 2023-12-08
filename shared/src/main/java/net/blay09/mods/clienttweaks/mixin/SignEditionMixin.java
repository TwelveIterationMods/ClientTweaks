package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlock.class)
public class SignEditionMixin {
    // We can't inject into the openTextEdit method as it is also used
    // when first placing a sign, resulting in an empty sign if the
    // player is not sneaking
    @Inject(method="hasEditableText", at = @At("HEAD"), cancellable = true)
    void openTextEdit(Player player, SignBlockEntity sbe, boolean bl, CallbackInfoReturnable<Boolean> cir) {
        if (ClientTweaksConfig.getActive().tweaks.sneakSignEdit && !player.isCrouching()) {
            cir.setReturnValue(false);
        }
    }
}
