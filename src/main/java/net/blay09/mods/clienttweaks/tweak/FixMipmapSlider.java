package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiOptionsRowList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FixMipmapSlider extends ClientTweak {

	public FixMipmapSlider() {
		super("Fix Mipmap Slider");
	}

	@Override
	public String getDescription() {
		return "This option makes the mipmap slider more responsive and adds a confirmation screen for it, to prevent accidents.";
	}

	@SubscribeEvent
	public void onInitGui(GuiScreenEvent.InitGuiEvent.Post event) {
		if (event.getGui() instanceof GuiVideoSettings) {
			GuiOptionsRowList optionsRowList = (GuiOptionsRowList) ((GuiVideoSettings) event.getGui()).optionsRowList;
			for (int i = 0; i < optionsRowList.getSize(); i++) {
				GuiOptionsRowList.Row row = optionsRowList.getListEntry(i);
				if (row.buttonA instanceof GuiOptionSlider && ((GuiOptionSlider) row.buttonA).options == GameSettings.Options.MIPMAP_LEVELS) {
					row.buttonA = createBetterMipmapSlider(row.buttonA);
					return;
				}
				if (row.buttonB instanceof GuiOptionSlider && ((GuiOptionSlider) row.buttonB).options == GameSettings.Options.MIPMAP_LEVELS) {
					row.buttonB = createBetterMipmapSlider(row.buttonB);
					return;
				}
			}
		}
	}

	private GuiButton createBetterMipmapSlider(GuiButton original) {
		return new GuiBetterMipmapSlider(original.id, original.xPosition, original.yPosition);
	}

	@Override
	public boolean isEnabledDefault() {
		return true;
	}

	public static class GuiBetterMipmapSlider extends GuiOptionSlider {

		private float sliderValue;

		public GuiBetterMipmapSlider(int buttonId, int x, int y) {
			super(buttonId, x, y, GameSettings.Options.MIPMAP_LEVELS);
			this.sliderValue = 1f;
			this.sliderValue = this.options.normalizeValue(Minecraft.getMinecraft().gameSettings.getOptionFloatValue(this.options));
		}

		@Override
		protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
			if (this.visible) {
				if (this.dragging) {
					this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);
					this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
					float f = this.options.denormalizeValue(this.sliderValue);
					this.sliderValue = this.options.normalizeValue(f);
					this.displayString = getDisplayString();
				}

				mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
				GlStateManager.color(1f, 1f, 1f, 1f);
				this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)), this.yPosition, 0, 66, 4, 20);
				this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
			}
		}

		@Override
		public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
			if (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height) {
				this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);
				this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0f, 1f);
				this.displayString = getDisplayString();
				this.dragging = true;
				return true;
			}
			return false;
		}

		@Override
		public void mouseReleased(int mouseX, int mouseY) {
			float sliderValueDenorm = this.options.denormalizeValue(this.sliderValue);
			if(sliderValueDenorm != Minecraft.getMinecraft().gameSettings.getOptionFloatValue(this.options)) {
				final GuiScreen parentScreen = Minecraft.getMinecraft().currentScreen;
				Minecraft.getMinecraft().displayGuiScreen(new GuiYesNo(new GuiYesNoCallback() {
					@Override
					public void confirmClicked(boolean result, int id) {
						if(result) {
							Minecraft.getMinecraft().gameSettings.setOptionFloatValue(GuiBetterMipmapSlider.this.options, GuiBetterMipmapSlider.this.options.denormalizeValue(GuiBetterMipmapSlider.this.sliderValue));
						}
						Minecraft.getMinecraft().displayGuiScreen(parentScreen);
					}
				}, I18n.format("gui.clienttweaks.mipmapConfirm"), I18n.format("gui.clienttweaks.mipmapWarning"), 0));
			}
			this.dragging = false;
		}

		private String getDisplayString() {
			float value = this.options.denormalizeValue(this.sliderValue);
			return I18n.format(this.options.getEnumString()) + ": " + (value == 0f ? I18n.format("options.off") : (int) value);
		}
	}
}
