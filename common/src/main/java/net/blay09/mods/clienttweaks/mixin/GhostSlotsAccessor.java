package net.blay09.mods.clienttweaks.mixin;

import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.SlotSelectTime;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GhostSlots.class)
public interface GhostSlotsAccessor {

    @Accessor
    Reference2ObjectMap<Slot, ?> getIngredients();

    @Accessor
    SlotSelectTime getSlotSelectTime();

}
