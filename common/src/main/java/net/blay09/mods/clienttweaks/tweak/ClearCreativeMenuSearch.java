package net.blay09.mods.clienttweaks.tweak;

import com.mojang.blaze3d.platform.InputConstants;
import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.ScreenCallback;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.blay09.mods.clienttweaks.mixin.CreativeModeInventoryScreenAccessor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.input.MouseButtonEvent;

public class ClearCreativeMenuSearch extends AbstractClientTweak {

    public ClearCreativeMenuSearch() {
        super("clear_creative_menu_search");

        ScreenCallback.MousePress.Before.EVENT.register(this::onRightClick);
    }

    private boolean onRightClick(Screen screen, MouseButtonEvent event) {
        if (isEnabled()) {
            if (event.button() == InputConstants.MOUSE_BUTTON_RIGHT && screen instanceof CreativeModeInventoryScreen creativeModeInventoryScreen) {
                var editBox = ((CreativeModeInventoryScreenAccessor) creativeModeInventoryScreen).getSearchBox();
                if (editBox != null && editBox.isVisible() && editBox.isMouseOver(event.x(), event.y())) {
                    editBox.setValue("");
                    ((CreativeModeInventoryScreenAccessor) creativeModeInventoryScreen).callRefreshSearchResults();
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.clearCreativeMenuSearchOnRightClick;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.clearCreativeMenuSearchOnRightClick = enabled);
    }
}
