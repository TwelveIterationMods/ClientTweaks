package net.blay09.mods.clienttweaks;

import net.blay09.mods.clienttweaks.tweak.*;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

@Mod(ClientTweaks.MOD_ID)
public class ClientTweaks {

    public static final String MOD_ID = "clienttweaks";

    private static final Map<String, AbstractClientTweak> tweaks = new HashMap<>();

    public ClientTweaks() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientTweaksConfig.clientSpec);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);

        // clientSideOnly = true was cooler
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }

    private void setupClient(FMLClientSetupEvent event) {
        registerTweak(new AdditionalVolumeSlider("masterVolumeSlider", ClientTweaksConfig.CLIENT.masterVolumeSlider, SoundCategory.MASTER, 0));
        registerTweak(new AdditionalVolumeSlider("musicVolumeSlider", ClientTweaksConfig.CLIENT.musicVolumeSlider, SoundCategory.MUSIC, 160));
        registerTweak(new NoOffhandTorchWithBlock());
        registerTweak(new NoOffhandTorchWithEmptyHand());
        registerTweak(new OffhandTorchWithToolOnly());
        registerTweak(new HideOwnEffectParticles());
        registerTweak(new HideOffhandItem());
        registerTweak(new StepAssistIsAnnoying());
        registerTweak(new AutoClimbLadder());
        registerTweak(new DisablePotionShift());
        registerTweak(new HideShieldUnlessHoldingWeapon());
        registerTweak(new DoNotUseLastTorch());

        MinecraftForge.EVENT_BUS.register(this);

        ClientEventHandler.setupKeyBindings(tweaks.values());
    }

    private void registerTweak(AbstractClientTweak tweak) {
        tweaks.put(tweak.getName(), tweak);
    }

}
