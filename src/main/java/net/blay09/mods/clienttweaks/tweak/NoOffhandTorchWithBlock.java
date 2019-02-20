package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NoOffhandTorchWithBlock extends AbstractClientTweak {

    public NoOffhandTorchWithBlock() {
        super("noOffhandTorchWithBlock");
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (isEnabled() && event.getHand() == EnumHand.OFF_HAND) {
            if (!event.getItemStack().isEmpty()) {
                ResourceLocation registryName = event.getItemStack().getItem().getRegistryName();
                if (registryName != null) {
                    if (ClientTweaksConfig.CLIENT.torchItems.get().contains(registryName.toString())) {
                        ItemStack mainItem = event.getEntityPlayer().getHeldItemMainhand();
                        if (!mainItem.isEmpty() && mainItem.getItem() instanceof ItemBlock) {
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isEnabledDefault() {
        return true;
    }

    @Override
    public String getDescription() {
        return "This prevents torches from being placed from your offhand if you have a block in your main hand.";
    }
}
