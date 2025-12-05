package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.client.screen.ScreenMouseEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.blay09.mods.clienttweaks.mixin.AbstractRecipeBookScreenAccessor;
import net.blay09.mods.clienttweaks.mixin.RecipeBookComponentAccessor;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;

public class ClearRecipeBookSearch extends AbstractClientTweak {

    public ClearRecipeBookSearch() {
        super("clear_recipe_book_search");

        Balm.getEvents().onEvent(ScreenMouseEvent.Click.Pre.class, this::onRightClick);
    }

    public void onRightClick(ScreenMouseEvent event) {
        if (isEnabled()) {
            if (event.getButton() == 1 && event.getScreen() instanceof AbstractRecipeBookScreen) {
                var recipeBookComponent = ((AbstractRecipeBookScreenAccessor) event.getScreen()).getRecipeBookComponent();
                var editBox = ((RecipeBookComponentAccessor) recipeBookComponent).getSearchBox();
                if (editBox.isMouseOver(event.getMouseX(), event.getMouseY())) {
                    editBox.setValue("");
                    ((RecipeBookComponentAccessor) recipeBookComponent).callCheckSearchStringUpdate();
                }
            }
        }
    }

    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.clearRecipeBook;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.clearRecipeBook = enabled);
    }

}
