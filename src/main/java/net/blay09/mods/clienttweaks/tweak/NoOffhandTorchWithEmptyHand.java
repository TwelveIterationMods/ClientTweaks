package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NoOffhandTorchWithEmptyHand extends AbstractClientTweak {

    public NoOffhandTorchWithEmptyHand() {
        super("noOffhandTorchWithEmptyHand");
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (isEnabled() && event.getHand() == Hand.OFF_HAND) {
            if (!event.getItemStack().isEmpty()) {
                ResourceLocation registryName = event.getItemStack().getItem().getRegistryName();
                if (registryName != null) {
                    if (ClientTweaksConfig.CLIENT.torchItems.get().contains(registryName.toString())) {
                        ItemStack mainItem = event.getEntityPlayer().getHeldItemMainhand();
                        if (mainItem.isEmpty()) {
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "This prevents torches from being placed from your off hand if you have an empty main hand.";
    }

}
