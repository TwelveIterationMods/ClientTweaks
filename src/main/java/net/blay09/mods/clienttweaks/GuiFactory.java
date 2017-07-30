package net.blay09.mods.clienttweaks;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.List;
import java.util.Set;

public class GuiFactory implements IModGuiFactory {
	@Override
	public void initialize(Minecraft minecraftInstance) {
	}

	@Override
	public boolean hasConfigGui() {
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		return new GuiConfigClientTweaks(parentScreen);
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	public static class GuiConfigClientTweaks extends GuiConfig {
		public GuiConfigClientTweaks(GuiScreen parentScreen) {
			super(parentScreen, getConfigElements(), ClientTweaks.MOD_ID, false, false, "Client Tweaks");
		}

		public static List<IConfigElement> getConfigElements() {
			List<IConfigElement> list = Lists.newArrayList();
			list.addAll(new ConfigElement(ClientTweaks.config.getCategory("tweaks")).getChildElements());
			list.addAll(new ConfigElement(ClientTweaks.config.getCategory("general")).getChildElements());
			return list;
		}
	}

}
