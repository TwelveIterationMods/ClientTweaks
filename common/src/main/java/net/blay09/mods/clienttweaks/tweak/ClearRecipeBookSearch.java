package net.blay09.mods.clienttweaks.tweak;

import com.mojang.blaze3d.platform.InputConstants;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.client.screen.ScreenMouseEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.blay09.mods.clienttweaks.mixin.RecipeBookComponentAccessor;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;

public class ClearRecipeBookSearch extends AbstractClientTweak {

    public ClearRecipeBookSearch() {
        super("clearRecipeBookOnRightClick");

        Balm.getEvents().onEvent(ScreenMouseEvent.Click.Pre.class, this::onRightClick);
    }

    private void onRightClick(ScreenMouseEvent.Click.Pre event) {
        if (isEnabled() && event.getButton() == InputConstants.MOUSE_BUTTON_RIGHT && event.getScreen() instanceof RecipeUpdateListener screen) {
            final var recipeBookComponent = screen.getRecipeBookComponent();
            final var searchBox = ((RecipeBookComponentAccessor) recipeBookComponent).getSearchBox();
            if (searchBox != null && searchBox.isMouseOver(event.getMouseX(), event.getMouseY())) {
                searchBox.setValue("");
                ((RecipeBookComponentAccessor) recipeBookComponent).callCheckSearchStringUpdate();
                event.setCanceled(true);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().recipeBook.clearRecipeBookOnRightClick;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.recipeBook.clearRecipeBookOnRightClick = enabled);
    }

}
