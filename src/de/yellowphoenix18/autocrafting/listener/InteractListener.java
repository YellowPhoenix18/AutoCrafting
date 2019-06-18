package de.yellowphoenix18.autocrafting.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.yellowphoenix18.autocrafting.utils.AutoCrafter;
import de.yellowphoenix18.autocrafting.utils.PluginUtils;

public class InteractListener implements Listener {

	@EventHandler
	public void on(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && (!player.isSneaking() || player.getInventory().getItemInMainHand().getType() == Material.AIR)) {
			if(event.getClickedBlock().getType() == Material.CRAFTING_TABLE) {
				if(PluginUtils.autoCrafterManager.isAutoCrafter(event.getClickedBlock().getLocation())) {
					event.setCancelled(true);
					
					AutoCrafter crafter = PluginUtils.autoCrafterManager.getCrafter(event.getClickedBlock().getLocation());
					Inventory inv = Bukkit.createInventory(null, 9, "§6AutoCrafter§eInterface §b" + crafter.getId());
					
					inv.setItem(0, getRunningItem(crafter));
					inv.setItem(4, getItem(crafter));
					
					player.openInventory(inv);
				}
			}
		}
	}
	
	@EventHandler
	public void on(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		
		if(event.getView().getTitle().startsWith("§6AutoCrafter§eInterface §b")) {
			AutoCrafter crafter = PluginUtils.autoCrafterManager.getCrafter(Integer.parseInt(event.getView().getTitle().replace("§6AutoCrafter§eInterface §b", "")));
			if(event.getCurrentItem() != null) {
				ItemStack item = event.getCurrentItem();
				if(item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
					if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§aRunning")) {
						event.setCancelled(true);
						crafter.setRunning(false);
						event.getInventory().setItem(0, getRunningItem(crafter));
						player.updateInventory();
					} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§cDisabled")) {
						event.setCancelled(true);
						crafter.setRunning(true);
						event.getInventory().setItem(0, getRunningItem(crafter));
						player.updateInventory();
					} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§eItem")) {
						event.setCancelled(true);
						if(player.getItemOnCursor().getType() != Material.AIR) {
							ItemStack crafterItem = new ItemStack(player.getItemOnCursor().getType());
							crafter.setItem(crafterItem);
							event.getInventory().setItem(4, getItem(crafter));
							player.updateInventory();
						}
					}
				}
			}
		}
	}
	
	public ItemStack getRunningItem(AutoCrafter crafter) {
		List<String> runningLore = new ArrayList<String>();
		runningLore.add("§7Set the status of the");
		runningLore.add("§7Auto-Crafter");
		if(crafter.isRunning()) {
			return PluginUtils.createItemStack("§aRunning", Material.GREEN_WOOL, runningLore);
		} else {
			return PluginUtils.createItemStack("§cDisabled", Material.RED_WOOL, runningLore);
		}		
	}
	
	public ItemStack getItem(AutoCrafter crafter) {
		List<String> itemLore = new ArrayList<String>();
		itemLore.add("§7Set item, which should be");
		itemLore.add("§7automatically crafted");	
		if(crafter.getItem() != null) {
			return PluginUtils.createItemStack("§eItem", crafter.getItem().getType(), itemLore);
		} else {
			return PluginUtils.createItemStack("§eItem", Material.BLACK_STAINED_GLASS, itemLore);
		}		
	}
	
}
