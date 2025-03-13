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

public class DisableLogStripping extends AbstractClientTweak {

    public DisableLogStripping() {
        super("disableLogStripping");

        Balm.getEvents().onEvent(UseItemInputEvent.class, this::onRightClick);
    }

    public void onRightClick(UseItemInputEvent event) {
        if (isEnabled()) {
            final var client = Minecraft.getInstance();
            if (client.level == null || client.hitResult == null || client.hitResult.getType() != HitResult.Type.BLOCK) {
                return;
            }

            final var blockHitResult = (BlockHitResult) client.hitResult;
            final var targetState = client.level.getBlockState(blockHitResult.getBlockPos());
            final var heldItem = client.player != null ? client.player.getItemInHand(event.getHand()) : ItemStack.EMPTY;
            if (!heldItem.isEmpty() && heldItem.getItem() instanceof AxeItemAccessor axeItem && axeItem.callGetStripped(targetState).isPresent()) {
                event.setCanceled(true);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.disableLogStripping;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.disableLogStripping = enabled);
    }

    @Override
    public boolean hasKeyBinding() {
        return true;
    }
}
