package net.blay09.mods.clienttweaks.tweak;

import net.minecraftforge.common.MinecraftForge;

public abstract class AbstractClientTweak {
    private final String name;

    public AbstractClientTweak(String name) {
        this.name = name;

        MinecraftForge.EVENT_BUS.register(this);
    }

    public abstract boolean isEnabled();

    public abstract void setEnabled(boolean enabled);

    public final String getName() {
        return name;
    }

    public boolean hasKeyBinding() {
        return false;
    }

}
