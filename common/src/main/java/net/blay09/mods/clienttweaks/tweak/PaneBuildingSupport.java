package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PaneBuildingSupport {

    private static final AABB MAX_BOUNDS = new AABB(0.01, 0.01, 0.01, 0.99, 0.99, 0.99);

    @Nullable
    public static VoxelShape getShape(BlockState state, CollisionContext context, VoxelShape originalShape) {
        final var minecraft = Minecraft.getInstance();
        @SuppressWarnings("ConstantValue") final var player = minecraft != null ? minecraft.player : null;
        final var isHoldingCrossCollisionBlock = player != null && Block.byItem(player.getMainHandItem().getItem()) instanceof CrossCollisionBlock;
        final var isPlayerShapeQuery = context instanceof EntityCollisionContext entityCollisionContext && entityCollisionContext.getEntity() == player;
        if (!isPlayerShapeQuery || !isHoldingCrossCollisionBlock || !ClientTweaksConfig.getActive().building.paneBuildingSupport) {
            return null;
        }

        if (!isSupported(state) || !isPillar(state) || originalShape.isEmpty()) {
            return null;
        }

        return Shapes.create(originalShape.bounds()
                .expandTowards(0.25, 0, 0.25)
                .expandTowards(-0.25, 0, -0.25)
                .intersect(MAX_BOUNDS)
        );
    }

    private static boolean isSupported(BlockState state) {
        if (!(state.getBlock() instanceof CrossCollisionBlock)) {
            return false;
        }

        final var properties = state.getProperties();
        if (!properties.contains(CrossCollisionBlock.NORTH)
                || !properties.contains(CrossCollisionBlock.EAST)
                || !properties.contains(CrossCollisionBlock.SOUTH)
                || !properties.contains(CrossCollisionBlock.WEST)) {
            return false;
        }

        return properties.size() == 4 || properties.size() == 5 && properties.contains(CrossCollisionBlock.WATERLOGGED);
    }

    private static boolean isPillar(BlockState state) {
        final var east = state.getValue(CrossCollisionBlock.EAST);
        final var west = state.getValue(CrossCollisionBlock.WEST);
        final var north = state.getValue(CrossCollisionBlock.NORTH);
        final var south = state.getValue(CrossCollisionBlock.SOUTH);
        final var connections = (east ? 1 : 0) + (west ? 1 : 0) + (north ? 1 : 0) + (south ? 1 : 0);
        return connections <= 1;
    }

}
