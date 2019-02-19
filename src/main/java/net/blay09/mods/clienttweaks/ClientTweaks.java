package net.blay09.mods.clienttweaks;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.blay09.mods.clienttweaks.tweak.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mod(ClientTweaks.MOD_ID)
public class ClientTweaks {

    public static final String MOD_ID = "clienttweaks";

    private static final Map<String, ClientTweak> tweaks = Maps.newHashMap();
    private static final List<ClientTweak> toggleableTweaks = Lists.newArrayList();

    public ClientTweaks() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);

        registerTweak(new AutoJumpMoreLikeAutoDumbAmirite());
        registerTweak(new MasterVolumeSlider());
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
        for (ClientTweak tweak : tweaks.values()) {
            tweak.init(event);

            KeyBinding keyBinding = tweak.registerToggleKeybind();
            if (keyBinding != null) {
                toggleableTweaks.add(tweak);
            }
        }
    }

    public static Collection<ClientTweak> getTweaks() {
        return tweaks.values();
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
//        if (Keyboard.getEventKeyState()) {
//            for (ClientTweak tweak : toggleableTweaks) {
//                if (tweak.getKeyBinding().isActiveAndMatches(Keyboard.getEventKey())) {
//                    toggleTweak(tweak);
//                }
//            }
//        }
    }

    private void toggleTweak(ClientTweak tweak) {
        tweak.setEnabled(!tweak.isEnabled());

        ClientTweaksConfig.updateTweakState(tweak);

        ITextComponent root = new TextComponentString(tweak.getName() + ": ");
        root.appendSibling(new TextComponentTranslation(tweak.isEnabled() ? "clienttweaks.on" : "clienttweaks.off"));
        Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(root, 5);
    }

    private void registerTweak(ClientTweak tweak) {
        tweaks.put(tweak.getName(), tweak);
    }

}
