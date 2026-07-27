package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChainBlock.class)
public class ChainBlockMixin {

    @Inject(method = "getShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"), cancellable = true)
    void getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> callbackInfo) {
        final var config = ClientTweaksConfig.getActiveOrNull();
        if (config == null || !config.building.chainBuildingSupport) {
            return;
        }

        final var minecraft = Minecraft.getInstance();
        @SuppressWarnings("ConstantValue") final var player = minecraft != null ? minecraft.player : null;
        if (player == null) {
            return;
        }

        if (player.getMainHandItem().is(ItemTags.CHAINS)
                || Block.byItem(player.getMainHandItem().getItem()) instanceof ChainBlock
                || (player.getBlockY() < pos.getY() - 1 && player.getMainHandItem().getItem() instanceof BlockItem)) {
            final var originalShape = callbackInfo.getReturnValue();
            if (!originalShape.isEmpty()) {
                final var modifiedShape = Shapes.create(originalShape.bounds()
                        .expandTowards(0.25, 0.25, 0.25)
                        .expandTowards(-0.25, -0.25, -0.25)
                        .intersect(new AABB(0.01, 0.01, 0.01, 0.99, 0.99, 0.99))
                );
                callbackInfo.setReturnValue(modifiedShape);
            }
        }
    }

}
