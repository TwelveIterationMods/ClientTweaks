package net.blay09.mods.clienttweaks.tweak;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.platform.event.callback.ClientItemCallback;
import net.blay09.mods.balm.platform.event.callback.InteractionEventResult;
import net.blay09.mods.clienttweaks.ClientTweaksConfig;
import net.blay09.mods.clienttweaks.ClientTweaksConfigData;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class NoOffhandUseWithFood extends AbstractClientTweak {

    public NoOffhandUseWithFood() {
        super("noOffhandUseWithFood");

        ClientItemCallback.Use.EVENT.register(this::onRightClick);
    }

    public InteractionEventResult onRightClick(Player player, InteractionHand hand) {
        if (isEnabled() && hand == InteractionHand.OFF_HAND) {
            Minecraft mc = Minecraft.getInstance();
            ItemStack mainItem = mc.player.getMainHandItem();
            if (!mainItem.isEmpty() && mainItem.has(DataComponents.FOOD)) {
                return InteractionEventResult.FAIL;
            }
        }

        return InteractionEventResult.DEFAULT;
    }

    @Override
    public boolean isEnabled() {
        return ClientTweaksConfig.getActive().tweaks.noOffhandUseWithFood;
    }

    @Override
    public void setEnabled(boolean enabled) {
        Balm.config().updateLocalConfig(ClientTweaksConfigData.class, it -> it.tweaks.noOffhandUseWithFood = enabled);
    }

}
