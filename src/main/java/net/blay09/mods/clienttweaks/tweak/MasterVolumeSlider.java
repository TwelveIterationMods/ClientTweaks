package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MasterVolumeSlider extends ClientTweak implements GuiPageButtonList.GuiResponder, GuiSlider.FormatHelper {

	public MasterVolumeSlider() {
		super("Master Volume Slider");

		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onInitGui(GuiScreenEvent.InitGuiEvent.Post event) {
		if (isEnabled() && event.getGui() instanceof GuiOptions) {
			int x = 0;
			int y = 0;
			for (GuiButton guiButton : event.getButtonList()) {
				if (guiButton instanceof GuiOptionSlider) {
					x = guiButton.xPosition;
					y = guiButton.yPosition;
				}
			}
			event.getButtonList().add(new GuiSlider(this, -999, x, y + 27, SoundCategory.MASTER.getName(), 0f, 1f, Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER), this));
		}
	}

	@Override
	public void setEntryValue(int id, boolean value) {
	}

	@Override
	public void setEntryValue(int id, float value) {
		Minecraft mc = Minecraft.getMinecraft();
		mc.gameSettings.setSoundLevel(SoundCategory.MASTER, value);
		mc.gameSettings.saveOptions();
	}

	@Override
	public void setEntryValue(int id, String value) {

	}

	@Override
	public String getText(int id, String name, float value) {
		float volume = Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER);
		String displayVolume = volume == 0f ? I18n.format("options.off") : (int) (volume * 100f) + "%";
		return I18n.format("soundCategory." + SoundCategory.MASTER.getName()) + ": " + displayVolume;
	}

	@Override
	public boolean isEnabledDefault() {
		return true;
	}

	@Override
	public String getDescription() {
		return "This adds back the master volume slider to the options screen. Saves you a click!";
	}
}
