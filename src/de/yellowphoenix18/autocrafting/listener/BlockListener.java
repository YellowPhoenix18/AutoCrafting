package de.yellowphoenix18.autocrafting.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import de.yellowphoenix18.autocrafting.AutoCrafting;
import de.yellowphoenix18.autocrafting.utils.PluginUtils;

public class BlockListener implements Listener {
	
	@EventHandler
	public void on(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		
		if(event.getBlock().getType() == Material.CRAFTING_TABLE) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§6Auto§eCrafting")) {
				PluginUtils.autoCrafterManager.addCrafter(event.getBlock().getLocation());
				player.sendMessage(AutoCrafting.name + "§6Auto§eCrafting§7-§6Table §7has been §acreated");
			}
		}
	}
	
	@EventHandler
	public void on(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		if(event.getBlock().getType() == Material.CRAFTING_TABLE) {
			if(PluginUtils.autoCrafterManager.isAutoCrafter(event.getBlock().getLocation())) {
				PluginUtils.autoCrafterManager.removeCrafter(event.getBlock().getLocation());
				player.sendMessage(AutoCrafting.name + "§6Auto§eCrafting§7-§6Table §7has been §cremoved");
			}
		}
	}

}
