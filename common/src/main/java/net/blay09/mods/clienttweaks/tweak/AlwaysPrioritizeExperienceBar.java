package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;

public class AlwaysPrioritizeExperienceBar extends AbstractClientTweak {

    public AlwaysPrioritizeExperienceBar() {
        super("always_prioritize_experience_bar");
    }

    @Override
    public boolean hasKeyBinding() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().ui.alwaysPrioritizeExperienceBar;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfig.class, it -> it.ui.alwaysPrioritizeExperienceBar = enabled);
    }
}
