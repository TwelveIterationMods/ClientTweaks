package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

public class DoNotUseLastTorch extends AbstractClientTweak {

    public DoNotUseLastTorch() {
        super("doNotUseLastTorch");
    }

    @SubscribeEvent
    public void onRightClick(InputEvent.ClickInputEvent event) {
        if (isEnabled() && event.getHand() == InteractionHand.OFF_HAND) {
            Minecraft mc = Minecraft.getInstance();
            ItemStack heldItem = mc.player != null ? mc.player.getItemInHand(event.getHand()) : ItemStack.EMPTY;
            if (!heldItem.isEmpty()) {
                ResourceLocation registryName = heldItem.getItem().getRegistryName();
                if (ClientTweaksConfig.getActive().customization.torchItems.contains(Objects.toString(registryName))) {
                    if (heldItem.getCount() == 1) {
                        TranslatableComponent chatComponent = new TranslatableComponent("chat.clienttweaks.lastTorch");
                        chatComponent.withStyle(ChatFormatting.RED);
                        mc.player.displayClientMessage(chatComponent, true);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.doNotUseLastTorch;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.doNotUseLastTorch = enabled);
    }

}
