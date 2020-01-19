package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OffhandTorchWithToolOnly extends AbstractClientTweak {

    public OffhandTorchWithToolOnly() {
        super("offhandTorchWithToolOnly", ClientTweaksConfig.CLIENT.offhandTorchWithToolOnly);
    }

    @SubscribeEvent
    public void onRightClick(InputEvent.ClickInputEvent event) {
        if (isEnabled() && event.getHand() == Hand.OFF_HAND) {
            Minecraft mc = Minecraft.getInstance();
            ItemStack heldItem = mc.player != null ? mc.player.getHeldItem(event.getHand()) : ItemStack.EMPTY;
            if (!heldItem.isEmpty()) {
                ResourceLocation registryName = heldItem.getItem().getRegistryName();
                if (registryName != null) {
                    if (ClientTweaksConfig.CLIENT.torchItems.get().contains(registryName.toString())) {
                        ItemStack mainItem = mc.player.getHeldItemMainhand();
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
