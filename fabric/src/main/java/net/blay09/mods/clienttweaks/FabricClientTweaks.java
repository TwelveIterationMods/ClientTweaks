package net.blay09.mods.clienttweaks;

import net.blay09.mods.balm.api.Balm;
import net.fabricmc.api.ModInitializer;

public class FabricClientTweaks implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initialize(ClientTweaks.MOD_ID, ClientTweaks::initializeCommon);
    }
}
