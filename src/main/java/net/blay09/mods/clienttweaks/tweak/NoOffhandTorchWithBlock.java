package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NoOffhandTorchWithBlock extends AbstractClientTweak {

    public NoOffhandTorchWithBlock() {
        super("noOffhandTorchWithBlock", ClientTweaksConfig.CLIENT.noOffhandTorchWithBlock);
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (isEnabled() && event.getHand() == Hand.OFF_HAND) {
            if (!event.getItemStack().isEmpty()) {
                ResourceLocation registryName = event.getItemStack().getItem().getRegistryName();
                if (registryName != null) {
                    if (ClientTweaksConfig.CLIENT.torchItems.get().contains(registryName.toString())) {
                        ItemStack mainItem = event.getPlayer().getHeldItemMainhand();
                        if (!mainItem.isEmpty() && mainItem.getItem() instanceof BlockItem) {
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

}
