package net.blay09.mods.clienttweaks;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.clienttweaks.tweak.*;
import net.minecraft.sounds.SoundSource;

import java.util.HashMap;
import java.util.Map;

public class ClientTweaks {

    public static final String MOD_ID = "clienttweaks";

    private static final Map<String, AbstractClientTweak> tweaks = new HashMap<>();

    public static void initializeCommon() {
        ClientTweaksConfig.initialize();
    }

    public static void initializeClient() {
        registerTweak(new AdditionalVolumeSlider("masterVolumeSlider", SoundSource.MASTER, 0) {
            @Override
            public boolean isEnabled() {
                return ClientTweaksConfig.getActive().tweaks.masterVolumeSlider;
            }

            @Override
            public void setEnabled(boolean enabled) {
                Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.masterVolumeSlider = enabled);
            }
        });

        registerTweak(new AdditionalVolumeSlider("musicVolumeSlider", SoundSource.MUSIC, 1) {
            @Override
            public boolean isEnabled() {
                return ClientTweaksConfig.getActive().tweaks.musicVolumeSlider;
            }

            @Override
            public void setEnabled(boolean enabled) {
                Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.musicVolumeSlider = enabled);
            }
        });

        registerTweak(new NoOffhandTorchAtAll());
        registerTweak(new NoOffhandTorchWithBlock());
        registerTweak(new NoOffhandTorchWithEmptyHand());
        registerTweak(new OffhandTorchWithToolOnly());
        registerTweak(new HideOwnEffectParticles());
        registerTweak(new HideOffhandItem());
        registerTweak(new StepAssistIsAnnoying());
        registerTweak(new AutoClimbLadder());
        registerTweak(new HideShieldUnlessHoldingWeapon());
        registerTweak(new DoNotUseLastTorch());
        registerTweak(new DisableLogStripping());
        registerTweak(new NoOffhandTorchWithFood());
        registerTweak(new NoOffhandFireworksWithElytra());

        ModKeyMappings.initialize(BalmClient.getKeyMappings(), tweaks.values());
    }

    private static void registerTweak(AbstractClientTweak tweak) {
        tweaks.put(tweak.getName(), tweak);
    }

}
