package net.blay09.mods.clienttweaks;

import net.blay09.mods.shogi.Shogi;
import net.blay09.mods.shogi.ShogiValue;
import net.blay09.mods.shogi.scope.ShogiScope;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public final class ClientTweaksRules {
    public static final ShogiScope SCOPE = Shogi.scope(ClientTweaks.id("rules"), scope -> scope.setDefaultNamespaces(List.of("clienttweaks", "shogi")));
    private static final TagKey<Item> BERRY_FOODS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "foods/berry"));

    public static final ShogiValue<ItemStack, Boolean> isTorch = SCOPE.booleanValue(ClientTweaks.id("is_torch"), itemStack ->
            itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock().defaultBlockState().getLightEmission() > 0);
    public static final ShogiValue<ItemStack, Boolean> isBerry = SCOPE.booleanValue(ClientTweaks.id("is_berry"), itemStack ->
            itemStack.is(Items.SWEET_BERRIES) || itemStack.is(BERRY_FOODS));
    public static final ShogiValue<ItemStack, Boolean> isTool = SCOPE.booleanValue(ClientTweaks.id("is_tool"), itemStack ->
            itemStack.has(DataComponents.TOOL));
    public static final ShogiValue<ItemStack, Boolean> isFood = SCOPE.booleanValue(ClientTweaks.id("is_food"), itemStack ->
            itemStack.has(DataComponents.FOOD));
    public static final ShogiValue<ItemStack, Boolean> isFirework = SCOPE.booleanValue(ClientTweaks.id("is_firework"), itemStack ->
            itemStack.has(DataComponents.FIREWORKS));
    public static final ShogiValue<ItemStack, Boolean> isWeapon = SCOPE.booleanValue(ClientTweaks.id("is_weapon"), itemStack ->
            itemStack.has(DataComponents.WEAPON));
    public static final ShogiValue<BlockState, Boolean> requiresShiftToMine = SCOPE.booleanValue(ClientTweaks.id("requires_shift_to_mine"), state ->
            state.is(Blocks.BUDDING_AMETHYST)
                    || state.is(Blocks.SMALL_AMETHYST_BUD)
                    || state.is(Blocks.MEDIUM_AMETHYST_BUD)
                    || state.is(Blocks.LARGE_AMETHYST_BUD)
                    || state.is(Blocks.SUSPICIOUS_SAND)
                    || state.is(Blocks.SUSPICIOUS_GRAVEL));

    public static boolean isTorch(ItemStack itemStack) {
        return isTorch.getOrDefault(itemStack);
    }

    public static boolean isBerry(ItemStack itemStack) {
        return isBerry.getOrDefault(itemStack);
    }

    public static boolean isTool(ItemStack itemStack) {
        return isTool.getOrDefault(itemStack);
    }

    public static boolean isFood(ItemStack itemStack) {
        return isFood.getOrDefault(itemStack);
    }

    public static boolean isFirework(ItemStack itemStack) {
        return isFirework.getOrDefault(itemStack);
    }

    public static boolean isWeapon(ItemStack itemStack) {
        return isWeapon.getOrDefault(itemStack);
    }

    public static boolean requiresShiftToMine(BlockState state) {
        return requiresShiftToMine.getOrDefault(state);
    }

    private ClientTweaksRules() {
    }

}
