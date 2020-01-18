package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OffhandTorchWithToolOnly extends AbstractClientTweak {

    public OffhandTorchWithToolOnly() {
        super("offhandTorchWithToolOnly", ClientTweaksConfig.CLIENT.offhandTorchWithToolOnly);
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (isEnabled() && event.getHand() == Hand.OFF_HAND) {
            if (!event.getItemStack().isEmpty()) {
                ResourceLocation registryName = event.getItemStack().getItem().getRegistryName();
                if (registryName != null) {
                    if (ClientTweaksConfig.CLIENT.torchItems.get().contains(registryName.toString())) {
                        ItemStack mainItem = event.getPlayer().getHeldItemMainhand();
                        ResourceLocation mainRegistryName = !mainItem.isEmpty() ? mainItem.getItem().getRegistryName() : null;
                        if (mainRegistryName == null || !ClientTweaksConfig.CLIENT.torchTools.get().contains(mainRegistryName.toString())) {
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

}
