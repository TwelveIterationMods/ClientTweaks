package net.blay09.mods.clienttweaks;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@Mod.EventBusSubscriber(modid = ClientTweaks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientTweaksConfig {

    public static class Client {
        public final ForgeConfigSpec.BooleanValue autoClimbLadder;
        public final ForgeConfigSpec.BooleanValue disablePotionShift;
        public final ForgeConfigSpec.BooleanValue doNotUseLastTorch;
        public final ForgeConfigSpec.BooleanValue hideOffhandItem;
        public final ForgeConfigSpec.BooleanValue hideOwnParticleEffects;
        public final ForgeConfigSpec.BooleanValue hideShieldUnlessHoldingWeapon;
        public final ForgeConfigSpec.BooleanValue noOffhandTorchWithBlock;
        public final ForgeConfigSpec.BooleanValue noOffhandTorchWithEmptyHand;
        public final ForgeConfigSpec.BooleanValue offhandTorchWithToolOnly;
        public final ForgeConfigSpec.BooleanValue disableStepAssist;
        public final ForgeConfigSpec.BooleanValue masterVolumeSlider;
        public final ForgeConfigSpec.BooleanValue musicVolumeSlider;

        public final ForgeConfigSpec.ConfigValue<List<? extends String>> torchItems;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> torchTools;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> offhandTorchTools;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> shieldWeapons;

        Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Configuration for Client Tweaks").push("client");

            builder.push("tweaks");

            autoClimbLadder = builder
                    .comment("This option will let you climb ladders automatically by just looking upwards, rather than requiring a key to be held down.")
                    .translation("config.clienttweaks.autoClimbLadder")
                    .define("autoClimbLadder", false);

            disablePotionShift = builder
                    .comment("This option disables the shifting of the inventory to the right when potion effects are active.")
                    .translation("config.clienttweaks.disablePotionShift")
                    .define("disablePotionShift", true);

            doNotUseLastTorch = builder
                    .comment("This prevents the last torch in the offhand from being placed.")
                    .translation("config.clienttweaks.doNotUseLastTorch")
                    .define("doNotUseLastTorch", false);

            hideOffhandItem = builder
                    .comment("This option will hide your offhand item. It can be toggled via an optional key binding.")
                    .translation("config.clienttweaks.hideOffhandItem")
                    .define("hideOffhandItem", false);

            hideOwnParticleEffects = builder
                    .comment("This option will hide your own potion particle effects for your client (other players will still see them).")
                    .translation("config.clienttweaks.hideOwnParticleEffects")
                    .define("hideOwnParticleEffects", false);

            hideShieldUnlessHoldingWeapon = builder
                    .comment("This option will hide your shield unless you are holding a weapon.")
                    .translation("config.clienttweaks.hideShieldUnlessHoldingWeapon")
                    .define("hideShieldUnlessHoldingWeapon", true);

            noOffhandTorchWithBlock = builder
                    .comment("This prevents torches from being placed from your offhand if you have a block in your main hand.")
                    .translation("config.clienttweaks.noOffhandTorchWithBlock")
                    .define("noOffhandTorchWithBlock", true);

            noOffhandTorchWithEmptyHand = builder
                    .comment("This prevents torches from being placed from your off hand if you have an empty main hand.")
                    .translation("config.clienttweaks.noOffhandTorchWithEmptyHand")
                    .define("noOffhandTorchWithEmptyHand", false);

            offhandTorchWithToolOnly = builder
                    .comment("This restricts torches to be placed from the offhand only when you're holding a tool in your main hand.")
                    .translation("config.clienttweaks.offhandTorchWithToolOnly")
                    .define("offhandTorchWithToolOnly", false);

            disableStepAssist = builder
                    .comment("This option will disable step assist added by other mods.")
                    .translation("config.clienttweaks.disableStepAssist")
                    .define("disableStepAssist", false);

            masterVolumeSlider = builder
                    .comment("This adds back the master volume slider to the options screen. Saves you a click!")
                    .translation("config.clienttweaks.masterVolumeSlider")
                    .define("masterVolumeSlider", true);

            musicVolumeSlider = builder
                    .comment("This adds back the music volume slider to the options screen. Saves you a click!")
                    .translation("config.clienttweaks.musicVolumeSlider")
                    .define("musicVolumeSlider", true);

            builder.pop().push("customization");

            torchItems = builder
                    .comment("Items that count as torches for the offhand-torch tweak options.")
                    .translation("clienttweaks.config.torchItems")
                    .defineList("torchItems", Lists.newArrayList(
                            "minecraft:torch",
                            "tconstruct:stone_torch"), it -> it instanceof String);

            torchTools = builder
                    .comment("Items that will place torches from your hotbar on right-click if enabled.")
                    .translation("clienttweaks.config.torchTools")
                    .defineList("torchTools", Lists.newArrayList(
                            "minecraft:wooden_pickaxe",
                            "minecraft:stone_pickaxe",
                            "minecraft:iron_pickaxe",
                            "minecraft:golden_pickaxe",
                            "minecraft:diamond_pickaxe",
                            "tconstruct:pickaxe",
                            "tconstruct:hammer"), it -> it instanceof String);

            offhandTorchTools = builder
                    .comment("Items that will not prevent offhand-torch placement while in offhand, but do not place torches by themselves")
                    .translation("clienttweaks.config.offhandTorchTools")
                    .defineList("offhandTorchTools", Lists.newArrayList(
                            "tconstruct:shovel",
                            "tconstruct:excavator"), it -> it instanceof String);

            shieldWeapons = builder
                    .comment("Items that count as weapons for the offhand-shield hiding tweak options.")
                    .translation("clienttweaks.config.shieldWeapons")
                    .defineList("shieldWeapons", Lists.newArrayList(
                            "tetra:modular_sword"
                    ), it -> it instanceof String);
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
    }

    public static void save() {
        config.save();
    }

}
