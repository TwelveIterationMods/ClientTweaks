package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.BlockTransformers;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void clientTweaks$disablePavingWithBlockInOffhand(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        final var player = context.getPlayer();
        final var blockTransformer = context.getItemInHand().get(DataComponents.BLOCK_TRANSFORMER);
        if (player != null
                && blockTransformer != null
                && blockTransformer.is(BlockTransformers.SHOVEL)
                && player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof BlockItem
                && ClientTweaksConfig.getActive().interactions.disablePavingWithBlockInOffhand) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }
}
