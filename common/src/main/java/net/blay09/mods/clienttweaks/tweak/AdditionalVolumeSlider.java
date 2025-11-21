package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.client.gui.screens.BalmScreenUtils;
import net.blay09.mods.balm.client.platform.event.callback.ScreenCallback;
import net.blay09.mods.balm.mixin.ScreenAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractOptionSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.sounds.SoundSource;

public abstract class AdditionalVolumeSlider extends AbstractClientTweak {

    private final SoundSource soundSource;
    private final int column;

    private AbstractWidget lastSlider;

    public AdditionalVolumeSlider(String name, SoundSource soundSource, int column) {
        super(name);
        this.soundSource = soundSource;
        this.column = column;

        ScreenCallback.Init.After.EVENT.register(this::onInitGui);
    }

    public void onInitGui(Screen screen) {
        if (screen instanceof OptionsScreen && isEnabled()) {
            int x = 0;
            int y = 0;
            final var offsetX = column == 0 ? 0 : 160;
            // Find the FOV slider on the original options screen...

            if (lastSlider != null) {
                final var accessor = (ScreenAccessor) screen;
                accessor.balm$getChildren().removeIf(widget -> widget == lastSlider);
                accessor.balm$getRenderables().removeIf(widget -> widget == lastSlider);
                accessor.balm$getNarratables().removeIf(widget -> widget == lastSlider);
            }

            for (GuiEventListener widget : ((ScreenAccessor) screen).balm$getChildren()) {
                if (widget instanceof AbstractOptionSliderButton slider) {
                    x = slider.getX();
                    y = slider.getY();
                    break;
                }
            }

            final var options = Minecraft.getInstance().options;
            final var option = options.getSoundSourceOptionInstance(soundSource);
            lastSlider = option.createButton(options, x + offsetX, y + 27, 150);
            BalmScreenUtils.addRenderableWidget(screen, lastSlider);
        }
    }

}
