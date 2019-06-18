package de.yellowphoenix18.autocrafting.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BlockCompare {
	
	public static boolean isWoodPlanks(Material material) {
		if(getWoodPlanks().contains(material)) {
			return true;
		}
		return false;
	}
	
	public static List<Material> getWoodPlanks() {
		List<Material> materials = new ArrayList<Material>();
		materials.add(Material.ACACIA_PLANKS);
		materials.add(Material.BIRCH_PLANKS);
		materials.add(Material.DARK_OAK_PLANKS);
		materials.add(Material.JUNGLE_PLANKS);
		materials.add(Material.OAK_PLANKS);
		materials.add(Material.SPRUCE_PLANKS);
		return materials;
	}
	
	public static boolean isWoodLog(Material material) {
		if(getWoodLog().contains(material)) {
			return true;
		}
		return false;
	}
	
	public static List<Material> getWoodLog() {
		List<Material> materials = new ArrayList<Material>();
		materials.add(Material.ACACIA_LOG);
		materials.add(Material.BIRCH_LOG);
		materials.add(Material.DARK_OAK_LOG);
		materials.add(Material.JUNGLE_LOG);
		materials.add(Material.OAK_LOG);
		materials.add(Material.SPRUCE_LOG);
		return materials;
	}
	
	public static boolean isWoodWood(Material material) {
		if(getWoodLog().contains(material)) {
			return true;
		}
		return false;
	}
	
	public static List<Material> getWoodWood() {
		List<Material> materials = new ArrayList<Material>();
		materials.add(Material.ACACIA_WOOD);
		materials.add(Material.BIRCH_WOOD);
		materials.add(Material.DARK_OAK_WOOD);
		materials.add(Material.JUNGLE_WOOD);
		materials.add(Material.OAK_WOOD);
		materials.add(Material.SPRUCE_WOOD);
		return materials;
	}
	
	public static List<Material> getMaterialList(Material material) {
		if(isWoodPlanks(material)) {
			return getWoodPlanks();
		} else if(isWoodLog(material)) {
			return getWoodLog();
		} else if(isWoodWood(material)) {
			return getWoodWood();
		}
		List<Material> materials = new ArrayList<Material>();
		materials.add(material);
		return materials;
	}
	
	public static boolean containsInventoryItems(Inventory inv, Material material, int amount) {
		int count = amount;
		List<Material> materials = getMaterialList(material);
		for(Material mat : materials) {
		    for (ItemStack is : inv.getContents()) {
		        if (is != null && is.getType() == mat) {
		            count -= is.getAmount();
		            if (count <= 0) {
		                return true;
		            }
		        }
		    }
		}
		return false;
	}
	
	public static void removeInventoryItems(Inventory inv, Material material, int amount) {
		List<Material> materials = getMaterialList(material);
		for (ItemStack itemStack : inv.getContents()) {
			if(amount <= 0) {
				break;
			}
		    for(Material mat : materials) {
		        if (itemStack != null && itemStack.getType() == mat) {
		            int itemAmount = itemStack.getAmount() - amount;
		            amount -= itemStack.getAmount();
		            if (itemAmount > 0) {
		                itemStack.setAmount(itemAmount);
		                break;
		            } else {
		                inv.removeItem(itemStack);
		                if (amount <= 0) {
		                	break;
		                }
		            }
		        }
		    }
		}
	}

}
