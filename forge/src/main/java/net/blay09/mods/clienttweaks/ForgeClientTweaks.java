package net.blay09.mods.clienttweaks;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.EmptyLoadContext;
import net.blay09.mods.balm.api.client.BalmClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(ClientTweaks.MOD_ID)
public class ForgeClientTweaks {

    public ForgeClientTweaks() {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> Balm.initialize(ClientTweaks.MOD_ID, EmptyLoadContext.INSTANCE, ClientTweaks::initializeCommon));
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> BalmClient.initialize(ClientTweaks.MOD_ID, EmptyLoadContext.INSTANCE, ClientTweaks::initializeClient));

        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));
    }

}
