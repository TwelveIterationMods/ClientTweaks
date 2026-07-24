package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.ClientTickCallback;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ReactiveSprintKeyState;
import net.minecraft.client.Minecraft;

public class ReactiveSprintKey extends AbstractClientTweak {

    public ReactiveSprintKey() {
        super("reactive_sprint_key");

        ClientTickCallback.AFTER.register(this::onClientTick);
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
        final var config = ClientTweaksConfig.getActiveOrNull();
        return config != null && config.mobility.reactiveSprintKey;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfig.class, it -> it.mobility.reactiveSprintKey = enabled);
    }
}
