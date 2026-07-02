package net.blay09.mods.clienttweaks.tweak;

import com.mojang.blaze3d.platform.InputConstants;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.client.screen.ScreenMouseEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.blay09.mods.clienttweaks.mixin.CreativeModeInventoryScreenAccessor;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;

public class ClearCreativeMenuSearch extends AbstractClientTweak {

    public ClearCreativeMenuSearch() {
        super("clearCreativeMenuSearchOnRightClick");

        Balm.getEvents().onEvent(ScreenMouseEvent.Click.Pre.class, this::onRightClick);
    }

    private void onRightClick(ScreenMouseEvent.Click.Pre event) {
        if (isEnabled() && event.getButton() == InputConstants.MOUSE_BUTTON_RIGHT && event.getScreen() instanceof CreativeModeInventoryScreen creativeModeInventoryScreen) {
            final var searchBox = ((CreativeModeInventoryScreenAccessor) creativeModeInventoryScreen).getSearchBox();
            if (searchBox != null && searchBox.isVisible() && searchBox.isMouseOver(event.getMouseX(), event.getMouseY())) {
                searchBox.setValue("");
                ((CreativeModeInventoryScreenAccessor) creativeModeInventoryScreen).callRefreshSearchResults();
                event.setCanceled(true);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.clearCreativeMenuSearchOnRightClick;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.clearCreativeMenuSearchOnRightClick = enabled);
    }
}
