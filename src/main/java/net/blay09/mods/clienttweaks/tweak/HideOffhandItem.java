package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.util.Hand;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HideOffhandItem extends AbstractClientTweak {

    public HideOffhandItem() {
        super("hideOffhandItem", ClientTweaksConfig.CLIENT.hideOffhandItem);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderHand(RenderSpecificHandEvent event) {
        if (isEnabled()) {
            // TODO Tinkers inverts this event by rendering manually with its dual harvesting, come up with a solution
            if (event.getHand() == Hand.OFF_HAND) {
                if (event.getSwingProgress() <= 0f) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public boolean hasKeyBinding() {
        return true;
    }

}
