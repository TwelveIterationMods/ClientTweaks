package net.blay09.mods.clienttweaks.tweak;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoLadder extends ClientTweak {

	public AutoLadder() {
		super("autoClimbLadder");
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START && isEnabled()) {
			if (event.player == mc.player && event.player.isOnLadder() && !event.player.isSneaking() && event.player.rotationPitch <= -50f) {
				event.player.motionY = 0.2;
			}
		}
	}

	@Override
	public String getDescription() {
		return "This option will let you climb ladders automatically by just looking upwards, rather than requiring a key to be held down.";
	}

}
