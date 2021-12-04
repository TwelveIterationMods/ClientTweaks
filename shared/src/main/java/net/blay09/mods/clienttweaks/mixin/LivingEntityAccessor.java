package net.blay09.mods.clienttweaks.mixin;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {

    @Accessor("DATA_EFFECT_AMBIENCE_ID")
    static EntityDataAccessor<Boolean> getDataEffectAmbienceId() {
        throw new AssertionError();
    }

    @Accessor("DATA_EFFECT_COLOR_ID")
    static EntityDataAccessor<Integer> getDataEffectColorId() {
        throw new AssertionError();
    }
}
