package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.RenderCallback;
import net.blay09.mods.balm.platform.event.EventPhases;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class HideOffhandItem extends AbstractClientTweak {

    public HideOffhandItem() {
        super("hide_offhand_item");

        RenderCallback.Hand.EVENT.register(EventPhases.HIGH, this::onRenderHand);
    }

    public boolean onRenderHand(InteractionHand hand, ItemStack itemStack, float swingProgress) {
        if (isEnabled()) {
            if (hand == InteractionHand.OFF_HAND) {
                return !(swingProgress <= 0f);
            }
        }

        return true;
    }

    @Override
    public boolean hasKeyBinding() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().rendering.hideOffhandItem;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfig.class, it -> it.rendering.hideOffhandItem = enabled);
    }
}
