package de.yellowphoenix18.autocrafting.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;

import de.yellowphoenix18.autocrafting.AutoCrafting;
import de.yellowphoenix18.autocrafting.command.AutoCraftingCommand;
import de.yellowphoenix18.autocrafting.listener.BlockListener;
import de.yellowphoenix18.autocrafting.listener.InteractListener;

public class PluginUtils {
	
	public static AutoCrafterManager autoCrafterManager;
	
	public static void setUp() {
		loadConfigs();
		loadListener();
		loadCommands();
		runAutoCrafting();
	}

	public static void loadListener() {
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new BlockListener(), AutoCrafting.m);
		pluginManager.registerEvents(new InteractListener(), AutoCrafting.m);
	}
	
	public static void loadCommands() {
		AutoCrafting.m.getCommand("ac").setExecutor(new AutoCraftingCommand());
		AutoCrafting.m.getCommand("autocrafting").setExecutor(new AutoCraftingCommand());		
	}
	
	public static void loadConfigs() {
		autoCrafterManager = new AutoCrafterManager();
	}
	
	public static ItemStack createItemStack(String title, Material m, List<String> lore) {
		ItemStack item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		if(title != null) {
			meta.setDisplayName(title);
		}
		if(lore != null) {
			meta.setLore(lore);
		}
		item.setItemMeta(meta);
		return item;
	}
	
	public static void runAutoCrafting() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(AutoCrafting.m, new Runnable() {
			@Override
			public void run() {
				PluginUtils.autoCrafterManager.runAutocraft();
			}		
		}, 15, 20);
	}
	
}
