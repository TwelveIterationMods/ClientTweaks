package net.blay09.mods.clienttweaks.tweak;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public abstract class ClientTweak {
	private String name;
	private boolean enabled = true;

	public ClientTweak(String name) {
		this.name = name;
	}

	public void init(FMLInitializationEvent event) {}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public boolean isEnabledDefault() {
		return false;
	}

	public abstract String getDescription();
}
