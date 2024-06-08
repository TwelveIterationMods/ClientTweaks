package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.TickPhase;
import net.blay09.mods.balm.api.event.TickType;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class StepAssistIsAnnoying extends AbstractClientTweak {

    private final ResourceLocation disableStepAssistModifierId = ResourceLocation.fromNamespaceAndPath("clienttweaks", "disable_step_assist");
    private final AttributeModifier disableStepAssistModifier = new AttributeModifier(disableStepAssistModifierId,
            -1,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    public StepAssistIsAnnoying() {
        super("disableStepAssist");

        // TODO might have to add prio to tick event handlers since this used to run on Lowest
        Balm.getEvents().onTickEvent(TickType.Client, TickPhase.Start, this::onPlayerTick);
    }

    public void onPlayerTick(Minecraft client) {
        Player player = client.player;
        if (player != null) {
            if (isEnabled()) {
                AttributeInstance attributeInstance = player.getAttribute(Attributes.STEP_HEIGHT);
                if (attributeInstance != null && !attributeInstance.hasModifier(disableStepAssistModifierId)) {
                    attributeInstance.addTransientModifier(disableStepAssistModifier);
                }
            } else {
                AttributeInstance attributeInstance = player.getAttribute(Attributes.STEP_HEIGHT);
                if (attributeInstance != null) {
                    attributeInstance.removeModifier(disableStepAssistModifier.id());
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
