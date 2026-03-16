package net.blay09.mods.clienttweaks;

import net.blay09.mods.balm.client.platform.event.callback.ClientTickCallback;
import net.blay09.mods.balm.platform.event.callback.BlockCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class MineSingleBlockHandler {
    private static boolean blockBreakConsumed;

    public static void initialize() {
        ClientTickCallback.AFTER.register(MineSingleBlockHandler::onClientTick);
        BlockCallback.DigSpeed.EVENT.register(MineSingleBlockHandler::onDigSpeed);
    }

    private static void onClientTick(Minecraft minecraft) {
        if (minecraft.player == null || minecraft.level == null || !isSafeMineHeld() || !isMiningKeyHeld(minecraft)) {
            blockBreakConsumed = false;
        }
    }

    private static float onDigSpeed(BlockGetter blockGetter, BlockPos pos, BlockState state, Player player, float digSpeed) {
        if (isLocalPlayer(player) && isSafeMineHeld() && blockBreakConsumed) {
            return 0f;
        }

        return digSpeed;
    }

    private static boolean isLocalPlayer(@Nullable Player player) {
        return player != null && player == Minecraft.getInstance().player;
    }

    private static boolean isSafeMineHeld() {
        final var keyMapping = ModKeyMappings.getMineSingleBlockKeyMapping();
        return keyMapping != null && keyMapping.isDown();
    }

    private static boolean isMiningKeyHeld(Minecraft minecraft) {
        return minecraft.options.keyAttack.isDown();
    }

    public static boolean shouldPreventBlockBreaking() {
        return isSafeMineHeld() && blockBreakConsumed;
    }

    public static void onBlockBroken() {
        if (isSafeMineHeld()) {
            blockBreakConsumed = true;
        }
    }
}
