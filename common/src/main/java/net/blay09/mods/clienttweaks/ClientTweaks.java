package net.blay09.mods.clienttweaks;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.BalmClientRegistrars;
import net.blay09.mods.balm.core.BalmRegistrars;
import net.blay09.mods.clienttweaks.tweak.*;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;

import java.util.HashMap;
import java.util.Map;

public class ClientTweaks {

    public static final String MOD_ID = "clienttweaks";

    private static final Map<String, AbstractClientTweak> tweaks = new HashMap<>();

    public static void initializeCommon(BalmRegistrars registrars) {
        ClientTweaksConfig.initialize();
    }

    public static void initializeClient(BalmClientRegistrars registrars) {
        registerTweak(new AdditionalVolumeSlider("master_volume_slider", SoundSource.MASTER, 0) {
            @Override
            public boolean isEnabled() {
                final var config = ClientTweaksConfig.getActive();
                return config != null && config.tweaks.masterVolumeSlider;
            }

            @Override
            public void setEnabled(boolean enabled) {
                Balm.config().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.masterVolumeSlider = enabled);
            }
        });

        registerTweak(new AdditionalVolumeSlider("music_volume_slider", SoundSource.MUSIC, 1) {
            @Override
            public boolean isEnabled() {
                final var config = ClientTweaksConfig.getActive();
                return config != null && config.tweaks.musicVolumeSlider;
            }

            @Override
            public void setEnabled(boolean enabled) {
                Balm.config().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.musicVolumeSlider = enabled);
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
        registerTweak(new PreventAccidentalMining());
        registerTweak(new NoOffhandUseWithFood());
        registerTweak(new DoNotUseLastMending());
        registerTweak(new ClearRecipeBookSearch());

        ModKeyMappings.initialize(tweaks.values());
    }

    private static void registerTweak(AbstractClientTweak tweak) {
        tweaks.put(tweak.getName(), tweak);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
