package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class HideOwnEffectParticles extends ClientTweak {

    public HideOwnEffectParticles() {
        super("hideOwnParticleEffects");
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && isEnabled()) {
            if (mc.player != null) {
                mc.player.getDataManager().set(EntityLivingBase.HIDE_PARTICLES, true);
                mc.player.getDataManager().set(EntityLivingBase.POTION_EFFECTS, 0);
            }
        }
    }

    @Override
    public String getDescription() {
        return "This option will hide your own potion particle effects for your client (other players will still see them).";
    }

}
