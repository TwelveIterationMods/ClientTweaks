package net.blay09.mods.clienttweaks.mixin;

import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.recipebook.GhostRecipe;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookPage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RecipeBookComponent.class)
public interface RecipeBookComponentAccessor {

    @Accessor
    EditBox getSearchBox();

    @Accessor
    GhostRecipe getGhostRecipe();

    @Accessor
    ClientRecipeBook getBook();

    @Accessor
    RecipeBookPage getRecipeBookPage();

    @Invoker
    void callCheckSearchStringUpdate();

}
