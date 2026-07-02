package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.EventPriority;
import net.blay09.mods.balm.api.event.client.RenderHandEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;

public class HideHands extends AbstractClientTweak {

    public HideHands() {
        super("hideHands");

        Balm.getEvents().onEvent(RenderHandEvent.class, this::onRenderHand, EventPriority.Highest);
    }

    public void onRenderHand(RenderHandEvent event) {
        if (isEnabled()) {
            event.setCanceled(true);
        }
    }

    @Override
    public boolean hasKeyBinding() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().rendering.hideHands;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.rendering.hideHands = enabled);
    }
}
