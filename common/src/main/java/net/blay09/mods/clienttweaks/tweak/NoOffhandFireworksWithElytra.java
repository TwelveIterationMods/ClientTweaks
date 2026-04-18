package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.ClientItemCallback;
import net.blay09.mods.balm.platform.event.callback.InteractionEventResult;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksRules;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.HitResult;

public class NoOffhandFireworksWithElytra extends AbstractClientTweak {

    public NoOffhandFireworksWithElytra() {
        super("no_offhand_fireworks_with_elytra");

        ClientItemCallback.Use.EVENT.register(this::onRightClick);
    }

    public InteractionEventResult onRightClick(Player player, InteractionHand hand) {
        if (isEnabled() && hand == InteractionHand.OFF_HAND) {
            final var client = Minecraft.getInstance();
            if (client.level == null || client.hitResult == null || client.hitResult.getType() != HitResult.Type.BLOCK) {
                return InteractionEventResult.DEFAULT;
            }

            final var heldItem = player.getItemInHand(hand);
            if (ClientTweaksRules.isFireworkItem(heldItem)) {
                final var wornChestItem = player.getItemBySlot(EquipmentSlot.CHEST);
                if (wornChestItem.is(Items.ELYTRA) && !player.isFallFlying()) {
                    return InteractionEventResult.FAIL;
                }
            }
        }

        return InteractionEventResult.DEFAULT;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().interactions.noOffhandFireworksWithElytra;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfig.class, it -> it.interactions.noOffhandFireworksWithElytra = enabled);
    }

}
