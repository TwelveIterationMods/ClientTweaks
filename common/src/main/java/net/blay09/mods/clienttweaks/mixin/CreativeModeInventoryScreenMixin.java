package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.util.StringUtil;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryScreenMixin {

    @Unique
    private static String clienttweaks$retainedSearch = "";

    @Unique
    private boolean clienttweaks$selectingTab;

    @Inject(method = "selectTab(Lnet/minecraft/world/item/CreativeModeTab;)V", at = @At("HEAD"))
    void beginSelectTab(CreativeModeTab creativeModeTab, CallbackInfo callbackInfo) {
        clienttweaks$selectingTab = true;
    }

    @Inject(method = "selectTab(Lnet/minecraft/world/item/CreativeModeTab;)V", at = @At("TAIL"))
    void restoreRetainedSearch(CreativeModeTab creativeModeTab, CallbackInfo callbackInfo) {
        try {
            final var config = ClientTweaksConfig.getActive();
            if (config == null || !config.creativeMode.retainCreativeMenuSearch || creativeModeTab.getType() != CreativeModeTab.Type.SEARCH) {
                return;
            }

            final var searchBox = ((CreativeModeInventoryScreenAccessor) this).getSearchBox();
            if (searchBox != null && !StringUtil.isBlank(clienttweaks$retainedSearch) && !clienttweaks$retainedSearch.equals(searchBox.getValue())) {
                searchBox.setValue(clienttweaks$retainedSearch);
                ((CreativeModeInventoryScreenAccessor) this).callRefreshSearchResults();
            }
        } finally {
            clienttweaks$selectingTab = false;
        }
    }

    @Inject(method = "refreshSearchResults()V", at = @At("TAIL"))
    void saveRetainedSearch(CallbackInfo callbackInfo) {
        final var config = ClientTweaksConfig.getActive();
        if (config == null || !config.creativeMode.retainCreativeMenuSearch || clienttweaks$selectingTab) {
            return;
        }

        final var selectedTab = CreativeModeInventoryScreenAccessor.getSelectedTab();
        if (selectedTab == null || selectedTab.getType() != CreativeModeTab.Type.SEARCH) {
            return;
        }

        final var searchBox = ((CreativeModeInventoryScreenAccessor) this).getSearchBox();
        if (searchBox != null) {
            clienttweaks$retainedSearch = searchBox.getValue();
        }
    }
}
