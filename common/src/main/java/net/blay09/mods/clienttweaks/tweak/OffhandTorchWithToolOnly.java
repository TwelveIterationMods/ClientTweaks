package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.client.UseItemInputEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class OffhandTorchWithToolOnly extends AbstractClientTweak {

    public OffhandTorchWithToolOnly() {
        super("offhandTorchWithToolOnly");

        Balm.getEvents().onEvent(UseItemInputEvent.class, this::onRightClick);
    }

    public void onRightClick(UseItemInputEvent event) {
        if (isEnabled() && event.getHand() == InteractionHand.OFF_HAND) {
            final var client = Minecraft.getInstance();
            final var heldItem = client.player != null ? client.player.getItemInHand(event.getHand()) : ItemStack.EMPTY;
            if (ClientTweaksConfig.isTorchItem(heldItem)) {
                final var mainItem = client.player.getMainHandItem();
                if (!ClientTweaksConfig.isTorchTool(mainItem)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.offhandTorchWithToolOnly;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.offhandTorchWithToolOnly = enabled);
    }
}
