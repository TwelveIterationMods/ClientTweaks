package net.blay09.mods.clienttweaks;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = ClientTweaks.MOD_ID, dist = Dist.CLIENT)
public class NeoForgeClientTweaks {

    public NeoForgeClientTweaks(IEventBus modEventBus) {
        final var context = new NeoForgeLoadContext(modEventBus);
        Balm.initializeMod(ClientTweaks.MOD_ID, context, ClientTweaks::initializeCommon);
        BalmClient.initializeMod(ClientTweaks.MOD_ID, context, ClientTweaks::initializeClient);
    }

}
