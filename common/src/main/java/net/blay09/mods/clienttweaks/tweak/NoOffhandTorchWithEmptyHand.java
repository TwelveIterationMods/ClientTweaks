package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.client.UseItemInputEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class NoOffhandTorchWithEmptyHand extends AbstractClientTweak {

    public NoOffhandTorchWithEmptyHand() {
        super("no_offhand_torch_with_empty_hand");

        Balm.getEvents().onEvent(UseItemInputEvent.class, this::onRightClick);
    }

    public void onRightClick(UseItemInputEvent event) {
        if (isEnabled() && event.getHand() == InteractionHand.OFF_HAND) {
            final var client = Minecraft.getInstance();
            final var heldItem = client.player != null ? client.player.getItemInHand(event.getHand()) : ItemStack.EMPTY;
            if (ClientTweaksConfig.isTorchItem(heldItem)) {
                final var mainItem = client.player.getMainHandItem();
                if (mainItem.isEmpty()) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.noOffhandTorchWithEmptyHand;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.noOffhandTorchWithEmptyHand = enabled);
    }

}
