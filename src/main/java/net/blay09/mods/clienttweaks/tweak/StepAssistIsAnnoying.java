package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nullable;

public class StepAssistIsAnnoying extends ClientTweak {

	private static final float DEFAULT_STEP_HEIGHT = 0.6f;

	public StepAssistIsAnnoying() {
		super("Disable Step Assist");
	}

	@Override
	public String getDescription() {
		return "This option will disable step assist added by other mods.";
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if(event.phase == TickEvent.Phase.START && isEnabled()) {
			if(mc.thePlayer != null) {
				mc.thePlayer.stepHeight = DEFAULT_STEP_HEIGHT;
			}
		}
	}

	@Nullable
	@Override
	protected KeyBinding createToggleKeybind() {
		return new KeyBinding("key.clienttweaks.disable_step_assist", KeyConflictContext.IN_GAME, KeyModifier.NONE, 0, "key.categories.clienttweaks");
	}
}
