package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.client.screen.ScreenInitEvent;
import net.blay09.mods.balm.mixin.ScreenAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.GridWidget;
import net.minecraft.client.gui.components.LayoutSettings;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.sounds.SoundSource;

public abstract class AdditionalVolumeSlider extends AbstractClientTweak {

    private final SoundSource soundSource;
    private final int column;

    public AdditionalVolumeSlider(String name, SoundSource soundSource, int column) {
        super(name);
        this.soundSource = soundSource;
        this.column = column;

        Balm.getEvents().onEvent(ScreenInitEvent.Post.class, this::onInitGui);
    }

    public void onInitGui(ScreenInitEvent.Post event) {
        if (isEnabled() && event.getScreen() instanceof OptionsScreen) {
            // Find the grid on the original options screen, then inject our volume slider into the specific cell
            for (GuiEventListener widget : ((ScreenAccessor) event.getScreen()).balm_getChildren()) {
                if (widget instanceof GridWidget grid) {
                    Options options = Minecraft.getInstance().options;
                    OptionInstance<Double> option = options.getSoundSourceOptionInstance(soundSource);
                    AbstractWidget slider = option.createButton(options, 0, 0, 150);
                    grid.addChild(slider, 1, column, 1, 1, grid.newCellSettings().paddingTop(3));
                    grid.pack();
                    break;
                }
            }
        }
    }

}
