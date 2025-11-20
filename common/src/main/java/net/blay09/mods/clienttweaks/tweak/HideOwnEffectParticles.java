package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.ClientTickCallback;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.blay09.mods.clienttweaks.mixin.LivingEntityAccessor;
import net.minecraft.client.Minecraft;

import java.util.Collections;

public class HideOwnEffectParticles extends AbstractClientTweak {

    public HideOwnEffectParticles() {
        super("hide_own_particle_effects");

        ClientTickCallback.AFTER.register(this::onClientTick);
    }

    public void onClientTick(Minecraft client) {
        final var player = client.player;
        if (player != null && isEnabled()) {
            player.getEntityData().set(LivingEntityAccessor.getDataEffectAmbienceId(), true);
            player.getEntityData().set(LivingEntityAccessor.getDataEffectParticles(), Collections.emptyList());
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.hideOwnParticleEffects;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.hideOwnParticleEffects = enabled);
    }

}
