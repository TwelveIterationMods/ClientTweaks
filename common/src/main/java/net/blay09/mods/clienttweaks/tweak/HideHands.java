package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.RenderCallback;
import net.blay09.mods.balm.platform.event.EventPhases;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class HideHands extends AbstractClientTweak {

    public HideHands() {
        super("hide_hands");

        RenderCallback.Hand.EVENT.register(EventPhases.HIGH, this::onRenderHand);
    }

    public boolean onRenderHand(InteractionHand hand, ItemStack itemStack, float swingProgress) {
        return !isEnabled();
    }

    @Override
    public boolean hasKeyBinding() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.hideHands;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.hideHands = enabled);
    }
}
