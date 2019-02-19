package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DoNotUseLastTorch extends ClientTweak {

    public DoNotUseLastTorch() {
        super("doNotUseLastTorch");
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

        if (ClientTweaksConfig.CLIENT.torchItems.get().contains(registryName.toString())) {
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
