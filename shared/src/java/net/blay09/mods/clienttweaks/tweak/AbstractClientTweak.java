package net.blay09.mods.clienttweaks.tweak;

public abstract class AbstractClientTweak {
    private final String name;

    public AbstractClientTweak(String name) {
        this.name = name;
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
