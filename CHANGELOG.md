- Hotfixed faces being culled below smaller blocks when `creativeBreakingSupport` was enabled

---

- Restructured the config file
  - You will have to reconfigure your tweaks, as all options but the `Customization` ones have moved elsewhere.
  - This was overdue with how much the file has grown, and results in a much nicer configuration screen as well, with tweaks now being grouped under descriptive categories.
- Added `creativeBreakingSupport` tweak that expands hitboxes of randomly offset blocks for easier breaking while in Creative Mode
- Added `chainBuildingSupport` tweak that expands hitboxes of chains while holding chain items for easier placement
- Added `Mine Single Block` key that, when held, will only allow breaking a single block until it's let go
- Added creative menu tweaks for clearing the search bar and retaining its content when reopening
- Added recipe book tweaks for clearing the search bar, retaining its content when reopening, and clicking ghost items to navigate to their recipes
- Added `logStrippingRequiresShift` tweak for requiring shift to be held to strip logs
- Added `berryPlacementRequiresShiftOrDirt` tweak to prevent Sweet Berries from being placed unless it's on Dirt or shift is being held
- Added `hideShieldUnlessBlocking` tweak to hide the shield unless the player is actively blocking
- Added `hideHands` (with optional keybind) and `hideEmptyMainHand` tweaks to hide hands in First Person view
- Fixed `Out of Torches` showing up even when another tweak would have canceled the torch placement anyways
- Fixed some Mixins being loaded on servers too, despite this mod being client-only
- Fixed crash if early setup accesses block shapes before Minecraft instance is set
- Fixed master and music sliders not resetting properly on resize

