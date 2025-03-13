package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.client.UseItemInputEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.HitResult;

public class NoOffhandFireworksWithElytra extends AbstractClientTweak {

    public NoOffhandFireworksWithElytra() {
        super("noOffhandFireworksWithElytra");

        Balm.getEvents().onEvent(UseItemInputEvent.class, this::onRightClick);
    }

    public void onRightClick(UseItemInputEvent event) {
        if (isEnabled() && event.getHand() == InteractionHand.OFF_HAND) {
            final var client = Minecraft.getInstance();
            final var player = client.player;
            if (client.level == null || player == null || client.hitResult == null || client.hitResult.getType() != HitResult.Type.BLOCK) {
                return;
            }

            final var heldItem = player.getItemInHand(event.getHand());
            if (ClientTweaksConfig.isFireworkItem(heldItem)) {
                final var wornChestItem = player.getItemBySlot(EquipmentSlot.CHEST);
                if (wornChestItem.is(Items.ELYTRA) && !player.isFallFlying()) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.noOffhandFireworksWithElytra;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.noOffhandFireworksWithElytra = enabled);
    }

}
