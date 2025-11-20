package net.blay09.mods.clienttweaks.fabric.client;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.BalmClient;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.blay09.mods.clienttweaks.ClientTweaks;
import net.fabricmc.api.ClientModInitializer;

public class FabricClientTweaksClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Balm.initializeMod(ClientTweaks.MOD_ID, FabricLoadContext.INSTANCE, ClientTweaks::initializeCommon);
        BalmClient.initializeMod(ClientTweaks.MOD_ID, FabricLoadContext.INSTANCE, ClientTweaks::initializeClient);
    }
}
