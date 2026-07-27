package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.clienttweaks.tweak.PaneBuildingSupport;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossCollisionBlock.class)
public class CrossCollisionBlockMixin {

    @Inject(method = "getShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"), cancellable = true)
    void getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> callbackInfo) {
        final var shape = PaneBuildingSupport.getShape(state, context, callbackInfo.getReturnValue());
        if (shape != null) {
            callbackInfo.setReturnValue(shape);
        }
    }

}
