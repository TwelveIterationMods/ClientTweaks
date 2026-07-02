package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.clienttweaks.MineSingleBlockHandler;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {

    @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void clientTweaks$preventAdditionalStartDestroyBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (MineSingleBlockHandler.shouldPreventBlockBreaking()) {
            callbackInfo.setReturnValue(false);
        }
    }

    @Inject(method = "continueDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void clientTweaks$preventAdditionalContinueDestroyBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (MineSingleBlockHandler.shouldPreventBlockBreaking()) {
            callbackInfo.setReturnValue(false);
        }
    }

    @Inject(method = "destroyBlock", at = @At("RETURN"))
    private void clientTweaks$markBlockBreakConsumed(BlockPos pos, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (callbackInfo.getReturnValue()) {
            MineSingleBlockHandler.onBlockBroken();
        }
    }
}
