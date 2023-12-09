package net.blay09.mods.clienttweaks;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.client.BalmClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.DistExecutor;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;

@Mod(ClientTweaks.MOD_ID)
public class NeoForgeClientTweaks {

    public NeoForgeClientTweaks() {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> Balm.initialize(ClientTweaks.MOD_ID, ClientTweaks::initializeCommon));
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> BalmClient.initialize(ClientTweaks.MOD_ID, ClientTweaks::initializeClient));

        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));
    }

}
