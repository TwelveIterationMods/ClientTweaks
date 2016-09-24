package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;

public class OffhandTorchWithToolOnly extends ClientTweak {

	private final String[] TORCH_ITEMS = new String[] {
			"minecraft:torch",
			"tconstruct:stone_torch"
	};

	public OffhandTorchWithToolOnly() {
		super("Offhand Torch With Tool Only");

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
