package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

public class DoNotUseLastTorch extends AbstractClientTweak {

    public DoNotUseLastTorch() {
        super("doNotUseLastTorch", ClientTweaksConfig.CLIENT.doNotUseLastTorch);
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (isEnabled() && event.getHand() == Hand.OFF_HAND && !event.getItemStack().isEmpty()) {
            ResourceLocation registryName = event.getItemStack().getItem().getRegistryName();
            if (ClientTweaksConfig.CLIENT.torchItems.get().contains(Objects.toString(registryName))) {
                if (event.getItemStack().getCount() == 1) {
                    event.setCanceled(true);
                }
            }
        }
    }

}
