package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class AutoClimbLadder extends AbstractClientTweak {

	public AutoClimbLadder() {
		super("autoClimbLadder", ClientTweaksConfig.CLIENT.autoClimbLadder);
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (isEnabled() && event.phase == TickEvent.Phase.START) {
			if (event.side == LogicalSide.CLIENT && event.player.isOnLadder() && !event.player.func_225608_bj_() && event.player.rotationPitch <= -50f) {
				Vec3d motion = event.player.getMotion();
				event.player.setMotion(motion.x, 0.2f, motion.z);
			}
		}
	}

}
