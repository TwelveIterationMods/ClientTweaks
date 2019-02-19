package net.blay09.mods.clienttweaks;

import com.google.common.collect.Lists;
import net.blay09.mods.clienttweaks.tweak.ClientTweak;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ClientTweaks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientTweaksConfig {

    public static class Client {
        public final ForgeConfigSpec.ConfigValue<List<String>> torchItems;
        public final ForgeConfigSpec.ConfigValue<List<String>> torchTools;
        public final ForgeConfigSpec.ConfigValue<List<String>> offhandTorchTools;
        public final Map<String, ForgeConfigSpec.BooleanValue> tweakStates = new HashMap<>();

        Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Configuration for Client Tweaks").push("client");

            torchItems = builder
                    .comment("Items that count as torches for the offhand-torch tweak options.")
                    .translation("clienttweaks.config.torchItems")
                    .define("torchItems", Lists.newArrayList(
                            "minecraft:torch",
                            "tconstruct:stone_torch"));

            torchTools = builder
                    .comment("Items that will place torches from your hotbar on right-click if enabled.")
                    .translation("clienttweaks.config.torchTools")
                    .define("torchTools", Lists.newArrayList(
                            "minecraft:wooden_pickaxe",
                            "minecraft:stone_pickaxe",
                            "minecraft:iron_pickaxe",
                            "minecraft:golden_pickaxe",
                            "minecraft:diamond_pickaxe",
                            "tconstruct:pickaxe",
                            "tconstruct:hammer"));

            offhandTorchTools = builder
                    .comment("Items that will not prevent offhand-torch placement while in offhand, but do not place torches by themselves")
                    .translation("clienttweaks.config.offhandTorchTools")
                    .define("offhandTorchTools", Lists.newArrayList(
                            "tconstruct:shovel",
                            "tconstruct:excavator"));

            for (ClientTweak tweak : ClientTweaks.getTweaks()) {
                tweakStates.put(tweak.getName(), builder.comment(tweak.getDescription())
                        .translation("clienttweaks.config.tweak." + tweak.getName())
                        .define(tweak.getName(), tweak.isEnabledDefault()));
            }
        }
    }

    static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;

    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    private static ModConfig config;

    @SubscribeEvent
    public static void onConfigLoad(ModConfig.ModConfigEvent event) {
        config = event.getConfig();

        for (ClientTweak tweak : ClientTweaks.getTweaks()) {
            tweak.setEnabled(CLIENT.tweakStates.get(tweak.getName()).get());
        }
    }

    public static void updateTweakState(ClientTweak tweak) {
        ForgeConfigSpec.BooleanValue property = ClientTweaksConfig.CLIENT.tweakStates.get(tweak.getName());
        config.getConfigData().set(property.getPath(), tweak.isEnabled());
        config.save();
    }

}
