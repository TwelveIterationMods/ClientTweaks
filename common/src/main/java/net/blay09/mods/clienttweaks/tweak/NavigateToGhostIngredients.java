package net.blay09.mods.clienttweaks.tweak;

import com.mojang.blaze3d.platform.InputConstants;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.client.OpenScreenEvent;
import net.blay09.mods.balm.api.event.client.screen.ScreenKeyEvent;
import net.blay09.mods.balm.api.event.client.screen.ScreenMouseEvent;
import net.blay09.mods.balm.mixin.AbstractContainerScreenAccessor;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.blay09.mods.clienttweaks.mixin.RecipeBookComponentAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;

public class NavigateToGhostIngredients extends AbstractClientTweak {

    private final Deque<HistoryEntry> history = new ArrayDeque<>();
    @Nullable
    private HistoryEntry currentEntry;

    public NavigateToGhostIngredients() {
        super("navigateToGhostIngredients");

        Balm.getEvents().onEvent(OpenScreenEvent.class, this::onScreenOpening);
        Balm.getEvents().onEvent(ScreenMouseEvent.Click.Pre.class, this::onMouseClick);
        Balm.getEvents().onEvent(ScreenKeyEvent.Press.Pre.class, this::onKeyPress);
    }

    private void onScreenOpening(OpenScreenEvent event) {
        history.clear();
        currentEntry = null;
    }

    private void onMouseClick(ScreenMouseEvent.Click.Pre event) {
        final var minecraft = Minecraft.getInstance();
        if (!isEnabled() || event.getButton() != InputConstants.MOUSE_BUTTON_LEFT || !(event.getScreen() instanceof RecipeUpdateListener screen) || minecraft.level == null) {
            return;
        }

        final var recipeBookComponent = screen.getRecipeBookComponent();
        if (!recipeBookComponent.isVisible()) {
            return;
        }

        final var ghostRecipe = ((RecipeBookComponentAccessor) recipeBookComponent).getGhostRecipe();
        final var ghostItem = getClickedGhostIngredient(screen, event.getMouseX(), event.getMouseY());
        if (ghostItem == null) {
            return;
        }

        final var registryAccess = minecraft.level.registryAccess();
        final var recipeBook = ((RecipeBookComponentAccessor) recipeBookComponent).getBook();
        for (final var collection : recipeBook.getCollections()) {
            for (final var recipe : collection.getRecipes()) {
                final var result = recipe.getResultItem(registryAccess);
                if (ItemStack.isSameItemSameTags(result, ghostItem)) {
                    if (currentEntry == null) {
                        final var recipeBookPage = ((RecipeBookComponentAccessor) recipeBookComponent).getRecipeBookPage();
                        currentEntry = new HistoryEntry(recipeBookPage.getLastClickedRecipeCollection(), recipeBookPage.getLastClickedRecipe());
                    }

                    history.add(currentEntry);
                    currentEntry = new HistoryEntry(collection, recipe);
                    ghostRecipe.clear();
                    minecraft.gameMode.handlePlaceRecipe(minecraft.player.containerMenu.containerId, recipe, Screen.hasShiftDown());
                    event.setCanceled(true);
                    return;
                }
            }
        }
    }

    @Nullable
    private ItemStack getClickedGhostIngredient(RecipeUpdateListener screen, double mouseX, double mouseY) {
        final var recipeBookComponent = screen.getRecipeBookComponent();
        final var ghostRecipe = ((RecipeBookComponentAccessor) recipeBookComponent).getGhostRecipe();
        final var screenAccessor = (AbstractContainerScreenAccessor) screen;

        for (int i = 1; i < ghostRecipe.size(); i++) {
            final var ghostIngredient = ghostRecipe.get(i);
            final var x = ghostIngredient.getX() + screenAccessor.getLeftPos();
            final var y = ghostIngredient.getY() + screenAccessor.getTopPos();
            if (mouseX >= x && mouseY >= y && mouseX < x + 16 && mouseY < y + 16) {
                final var itemStack = ghostIngredient.getItem();
                return itemStack.isEmpty() ? null : itemStack;
            }
        }

        return null;
    }

    private void onKeyPress(ScreenKeyEvent.Press.Pre event) {
        if (!isEnabled() || event.getKey() != InputConstants.KEY_BACKSPACE || !(event.getScreen() instanceof RecipeUpdateListener screen)) {
            return;
        }

        final var recipeBookComponent = screen.getRecipeBookComponent();
        if (!recipeBookComponent.isVisible()) {
            return;
        }

        final var searchBox = ((RecipeBookComponentAccessor) recipeBookComponent).getSearchBox();
        if (searchBox != null && searchBox.isFocused()) {
            return;
        }

        if (history.isEmpty()) {
            return;
        }

        final var minecraft = Minecraft.getInstance();
        currentEntry = history.pop();
        if (currentEntry.collection() != null && currentEntry.recipe() != null) {
            ((RecipeBookComponentAccessor) recipeBookComponent).getGhostRecipe().clear();
            minecraft.gameMode.handlePlaceRecipe(minecraft.player.containerMenu.containerId, currentEntry.recipe(), false);
            event.setCanceled(true);
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().recipeBook.navigateToGhostIngredients;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.recipeBook.navigateToGhostIngredients = enabled);
    }

    private record HistoryEntry(@Nullable RecipeCollection collection, @Nullable Recipe<?> recipe) {
    }

}
