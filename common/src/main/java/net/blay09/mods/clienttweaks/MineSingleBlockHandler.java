package net.blay09.mods.clienttweaks;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.DigSpeedEvent;
import net.blay09.mods.balm.api.event.TickPhase;
import net.blay09.mods.balm.api.event.TickType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class MineSingleBlockHandler {
    private static boolean blockBreakConsumed;

    public static void initialize() {
        Balm.getEvents().onTickEvent(TickType.Client, TickPhase.End, MineSingleBlockHandler::onClientTick);
        Balm.getEvents().onEvent(DigSpeedEvent.class, MineSingleBlockHandler::onDigSpeed);
    }

    private static void onClientTick(Minecraft minecraft) {
        if (minecraft.player == null || minecraft.level == null || !isMineSingleBlockKeyHeld() || !isMiningKeyHeld(minecraft)) {
            blockBreakConsumed = false;
        }
    }

    private static void onDigSpeed(DigSpeedEvent event) {
        if (isLocalPlayer(event.getPlayer()) && isMineSingleBlockKeyHeld() && blockBreakConsumed) {
            event.setSpeedOverride(0f);
            event.setCanceled(true);
        }
    }

    private static boolean isLocalPlayer(@Nullable Player player) {
        return player != null && player == Minecraft.getInstance().player;
    }

    private static boolean isMineSingleBlockKeyHeld() {
        return ModKeyMappings.mineSingleBlockKeyMapping != null && ModKeyMappings.mineSingleBlockKeyMapping.isDown();
    }

    private static boolean isMiningKeyHeld(Minecraft minecraft) {
        return minecraft.options.keyAttack.isDown();
    }

    public static boolean shouldPreventBlockBreaking() {
        return isMineSingleBlockKeyHeld() && blockBreakConsumed;
    }

    public static void onBlockBroken() {
        if (isMineSingleBlockKeyHeld()) {
            blockBreakConsumed = true;
        }
    }
}
