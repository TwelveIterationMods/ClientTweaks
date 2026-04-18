package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.platform.event.callback.BlockCallback;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksRules;
import net.minecraft.core.BlockPos;
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
            if (ClientTweaksRules.requiresShiftToMine(state)) {
                return 0f;
            }
        }

        return speed;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().mining.preventAccidentalMining;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfig.class, it -> it.mining.preventAccidentalMining = enabled);
    }
}
