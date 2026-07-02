package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(RecipeBookComponent.class)
public class RecipeBookComponentMixin {

    private static final Map<RecipeBookType, String> clienttweaks$retainedSearches = new HashMap<>();

    @Shadow
    private int xOffset;

    @Shadow
    private boolean widthTooNarrow;

    @Shadow
    protected RecipeBookMenu<?> menu;

    @Shadow
    private EditBox searchBox;

    @Shadow
    private void checkSearchStringUpdate() {
    }

    @Inject(method = "initVisuals()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/recipebook/RecipeBookComponent;xOffset:I", shift = At.Shift.AFTER))
    void initVisuals(CallbackInfo callbackInfo) {
        if (ClientTweaksConfig.getActive().recipeBook.noRecipeBookShifting) {
            widthTooNarrow = true;
            xOffset = 162;
        }
    }

    @Inject(method = "isOffsetNextToMainGUI()Z", at = @At("HEAD"), cancellable = true)
    void isOffsetNextToMainGUI(CallbackInfoReturnable<Boolean> callbackInfo) {
        if (ClientTweaksConfig.getActive().recipeBook.noRecipeBookShifting) {
            callbackInfo.setReturnValue(true); // we pretend like we're not shifted to prevent the recipe book from being closed
        }
    }

    @Inject(method = "initVisuals()V", at = @At("TAIL"))
    void restoreRetainedRecipeBookSearch(CallbackInfo callbackInfo) {
        final var config = ClientTweaksConfig.getActive();
        if (config == null || !config.recipeBook.retainRecipeBookSearch) {
            return;
        }

        final var retainedSearch = clienttweaks$retainedSearches.get(menu.getRecipeBookType());
        if (retainedSearch != null && searchBox != null && !retainedSearch.equals(searchBox.getValue())) {
            searchBox.setValue(retainedSearch);
            checkSearchStringUpdate();
        }
    }

    @Inject(method = "checkSearchStringUpdate()V", at = @At("TAIL"))
    void retainRecipeBookSearch(CallbackInfo callbackInfo) {
        final var config = ClientTweaksConfig.getActive();
        if (config == null || !config.recipeBook.retainRecipeBookSearch || searchBox == null) {
            return;
        }

        clienttweaks$retainedSearches.put(menu.getRecipeBookType(), searchBox.getValue());
    }

}
