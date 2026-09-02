package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.ClientItemCallback;
import net.blay09.mods.balm.platform.event.callback.InteractionEventResult;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksRules;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class DisableLogStripping extends AbstractClientTweak {

    public DisableLogStripping() {
        super("disable_log_stripping");

        ClientItemCallback.Use.EVENT.register(this::onRightClick);
    }

    public InteractionEventResult onRightClick(Player player, InteractionHand hand) {
        if (isEnabled()) {
            final var client = Minecraft.getInstance();
            if (client.level == null || client.hitResult == null || client.hitResult.getType() != HitResult.Type.BLOCK) {
                return InteractionEventResult.DEFAULT;
            }

            final var blockHitResult = (BlockHitResult) client.hitResult;
            final var heldItem = client.player != null ? client.player.getItemInHand(hand) : ItemStack.EMPTY;
            if (ClientTweaksRules.canStripLog(heldItem, client.level, blockHitResult.getBlockPos())) {
                return InteractionEventResult.FAIL;
            }
        }

        return InteractionEventResult.DEFAULT;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().interactions.disableLogStripping;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfig.class, it -> it.interactions.disableLogStripping = enabled);
    }

    @Override
    public boolean hasKeyBinding() {
        return true;
    }
}
