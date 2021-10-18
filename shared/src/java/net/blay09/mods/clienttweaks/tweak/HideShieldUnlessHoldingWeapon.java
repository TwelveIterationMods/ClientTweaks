package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HideShieldUnlessHoldingWeapon extends AbstractClientTweak {

    public HideShieldUnlessHoldingWeapon() {
        super("hideShieldUnlessHoldingWeapon");
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderHand(RenderHandEvent event) {
        if (!isEnabled()) {
            return;
        }

        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        if (event.getHand() != InteractionHand.OFF_HAND || !event.getItemStack().getItem().canPerformAction(event.getItemStack(), ToolActions.SHIELD_BLOCK)) {
            return;
        }

        if (player.getUsedItemHand() == InteractionHand.OFF_HAND && player.isBlocking()) {
            return;
        }

        ItemStack mainItem = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (mainItem.getItem() instanceof SwordItem) {
            return;
        }

        float attackDamage = mainItem.getItem() instanceof DiggerItem ? ((DiggerItem) mainItem.getItem()).getAttackDamage() : 0;
        if (attackDamage >= 3) {
            return;
        }

        ResourceLocation registryName = Balm.getRegistries().getKey(mainItem.getItem());
        if (ClientTweaksConfig.getActive().customization.shieldWeapons.contains(registryName.toString())) {
            return;
        }

        event.setCanceled(true);
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.hideShieldUnlessHoldingWeapon;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.hideShieldUnlessHoldingWeapon = enabled);
    }

}
