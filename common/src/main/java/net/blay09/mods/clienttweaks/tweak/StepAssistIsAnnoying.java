package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;

public class StepAssistIsAnnoying extends AbstractClientTweak {

    public StepAssistIsAnnoying() {
        super("disableStepAssist");
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.disableStepAssist;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.disableStepAssist = enabled);
    }

    @Override
    public boolean hasKeyBinding() {
        return true;
    }
}
