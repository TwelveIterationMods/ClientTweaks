package net.blay09.mods.clienttweaks;

import net.blay09.mods.balm.api.Balm;

public class ClientTweaksConfig {

    public static ClientTweaksConfigData getActive() {
        return Balm.getConfig().getActive(ClientTweaksConfigData.class);
    }

    public static void initialize() {
        Balm.getConfig().registerConfig(ClientTweaksConfigData.class, null);
    }

}
