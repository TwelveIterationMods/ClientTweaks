package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.client.PotionShiftEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;

public class DisablePotionShift extends AbstractClientTweak {

    public DisablePotionShift() {
        super("disablePotionShift");

        Balm.getEvents().onEvent(PotionShiftEvent.class, this::onPotionShift);
    }

    public void onPotionShift(PotionShiftEvent event) {
        if (isEnabled()) {
            event.setCanceled(true);
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.disablePotionShift;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.disablePotionShift = enabled);
    }
}
