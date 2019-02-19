package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HideShieldUnlessHoldingWeapon extends ClientTweak {

    public HideShieldUnlessHoldingWeapon() {
        super("hideShieldUnlessHoldingWeapon");
    }

    @Override
    public String getDescription() {
        return "This option will hide your shield unless you are holding a weapon.";
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderHand(RenderSpecificHandEvent event) {
        if (!isEnabled()) {
            return;
        }

        EntityPlayer player = Minecraft.getInstance().player;
        if (event.getHand() != EnumHand.OFF_HAND || !event.getItemStack().getItem().isShield(event.getItemStack(), player)) {
            return;
        }

        if (player.getActiveHand() == EnumHand.OFF_HAND && player.isActiveItemStackBlocking()) {
            return;
        }

        ItemStack mainItem = player.getHeldItem(EnumHand.MAIN_HAND);

        if (mainItem.getItem() instanceof ItemSword) {
            return;
        }

        float attackDamage = mainItem.getItem() instanceof ItemTool ? ((ItemTool) mainItem.getItem()).attackDamage : 0;
        if (attackDamage >= 3) {
            return;
        }

        event.setCanceled(true);
    }

    @Override
    public boolean isEnabledDefault() {
        return true;
    }
}
