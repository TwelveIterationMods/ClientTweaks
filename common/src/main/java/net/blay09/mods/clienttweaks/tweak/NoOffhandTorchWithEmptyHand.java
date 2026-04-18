package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.ClientItemCallback;
import net.blay09.mods.balm.platform.event.callback.InteractionEventResult;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksRules;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

public class NoOffhandTorchWithEmptyHand extends AbstractClientTweak {

    public NoOffhandTorchWithEmptyHand() {
        super("no_offhand_torch_with_empty_hand");

        ClientItemCallback.Use.EVENT.register(this::onRightClick);
    }

    public InteractionEventResult onRightClick(Player player, InteractionHand hand) {
        if (isEnabled() && hand == InteractionHand.OFF_HAND) {
            if (ClientTweaksRules.isTorchItem(player.getOffhandItem())) {
                final var mainItem = player.getMainHandItem();
                if (mainItem.isEmpty()) {
                    return InteractionEventResult.FAIL;
                }
            }
        }

        return InteractionEventResult.DEFAULT;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().torches.noOffhandTorchWithEmptyHand;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfig.class, it -> it.torches.noOffhandTorchWithEmptyHand = enabled);
    }

}
