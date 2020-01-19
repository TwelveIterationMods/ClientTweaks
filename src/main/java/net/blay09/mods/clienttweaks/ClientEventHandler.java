package net.blay09.mods.clienttweaks;

import net.blay09.mods.clienttweaks.tweak.AbstractClientTweak;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ClientTweaks.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {

    private static final Map<KeyBinding, AbstractClientTweak> toggleableTweaks = new HashMap<>();

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (event.getAction() == GLFW.GLFW_PRESS) {
            for (Map.Entry<KeyBinding, AbstractClientTweak> entry : toggleableTweaks.entrySet()) {
                InputMappings.Input input = InputMappings.getInputByCode(event.getKey(), event.getScanCode());
                if (entry.getKey().isActiveAndMatches(input)) {
                    toggleTweak(entry.getValue());
                }
            }
        }
    }

    private static void toggleTweak(AbstractClientTweak tweak) {
        tweak.setEnabled(!tweak.isEnabled());
        ClientTweaksConfig.save();

        ITextComponent root = new StringTextComponent(tweak.getName() + ": ");
        root.appendSibling(new TranslationTextComponent(tweak.isEnabled() ? "chat.clienttweaks.on" : "chat.clienttweaks.off"));
        Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(root, 5);
    }

    public static void setupKeyBindings(Collection<AbstractClientTweak> tweaks) {
        for (AbstractClientTweak tweak : tweaks) {
            if(tweak.hasKeyBinding()) {
                KeyBinding keyBinding = new KeyBinding("key.clienttweaks." + tweak.getName(), KeyConflictContext.IN_GAME, KeyModifier.NONE, InputMappings.Type.KEYSYM, InputMappings.INPUT_INVALID.getKeyCode(), "key.categories.clienttweaks");
                ClientRegistry.registerKeyBinding(keyBinding);
                toggleableTweaks.put(keyBinding, tweak);
            }
        }
    }
}
