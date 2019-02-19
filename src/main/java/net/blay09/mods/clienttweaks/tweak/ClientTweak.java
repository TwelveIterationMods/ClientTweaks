package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;

public abstract class ClientTweak {
    private final String name;
    private boolean enabled = true;
    protected Minecraft mc;
    private KeyBinding keyBinding;

    public ClientTweak(String name) {
        this.name = name;

        if (registerAsEventHandler()) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    public final void init(FMLClientSetupEvent event) {
        mc = event.getMinecraftSupplier().get();
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public final String getName() {
        return name;
    }

    public boolean isEnabledDefault() {
        return false;
    }

    @Nullable
    protected KeyBinding createToggleKeybind() {
        return null;
    }

    public final KeyBinding getKeyBinding() {
        return keyBinding;
    }

    public abstract String getDescription();

    public boolean registerAsEventHandler() {
        return true;
    }

    @Nullable
    public final KeyBinding registerToggleKeybind() {
        keyBinding = createToggleKeybind();
        if (keyBinding != null) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
        return keyBinding;
    }
}
