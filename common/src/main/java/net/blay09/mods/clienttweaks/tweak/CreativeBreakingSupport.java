package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CreativeBreakingSupport {

    private static final AABB MAX_BOUNDS = new AABB(0.01, 0.01, 0.01, 0.99, 0.99, 0.99);

    @Nullable
    public static VoxelShape getShape(BlockState state, CollisionContext context, VoxelShape originalShape) {
        if (state == null || context == null || originalShape == null) {
            return null;
        }

        final var minecraft = Minecraft.getInstance();
        @SuppressWarnings("ConstantValue") final var player = minecraft != null ? minecraft.player : null;
        final var isCreative = player != null && player.getAbilities().instabuild;
        final var isPlayerShapeQuery = context instanceof EntityCollisionContext entityCollisionContext && entityCollisionContext.getEntity() == player;
        if (!isCreative || !isPlayerShapeQuery || !ClientTweaksConfig.getActive().creativeMode.creativeBreakingSupport) {
            return null;
        }

        if (!isSupported(state) || originalShape.isEmpty()) {
            return null;
        }

        return Shapes.create(originalShape.bounds()
                .expandTowards(-1, 0, -1)
                .expandTowards(1, 0, 1)
                .intersect(MAX_BOUNDS)
        );
    }

    private static boolean isSupported(BlockState state) {
        return state.hasOffsetFunction();
    }
}
