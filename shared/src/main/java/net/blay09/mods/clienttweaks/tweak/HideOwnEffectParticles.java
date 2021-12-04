package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.TickPhase;
import net.blay09.mods.balm.api.event.TickType;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.blay09.mods.clienttweaks.mixin.LivingEntityAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class HideOwnEffectParticles extends AbstractClientTweak {

    public HideOwnEffectParticles() {
        super("hideOwnParticleEffects");

        Balm.getEvents().onTickEvent(TickType.Client, TickPhase.End, this::onClientTick);
    }

    public void onClientTick(Minecraft client) {
        if (isEnabled()) {
            Player player = client.player;
            if (player != null) {
                player.getEntityData().set(LivingEntityAccessor.getDataEffectAmbienceId(), true);
                player.getEntityData().set(LivingEntityAccessor.getDataEffectColorId(), 0);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.hideOwnParticleEffects;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.hideOwnParticleEffects = enabled);
    }

}
