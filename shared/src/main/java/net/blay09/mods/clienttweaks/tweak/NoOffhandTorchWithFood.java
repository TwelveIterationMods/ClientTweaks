package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.client.UseItemInputEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class NoOffhandTorchWithFood extends AbstractClientTweak {

    public NoOffhandTorchWithFood() {
        super("noOffhandTorchWithFood");

        Balm.getEvents().onEvent(UseItemInputEvent.class, this::onRightClick);
    }

    public void onRightClick(UseItemInputEvent event) {
        if (isEnabled() && event.getHand() == InteractionHand.OFF_HAND) {
            Minecraft mc = Minecraft.getInstance();
            ItemStack heldItem = mc.player != null ? mc.player.getItemInHand(event.getHand()) : ItemStack.EMPTY;
            if (!heldItem.isEmpty()) {
                ResourceLocation registryName = Balm.getRegistries().getKey(heldItem.getItem());
                if (registryName != null) {
                    if (ClientTweaksConfig.getActive().customization.torchItems.contains(registryName.toString())) {
                        ItemStack mainItem = mc.player.getMainHandItem();
                        if (!mainItem.isEmpty() && mainItem.getItem().isEdible()) {
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.noOffhandTorchWithFood;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.noOffhandTorchWithFood = enabled);
    }

}
