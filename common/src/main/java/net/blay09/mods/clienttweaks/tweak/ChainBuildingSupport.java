package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ChainBuildingSupport {

    @Nullable
    public static VoxelShape getShape(BlockState state, BlockPos pos, CollisionContext context, VoxelShape originalShape) {
        final var minecraft = Minecraft.getInstance();
        @SuppressWarnings("ConstantValue") final var player = minecraft != null ? minecraft.player : null;
        final var isHoldingChainBlock = player != null && (player.getMainHandItem().is(BlockItemTags.CHAINS.item()) || Block.byItem(player.getMainHandItem().getItem()) instanceof ChainBlock);
        final var isHoldingBlockFromBelow = player != null && player.getBlockY() < pos.getY() - 1 && player.getMainHandItem().getItem() instanceof BlockItem;
        final var isPlayerShapeQuery = context instanceof EntityCollisionContext entityCollisionContext && entityCollisionContext.getEntity() == player;
        if (!isPlayerShapeQuery || !(isHoldingChainBlock || isHoldingBlockFromBelow) || !ClientTweaksConfig.getActive().building.chainBuildingSupport) {
            return null;
        }

        if (!isSupported(state) || originalShape.isEmpty()) {
            return null;
        }

        return Shapes.create(originalShape.bounds()
                .expandTowards(0.25, 0.25, 0.25)
                .expandTowards(-0.25, -0.25, -0.25)
                .intersect(new AABB(0.01, 0.01, 0.01, 0.99, 0.99, 0.99))
        );
    }

    private static boolean isSupported(BlockState state) {
        return state.getBlock() instanceof ChainBlock;
    }

}
