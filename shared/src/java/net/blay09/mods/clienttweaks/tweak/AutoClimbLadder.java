package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class AutoClimbLadder extends AbstractClientTweak {

	public AutoClimbLadder() {
		super("autoClimbLadder");
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (isEnabled() && event.phase == TickEvent.Phase.START) {
			if (event.side == LogicalSide.CLIENT && event.player.onClimbable() && !event.player.isShiftKeyDown() && event.player.getXRot() <= -50f) {
				Vec3 motion = event.player.getDeltaMovement();
				event.player.setDeltaMovement(motion.x, 0.2f, motion.z);
			}
		}
	}

	@Override
	public boolean isEnabled() {
		return ClientTweaksConfig.getActive().tweaks.autoClimbLadder;
	}

	@Override
	public void setEnabled(boolean enabled) {
		Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.autoClimbLadder = enabled);
	}
}
