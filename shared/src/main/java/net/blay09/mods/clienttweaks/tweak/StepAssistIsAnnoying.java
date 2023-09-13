package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.TickPhase;
import net.blay09.mods.balm.api.event.TickType;
import net.blay09.mods.balm.api.event.client.ClientStartedEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

public class StepAssistIsAnnoying extends AbstractClientTweak {

    private static final float DEFAULT_STEP_HEIGHT = 0.6f;
    private final AttributeModifier disableStepAssistModifier = new AttributeModifier("Disable step assist", -1, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private Attribute stepHeightAttribute;

    public StepAssistIsAnnoying() {
        super("disableStepAssist");

        Balm.getEvents()
                .onEvent(ClientStartedEvent.class,
                        event -> stepHeightAttribute = Balm.getRegistries().getAttribute(new ResourceLocation("forge", "step_height_addition")));

        // TODO might have to add prio to tick event handlers since this used to run on Lowest
        Balm.getEvents().onTickEvent(TickType.Client, TickPhase.Start, this::onPlayerTick);
    }

    public void onPlayerTick(Minecraft client) {
        Player player = client.player;
        if (player != null) {
            if (isEnabled()) {
                player.setMaxUpStep(DEFAULT_STEP_HEIGHT);
                if (stepHeightAttribute != null) {
                    AttributeInstance attributeInstance = player.getAttribute(stepHeightAttribute);
                    if (attributeInstance != null && !attributeInstance.hasModifier(disableStepAssistModifier)) {
                        attributeInstance.addTransientModifier(disableStepAssistModifier);
                    }
                }
            } else {
                if (stepHeightAttribute != null) {
                    AttributeInstance attributeInstance = player.getAttribute(stepHeightAttribute);
                    if (attributeInstance != null) {
                        attributeInstance.removeModifier(disableStepAssistModifier);
                    }
                }
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.disableStepAssist;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.disableStepAssist = enabled);
    }

    @Override
    public boolean hasKeyBinding() {
        return true;
    }
}
