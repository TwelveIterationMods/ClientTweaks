package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.TickPhase;
import net.blay09.mods.balm.api.event.TickType;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ReactiveSprintKeyState;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.client.Minecraft;

public class ReactiveSprintKey extends AbstractClientTweak {

    public ReactiveSprintKey() {
        super("reactiveSprintKey");

        Balm.getEvents().onTickEvent(TickType.Client, TickPhase.End, this::onClientTick);
    }

    private void onClientTick(Minecraft client) {
        final var player = client.player;
        if (player == null) {
            return;
        }

        final var sprintKeyState = (ReactiveSprintKeyState) player;
        if (!isEnabled()) {
            sprintKeyState.clienttweaks$setSprintingWithSprintKey(false);
            return;
        }

        final var sprintKeyDown = client.options.keySprint.isDown();
        if (!player.isSprinting()) {
            sprintKeyState.clienttweaks$setSprintingWithSprintKey(false);
        } else if (sprintKeyDown) {
            sprintKeyState.clienttweaks$setSprintingWithSprintKey(true);
        } else if (sprintKeyState.clienttweaks$isSprintingWithSprintKey()) {
            player.setSprinting(false);
            sprintKeyState.clienttweaks$setSprintingWithSprintKey(false);
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().mobility.reactiveSprintKey;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.mobility.reactiveSprintKey = enabled);
    }
}
