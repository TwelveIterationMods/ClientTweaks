package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.client.gui.screen.ControlsScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.client.settings.AbstractOption;
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
		if(isEnabled() && event.getGui() instanceof ControlsScreen) {
			for(Widget widget : event.getWidgetList()) {
				if(widget instanceof OptionButton && ((OptionButton) widget).field_146137_o == AbstractOption.field_216719_z) {
					widget.active = false;
				}
			}
		}
	}

	@Override
	public String getDescription() {
		return "This option forces auto jump to be disabled and also disables the button for it. Because it should never have been a thing.";
	}
}
