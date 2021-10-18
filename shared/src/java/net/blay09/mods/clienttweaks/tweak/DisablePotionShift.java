package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DisablePotionShift extends AbstractClientTweak {

    public DisablePotionShift() {
        super("disablePotionShift");
    }

    @SubscribeEvent
    public void onPotionShift(GuiScreenEvent.PotionShiftEvent event) {
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
