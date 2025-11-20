package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.RenderCallback;
import net.blay09.mods.balm.platform.event.EventHandling;
import net.blay09.mods.balm.platform.event.EventPhases;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class HideOffhandItem extends AbstractClientTweak {

    public HideOffhandItem() {
        super("hide_offhand_item");

        RenderCallback.Hand.EVENT.register(EventPhases.HIGH, this::onRenderHand);
    }

    public EventHandling onRenderHand(InteractionHand hand, ItemStack itemStack, float swingProgress) {
        if (isEnabled()) {
            // TODO Tinkers inverts this event by rendering manually with its dual harvesting, come up with a solution
            if (hand == InteractionHand.OFF_HAND) {
                if (swingProgress <= 0f) {
                    return EventHandling.CANCEL;
                }
            }
        }

        return EventHandling.RESUME;
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
        Balm.config().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.hideOffhandItem = enabled);
    }
}
