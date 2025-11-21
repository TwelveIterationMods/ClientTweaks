package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.ClientItemCallback;
import net.blay09.mods.balm.platform.event.callback.InteractionEventResult;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;

public class DoNotUseLastTorch extends AbstractClientTweak {

    public DoNotUseLastTorch() {
        super("do_not_use_last_torch");

        ClientItemCallback.Use.EVENT.register(this::onRightClick);
    }

    public InteractionEventResult onRightClick(Player player, InteractionHand hand) {
        if (isEnabled() && hand == InteractionHand.OFF_HAND) {
            final var client = Minecraft.getInstance();
            if (client.hitResult == null || client.hitResult.getType() != HitResult.Type.BLOCK) {
                return InteractionEventResult.DEFAULT;
            }

            final var heldItem = client.player != null ? client.player.getItemInHand(hand) : ItemStack.EMPTY;
            if (ClientTweaksConfig.isTorchItem(heldItem)) {
                if (heldItem.getCount() == 1) {
                    final var chatComponent = Component.translatable("chat.clienttweaks.lastTorch");
                    chatComponent.withStyle(ChatFormatting.RED);
                    client.player.displayClientMessage(chatComponent, true);
                    return InteractionEventResult.FAIL;
                }
            }
        }

        return InteractionEventResult.DEFAULT;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.doNotUseLastTorch;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.doNotUseLastTorch = enabled);
    }

}
