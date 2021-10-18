package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StepAssistIsAnnoying extends AbstractClientTweak {

    private static final float DEFAULT_STEP_HEIGHT = 0.6f;

    public StepAssistIsAnnoying() {
        super("disableStepAssist");
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (isEnabled() && event.phase == TickEvent.Phase.START) {
            if (event.player != null) {
                event.player.maxUpStep = DEFAULT_STEP_HEIGHT;
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.disableStepAssist;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.disableStepAssist = enabled);
    }

    @Override
    public boolean hasKeyBinding() {
        return true;
    }
}
