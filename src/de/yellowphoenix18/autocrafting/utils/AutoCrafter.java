package de.yellowphoenix18.autocrafting.utils;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class AutoCrafter {
	
	private Location loc;
	private ItemStack item;
	private boolean running;
	private int id;
	
	public AutoCrafter(Location loc, ItemStack item, int id) {
		this.loc = loc;
		this.item = item;
		this.running = false;
		this.id = id;
	}
	
	public AutoCrafter(Location loc, ItemStack item, int id, boolean running) {
		this.loc = loc;
		this.item = item;
		this.running = false;
		this.id = id;
		this.running = running;
	}
	
	public void craftItem() {
		if(!this.running) {
			return;
		}
		if(this.item == null) {
			return;
		}
		Recipe recipe = this.getPossibleRecipe(this.item);
		if(recipe == null) {
			return;
		}
		HashMap<Material, Integer> needed = this.getItemAmount(recipe);
		for(Material material : needed.keySet()) {
			this.removeItemFromHopper(material, needed.get(material));
		}
		
		Location locUnder = new Location(this.loc.getWorld(), this.loc.getX(), this.loc.getY()-1, this.loc.getZ());
		if(locUnder.getBlock().getType() == Material.HOPPER) {
			Hopper hopper = (Hopper) locUnder.getBlock().getState();
			hopper.getInventory().addItem(this.item);
		} else {
			this.loc.getWorld().dropItemNaturally(this.loc, this.item);
		}
	}
	
	public Recipe getPossibleRecipe(ItemStack item) {
		if(item != null) {
			List<Recipe> recipes = Bukkit.getRecipesFor(item);
			
			for(Recipe recipe : recipes) {
				if(recipe instanceof ShapedRecipe || recipe instanceof ShapelessRecipe) { // Check if craftable Recipe
					boolean possibleRecipe = true;
					HashMap<Material, Integer> needed = this.getItemAmount(recipe);
					
					// Check items in hopper
					for(Material material : needed.keySet()) {
						if(!this.isItemInHopper(material, needed.get(material))) {
							possibleRecipe = false;
						}
					}
					
					// Return recipe if possible
					if(possibleRecipe) {
						return recipe;
					}	
				}
			}
		}	
		return null;
	}
	
	public HashMap<Material, Integer> getItemAmount(Recipe recipe) {
		HashMap<Material, Integer> needed = new HashMap<Material, Integer>();
		if(recipe instanceof ShapedRecipe) { // Recipe needs special shape
			ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
			HashMap<Character, ItemStack> ingredients = (HashMap<Character, ItemStack>) shapedRecipe.getIngredientMap();
			
			for (char c : ingredients.keySet()) {						
		        ItemStack stack = ingredients.get(c);
		        if (stack != null) {
		        	if(needed.containsKey(stack.getType())) {
		        		needed.put(stack.getType(), needed.get(stack.getType())+1);
		        	} else {
		        		needed.put(stack.getType(), 1);
		        	}
		        }
		    }
		} else { // Recipe is without any shape
			ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
			List<ItemStack> items = shapelessRecipe.getIngredientList();
			
			for(ItemStack ingredient : items) {
		        if (ingredient != null) {
		        	if(needed.containsKey(ingredient.getType())) {
		        		needed.put(ingredient.getType(), needed.get(ingredient.getType())+1);
		        	} else {
		        		needed.put(ingredient.getType(), 1);
		        	}
		        }
			}	
		}
		return needed;
	}
	
	public boolean isItemInHopper(Material material, int amount) {
		Hopper hopper = this.inputHopper(this.loc);
		if(hopper != null) {
			Inventory inv = hopper.getInventory();
			if(BlockCompare.containsInventoryItems(inv, material, amount)) {			
				return true;
			}
		}
		return false;
	}
	
	public void removeItemFromHopper(Material material, int amount) {
		Hopper hopper = this.inputHopper(this.loc);
		if(hopper != null) {
			Inventory inv = hopper.getInventory();
			BlockCompare.removeInventoryItems(inv, material, amount);
		}
	}
	
	public Hopper inputHopper(Location loc) {
		Location locUpper = new Location(loc.getWorld(), loc.getX(), loc.getY()+1, loc.getZ());
		Location locWest = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ());
		Location locEast = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ());
		Location locSouth = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()-1);
		Location locNorth = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()+1);
		
		if(locUpper.getBlock().getType() == Material.HOPPER) {
			return (Hopper) locUpper.getBlock().getState();
		} else if(locSouth.getBlock().getType() == Material.HOPPER) {
			return (Hopper) locSouth.getBlock().getState();
		} else if(locNorth.getBlock().getType() == Material.HOPPER) {
			return (Hopper) locNorth.getBlock().getState();
		} else if(locEast.getBlock().getType() == Material.HOPPER) {
			return (Hopper) locEast.getBlock().getState();
		} else if(locWest.getBlock().getType() == Material.HOPPER) {
			return (Hopper) locWest.getBlock().getState();
		}	
		return null;
	}
	
	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
