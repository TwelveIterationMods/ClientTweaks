package net.blay09.mods.clienttweaks;

import com.mojang.blaze3d.platform.InputConstants;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.balm.api.client.keymappings.BalmKeyMappings;
import net.blay09.mods.balm.api.client.keymappings.KeyConflictContext;
import net.blay09.mods.balm.api.client.keymappings.KeyModifier;
import net.blay09.mods.balm.api.event.client.KeyInputEvent;
import net.blay09.mods.clienttweaks.tweak.AbstractClientTweak;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModKeyMappings {

    private static final Map<KeyMapping, AbstractClientTweak> toggleableTweaks = new HashMap<>();

    public static void initialize(BalmKeyMappings keyMappings, Collection<AbstractClientTweak> tweaks) {
        for (AbstractClientTweak tweak : tweaks) {
            if (tweak.hasKeyBinding()) {
                KeyMapping keyBinding = keyMappings.registerKeyMapping("key.clienttweaks." + tweak.getName(), KeyConflictContext.INGAME, KeyModifier.NONE, InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), "key.categories.clienttweaks");
                toggleableTweaks.put(keyBinding, tweak);
            }
        }

        Balm.getEvents().onEvent(KeyInputEvent.class, ModKeyMappings::onKeyInput);
    }

    public static void onKeyInput(KeyInputEvent event) {
        if (event.getAction() == GLFW.GLFW_PRESS) {
            for (Map.Entry<KeyMapping, AbstractClientTweak> entry : toggleableTweaks.entrySet()) {
                if (BalmClient.getKeyMappings().isActiveAndMatches(entry.getKey(), event.getKey(), event.getScanCode())) {
                    toggleTweak(entry.getValue());
                }
            }
        }
    }

    private static void toggleTweak(AbstractClientTweak tweak) {
        tweak.setEnabled(!tweak.isEnabled());

        Player player = Minecraft.getInstance().player;
        if (player != null) {
            MutableComponent component = Component.translatable("clienttweaks." + tweak.getName(), Component.translatable(tweak.isEnabled() ? "chat.clienttweaks.on" : "chat.clienttweaks.off"));
            player.displayClientMessage(component, true);
        }
    }
}
