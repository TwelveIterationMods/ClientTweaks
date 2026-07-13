package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.RenderCallback;
import net.blay09.mods.balm.platform.event.EventPhases;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.mixin.ItemInHandRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;

public class HideShieldUnlessBlocking extends AbstractClientTweak {

    public HideShieldUnlessBlocking() {
        super("hide_shield_unless_blocking");

        RenderCallback.Hand.EVENT.register(EventPhases.HIGHEST, this::onRenderHand);
    }

    public boolean onRenderHand(InteractionHand hand, ItemStack itemStack, float swingProgress) {
        if (!isEnabled() || hand != InteractionHand.OFF_HAND) {
            return true;
        }

        final var player = Minecraft.getInstance().player;
        if (player == null) {
            return true;
        }

        final var isBlocking = player.getUsedItemHand() == InteractionHand.OFF_HAND && player.isBlocking();
        final var isStartingToBlock = player.isUsingItem() && player.getUsedItemHand() == InteractionHand.OFF_HAND && player.getOffhandItem().has(DataComponents.BLOCKS_ATTACKS);
        return !player.getOffhandItem().has(DataComponents.BLOCKS_ATTACKS) || isBlocking || isStartingToBlock;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().rendering.hideShieldUnlessBlocking;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfig.class, it -> it.rendering.hideShieldUnlessBlocking = enabled);
    }

}
