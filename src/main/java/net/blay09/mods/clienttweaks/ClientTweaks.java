package net.blay09.mods.clienttweaks;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.blay09.mods.clienttweaks.tweak.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mod(ClientTweaks.MOD_ID)
public class ClientTweaks {

    public static final String MOD_ID = "clienttweaks";

    private static final Map<String, AbstractClientTweak> tweaks = Maps.newHashMap();
    private static final List<AbstractClientTweak> toggleableTweaks = Lists.newArrayList();

    public ClientTweaks() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);

        registerTweak(new AutoJumpMoreLikeAutoDumbAmirite());
        registerTweak(new MasterVolumeSlider("masterVolumeSlider", "This adds back the master volume slider to the options screen. Saves you a click!", SoundCategory.MASTER, 0));
        registerTweak(new MasterVolumeSlider("musicVolumeSlider", "This adds a music volume slider to the options screen. Saves you a click!", SoundCategory.MUSIC, 160));
        registerTweak(new UnderlineLooksTerribleInChat());
        registerTweak(new NoOffhandTorchWithBlock());
        registerTweak(new NoOffhandTorchWithEmptyHand());
        registerTweak(new OffhandTorchWithToolOnly());
        registerTweak(new HideOwnEffectParticles());
        registerTweak(new HideOffhandItem());
        registerTweak(new StepAssistIsAnnoying());
        registerTweak(new AutoLadder());
        registerTweak(new DisablePotionShift());
        registerTweak(new HideShieldUnlessHoldingWeapon());
        registerTweak(new DoNotUseLastTorch());

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientTweaksConfig.clientSpec);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setupClient(FMLClientSetupEvent event) {
        for (AbstractClientTweak tweak : tweaks.values()) {
            tweak.init(event);

            KeyBinding keyBinding = tweak.registerToggleKeybind();
            if (keyBinding != null) {
                toggleableTweaks.add(tweak);
            }
        }
    }

    public static Collection<AbstractClientTweak> getTweaks() {
        return tweaks.values();
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (event.getAction() == GLFW.GLFW_PRESS) {
            for (AbstractClientTweak tweak : toggleableTweaks) {
                InputMappings.Input input = InputMappings.getInputByCode(event.getKey(), event.getScanCode());
                if (tweak.getKeyBinding().isActiveAndMatches(input)) {
                    toggleTweak(tweak);
                }
            }
        }
    }

    private void toggleTweak(AbstractClientTweak tweak) {
        tweak.setEnabled(!tweak.isEnabled());

        ClientTweaksConfig.updateTweakState(tweak);

        ITextComponent root = new StringTextComponent(tweak.getName() + ": ");
        root.appendSibling(new TranslationTextComponent(tweak.isEnabled() ? "clienttweaks.on" : "clienttweaks.off"));
        Minecraft.getInstance().field_71456_v.getChatGUI().printChatMessageWithOptionalDeletion(root, 5);
    }

    private void registerTweak(AbstractClientTweak tweak) {
        tweaks.put(tweak.getName(), tweak);
    }

}
