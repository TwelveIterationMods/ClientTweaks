package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;

public class StepAssistIsAnnoying extends AbstractClientTweak {

    public StepAssistIsAnnoying() {
        super("disable_step_assist");
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().mobility.disableStepAssist;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfig.class, it -> it.mobility.disableStepAssist = enabled);
    }

    @Override
    public boolean hasKeyBinding() {
        return true;
    }
}
