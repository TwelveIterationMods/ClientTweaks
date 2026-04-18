package net.blay09.mods.clienttweaks.tweak;

import com.mojang.blaze3d.platform.InputConstants;
import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.ScreenCallback;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.mixin.AbstractRecipeBookScreenAccessor;
import net.blay09.mods.clienttweaks.mixin.RecipeBookComponentAccessor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.input.MouseButtonEvent;

public class ClearRecipeBookSearch extends AbstractClientTweak {

    public ClearRecipeBookSearch() {
        super("clear_recipe_book_search");

        ScreenCallback.MousePress.Before.EVENT.register(this::onRightClick);
    }

    private boolean onRightClick(Screen screen, MouseButtonEvent event) {
        if (isEnabled()) {
            if (event.button() == InputConstants.MOUSE_BUTTON_RIGHT && screen instanceof AbstractRecipeBookScreen) {
                var recipeBookComponent = ((AbstractRecipeBookScreenAccessor) screen).getRecipeBookComponent();
                var editBox = ((RecipeBookComponentAccessor) recipeBookComponent).getSearchBox();
                if (editBox != null && editBox.isMouseOver(event.x(), event.y())) {
                    editBox.setValue("");
                    editBox.setFocused(true);
                    ((RecipeBookComponentAccessor) recipeBookComponent).callCheckSearchStringUpdate();
                    return true;
                }
            }
        }

        return false;
    }
    
    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().recipeBook.clearRecipeBookOnRightClick;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfig.class, it -> it.recipeBook.clearRecipeBookOnRightClick = enabled);
    }

}
