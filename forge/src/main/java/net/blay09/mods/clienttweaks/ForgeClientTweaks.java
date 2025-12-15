package net.blay09.mods.clienttweaks;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.BalmClient;
import net.blay09.mods.balm.forge.platform.runtime.ForgeLoadContext;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(ClientTweaks.MOD_ID)
public class ForgeClientTweaks {

    public ForgeClientTweaks(FMLJavaModLoadingContext context) {
        final var loadContext = new ForgeLoadContext(context.getModBusGroup());
        if (FMLEnvironment.dist.isClient()) {
            Balm.initializeMod(ClientTweaks.MOD_ID, loadContext, ClientTweaks::initializeCommon);
            BalmClient.initializeMod(ClientTweaks.MOD_ID, loadContext, ClientTweaks::initializeClient);
        }

        context.registerDisplayTest(IExtensionPoint.DisplayTest.IGNORE_ALL_VERSION);
    }

}
