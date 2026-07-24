package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.ClientItemCallback;
import net.blay09.mods.balm.platform.event.callback.InteractionEventResult;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.HitResult;

public class BerryPlacementRequiresShift extends AbstractClientTweak {

    public BerryPlacementRequiresShift() {
        super("berry_placement_requires_shift");

        ClientItemCallback.Use.EVENT.register(this::onRightClick);
    }

    public InteractionEventResult onRightClick(Player player, InteractionHand hand) {
        if (!isEnabled()) {
            return InteractionEventResult.DEFAULT;
        }

        final var client = Minecraft.getInstance();
        final var heldItem = client.player != null ? client.player.getItemInHand(hand) : player.getItemInHand(hand);
        if (!heldItem.is(Items.SWEET_BERRIES)) {
            return InteractionEventResult.DEFAULT;
        }

        if (player.isShiftKeyDown() || client.level == null || client.hitResult == null || client.hitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionEventResult.DEFAULT;
        }

        return InteractionEventResult.FAIL;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().interactions.berryPlacementRequiresShift;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfig.class, it -> it.interactions.berryPlacementRequiresShift = enabled);
    }
}
