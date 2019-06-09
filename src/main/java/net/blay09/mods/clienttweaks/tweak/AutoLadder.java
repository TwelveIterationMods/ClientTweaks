package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.util.math.Vec3d;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoLadder extends AbstractClientTweak {

	public AutoLadder() {
		super("autoClimbLadder");
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START && isEnabled()) {
			if (event.player == mc.player && event.player.isOnLadder() && !event.player.isSneaking() && event.player.rotationPitch <= -50f) {
				Vec3d motion = event.player.getMotion();
				event.player.setMotion(motion.x, 0.2f, motion.z);
			}
		}
	}

	@Override
	public String getDescription() {
		return "This option will let you climb ladders automatically by just looking upwards, rather than requiring a key to be held down.";
	}

}
