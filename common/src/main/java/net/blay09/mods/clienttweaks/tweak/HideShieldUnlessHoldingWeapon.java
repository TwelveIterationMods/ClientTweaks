package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.RenderCallback;
import net.blay09.mods.balm.platform.event.EventPhases;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksRules;
import net.blay09.mods.clienttweaks.mixin.FirstPersonHandsAndItemsAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class HideShieldUnlessHoldingWeapon extends AbstractClientTweak {

    private boolean wasWeaponInHand;

    public HideShieldUnlessHoldingWeapon() {
        super("hide_shield_unless_holding_weapon");

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

        final var isShield = itemStack.get(DataComponents.BLOCKS_ATTACKS) != null;
        if (!isShield) {
            return true;
        }

        final var isBlocking = player.getUsedItemHand() == InteractionHand.OFF_HAND && player.isBlocking();
        final var isStartingToBlock = player.isUsingItem() && player.getUsedItemHand() == InteractionHand.OFF_HAND && player.getOffhandItem().has(DataComponents.BLOCKS_ATTACKS);
        final var weaponInHand = ClientTweaksRules.isWeapon(player.getMainHandItem());
        final var wasWeaponInHand = this.wasWeaponInHand;
        this.wasWeaponInHand = weaponInHand;
        if (!weaponInHand && !isBlocking && !isStartingToBlock) {
            return false;
        } else if (weaponInHand && !wasWeaponInHand) {
            final var firstPersonHandsAndItems = player.firstPersonHandsAndItems();
            if (firstPersonHandsAndItems instanceof FirstPersonHandsAndItemsAccessor accessor) {
                accessor.setOOffHandHeight(0f);
                accessor.setOffHandHeight(0f);
            }
            // we skip the first frame so the offset can update since this event fires after tick()
            return false;
        }

        return true;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().rendering.hideShieldUnlessHoldingWeapon;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfig.class, it -> it.rendering.hideShieldUnlessHoldingWeapon = enabled);
    }

}
