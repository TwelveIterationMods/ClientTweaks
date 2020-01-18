package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Hand;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HideShieldUnlessHoldingWeapon extends AbstractClientTweak {

    public HideShieldUnlessHoldingWeapon() {
        super("hideShieldUnlessHoldingWeapon", ClientTweaksConfig.CLIENT.hideShieldUnlessHoldingWeapon);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderHand(RenderSpecificHandEvent event) {
        if (!isEnabled()) {
            return;
        }

        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        if (event.getHand() != Hand.OFF_HAND || !event.getItemStack().getItem().isShield(event.getItemStack(), player)) {
            return;
        }

        if (player.getActiveHand() == Hand.OFF_HAND && player.isActiveItemStackBlocking()) {
            return;
        }

        ItemStack mainItem = player.getHeldItem(Hand.MAIN_HAND);

        if (mainItem.getItem() instanceof SwordItem) {
            return;
        }

        float attackDamage = mainItem.getItem() instanceof ToolItem ? ((ToolItem) mainItem.getItem()).attackDamage : 0;
        if (attackDamage >= 3) {
            return;
        }

        event.setCanceled(true);
    }

}
