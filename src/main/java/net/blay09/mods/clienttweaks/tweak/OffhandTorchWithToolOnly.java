package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OffhandTorchWithToolOnly extends ClientTweak {

	public OffhandTorchWithToolOnly() {
		super("Offhand Torch With Tool Only");
	}

	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
		if(isEnabled() && event.getHand() == EnumHand.OFF_HAND) {
			if(event.getItemStack() != null) {
				ResourceLocation registryName = event.getItemStack().getItem().getRegistryName();
				if(registryName != null) {
					if(ClientTweaks.torchItems.contains(registryName.toString())) {
						ItemStack mainItem = event.getEntityPlayer().getHeldItemMainhand();
						if(mainItem == null || !(mainItem.getItem() instanceof ItemTool)) {
							event.setCanceled(true);
						}
					}
				}
			}
		}
	}

	@Override
	public String getDescription() {
		return "This restricts torches to be placed from the offhand only when you're holding a tool in your main hand.";
	}
}
