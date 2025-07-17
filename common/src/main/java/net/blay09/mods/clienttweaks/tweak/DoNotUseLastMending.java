package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.DigSpeedEvent;
import net.blay09.mods.balm.api.event.client.UseItemInputEvent;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class DoNotUseLastMending extends AbstractClientTweak {

    public DoNotUseLastMending() {
        super("doNotUseLastMending");

        Balm.getEvents().onEvent(UseItemInputEvent.class, this::onRightClick);
        Balm.getEvents().onEvent(DigSpeedEvent.class, this::onDigSpeed);
    }

    public void onRightClick(UseItemInputEvent event) {
        if (isEnabled()) {
            final var minecraft = Minecraft.getInstance();
            final var heldItem = minecraft.player != null ? minecraft.player.getItemInHand(event.getHand()) : ItemStack.EMPTY;
            final var enchantments = minecraft.player.level().registryAccess().registry(Registries.ENCHANTMENT).orElseThrow();
            final var mending = enchantments.getHolderOrThrow(Enchantments.MENDING);
            if (EnchantmentHelper.getItemEnchantmentLevel(mending, heldItem) > 0 && heldItem.getDamageValue() >= heldItem.getMaxDamage() - 1) {
                final var chatComponent = Component.translatable("chat.clienttweaks.lastMending");
                chatComponent.withStyle(ChatFormatting.RED);
                minecraft.player.displayClientMessage(chatComponent, true);
                event.setCanceled(true);
            }
        }
    }

    public void onDigSpeed(DigSpeedEvent event) {
        if (isEnabled()) {
            final var heldItem = event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND);
            final var enchantments = event.getPlayer().level().registryAccess().registry(Registries.ENCHANTMENT).orElseThrow();
            final var mending = enchantments.getHolderOrThrow(Enchantments.MENDING);
            if (EnchantmentHelper.getItemEnchantmentLevel(mending, heldItem) > 0 && heldItem.getDamageValue() >= heldItem.getMaxDamage() - 1) {
                event.setSpeedOverride(0f);
                event.setCanceled(true);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.doNotUseLastMending;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.getConfig().updateConfig(ClientTweaksConfigData.class, it -> it.tweaks.doNotUseLastMending = enabled);
    }

}
