package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.TickPhase;
import net.blay09.mods.balm.api.event.TickType;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.blay09.mods.clienttweaks.mixin.LivingEntityAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class AutoClimbLadder extends AbstractClientTweak {

	public AutoClimbLadder() {
		super("autoClimbLadder");

		Balm.getEvents().onTickEvent(TickType.Client, TickPhase.Start, this::onPlayerTick);
	}

	public void onPlayerTick(Minecraft client) {
		if (isEnabled()) {
			Player player = client.player;
			if (player != null && player.onClimbable() && !player.isSuppressingSlidingDownLadder() && player.getXRot() <= -50f) {
				player.resetFallDistance();
				Vec3 motion = player.getDeltaMovement();
				player.setDeltaMovement(motion.x, player.getSpeed(), motion.z);
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
