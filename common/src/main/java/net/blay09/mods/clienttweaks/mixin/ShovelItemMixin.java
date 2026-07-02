package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        final var player = context.getPlayer();
        if (player == null) {
            return;
        }

        final var offhandItem = player.getItemInHand(InteractionHand.OFF_HAND);
        if (offhandItem.getItem() instanceof BlockItem) {
            if (ClientTweaksConfig.getActive().interactions.disablePavingWithBlockInOffhand) {
                cir.setReturnValue(InteractionResult.PASS);
            }
        }
    }
}
