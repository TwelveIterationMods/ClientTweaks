package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.EventPriority;
import net.blay09.mods.balm.api.event.client.RenderHandEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;

public class HideShieldUnlessBlocking extends AbstractClientTweak {

    public HideShieldUnlessBlocking() {
        super("hideShieldUnlessBlocking");

        Balm.getEvents().onEvent(RenderHandEvent.class, this::onRenderHand, EventPriority.Highest);
    }

    public void onRenderHand(RenderHandEvent event) {
        if (!isEnabled() || event.getHand() != InteractionHand.OFF_HAND) {
            return;
        }

        final var player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        final var isShield = Balm.getHooks().isShield(event.getItemStack())
                || ClientTweaksConfig.isShieldItem(event.getItemStack());
        if (!isShield) {
            return;
        }

        final var isBlocking = player.getUsedItemHand() == InteractionHand.OFF_HAND && player.isBlocking();
        final var isStartingToBlock = player.isUsingItem() && player.getUsedItemHand() == InteractionHand.OFF_HAND;
        if (!isBlocking && !isStartingToBlock) {
            event.setCanceled(true);
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.hideShieldUnlessBlocking;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.hideShieldUnlessBlocking = enabled);
    }
}
