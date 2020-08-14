package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

public class DoNotUseLastTorch extends AbstractClientTweak {

    public DoNotUseLastTorch() {
        super("doNotUseLastTorch", ClientTweaksConfig.CLIENT.doNotUseLastTorch);
    }

    @SubscribeEvent
    public void onRightClick(InputEvent.ClickInputEvent event) {
        if (isEnabled() && event.getHand() == Hand.OFF_HAND) {
            Minecraft mc = Minecraft.getInstance();
            ItemStack heldItem = mc.player != null ? mc.player.getHeldItem(event.getHand()) : ItemStack.EMPTY;
            if (!heldItem.isEmpty()) {
                ResourceLocation registryName = heldItem.getItem().getRegistryName();
                if (ClientTweaksConfig.CLIENT.torchItems.get().contains(Objects.toString(registryName))) {
                    if (heldItem.getCount() == 1) {
                        TranslationTextComponent chatComponent = new TranslationTextComponent("chat.clienttweaks.lastTorch");
                        chatComponent.getStyle().setFormatting(TextFormatting.RED);
                        mc.player.sendStatusMessage(chatComponent, true);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

}
