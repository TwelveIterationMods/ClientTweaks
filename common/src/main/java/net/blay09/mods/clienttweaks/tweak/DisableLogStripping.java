package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.ClientItemCallback;
import net.blay09.mods.balm.platform.event.callback.InteractionEventResult;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.blay09.mods.clienttweaks.mixin.AxeItemAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
            final var targetState = client.level.getBlockState(blockHitResult.getBlockPos());
            final var heldItem = client.player != null ? client.player.getItemInHand(hand) : ItemStack.EMPTY;
            if (!heldItem.isEmpty() && heldItem.getItem() instanceof AxeItemAccessor axeItem && axeItem.callGetStripped(targetState).isPresent()) {
                return InteractionEventResult.FAIL;
            }
        }

        return InteractionEventResult.DEFAULT;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.disableLogStripping;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.disableLogStripping = enabled);
    }

    @Override
    public boolean hasKeyBinding() {
        return true;
    }
}
