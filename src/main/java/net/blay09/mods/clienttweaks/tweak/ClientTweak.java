package net.blay09.mods.clienttweaks.tweak;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import javax.annotation.Nullable;

public abstract class ClientTweak {
	private String name;
	private boolean enabled = true;
	private KeyBinding keyBinding;

	public ClientTweak(String name) {
		this.name = name;
	}

	public void init(FMLInitializationEvent event) {}

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

	@Nullable
	public final KeyBinding registerToggleKeybind() {
		keyBinding = createToggleKeybind();
		if(keyBinding != null) {
			ClientRegistry.registerKeyBinding(keyBinding);
		}
		return keyBinding;
	}
}
