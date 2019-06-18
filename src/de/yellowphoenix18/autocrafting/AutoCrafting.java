package de.yellowphoenix18.autocrafting;

import org.bukkit.plugin.java.JavaPlugin;

import de.yellowphoenix18.autocrafting.utils.PluginUtils;

public class AutoCrafting extends JavaPlugin {
	
	public static AutoCrafting m;
	public static String name = "§7[§6Auto§eCrafting§7] §7";
	
	public void onEnable() {
		m = this;
		PluginUtils.setUp();
	}
	
	public void onDisable() {
		PluginUtils.autoCrafterManager.saveCrafters();
	}

}
