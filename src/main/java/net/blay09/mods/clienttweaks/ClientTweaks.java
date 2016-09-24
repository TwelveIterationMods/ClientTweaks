package net.blay09.mods.clienttweaks;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.blay09.mods.clienttweaks.tweak.AutoJumpMoreLikeAutoDumbAmirite;
import net.blay09.mods.clienttweaks.tweak.HideOwnEffectParticles;
import net.blay09.mods.clienttweaks.tweak.NoOffhandTorchWithBlock;
import net.blay09.mods.clienttweaks.tweak.ClientTweak;
import net.blay09.mods.clienttweaks.tweak.MasterVolumeSlider;
import net.blay09.mods.clienttweaks.tweak.NoOffhandTorchWithEmptyHand;
import net.blay09.mods.clienttweaks.tweak.OffhandTorchWithToolOnly;
import net.blay09.mods.clienttweaks.tweak.Screw3dAnaglyph;
import net.blay09.mods.clienttweaks.tweak.UnderlineLooksTerribleInChat;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;
import java.util.Set;

@Mod(modid = ClientTweaks.MOD_ID, name = "ClientTweaks", acceptedMinecraftVersions = "[1.9.4]", clientSideOnly = true,
		guiFactory = "net.blay09.mods.clienttweaks.GuiFactory")
public class ClientTweaks {

	public static final String MOD_ID = "clienttweaks";

	@Mod.Instance
	public static ClientTweaks instance;

	public static Configuration config;

	private static Map<String, ClientTweak> tweaks = Maps.newHashMap();
	public static Set<String> torchItems = Sets.newHashSet();

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		registerTweak(new Screw3dAnaglyph());
		registerTweak(new AutoJumpMoreLikeAutoDumbAmirite());
		registerTweak(new MasterVolumeSlider());
		registerTweak(new UnderlineLooksTerribleInChat());
		registerTweak(new NoOffhandTorchWithBlock());
		registerTweak(new NoOffhandTorchWithEmptyHand());
		registerTweak(new OffhandTorchWithToolOnly());
		registerTweak(new HideOwnEffectParticles());

		config = new Configuration(event.getSuggestedConfigurationFile());
		reloadConfig();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		for(ClientTweak tweak : tweaks.values()) {
			tweak.init(event);
		}
	}

	private void reloadConfig() {
		torchItems = Sets.newHashSet(config.getStringList("Torch Items", "general", new String[] {
				"minecraft:torch",
				"tconstruct:stone_torch"
		}, "Items that count as torches for the offhand-torch tweak options."));
		for(ClientTweak tweak : tweaks.values()) {
			config.getBoolean(tweak.getName(), "tweaks", tweak.isEnabledDefault(), tweak.getDescription());
		}
		if (config.hasChanged()) {
			config.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent event) {
		if (event.getModID().equals(MOD_ID)) {
			reloadConfig();
		}
	}

	private void registerTweak(ClientTweak tweak) {
		tweaks.put(tweak.getName(), tweak);
	}

}
