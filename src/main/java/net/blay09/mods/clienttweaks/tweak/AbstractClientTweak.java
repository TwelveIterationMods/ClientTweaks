package net.blay09.mods.clienttweaks.tweak;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;

public abstract class AbstractClientTweak {
    private final String name;
    private final ForgeConfigSpec.BooleanValue configProperty;

    public AbstractClientTweak(String name, ForgeConfigSpec.BooleanValue configProperty) {
        this.name = name;
        this.configProperty = configProperty;

        MinecraftForge.EVENT_BUS.register(this);
    }

    public final boolean isEnabled() {
        return configProperty.get();
    }

    public final void setEnabled(boolean enabled) {
        configProperty.set(enabled);
    }

    public final String getName() {
        return name;
    }

    public boolean hasKeyBinding() {
        return false;
    }

}
