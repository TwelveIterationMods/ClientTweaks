package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.client.UseItemInputEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class DoNotUseLastTorch extends AbstractClientTweak {

    public DoNotUseLastTorch() {
        super("do_not_use_last_torch");

        Balm.getEvents().onEvent(UseItemInputEvent.class, this::onRightClick);
    }

    public void onRightClick(UseItemInputEvent event) {
        if (isEnabled() && event.getHand() == InteractionHand.OFF_HAND) {
            final var client = Minecraft.getInstance();
            final var heldItem = client.player != null ? client.player.getItemInHand(event.getHand()) : ItemStack.EMPTY;
            if (ClientTweaksConfig.isTorchItem(heldItem)) {
                if (heldItem.getCount() == 1) {
                    final var chatComponent = Component.translatable("chat.clienttweaks.lastTorch");
                    chatComponent.withStyle(ChatFormatting.RED);
                    client.player.displayClientMessage(chatComponent, true);
                    event.setCanceled(true);
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
        Balm.getConfig().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.doNotUseLastTorch = enabled);
    }

}
