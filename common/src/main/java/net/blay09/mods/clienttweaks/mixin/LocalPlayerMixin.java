package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.clienttweaks.ReactiveSprintKeyState;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin implements ReactiveSprintKeyState {

    @Unique
    private boolean clienttweaks$sprintingWithSprintKey;

    @Override
    public boolean clienttweaks$isSprintingWithSprintKey() {
        return clienttweaks$sprintingWithSprintKey;
    }

    @Override
    public void clienttweaks$setSprintingWithSprintKey(boolean sprintingWithSprintKey) {
        clienttweaks$sprintingWithSprintKey = sprintingWithSprintKey;
    }
}
