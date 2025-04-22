package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.balm.api.event.client.screen.ScreenInitEvent;
import net.blay09.mods.balm.mixin.ScreenAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractOptionSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.sounds.SoundSource;

public abstract class AdditionalVolumeSlider extends AbstractClientTweak {

    private final SoundSource soundSource;
    private final int column;

    private AbstractWidget slider;

    public AdditionalVolumeSlider(String name, SoundSource soundSource, int column) {
        super(name);
        this.soundSource = soundSource;
        this.column = column;

        Balm.getEvents().onEvent(ScreenInitEvent.Post.class, this::onInitGui);
    }

    public void onInitGui(ScreenInitEvent.Post event) {
        if (event.getScreen() instanceof OptionsScreen && isEnabled()) {
            int x = 0;
            int y = 0;
            final var offsetX = column == 0 ? 0 : 160;
            // Find the FOV slider on the original options screen...

            if (slider != null) {
                final var accessor = (ScreenAccessor) event.getScreen();
                accessor.balm_getChildren().removeIf(widget -> widget == slider);
                accessor.balm_getRenderables().removeIf(widget -> widget == slider);
                accessor.balm_getNarratables().removeIf(widget -> widget == slider);
            }

            for (GuiEventListener widget : ((ScreenAccessor) event.getScreen()).balm_getChildren()) {
                if (widget instanceof AbstractOptionSliderButton slider) {
                    x = slider.getX();
                    y = slider.getY();
                    break;
                }
            }

            final var options = Minecraft.getInstance().options;
            final var option = options.getSoundSourceOptionInstance(soundSource);
            slider = option.createButton(options, x + offsetX, y + 27, 150);
            BalmClient.getScreens().addRenderableWidget(event.getScreen(), slider);
        }
    }

}
