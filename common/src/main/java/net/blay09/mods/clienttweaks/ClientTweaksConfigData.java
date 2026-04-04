package net.blay09.mods.clienttweaks;

import com.google.common.collect.Lists;
import net.blay09.mods.balm.platform.config.reflection.Comment;
import net.blay09.mods.balm.platform.config.reflection.Config;
import net.blay09.mods.balm.platform.config.reflection.NestedType;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Set;

@Config(ClientTweaks.MOD_ID)
public class ClientTweaksConfigData {

    public Tweaks tweaks = new Tweaks();

    public Customization customization = new Customization();

    public static class Tweaks {
        @Comment("This option will let you climb ladders automatically by just looking upwards, rather than requiring a key to be held down.")
        public boolean autoClimbLadder = false;

        @Comment("This prevents the last torch in the offhand from being placed.")
        public boolean doNotUseLastTorch = false;

        @Comment("This prevents the last durability from being used up.")
        public boolean doNotUseLastMending = false;

        @Comment("This option will hide your offhand item. It can be toggled via an optional key binding.")
        public boolean hideOffhandItem = false;

        @Comment("This option will hide your empty main hand in first person.")
        public boolean hideEmptyMainHand = false;

        @Comment("This option will hide both first person hands. It can be toggled via an optional key binding.")
        public boolean hideHands = false;

        @Comment("This option will hide your own potion particle effects for your client (other players will still see them).")
        public boolean hideOwnParticleEffects = false;

        @Comment("This option will hide your shield unless you are holding a weapon.")
        public boolean hideShieldUnlessHoldingWeapon = true;

        @Comment("This prevents torches from being placed from your offhand at all.")
        public boolean noOffhandTorchAtAll = false;

        @Comment("This prevents torches from being placed from your offhand if you have a block in your main hand.")
        public boolean noOffhandTorchWithBlock = true;

        @Comment("This prevents torches from being placed from your offhand if you have food in your main hand.")
        public boolean noOffhandTorchWithFood = true;

        @Comment("This prevents torches from being placed from your offhand if you have an empty main hand.")
        public boolean noOffhandTorchWithEmptyHand = false;

        @Comment("This prevents items from being used from your offhand if you have food in your main hand.")
        public boolean noOffhandUseWithFood = false;

        @Comment("This restricts torches to be placed from the offhand only when you're holding a tool in your main hand.")
        public boolean offhandTorchWithToolOnly = false;

        @Comment("This prevents fireworks from being launched from your offhand if you are wearing an Elytra, unless you're flying.")
        public boolean noOffhandFireworksWithElytra = true;

        @Comment("This option will disable step assist added by other mods.")
        public boolean disableStepAssist = false;

        @Comment("This option will disable log stripping.")
        public boolean disableLogStripping = false;

        @Comment("This option will disable log stripping unless the player is holding shift.")
        public boolean logStrippingRequiresShift = false;

        @Comment("This prevents sweet berries from being placed unless the player is holding shift or placing them on dirt or farmland.")
        public boolean berryPlacementRequiresShiftOrDirt = false;

        @Comment("This option will disable paving when holding a block in your offhand.")
        public boolean disablePavingWithBlockInOffhand = true;

        @Comment("This adds back the master volume slider to the options screen. Saves you a click!")
        public boolean masterVolumeSlider = true;

        @Comment("This adds back the music volume slider to the options screen. Saves you a click!")
        public boolean musicVolumeSlider = true;

        @Comment("This option will make iron fences and glass panes have a bigger hitbox while placing them, making it easier to aim.")
        public boolean paneBuildingSupport = true;

        @Comment("This option will make chains have a bigger hitbox while placing them, making it easier to aim.")
        public boolean chainBuildingSupport = true;

        @Comment("This option makes the recipe book not shift the inventory when opened. Works best with smaller GUI scales / bigger resolutions.")
        public boolean noRecipeBookShifting = false;

        @Comment("Prevents accidental mining of certain fragile blocks like budding amethysts.")
        public boolean preventAccidentalMining = false;

        @Comment("This option will increase the hitbox of random-offset blocks in creative mode, making it easier to break them quickly.")
        public boolean creativeBreakingSupport = true;

        @Comment("This option makes right clicking the recipe book search bar clear it.")
        public boolean clearRecipeBookOnRightClick = true;

        @Comment("This option makes clicking ghost ingredients in the crafting grid navigate to it in the recipe book.")
        public boolean navigateToGhostIngredients = true;

        @Comment("This option retains recipe book search text when reopening the recipe book.")
        public boolean retainRecipeBookSearch = true;

        @Comment("This option makes right clicking the creative menu search bar clear it.")
        public boolean clearCreativeMenuSearchOnRightClick = true;

        @Comment("This option retains creative menu search text when reopening the creative menu search tab.")
        public boolean retainCreativeMenuSearch = true;
    }

    public static class Customization {
        @Comment("Items that count as torches for the offhand-torch tweak options.")
        @NestedType(String.class)
        public List<String> torchItems = Lists.newArrayList(
                "minecraft:torch",
                "minecraft:soul_torch",
                "tconstruct:stone_torch"
        );

        @Comment("Items that are allowed to place torches from the offhand if offhandTorchWithToolOnly is enabled.")
        @NestedType(String.class)
        public List<String> torchTools = Lists.newArrayList(
                "minecraft:wooden_pickaxe",
                "minecraft:stone_pickaxe",
                "minecraft:iron_pickaxe",
                "minecraft:golden_pickaxe",
                "minecraft:diamond_pickaxe",
                "minecraft:netherite_pickaxe",
                "tconstruct:pickaxe",
                "tconstruct:hammer"
        );

        @Comment("Additional items that count as weapons for the offhand-shield hiding tweak options.")
        @NestedType(String.class)
        public List<String> shieldWeapons = Lists.newArrayList(
                "tetra:modular_sword"
        );

        @Comment("Additional items that count as shields for the offhand-shield hiding tweak options.")
        @NestedType(String.class)
        public List<String> shieldItems = Lists.newArrayList(
                "basicshields:wooden_shield",
                "basicshields:golden_shield",
                "basicshields:diamond_shield",
                "basicshields:netherite_shield"
        );

        @Comment("Items that count as fireworks for the offhand-firework tweak options.")
        @NestedType(String.class)
        public List<String> fireworkItems = Lists.newArrayList(
                "minecraft:firework_rocket"
        );

        @Comment("Blocks that should be protected in the prevent accidental mining tweak.")
        @NestedType(Identifier.class)
        public Set<Identifier> fragileBlocks = Set.of(
                Identifier.withDefaultNamespace("budding_amethyst"),
                Identifier.withDefaultNamespace("small_amethyst_bud"),
                Identifier.withDefaultNamespace("medium_amethyst_bud"),
                Identifier.withDefaultNamespace("large_amethyst_bud")
        );

        @Comment("Additional items that count as food for the offhand-torch and -use tweak options.")
        @NestedType(String.class)
        public List<String> foodItems = Lists.newArrayList(
                "supplementaries:lunch_basket"
        );
    }
}
