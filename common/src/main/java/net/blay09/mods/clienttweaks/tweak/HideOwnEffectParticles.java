package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;

public class HideOwnEffectParticles extends AbstractClientTweak {

    public HideOwnEffectParticles() {
        super("hide_own_particle_effects");
    }

    public static boolean shouldSuppressFor(LivingEntity entity) {
        final var client = Minecraft.getInstance();
        final var config = ClientTweaksConfig.getActiveOrNull();
        return config != null
                && config.rendering.hideOwnParticleEffects
                && client.player == entity
                && client.getCameraEntity() == entity
                && client.options.getCameraType().isFirstPerson();
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().rendering.hideOwnParticleEffects;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfig.class, it -> it.rendering.hideOwnParticleEffects = enabled);
    }

}
