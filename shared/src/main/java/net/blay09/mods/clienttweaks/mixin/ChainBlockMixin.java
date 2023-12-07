package net.blay09.mods.clienttweaks.mixin;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.CrossCollisionBlock;
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
    void getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> callbackInfo) {
        Player player = Minecraft.getInstance().player;
        boolean isHoldingChainBlock = player != null && Block.byItem(player.getMainHandItem().getItem()) instanceof ChainBlock;
        if (isHoldingChainBlock && ClientTweaksConfig.getActive().tweaks.chainBuildingSupport) {
            VoxelShape originalShape = callbackInfo.getReturnValue();
            VoxelShape modifiedShape = Shapes.create(originalShape.bounds()
                    .expandTowards(0.25, 0.25, 0.25)
                    .expandTowards(-0.25, -0.25, -0.25)
                    .intersect(new AABB(0, 0, 0, 1, 1, 1))
            );
            callbackInfo.setReturnValue(modifiedShape);
        }
    }

}
