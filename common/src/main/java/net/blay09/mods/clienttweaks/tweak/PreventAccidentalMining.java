package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.platform.event.callback.BlockCallback;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class PreventAccidentalMining extends AbstractClientTweak {
    public PreventAccidentalMining() {
        super("prevent_accidental_mining");

        BlockCallback.DigSpeed.EVENT.register(this::onDigSpeed);
    }

    public float onDigSpeed(BlockGetter blockGetter, BlockPos pos, BlockState state, Player player, float speed) {
        if (isEnabled() && !player.isShiftKeyDown()) {
            final var blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
            final var fragileBlockIds = ClientTweaksConfig.getActive().customization.fragileBlocks;
            if (fragileBlockIds.contains(blockId)) {
                return 0f;
            }
        }

        return speed;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.preventAccidentalMining;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.preventAccidentalMining = enabled);
    }
}
