package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DoNotUseLastTorch extends ClientTweak {

    public DoNotUseLastTorch() {
        super("Do Not Use Last Torch");
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (!isEnabled() || event.getHand() != EnumHand.OFF_HAND) {
            return;
        }

        if (event.getItemStack().isEmpty()) {
            return;
        }

        ResourceLocation registryName = event.getItemStack().getItem().getRegistryName();
        if (registryName == null) {
            return;
        }

        if (ClientTweaks.torchItems.contains(registryName.toString())) {
            if (event.getItemStack().getCount() == 1) {
                event.setCanceled(true);
            }
        }
    }

    @Override
    public String getDescription() {
        return "This prevents the last torch in the offhand from being placed.";
    }

}
