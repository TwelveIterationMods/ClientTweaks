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

public class MasterVolumeSlider extends AbstractClientTweak implements GuiSlider.ISlider {

    private final String description;
    private final SoundCategory soundCategory;
    private final int offsetX;

    public MasterVolumeSlider(String name, String description, SoundCategory soundCategory, int offsetX) {
        super(name);
        this.description = description;
        this.soundCategory = soundCategory;
        this.offsetX = offsetX;
    }

    @SubscribeEvent
    public void onInitGui(GuiScreenEvent.InitGuiEvent.Post event) {
        if (isEnabled() && event.getGui() instanceof GuiOptions) {
            int x = 0;
            int y = 0;
            // Find the FOV slider on the original options screen...
            for (GuiButton guiButton : event.getButtonList()) {
                if (guiButton instanceof GuiOptionSlider) {
                    x = guiButton.x;
                    y = guiButton.y;
                }
            }

            GuiSlider slider = new GuiSlider(-999, x + offsetX, y + 27, getSliderDisplayString(), 0f, 1f, Minecraft.getInstance().gameSettings.getSoundLevel(soundCategory), this);
            slider.displayString = getSliderDisplayString();
            event.addButton(slider);
        }
    }

    @Override
    public void onChangeSliderValue(GuiSlider slider) {
        Minecraft mc = Minecraft.getInstance();
        mc.gameSettings.setSoundLevel(soundCategory, (float) slider.getValue());
        mc.gameSettings.saveOptions();

        slider.displayString = getSliderDisplayString();
    }

    private String getSliderDisplayString() {
        float volume = Minecraft.getInstance().gameSettings.getSoundLevel(soundCategory);
        String displayVolume = volume == 0f ? I18n.format("options.off") : (int) (volume * 100f) + "%";
        return I18n.format("soundCategory." + soundCategory.getName()) + ": " + displayVolume;
    }

    @Override
    public boolean isEnabledDefault() {
        return true;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
