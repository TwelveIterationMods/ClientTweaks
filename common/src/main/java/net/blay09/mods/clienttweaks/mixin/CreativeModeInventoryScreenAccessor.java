package net.blay09.mods.clienttweaks.mixin;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CreativeModeInventoryScreen.class)
public interface CreativeModeInventoryScreenAccessor {

    @Accessor("searchBox")
    EditBox getSearchBox();

    @Accessor("selectedTab")
    static CreativeModeTab getSelectedTab() {
        throw new IllegalStateException();
    }

    @Invoker("refreshSearchResults")
    void callRefreshSearchResults();
}
