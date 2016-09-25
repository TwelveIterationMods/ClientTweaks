package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionsRowList;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Screw3dAnaglyph extends ClientTweak {

	public Screw3dAnaglyph() {
		super("Disable 3D Anaglyph");
	}

	@SubscribeEvent
	public void onInitGui(GuiScreenEvent.InitGuiEvent.Post event) {
		if(isEnabled() && event.getGui() instanceof GuiVideoSettings) {
			GuiOptionsRowList optionsRowList = (GuiOptionsRowList) ((GuiVideoSettings) event.getGui()).optionsRowList;
			for(int i = 0; i < optionsRowList.getSize(); i++) {
				GuiOptionsRowList.Row row = optionsRowList.getListEntry(i);
				if(row.buttonA instanceof GuiOptionButton) {
					if(((GuiOptionButton) row.buttonA).returnEnumOptions() == GameSettings.Options.ANAGLYPH) {
						row.buttonA.enabled = false;
						return;
					}
				}
				if(row.buttonB instanceof GuiOptionButton) {
					if(((GuiOptionButton) row.buttonB).returnEnumOptions() == GameSettings.Options.ANAGLYPH) {
						row.buttonB.enabled = false;
						return;
					}
				}
			}
		}
	}

	@Override
	public boolean isEnabledDefault() {
		return true;
	}

	@Override
	public String getDescription() {
		return "Disables the 3d Anaglyph button so you don't accidentally click it and cause a whole resource pack reload.";
	}
}
