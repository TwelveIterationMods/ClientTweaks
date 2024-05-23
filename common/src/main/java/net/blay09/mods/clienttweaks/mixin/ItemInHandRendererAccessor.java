package net.blay09.mods.clienttweaks.mixin;

import net.minecraft.client.renderer.ItemInHandRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemInHandRenderer.class)
public interface ItemInHandRendererAccessor {

    @Accessor
    void setOOffHandHeight(float oOffHandHeight);

    @Accessor
    void setOffHandHeight(float offHandHeight);

}
