package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoJumpMoreLikeAutoDumbAmirite extends ClientTweak {

	public AutoJumpMoreLikeAutoDumbAmirite() {
		super("Disable Auto Jump");

		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		if(isEnabled()) {
			Minecraft.getMinecraft().gameSettings.field_189989_R = false; // autoJump
		}
	}

	@SubscribeEvent
	public void onInitGui(GuiScreenEvent.InitGuiEvent.Post event) {
		if(isEnabled() && event.getGui() instanceof GuiControls) {
			for(GuiButton button : event.getButtonList()) {
				if(button instanceof GuiOptionButton && ((GuiOptionButton) button).returnEnumOptions() == GameSettings.Options.AUTO_JUMP) {
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
