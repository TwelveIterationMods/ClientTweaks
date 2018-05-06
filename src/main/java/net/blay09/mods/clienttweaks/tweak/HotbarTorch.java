package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaks;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HotbarTorch extends ClientTweak {

    public HotbarTorch() {
        super("Right-Click Places Torch from Hotbar");
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (!isEnabled() || event.getHand() != EnumHand.MAIN_HAND) {
            return;
        }

        ItemStack otherHandItem = event.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND);
        ResourceLocation otherHandRegistryName = !otherHandItem.isEmpty() ? otherHandItem.getItem().getRegistryName() : null;
        if (event.getItemStack().isEmpty() || (otherHandRegistryName != null && !ClientTweaks.torchTools.contains(otherHandRegistryName.toString()))) {
            return;
        }

        ResourceLocation registryName = event.getItemStack().getItem().getRegistryName();
        if (registryName == null) {
            return;
        }

        if (!ClientTweaks.torchTools.contains(registryName.toString())) {
            return;
        }

        for (int i = 0; i < InventoryPlayer.getHotbarSize(); i++) {
            ItemStack hotbarStack = event.getEntityPlayer().inventory.mainInventory.get(i);
            if (hotbarStack.isEmpty()) {
                continue;
            }

            ResourceLocation torchRegistryName = hotbarStack.getItem().getRegistryName();
            if (torchRegistryName == null || !ClientTweaks.torchItems.contains(torchRegistryName.toString())) {
                continue;
            }

            int oldSelectedSlot = event.getEntityPlayer().inventory.currentItem;
            event.getEntityPlayer().inventory.currentItem = i;
            if (mc.playerController != null) {
                EnumFacing facing = event.getFace();
                if (facing == null) {
                    facing = EnumFacing.UP;
                }
                mc.playerController.processRightClickBlock((EntityPlayerSP) event.getEntityPlayer(), (WorldClient) event.getWorld(), event.getPos(), facing, new Vec3d(0.5, 0.5, 0.5), EnumHand.MAIN_HAND);
            }
            event.getEntityPlayer().inventory.currentItem = oldSelectedSlot;
            event.getEntityPlayer().swingArm(EnumHand.MAIN_HAND);
            event.setCanceled(true);
            return;
        }
    }

    @Override
    public String getDescription() {
        return "This allows right-clicking to place torches from your hotbar as long as you're holding a tool configured in the 'Torch Tools' option.";
    }

}
