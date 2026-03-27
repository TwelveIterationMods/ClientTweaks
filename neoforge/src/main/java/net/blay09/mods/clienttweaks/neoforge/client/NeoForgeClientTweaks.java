package net.blay09.mods.clienttweaks.neoforge.client;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.BalmClient;
import net.blay09.mods.balm.neoforge.platform.runtime.NeoForgeLoadContext;
import net.blay09.mods.clienttweaks.ClientTweaks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = ClientTweaks.MOD_ID, dist = Dist.CLIENT)
public class NeoForgeClientTweaks {

    public NeoForgeClientTweaks(ModContainer modContainer, IEventBus modEventBus) {
        final var context = new NeoForgeLoadContext(modContainer, modEventBus);
        Balm.initializeMod(ClientTweaks.MOD_ID, context, ClientTweaks::initializeCommon);
        BalmClient.initializeMod(ClientTweaks.MOD_ID, context, ClientTweaks::initializeClient);
    }

}
