package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.balm.api.event.client.screen.ScreenInitEvent;
import net.blay09.mods.balm.mixin.ScreenAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SliderButton;
import net.minecraft.client.gui.components.VolumeSlider;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;

public abstract class AdditionalVolumeSlider extends AbstractClientTweak {

    private final SoundSource soundSource;
    private final int offsetX;

    public AdditionalVolumeSlider(String name, SoundSource soundSource, int offsetX) {
        super(name);
        this.soundSource = soundSource;
        this.offsetX = offsetX;

        Balm.getEvents().onEvent(ScreenInitEvent.Post.class, this::onInitGui);
    }

    public void onInitGui(ScreenInitEvent.Post event) {
        if (isEnabled() && event.getScreen() instanceof OptionsScreen) {
            int x = 0;
            int y = 0;
            // Find the FOV slider on the original options screen...
            for (GuiEventListener widget : ((ScreenAccessor) event.getScreen()).balm_getChildren()) {
                if (widget instanceof SliderButton slider) {
                    x = slider.x;
                    y = slider.y;
                }
            }

            VolumeSlider slider = new VolumeSlider(Minecraft.getInstance(), x + offsetX, y + 27, soundSource, 150);
            slider.setMessage(getSliderDisplayString());
            BalmClient.getScreens().addRenderableWidget(event.getScreen(), slider);
        }
    }

    private Component getSliderDisplayString() {
        float volume = Minecraft.getInstance().options.getSoundSourceVolume(soundSource);
        String displayVolume = volume == 0f ? I18n.get("options.off") : (int) (volume * 100f) + "%";

        final TranslatableComponent volumeText = new TranslatableComponent("soundCategory." + soundSource.getName());
        volumeText.append(new TextComponent(": " + displayVolume));
        return volumeText;
    }

}
