package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoJumpMoreLikeAutoDumbAmirite extends AbstractClientTweak {

	public AutoJumpMoreLikeAutoDumbAmirite() {
		super("disableAutoJump");
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if(event.phase == TickEvent.Phase.END && isEnabled()) {
			mc.gameSettings.autoJump = false;
		}
	}

	@SubscribeEvent
	public void onInitGui(GuiScreenEvent.InitGuiEvent.Post event) {
		if(isEnabled() && event.getGui() instanceof GuiControls) {
			for(GuiButton button : event.getButtonList()) {
				if(button instanceof GuiOptionButton && ((GuiOptionButton) button).getOption() == GameSettings.Options.AUTO_JUMP) {
					button.enabled = false;
				}
			}
		}
	}

	@Override
	public String getDescription() {
		return "This option forces auto jump to be disabled and also disables the button for it. Because it should never have been a thing.";
	}
}
