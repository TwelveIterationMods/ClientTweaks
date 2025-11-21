package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.RenderCallback;
import net.blay09.mods.balm.platform.event.EventPhases;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.blay09.mods.clienttweaks.mixin.ItemInHandRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
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

        final var isShield = itemStack.get(DataComponents.BLOCKS_ATTACKS) != null || ClientTweaksConfig.isShieldItem(itemStack);
        if (!isShield) {
            return true;
        }

        final var isBlocking = player.getUsedItemHand() == InteractionHand.OFF_HAND && player.isBlocking();
        final var weaponInHand = hasWeaponInHand(player);
        wasWeaponInHand = weaponInHand;
        if (!weaponInHand && !isBlocking) {
            return false;
        } else if (weaponInHand && !wasWeaponInHand) {
            ItemInHandRenderer itemInHandRenderer = Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer();
            if (itemInHandRenderer instanceof ItemInHandRendererAccessor accessor) {
                accessor.setOOffHandHeight(0f);
                accessor.setOffHandHeight(0f);
            }
            // we skip the first frame so the offset can update since this event fires after tick()
            return false;
        }

        return true;
    }

    private boolean hasWeaponInHand(Player player) {
        final var mainItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        final var weaponComponent = player.get(DataComponents.WEAPON);
        if (weaponComponent != null || mainItem.getItem() instanceof AxeItem) {
            return true;
        }

        return ClientTweaksConfig.isShieldWeapon(mainItem);
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.hideShieldUnlessHoldingWeapon;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.hideShieldUnlessHoldingWeapon = enabled);
    }

}
