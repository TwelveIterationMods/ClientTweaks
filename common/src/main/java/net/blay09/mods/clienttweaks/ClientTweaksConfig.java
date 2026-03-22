package net.blay09.mods.clienttweaks;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.platform.config.schema.BalmConfigSchema;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ClientTweaksConfig {

    public static BalmConfigSchema schema;

    public static ClientTweaksConfigData getActive() {
        return Balm.config().getActiveConfig(ClientTweaksConfigData.class);
    }

    public static void initialize() {
        schema = Balm.config().registerConfig(ClientTweaksConfigData.class);
    }

    public static boolean isTorchItem(ItemStack itemStack) {
        return isItemConfiguredFor(itemStack, ClientTweaksConfig.getActive().customization.torchItems);
    }

    public static boolean isTorchTool(ItemStack itemStack) {
        return isItemConfiguredFor(itemStack, ClientTweaksConfig.getActive().customization.torchTools);
    }

    public static boolean isShieldWeapon(ItemStack itemStack) {
        return isItemConfiguredFor(itemStack, ClientTweaksConfig.getActive().customization.shieldWeapons);
    }

    public static boolean isShieldItem(ItemStack itemStack) {
        return isItemConfiguredFor(itemStack, ClientTweaksConfig.getActive().customization.shieldItems);
    }

    public static boolean isFireworkItem(ItemStack itemStack) {
        return isItemConfiguredFor(itemStack, ClientTweaksConfig.getActive().customization.fireworkItems);
    }

    public static boolean isItemConfiguredFor(ItemStack itemStack, List<String> items) {
        if (itemStack.isEmpty()) {
            return false;
        }

        final var registryName = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        final var itemPasses = items.contains(registryName.toString());
        final var tagPasses = itemStack.tags().anyMatch(it -> items.contains("#" + it.location()));
        return itemPasses || tagPasses;
    }

    public static boolean isFoodItem(ItemStack itemStack) {
        return isItemConfiguredFor(itemStack, ClientTweaksConfig.getActive().customization.foodItems);
    }
}
