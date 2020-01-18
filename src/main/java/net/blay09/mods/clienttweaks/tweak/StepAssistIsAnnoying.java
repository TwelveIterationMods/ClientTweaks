package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StepAssistIsAnnoying extends AbstractClientTweak {

    private static final float DEFAULT_STEP_HEIGHT = 0.6f;

    public StepAssistIsAnnoying() {
        super("disableStepAssist", ClientTweaksConfig.CLIENT.disableStepAssist);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (isEnabled() && event.phase == TickEvent.Phase.START) {
            if (event.player != null) {
                event.player.stepHeight = DEFAULT_STEP_HEIGHT;
            }
        }
    }

    @Override
    public boolean hasKeyBinding() {
        return true;
    }
}
