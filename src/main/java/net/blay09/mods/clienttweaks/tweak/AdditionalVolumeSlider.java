package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.widget.OptionSlider;
import net.minecraft.client.gui.widget.SoundSlider;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AdditionalVolumeSlider extends AbstractClientTweak {

    private final SoundCategory soundCategory;
    private final int offsetX;

    public AdditionalVolumeSlider(String name, ForgeConfigSpec.BooleanValue configProperty, SoundCategory soundCategory, int offsetX) {
        super(name, configProperty);
        this.soundCategory = soundCategory;
        this.offsetX = offsetX;
    }

    @SubscribeEvent
    public void onInitGui(GuiScreenEvent.InitGuiEvent.Post event) {
        if (isEnabled() && event.getGui() instanceof OptionsScreen) {
            int x = 0;
            int y = 0;
            // Find the FOV slider on the original options screen...
            for (Widget widget : event.getWidgetList()) {
                if (widget instanceof OptionSlider) {
                    x = widget.x;
                    y = widget.y;
                }
            }

            SoundSlider slider = new SoundSlider(Minecraft.getInstance(), x + offsetX, y + 27, soundCategory, 150);
            slider.setMessage(getSliderDisplayString());
            event.addWidget(slider);
        }
    }

    private ITextComponent getSliderDisplayString() {
        float volume = Minecraft.getInstance().gameSettings.getSoundLevel(soundCategory);
        String displayVolume = volume == 0f ? I18n.format("options.off") : (int) (volume * 100f) + "%";

        final TranslationTextComponent volumeText = new TranslationTextComponent("soundCategory." + soundCategory.getName());
        volumeText.append(new StringTextComponent(": " + displayVolume));
        return volumeText;
    }

}
