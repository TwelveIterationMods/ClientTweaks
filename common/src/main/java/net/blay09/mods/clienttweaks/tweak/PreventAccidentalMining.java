package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.DigSpeedEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.core.registries.BuiltInRegistries;

public class PreventAccidentalMining extends AbstractClientTweak {
    public PreventAccidentalMining() {
        super("preventAccidentalMining");

        Balm.getEvents().onEvent(DigSpeedEvent.class, this::onDigSpeed);
    }

    public void onDigSpeed(DigSpeedEvent event) {
        if (isEnabled() && !event.getPlayer().isShiftKeyDown()) {
            final var blockId = BuiltInRegistries.BLOCK.getKey(event.getState().getBlock());
            final var fragileBlockIds = ClientTweaksConfig.getActive().customization.fragileBlocks;
            if (fragileBlockIds.contains(blockId)) {
                event.setSpeedOverride(0f);
                event.setCanceled(true);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.preventAccidentalMining;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.preventAccidentalMining = enabled);
    }
}
