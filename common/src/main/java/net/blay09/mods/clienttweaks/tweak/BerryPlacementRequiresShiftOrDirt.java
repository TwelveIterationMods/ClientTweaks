package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.client.UseItemInputEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BerryPlacementRequiresShiftOrDirt extends AbstractClientTweak {

    public BerryPlacementRequiresShiftOrDirt() {
        super("berryPlacementRequiresShiftOrDirt");

        Balm.getEvents().onEvent(UseItemInputEvent.class, this::onRightClick);
    }

    public void onRightClick(UseItemInputEvent event) {
        if (!isEnabled()) {
            return;
        }

        final var mc = Minecraft.getInstance();
        final var player = mc.player;
        if (player == null || player.isShiftKeyDown()) {
            return;
        }

        final var heldItem = player.getItemInHand(event.getHand());
        if (!heldItem.is(Items.SWEET_BERRIES)) {
            return;
        }

        if (mc.level == null || mc.hitResult == null || mc.hitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }

        final var blockHitResult = (BlockHitResult) mc.hitResult;
        final var clickedPos = blockHitResult.getBlockPos();
        final BlockPos placementPos;
        if (mc.level.getBlockState(clickedPos).canBeReplaced()) {
            placementPos = clickedPos;
        } else {
            placementPos = clickedPos.relative(blockHitResult.getDirection());
        }

        final var supportState = mc.level.getBlockState(placementPos.below());
        if ((supportState.is(BlockTags.DIRT) && !supportState.is(Blocks.GRASS_BLOCK)) || supportState.is(Blocks.FARMLAND)) {
            return;
        }

        event.setCanceled(true);
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().interactions.berryPlacementRequiresShiftOrDirt;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.interactions.berryPlacementRequiresShiftOrDirt = enabled);
    }
}
