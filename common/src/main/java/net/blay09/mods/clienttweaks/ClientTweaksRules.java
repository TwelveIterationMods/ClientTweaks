package net.blay09.mods.clienttweaks;

import net.blay09.mods.shogi.Shogi;
import net.blay09.mods.shogi.ShogiValue;
import net.blay09.mods.shogi.scope.ShogiScope;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public final class ClientTweaksRules {
    public static final ShogiScope SCOPE = Shogi.scope(ClientTweaks.id("rules"), scope -> scope.setDefaultNamespaces(List.of("clienttweaks", "shogi")));

    public static final ShogiValue<ItemStack, Boolean> isTorchItem = SCOPE.booleanValue(ClientTweaks.id("is_torch_item"), itemStack ->
            itemStack.is(Items.TORCH)
                    || itemStack.is(Items.COPPER_TORCH)
                    || itemStack.is(Items.REDSTONE_TORCH)
                    || itemStack.is(Items.SOUL_TORCH));
    public static final ShogiValue<ItemStack, Boolean> isToolItem = SCOPE.booleanValue(ClientTweaks.id("is_tool_item"), itemStack ->
            itemStack.has(DataComponents.TOOL));
    public static final ShogiValue<ItemStack, Boolean> isFoodItem = SCOPE.booleanValue(ClientTweaks.id("is_food_item"), itemStack ->
            itemStack.has(DataComponents.FOOD));
    public static final ShogiValue<ItemStack, Boolean> isFireworkItem = SCOPE.booleanValue(ClientTweaks.id("is_firework_item"), itemStack ->
            itemStack.has(DataComponents.FIREWORKS));

    public static final ShogiValue<BlockState, Boolean> requiresShiftToMine = SCOPE.booleanValue(ClientTweaks.id("requires_shift_to_mine"), state ->
            state.is(Blocks.BUDDING_AMETHYST)
                    || state.is(Blocks.SMALL_AMETHYST_BUD)
                    || state.is(Blocks.MEDIUM_AMETHYST_BUD)
                    || state.is(Blocks.LARGE_AMETHYST_BUD));

    public static boolean isTorchItem(ItemStack itemStack) {
        return isTorchItem.getOrDefault(itemStack);
    }

    public static boolean isToolItem(ItemStack itemStack) {
        return isToolItem.getOrDefault(itemStack);
    }

    public static boolean isFoodItem(ItemStack itemStack) {
        return isFoodItem.getOrDefault(itemStack);
    }

    public static boolean isFireworkItem(ItemStack itemStack) {
        return isFireworkItem.getOrDefault(itemStack);
    }

    public static boolean requiresShiftToMine(BlockState state) {
        return requiresShiftToMine.getOrDefault(state);
    }

    private ClientTweaksRules() {
    }
}
