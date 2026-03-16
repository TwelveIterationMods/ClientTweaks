package net.blay09.mods.clienttweaks.tweak;

import com.mojang.blaze3d.platform.InputConstants;
import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.ScreenCallback;
import net.blay09.mods.balm.mixin.AbstractContainerScreenAccessor;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.blay09.mods.clienttweaks.mixin.AbstractRecipeBookScreenAccessor;
import net.blay09.mods.clienttweaks.mixin.GhostSlotAccessor;
import net.blay09.mods.clienttweaks.mixin.GhostSlotsAccessor;
import net.blay09.mods.clienttweaks.mixin.RecipeBookComponentAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.RecipeDisplayId;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import org.jspecify.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;

public class NavigateToGhostIngredients extends AbstractClientTweak {

    private final Deque<HistoryEntry> history = new ArrayDeque<>();
    @Nullable
    private HistoryEntry currentEntry;

    public NavigateToGhostIngredients() {
        super("click_ghost_ingredients_in_recipe_book");

        ScreenCallback.Opening.EVENT.register(this::onScreenOpening);
        ScreenCallback.MousePress.Before.EVENT.register(this::onMousePress);
        ScreenCallback.KeyPress.Before.EVENT.register(this::onKeyPress);
    }

    private Screen onScreenOpening(Screen screen) {
        history.clear();
        return screen;
    }

    private boolean onMousePress(Screen screen, MouseButtonEvent event) {
        final var level = Minecraft.getInstance().level;
        if (!isEnabled() || event.button() != 0 || !(screen instanceof AbstractRecipeBookScreen) || level == null) {
            return false;
        }

        final var recipeBookComponent = ((AbstractRecipeBookScreenAccessor) screen).getRecipeBookComponent();
        if (!recipeBookComponent.isVisible()) {
            return false;
        }

        final var ghostSlots = (GhostSlotsAccessor) ((RecipeBookComponentAccessor) recipeBookComponent).getGhostSlots();
        final var hoveredSlot = ((AbstractContainerScreenAccessor) screen).getHoveredSlot();
        final var ghostSlot = (GhostSlotAccessor) ghostSlots.getIngredients().get(hoveredSlot);
        if (ghostSlot == null || ghostSlot.getIsResultSlot()) {
            return false;
        }
        final var currentIndex = ghostSlots.getSlotSelectTime().currentIndex();
        final var ghostItem = ghostSlot.callGetItem(currentIndex);

        var recipeBook = ((RecipeBookComponentAccessor) recipeBookComponent).getBook();
        var context = SlotDisplayContext.fromLevel(level);

        for (final var collection : recipeBook.getCollections()) {
            for (final var entry : collection.getRecipes()) {
                for (final var result : entry.resultItems(context)) {
                    if (ItemStack.isSameItemSameComponents(result, ghostItem)) {
                        if (currentEntry == null) {
                            final var recipeBookPage = ((RecipeBookComponentAccessor) recipeBookComponent).getRecipeBookPage();
                            final var initialCollection = recipeBookPage.getLastClickedRecipeCollection();
                            final var initialRecipeId = recipeBookPage.getLastClickedRecipe();
                            currentEntry = new HistoryEntry(initialCollection, initialRecipeId);
                        }
                        history.add(currentEntry);
                        currentEntry = new HistoryEntry(collection, entry.id());
                        ((RecipeBookComponentAccessor) recipeBookComponent).callTryPlaceRecipe(collection, entry.id(), event.hasShiftDown());
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean onKeyPress(Screen screen, KeyEvent event) {
        if (!isEnabled() || event.key() != InputConstants.KEY_BACKSPACE || !(screen instanceof AbstractRecipeBookScreen)) {
            return false;
        }

        final var recipeBookComponent = ((AbstractRecipeBookScreenAccessor) screen).getRecipeBookComponent();
        if (!recipeBookComponent.isVisible()) {
            return false;
        }

        final var searchBox = ((RecipeBookComponentAccessor) recipeBookComponent).getSearchBox();
        if (searchBox != null && searchBox.isFocused()) {
            return false;
        }

        if (history.isEmpty()) {
            return false;
        }

        currentEntry = history.pop();
        ((RecipeBookComponentAccessor) recipeBookComponent).callTryPlaceRecipe(currentEntry.collection(), currentEntry.recipeDisplayId(), false);
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.navigateToGhostIngredients;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.navigateToGhostIngredients = enabled);
    }

    private record HistoryEntry(RecipeCollection collection, RecipeDisplayId recipeDisplayId) {
    }

}
