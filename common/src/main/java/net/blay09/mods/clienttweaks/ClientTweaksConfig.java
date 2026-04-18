package net.blay09.mods.clienttweaks;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.platform.config.reflection.Comment;
import net.blay09.mods.balm.platform.config.reflection.Config;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@Config(ClientTweaks.MOD_ID)
public class ClientTweaksConfig {

    public UI ui = new UI();
    public Rendering rendering = new Rendering();
    public Mobility mobility = new Mobility();
    public Mining mining = new Mining();
    public Building building = new Building();
    public Interactions interactions = new Interactions();
    public Torches torches = new Torches();
    public CreativeMode creativeMode = new CreativeMode();
    public RecipeBook recipeBook = new RecipeBook();

    public static class UI {
        @Comment("This adds back the master volume slider to the options screen. Saves you a click!")
        public boolean masterVolumeSlider = true;

        @Comment("This adds back the music volume slider to the options screen. Saves you a click!")
        public boolean musicVolumeSlider = true;

        @Comment("Prioritizes the experience bar info to show instead of the locator bar. Best toggled with the keybind if you care about the locater bar.")
        public boolean alwaysPrioritizeExperienceBar = false;
    }

    public static class Mobility {
        @Comment("This option will let you climb ladders automatically by just looking upwards, rather than requiring a key to be held down.")
        public boolean autoClimbLadder = false;

        @Comment("This option will disable step assist added by other mods.")
        public boolean disableStepAssist = false;
    }

    public static class Rendering {
        @Comment("This option will hide your shield unless you are holding a weapon.")
        public boolean hideShieldUnlessHoldingWeapon = true;

        @Comment("This option will hide your shield unless you are actively blocking.")
        public boolean hideShieldUnlessBlocking = false;

        @Comment("This option will hide your offhand item. It can be toggled via an optional key binding.")
        public boolean hideOffhandItem = false;

        @Comment("This option will hide your empty main hand in first person.")
        public boolean hideEmptyMainHand = false;

        @Comment("This option will hide both first person hands. It can be toggled via an optional key binding.")
        public boolean hideHands = false;

        @Comment("This option will hide your own potion particle effects for your client (other players will still see them).")
        public boolean hideOwnParticleEffects = false;
    }

    public static class RecipeBook {
        @Comment("This option makes the recipe book not shift the inventory when opened. Works best with smaller GUI scales / bigger resolutions.")
        public boolean noRecipeBookShifting = false;

        @Comment("This option makes right clicking the recipe book search bar clear it.")
        public boolean clearRecipeBookOnRightClick = true;

        @Comment("This option makes clicking ghost ingredients in the crafting grid navigate to it in the recipe book.")
        public boolean navigateToGhostIngredients = true;

        @Comment("This option retains recipe book search text when reopening the recipe book.")
        public boolean retainRecipeBookSearch = true;
    }

    public static class CreativeMode {
        @Comment("This option will increase the hitbox of random-offset blocks in creative mode, making it easier to break them quickly.")
        public boolean creativeBreakingSupport = true;

        @Comment("This option makes right clicking the creative menu search bar clear it.")
        public boolean clearCreativeMenuSearchOnRightClick = true;

        @Comment("This option retains creative menu search text when reopening the creative menu search tab.")
        public boolean retainCreativeMenuSearch = true;
    }

    public static class Torches {
        @Comment("This prevents the last torch in the offhand from being placed.")
        public boolean doNotUseLastTorch = false;

        @Comment("This prevents torches from being placed from your offhand at all.")
        public boolean noOffhandTorchAtAll = false;

        @Comment("This prevents torches from being placed from your offhand if you have a block in your main hand.")
        public boolean noOffhandTorchWithBlock = true;

        @Comment("This prevents torches from being placed from your offhand if you have food in your main hand.")
        public boolean noOffhandTorchWithFood = true;

        @Comment("This prevents torches from being placed from your offhand if you have an empty main hand.")
        public boolean noOffhandTorchWithEmptyHand = false;

        @Comment("This restricts torches to be placed from the offhand only when you're holding a tool in your main hand.")
        public boolean offhandTorchWithToolOnly = false;
    }

    public static class Building {
        @Comment("This option will make iron fences and glass panes have a bigger hitbox while placing them, making it easier to aim.")
        public boolean paneBuildingSupport = true;

        @Comment("This option will make chains have a bigger hitbox while placing them, making it easier to aim.")
        public boolean chainBuildingSupport = true;
    }

    public static class Mining {
        @Comment("This prevents the last durability from being used up.")
        public boolean doNotUseLastMending = false;

        @Comment("Prevents accidental mining of certain fragile blocks like budding amethysts.")
        public boolean preventAccidentalMining = false;
    }

    public static class Interactions {
        @Comment("This option will disable log stripping.")
        public boolean disableLogStripping = false;

        @Comment("This option will disable log stripping unless the player is holding shift.")
        public boolean logStrippingRequiresShift = false;

        @Comment("This option will disable paving when holding a block in your offhand.")
        public boolean disablePavingWithBlockInOffhand = true;

        @Comment("This prevents items from being used from your offhand if you have food in your main hand.")
        public boolean noOffhandUseWithFood = false;

        @Comment("This prevents fireworks from being launched from your offhand if you are wearing an Elytra, unless you're flying.")
        public boolean noOffhandFireworksWithElytra = true;

        @Comment("This prevents sweet berries from being placed unless the player is holding shift or placing them on dirt or farmland.")
        public boolean berryPlacementRequiresShiftOrDirt = false;
    }

    public static @Nullable ClientTweaksConfig getActiveOrNull() {
        return Balm.config().getActiveConfig(ClientTweaksConfig.class);
    }

    public static ClientTweaksConfig getActive() {
        return Objects.requireNonNull(Balm.config().getActiveConfig(ClientTweaksConfig.class));
    }

    public static void initialize() {
        Balm.config().registerConfig(ClientTweaksConfig.class);
    }

}
