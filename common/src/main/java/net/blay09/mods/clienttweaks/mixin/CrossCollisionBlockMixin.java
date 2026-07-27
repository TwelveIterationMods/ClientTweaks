package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(CrossCollisionBlock.class)
public class CrossCollisionBlockMixin {

    @Inject(method = "getShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"), cancellable = true)
    void getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> callbackInfo) {
        final var minecraft = Minecraft.getInstance();
        @SuppressWarnings("ConstantValue") final var player = minecraft != null ? minecraft.player : null;
        boolean isHoldingCrossCollisionBlock = player != null && Block.byItem(player.getMainHandItem().getItem()) instanceof CrossCollisionBlock;
        if (isHoldingCrossCollisionBlock && ClientTweaksConfig.getActive().building.paneBuildingSupport) {
            // Only touch block states built from the properties this logic understands; anything else
            // (e.g. diagonal connectors adding extra directional properties) is left alone
            if (!state.getProperties().containsAll(Set.<Property<?>>of(CrossCollisionBlock.NORTH, CrossCollisionBlock.EAST, CrossCollisionBlock.SOUTH, CrossCollisionBlock.WEST))
                    || !Set.<Property<?>>of(CrossCollisionBlock.NORTH, CrossCollisionBlock.EAST, CrossCollisionBlock.SOUTH, CrossCollisionBlock.WEST, CrossCollisionBlock.WATERLOGGED).containsAll(state.getProperties())) {
                return;
            }

            boolean isPillarSection = !state.getValue(CrossCollisionBlock.EAST) && !state.getValue(CrossCollisionBlock.WEST) && !state.getValue(
                    CrossCollisionBlock.NORTH) && !state.getValue(CrossCollisionBlock.SOUTH);
            boolean isThinSection = isOnlyOneTrue(state.getValue(CrossCollisionBlock.EAST),
                    state.getValue(CrossCollisionBlock.WEST),
                    state.getValue(CrossCollisionBlock.NORTH),
                    state.getValue(CrossCollisionBlock.SOUTH));
            if (isThinSection || isPillarSection) {
                final var originalShape = callbackInfo.getReturnValue();
                if (!originalShape.isEmpty()) {
                    final var modifiedShape = Shapes.create(originalShape.bounds()
                            .expandTowards(0.25, 0, 0.25)
                            .expandTowards(-0.25, 0, -0.25)
                            .intersect(new AABB(0.01, 0.01, 0.01, 0.99, 0.99, 0.99))
                    );
                    callbackInfo.setReturnValue(modifiedShape);
                }
            }
        }
    }

    private static boolean isOnlyOneTrue(boolean... args) {
        int trues = 0;
        for (boolean arg : args) {
            if (arg) {
                trues++;
            }
        }
        return trues == 1;
    }

}
