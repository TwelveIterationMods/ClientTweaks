package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.config.GuiSlider;

public class MasterVolumeSlider extends ClientTweak implements GuiSlider.ISlider {

    public MasterVolumeSlider() {
        super("masterVolumeSlider");
    }

    @SubscribeEvent
    public void onInitGui(GuiScreenEvent.InitGuiEvent.Post event) {
        if (isEnabled() && event.getGui() instanceof GuiOptions) {
            int x = 0;
            int y = 0;
            for (GuiButton guiButton : event.getButtonList()) {
                if (guiButton instanceof GuiOptionSlider) {
                    x = guiButton.x;
                    y = guiButton.y;
                }
            }

            GuiSlider slider = new GuiSlider(-999, x, y + 27, getSliderDisplayString(), 0f, 1f, Minecraft.getInstance().gameSettings.getSoundLevel(SoundCategory.MASTER), this);
            slider.displayString = getSliderDisplayString();
            event.addButton(slider);
        }
    }

    @Override
    public void onChangeSliderValue(GuiSlider slider) {
        Minecraft mc = Minecraft.getInstance();
        mc.gameSettings.setSoundLevel(SoundCategory.MASTER, (float) slider.getValue());
        mc.gameSettings.saveOptions();

        slider.displayString = getSliderDisplayString();
    }

    private String getSliderDisplayString() {
        float volume = Minecraft.getInstance().gameSettings.getSoundLevel(SoundCategory.MASTER);
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
