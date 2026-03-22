package net.blay09.mods.clienttweaks;

import net.blay09.mods.clienttweaks.tweak.AbstractClientTweak;
import net.blay09.mods.kuma.api.Kuma;
import net.blay09.mods.kuma.api.KeyConflictContext;
import net.blay09.mods.kuma.api.ManagedKeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.Collection;

import static net.blay09.mods.clienttweaks.ClientTweaks.id;

public class ModKeyMappings {
    private static ManagedKeyMapping mineSingleBlockKeyMapping;

    public static void initialize(Collection<AbstractClientTweak> tweaks) {
        for (final var tweak : tweaks) {
            if (tweak.hasKeyBinding()) {
                Kuma.createKeyMapping(id(tweak.getName()))
                        .handleWorldInput(event -> {
                            toggleTweak(tweak);
                            return true;
                        })
                        .build();
            }
        }

        mineSingleBlockKeyMapping = Kuma.createKeyMapping(id("mine_single_block"))
                .withContext(KeyConflictContext.WORLD)
                .build();
    }

    public static ManagedKeyMapping getMineSingleBlockKeyMapping() {
        return mineSingleBlockKeyMapping;
    }

    private static void toggleTweak(AbstractClientTweak tweak) {
        tweak.setEnabled(!tweak.isEnabled());

        final var player = Minecraft.getInstance().player;
        if (player != null) {
            final var component = Component.translatable("chat.clienttweaks." + tweak.getName() + ".toggled",
                    Component.translatable(tweak.isEnabled() ? "chat.clienttweaks.on" : "chat.clienttweaks.off"));
            player.sendOverlayMessage(component);
        }
    }
}
