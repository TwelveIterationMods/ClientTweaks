package net.blay09.mods.clienttweaks.fabric.client;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.EmptyLoadContext;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.clienttweaks.ClientTweaks;
import net.fabricmc.api.ClientModInitializer;

public class FabricClientTweaksClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Balm.initializeMod(ClientTweaks.MOD_ID, EmptyLoadContext.INSTANCE, ClientTweaks::initializeCommon);
        BalmClient.initializeMod(ClientTweaks.MOD_ID, EmptyLoadContext.INSTANCE, ClientTweaks::initializeClient);
    }
}
