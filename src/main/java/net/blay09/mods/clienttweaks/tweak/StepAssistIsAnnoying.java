package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nullable;

public class StepAssistIsAnnoying extends AbstractClientTweak {

	private static final float DEFAULT_STEP_HEIGHT = 0.6f;

	public StepAssistIsAnnoying() {
		super("disableStepAssist");
	}

	@Override
	public String getDescription() {
		return "This option will disable step assist added by other mods.";
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if(event.phase == TickEvent.Phase.START && isEnabled()) {
			if(mc.player != null) {
				mc.player.stepHeight = DEFAULT_STEP_HEIGHT;
			}
		}
	}

	@Nullable
	@Override
	protected KeyBinding createToggleKeybind() {
		return new KeyBinding("key.clienttweaks.disable_step_assist", KeyConflictContext.IN_GAME, KeyModifier.NONE, InputMappings.INPUT_INVALID, "key.categories.clienttweaks");
	}
}
