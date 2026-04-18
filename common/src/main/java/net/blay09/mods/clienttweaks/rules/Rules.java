package net.blay09.mods.clienttweaks.rules;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Rules {
    public static boolean isTorchItem(ItemStack itemStack) {
        return itemStack.is(Items.TORCH)
                || itemStack.is(Items.COPPER_TORCH)
                || itemStack.is(Items.REDSTONE_TORCH)
                || itemStack.is(Items.SOUL_TORCH);
    }

    public static boolean isToolItem(ItemStack itemStack) {
        return itemStack.has(DataComponents.TOOL);
    }

    public static boolean isFoodItem(ItemStack itemStack) {
        return itemStack.has(DataComponents.FOOD);
    }

    public static boolean isFireworkItem(ItemStack itemStack) {
        return itemStack.has(DataComponents.FIREWORKS);
    }

    public static boolean requiresShiftToMine(BlockState state) {
        return state.is(Blocks.BUDDING_AMETHYST)
                || state.is(Blocks.SMALL_AMETHYST_BUD)
                || state.is(Blocks.MEDIUM_AMETHYST_BUD)
                || state.is(Blocks.LARGE_AMETHYST_BUD);
    }
}
