package net.blay09.mods.clienttweaks.mixin;

import net.minecraft.client.player.FirstPersonHandsAndItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FirstPersonHandsAndItems.class)
public interface FirstPersonHandsAndItemsAccessor {

    @Accessor
    void setOOffHandHeight(float oOffHandHeight);

    @Accessor
    void setOffHandHeight(float offHandHeight);
}
