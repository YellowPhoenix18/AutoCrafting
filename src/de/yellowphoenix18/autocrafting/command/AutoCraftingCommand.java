package de.yellowphoenix18.autocrafting.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AutoCraftingCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0) {
				
			} else {
				if(args[0].equalsIgnoreCase("give")) {
					if(player.hasPermission("autocrafting.give")) {
						ItemStack item = new ItemStack(Material.CRAFTING_TABLE);
						ItemMeta imeta = item.getItemMeta();
						imeta.setDisplayName("§6Auto§eCrafting");
						item.setItemMeta(imeta);
						player.getInventory().addItem(item);
					}
				} else {
					
				}
			}
		}	
		return true;
	}

}
