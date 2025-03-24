package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.TickPhase;
import net.blay09.mods.balm.api.event.TickType;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.blay09.mods.clienttweaks.mixin.LivingEntityAccessor;
import net.minecraft.client.Minecraft;

import java.util.Collections;

public class HideOwnEffectParticles extends AbstractClientTweak {

    public HideOwnEffectParticles() {
        super("hide_own_particle_effects");

        Balm.getEvents().onTickEvent(TickType.Client, TickPhase.End, this::onClientTick);
    }

    public void onClientTick(Minecraft client) {
        if (isEnabled()) {
            final var player = client.player;
            if (player != null) {
                player.getEntityData().set(LivingEntityAccessor.getDataEffectAmbienceId(), true);
                player.getEntityData().set(LivingEntityAccessor.getDataEffectParticles(), Collections.emptyList());
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.hideOwnParticleEffects;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.hideOwnParticleEffects = enabled);
    }

}
