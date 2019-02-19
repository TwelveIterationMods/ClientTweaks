package net.blay09.mods.clienttweaks.tweak;

import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DisablePotionShift extends ClientTweak {

	public DisablePotionShift() {
		super("disablePotionShift");
	}

	@Override
	public String getDescription() {
		return "This option disables the shifting of the inventory to the right when potion effects are active.";
	}

	@SubscribeEvent
	public void onPotionShift(GuiScreenEvent.PotionShiftEvent event) {
		event.setCanceled(true);
	}

	@Override
	public boolean isEnabledDefault() {
		return true;
	}
}
