package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.client.UseItemInputEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.blay09.mods.clienttweaks.mixin.AxeItemAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class LogStrippingRequiresShift extends AbstractClientTweak {

    public LogStrippingRequiresShift() {
        super("logStrippingRequiresShift");

        Balm.getEvents().onEvent(UseItemInputEvent.class, this::onRightClick);
    }

    public void onRightClick(UseItemInputEvent event) {
        if (!isEnabled()) {
            return;
        }

        final var mc = Minecraft.getInstance();
        final var player = mc.player;
        if (player == null || player.isShiftKeyDown()) {
            return;
        }

        if (mc.level == null || mc.hitResult == null || mc.hitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }

        final var blockHitResult = (BlockHitResult) mc.hitResult;
        final var targetState = mc.level.getBlockState(blockHitResult.getBlockPos());
        final var heldItem = player.getItemInHand(event.getHand());
        if (!heldItem.isEmpty() && heldItem.getItem() instanceof AxeItemAccessor axeItem && axeItem.callGetStripped(targetState).isPresent()) {
            event.setCanceled(true);
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.logStrippingRequiresShift;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.logStrippingRequiresShift = enabled);
    }
}
