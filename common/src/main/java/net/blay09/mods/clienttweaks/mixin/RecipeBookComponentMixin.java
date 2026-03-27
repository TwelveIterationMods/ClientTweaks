package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.EnumMap;
import java.util.Map;

@Mixin(RecipeBookComponent.class)
public class RecipeBookComponentMixin {

    @Unique
    private static final Map<RecipeBookType, String> clienttweaks$retainedSearches = new EnumMap<>(RecipeBookType.class);

    @Shadow
    private int xOffset;

    @Shadow
    private boolean widthTooNarrow;

    @Shadow
    private @Nullable EditBox searchBox;

    @Final
    @Shadow
    protected RecipeBookMenu menu;

    @Inject(method = "initVisuals()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/recipebook/RecipeBookComponent;xOffset:I", shift = At.Shift.AFTER))
    void initVisuals(CallbackInfo callbackInfo) {
        if (ClientTweaksConfig.getActive().tweaks.noRecipeBookShifting) {
            widthTooNarrow = true;
            xOffset = 162;
        }
    }

    @Inject(method = "isOffsetNextToMainGUI()Z", at = @At("HEAD"), cancellable = true)
    void isOffsetNextToMainGUI(CallbackInfoReturnable<Boolean> callbackInfo) {
        if (ClientTweaksConfig.getActive().tweaks.noRecipeBookShifting) {
            callbackInfo.setReturnValue(true); // we pretend like we're not shifted to prevent the recipe book from being closed
        }
    }

    @Inject(method = "initVisuals()V", at = @At("TAIL"))
    void restoreRetainedSearch(CallbackInfo callbackInfo) {
        final var config = ClientTweaksConfig.getActiveOrNull();
        if (config == null || !config.tweaks.retainRecipeBookSearch) {
            return;
        }

        if (searchBox != null) {
            final var recipeBookType = menu.getRecipeBookType();
            final var retainedSearch = clienttweaks$retainedSearches.get(recipeBookType);
            if (retainedSearch != null && !retainedSearch.equals(searchBox.getValue())) {
                searchBox.setValue(retainedSearch);
                ((RecipeBookComponentAccessor) this).callCheckSearchStringUpdate();
            }
        }
    }

    @Inject(method = "checkSearchStringUpdate()V", at = @At("TAIL"))
    void saveRetainedSearch(CallbackInfo callbackInfo) {
        final var config = ClientTweaksConfig.getActiveOrNull();
        if (config == null || !config.tweaks.retainRecipeBookSearch) {
            return;
        }

        if (searchBox != null) {
            clienttweaks$retainedSearches.put(menu.getRecipeBookType(), searchBox.getValue());
        }
    }

}
