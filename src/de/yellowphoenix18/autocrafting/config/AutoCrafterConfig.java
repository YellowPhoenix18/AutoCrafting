package de.yellowphoenix18.autocrafting.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import de.yellowphoenix18.autocrafting.utils.AutoCrafter;

public class AutoCrafterConfig extends Config {
	
	public int autoCount = 0;

	public AutoCrafterConfig(String name) {
		super(new File("plugins/AutoCrafter", name));
		this.autoCount = (int) this.setDefault("setup.counter", autoCount);
	}
	
	public void addAutoCrafter(AutoCrafter crafter) {
		int id = this.updateAutoCount();
		setLocation("autocrafter." + id + ".location", crafter.getLoc());
		this.set("autocrafter." + id + ".item", crafter.getItem());
		this.set("autocrafter." + id + ".status", crafter.isRunning());
		crafter.setId(id);
	}
	
	public AutoCrafter getAutoCrafter(int id) {
		Location loc = this.loadLocation("autocrafter." + id + ".location");
		ItemStack item = (ItemStack) this.get("autocrafter." + id + ".item");
		boolean status = (boolean) this.get("autocrafter." + id + ".status");
		
		return new AutoCrafter(loc, item, id, status);
	}
	
	public void updateAutoCrafter(AutoCrafter crafter) {
		this.set("autocrafter." + crafter.getId() + ".item", crafter.getItem());
		this.set("autocrafter." + crafter.getId() + ".status", crafter.isRunning());
	}
	
	public void removeAutoCrafter(int id) {
		this.set("autocrafters." + id, null);
	}
	
	public List<Integer> getAutoCrafterIds() {
		List<Integer> ids = new ArrayList<Integer>();
		if(this.contains("autocrafter")) {
			for(String key : this.cfg.getConfigurationSection("autocrafter").getKeys(false)) {
				ids.add(Integer.parseInt(key));
			}
		}
		return ids;
	}
	
	/** ###############
	 *  Private Methods
	 *  ###############
	 */
	
	private int updateAutoCount() {
		this.autoCount++;
		this.set("setup.counter", this.autoCount);	
		return autoCount;
	}
	
	private void setLocation(String path, Location loc) {
		this.set(path + ".world", loc.getWorld().getName());
		this.set(path + ".x", loc.getX());
		this.set(path + ".y", loc.getY());
		this.set(path + ".z", loc.getZ());
	}
	
	private Location loadLocation(String path) {
		if(this.contains(path)) {
			World world = Bukkit.getWorld((String) this.get(path + ".world"));
			double x = (double) this.get(path + ".x");
			double y = (double) this.get(path + ".y");
			double z = (double) this.get(path + ".z");
			
			return new Location(world, x, y, z);
		}
		return null;
	}

}
