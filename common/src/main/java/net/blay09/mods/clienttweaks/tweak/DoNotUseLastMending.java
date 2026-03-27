package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.ClientItemCallback;
import net.blay09.mods.balm.platform.event.callback.BlockCallback;
import net.blay09.mods.balm.platform.event.callback.InteractionEventResult;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class DoNotUseLastMending extends AbstractClientTweak {

    public DoNotUseLastMending() {
        super("doNotUseLastMending");

        ClientItemCallback.Use.EVENT.register(this::onRightClick);
        BlockCallback.DigSpeed.EVENT.register(this::onDigSpeed);
    }

    public InteractionEventResult onRightClick(Player player, InteractionHand hand) {
        if (isEnabled()) {
            final var heldItem = player.getItemInHand(hand);
            final var enchantments = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            final var mending = enchantments.getOrThrow(Enchantments.MENDING);
            if (EnchantmentHelper.getItemEnchantmentLevel(mending, heldItem) > 0 && heldItem.getDamageValue() >= heldItem.getMaxDamage() - 1) {
                final var chatComponent = Component.translatable("chat.clienttweaks.lastMending");
                chatComponent.withStyle(ChatFormatting.RED);
                player.sendOverlayMessage(chatComponent);
                return InteractionEventResult.FAIL;
            }
        }

        return InteractionEventResult.DEFAULT;
    }

    public float onDigSpeed(BlockGetter blockGetter, BlockPos pos, BlockState state, Player player, float speed) {
        if (isEnabled()) {
            final var heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);
            final var enchantments = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            final var mending = enchantments.getOrThrow(Enchantments.MENDING);
            if (EnchantmentHelper.getItemEnchantmentLevel(mending, heldItem) > 0 && heldItem.getDamageValue() >= heldItem.getMaxDamage() - 1) {
                return 0f;
            }
        }
        return speed;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.doNotUseLastMending;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.doNotUseLastMending = enabled);
    }

}
