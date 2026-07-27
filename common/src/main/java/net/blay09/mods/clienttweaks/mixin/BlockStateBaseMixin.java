package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class BlockStateBaseMixin {
    @SuppressWarnings("UnreachableCode")
    @Inject(method = "getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"), cancellable = true)
    void getShape(BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> callbackInfo) {
        @SuppressWarnings("DataFlowIssue") final var state = (BlockState) (Object) this;
        final var minecraft = Minecraft.getInstance();
        final var player = minecraft != null ? minecraft.player : null;
        final var isCreative = player != null && player.getAbilities().instabuild;
        boolean isPlayerShapeQuery = context instanceof EntityCollisionContext entityCollisionContext && entityCollisionContext.getEntity() == player;
        if (isCreative && isPlayerShapeQuery && ClientTweaksConfig.getActive().creativeMode.creativeBreakingSupport && state.hasOffsetFunction()) {
            final var originalShape = callbackInfo.getReturnValue();
            if (!originalShape.isEmpty()) {
                final var modifiedShape = Shapes.create(originalShape.bounds()
                        .expandTowards(-1, 0, -1)
                        .expandTowards(1, 0, 1)
                        .intersect(new AABB(0.01, 0.01, 0.01, 0.99, 0.99, 0.99))
                );
                callbackInfo.setReturnValue(modifiedShape);
            }
        }
    }
}
