package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DisablePotionShift extends AbstractClientTweak {

    public DisablePotionShift() {
        super("disablePotionShift", ClientTweaksConfig.CLIENT.disablePotionShift);
    }

    @SubscribeEvent
    public void onPotionShift(GuiScreenEvent.PotionShiftEvent event) {
        if (isEnabled()) {
            event.setCanceled(true);
        }
    }
}
