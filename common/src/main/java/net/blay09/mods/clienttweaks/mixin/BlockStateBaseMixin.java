package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.clienttweaks.tweak.ChainBuildingSupport;
import net.blay09.mods.clienttweaks.tweak.CreativeBreakingSupport;
import net.blay09.mods.clienttweaks.tweak.PaneBuildingSupport;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class BlockStateBaseMixin {
    @SuppressWarnings("UnreachableCode")
    @Inject(method = "getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"), cancellable = true)
    void getShape(BlockGetter blockGetter, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> callbackInfo) {
        @SuppressWarnings("DataFlowIssue") final var state = (BlockState) (Object) this;
        final var creativeBreakingSupportShape = CreativeBreakingSupport.getShape(state, context, callbackInfo.getReturnValue());
        if (creativeBreakingSupportShape != null) {
            callbackInfo.setReturnValue(creativeBreakingSupportShape);
            return;
        }

        final var paneBuildingSupportShape = PaneBuildingSupport.getShape(state, context, callbackInfo.getReturnValue());
        if (paneBuildingSupportShape != null) {
            callbackInfo.setReturnValue(paneBuildingSupportShape);
            return;
        }

        final var chainBuildingSupportShape = ChainBuildingSupport.getShape(state, pos, context, callbackInfo.getReturnValue());
        if (chainBuildingSupportShape != null) {
            callbackInfo.setReturnValue(chainBuildingSupportShape);
            return;
        }
    }
}
