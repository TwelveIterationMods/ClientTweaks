package net.blay09.mods.clienttweaks.mixin;

import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookPage;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.item.crafting.display.RecipeDisplayId;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RecipeBookComponent.class)
public interface RecipeBookComponentAccessor {

    @Accessor
    @Nullable EditBox getSearchBox();

    @Accessor
    GhostSlots getGhostSlots();

    @Accessor
    ClientRecipeBook getBook();

    @Accessor
    RecipeBookPage getRecipeBookPage();

    @Invoker
    void callCheckSearchStringUpdate();

    @Invoker
    boolean callTryPlaceRecipe(RecipeCollection recipeCollection, RecipeDisplayId recipeDisplayId, boolean shiftDown);

}
