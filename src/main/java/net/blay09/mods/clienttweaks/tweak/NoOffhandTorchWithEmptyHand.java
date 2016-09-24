package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;

public class NoOffhandTorchWithEmptyHand extends ClientTweak {

	private final String[] TORCH_ITEMS = new String[] {
			"minecraft:torch",
			"tconstruct:stone_torch"
	};

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
					if(ArrayUtils.contains(TORCH_ITEMS, registryName.toString())) {
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
