Note: The config file has been restructured to account for the growing number of tweaks. You will need to reconfigure your tweaks.

- Added `alwaysPrioritizeExperienceBar` tweak with keybind
- Added `hideShieldUnlessBlocking` option
- Added `is_torch`, `is_tool`, `is_food`, `is_firework`, `is_weapon` and `requires_shift_to_mine` rules for use with [Shogi](https://www.curseforge.com/minecraft/mc-mods/shogi)
- Removed some legacy config options and exposed them as Shogi rules instead
  - The options to define additional trigger items came from a time before Mojang introduced Item Components. Nowadays item functionalities are a lot more standardized, so in most cases, they are no longer needed.
  - For edge cases that are not covered by the defaults, Shogi can be used via `config/clienttweaks.rules.json`. See the Shogi documentation for more information.
- Fixed `hideShieldUnlessWeapon` testing player components instead of item components
