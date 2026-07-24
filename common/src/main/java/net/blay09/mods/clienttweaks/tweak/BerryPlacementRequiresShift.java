package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.client.UseItemInputEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.HitResult;

public class BerryPlacementRequiresShift extends AbstractClientTweak {

    private static final TagKey<Item> BERRY_FOODS = TagKey.create(Registries.ITEM, new ResourceLocation("c", "foods/berry"));

    public BerryPlacementRequiresShift() {
        super("berryPlacementRequiresShift");

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
        if (!heldItem.is(Items.SWEET_BERRIES) && !heldItem.is(BERRY_FOODS)) {
            return;
        }

        if (mc.level == null || mc.hitResult == null || mc.hitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }

        event.setCanceled(true);
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().interactions.berryPlacementRequiresShift;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.interactions.berryPlacementRequiresShift = enabled);
    }
}
