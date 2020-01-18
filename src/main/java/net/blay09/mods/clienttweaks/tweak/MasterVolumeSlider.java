package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.widget.OptionSlider;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.config.GuiSlider;

public class MasterVolumeSlider extends AbstractClientTweak implements GuiSlider.ISlider {

    private final SoundCategory soundCategory;
    private final int offsetX;

    public MasterVolumeSlider(String name, ForgeConfigSpec.BooleanValue configProperty, SoundCategory soundCategory, int offsetX) {
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

            float soundLevel = Minecraft.getInstance().gameSettings.getSoundLevel(soundCategory);
            GuiSlider slider = new GuiSlider(x + offsetX, y + 27, getSliderDisplayString(), 0.0, 1.0, soundLevel, it -> {
            }, this);
            slider.setMessage(getSliderDisplayString());
            event.addWidget(slider);
        }
    }

    @Override
    public void onChangeSliderValue(GuiSlider slider) {
        Minecraft mc = Minecraft.getInstance();
        mc.gameSettings.setSoundLevel(soundCategory, (float) slider.getValue());
        mc.gameSettings.saveOptions();

        slider.setMessage(getSliderDisplayString());
    }

    private String getSliderDisplayString() {
        float volume = Minecraft.getInstance().gameSettings.getSoundLevel(soundCategory);
        String displayVolume = volume == 0f ? I18n.format("options.off") : (int) (volume * 100f) + "%";
        return I18n.format("soundCategory." + soundCategory.getName()) + ": " + displayVolume;
    }

}
