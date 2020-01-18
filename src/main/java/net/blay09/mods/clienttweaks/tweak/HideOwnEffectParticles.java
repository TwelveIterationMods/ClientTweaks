package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HideOwnEffectParticles extends AbstractClientTweak {

    public HideOwnEffectParticles() {
        super("hideOwnParticleEffects", ClientTweaksConfig.CLIENT.hideOwnParticleEffects);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.END && isEnabled()) {
            if (mc.player != null) {
                mc.player.getDataManager().set(LivingEntity.HIDE_PARTICLES, true);
                mc.player.getDataManager().set(LivingEntity.POTION_EFFECTS, 0);
            }
        }
    }

}
