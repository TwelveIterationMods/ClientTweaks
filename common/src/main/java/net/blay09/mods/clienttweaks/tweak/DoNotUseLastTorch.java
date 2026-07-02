package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.EventPriority;
import net.blay09.mods.balm.api.event.client.UseItemInputEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class DoNotUseLastTorch extends AbstractClientTweak {

    public DoNotUseLastTorch() {
        super("doNotUseLastTorch");

        Balm.getEvents().onEvent(UseItemInputEvent.class, this::onRightClick, EventPriority.Lowest);
    }

    public void onRightClick(UseItemInputEvent event) {
        if (isEnabled() && event.getHand() == InteractionHand.OFF_HAND) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.hitResult == null || mc.hitResult.getType() != HitResult.Type.BLOCK) {
                return;
            }

            ItemStack heldItem = mc.player != null ? mc.player.getItemInHand(event.getHand()) : ItemStack.EMPTY;
            if (ClientTweaksConfig.isTorchItem(heldItem)) {
                if (heldItem.getCount() == 1) {
                    MutableComponent chatComponent = Component.translatable("chat.clienttweaks.lastTorch");
                    chatComponent.withStyle(ChatFormatting.RED);
                    mc.player.displayClientMessage(chatComponent, true);
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().torches.doNotUseLastTorch;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.torches.doNotUseLastTorch = enabled);
    }

}
