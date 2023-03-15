package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.TickPhase;
import net.blay09.mods.balm.api.event.TickType;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class StepAssistIsAnnoying extends AbstractClientTweak {

    private static final float DEFAULT_STEP_HEIGHT = 0.6f;

    public StepAssistIsAnnoying() {
        super("disableStepAssist");

        // TODO might have to add prio to tick event handlers since this used to run on Lowest
        Balm.getEvents().onTickEvent(TickType.Client, TickPhase.Start, this::onPlayerTick);
    }

    public void onPlayerTick(Minecraft client) {
        if (isEnabled()) {
            Player player = client.player;
            if (player != null) {
                player.setMaxUpStep(DEFAULT_STEP_HEIGHT);
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
