package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoOffhandTorchWithEmptyHand extends ClientTweak {

	public NoOffhandTorchWithEmptyHand() {
		super("No Offhand Torch With Empty Hand");

		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
		if(isEnabled() && event.getHand() == EnumHand.OFF_HAND) {
			if(event.getItemStack() != null) {
				ResourceLocation registryName = event.getItemStack().getItem().getRegistryName();
				if(registryName != null) {
					if(ClientTweaks.torchItems.contains(registryName.toString())) {
						ItemStack mainItem = event.getEntityPlayer().getHeldItemMainhand();
						if(mainItem == null) {
							event.setCanceled(true);
						}
					}
				}
			}
		}
	}

	@Override
	public String getDescription() {
		return "This prevents torches from being placed from your off hand if you have an empty main hand.";
	}

}
