package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.EventPriority;
import net.blay09.mods.balm.api.event.client.RenderHandEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.world.InteractionHand;

public class HideOffhandItem extends AbstractClientTweak {

    public HideOffhandItem() {
        super("hideOffhandItem");

        Balm.getEvents().onEvent(RenderHandEvent.class, this::onRenderHand, EventPriority.Highest);
    }

    public void onRenderHand(RenderHandEvent event) {
        if (isEnabled()) {
            // TODO Tinkers inverts this event by rendering manually with its dual harvesting, come up with a solution
            if (event.getHand() == InteractionHand.OFF_HAND) {
                if (event.getSwingProgress() <= 0f) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public boolean hasKeyBinding() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.hideOffhandItem;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.hideOffhandItem = enabled);
    }
}
