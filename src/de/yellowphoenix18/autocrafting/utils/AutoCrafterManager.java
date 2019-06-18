package de.yellowphoenix18.autocrafting.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import de.yellowphoenix18.autocrafting.config.AutoCrafterConfig;

public class AutoCrafterManager {
	
	private List<AutoCrafter> crafters;
	private AutoCrafterConfig config;
	
	public AutoCrafterManager() {
		this.crafters = new ArrayList<AutoCrafter>();
		this.config = new AutoCrafterConfig("crafters.yml");
		
		for(int id : this.config.getAutoCrafterIds()) {
			this.crafters.add(this.config.getAutoCrafter(id));
		}
	}
	
	public void saveCrafters() {
		for(AutoCrafter crafter : this.crafters) {
			System.out.println("Check Crafters");
			this.config.updateAutoCrafter(crafter);
		}
	}
	
	public void runAutocraft() {
		for(AutoCrafter crafter : this.crafters) {
			crafter.craftItem();
		}
	}
	
	public void addCrafter(Location loc) {
		AutoCrafter crafter = new AutoCrafter(loc, null, 0);
		this.config.addAutoCrafter(crafter);
		this.crafters.add(crafter);		
	}
	
	public void removeCrafter(Location loc) {
		AutoCrafter crafter = this.getCrafter(loc);
		this.config.removeAutoCrafter(crafter.getId());
		this.crafters.remove(crafter);
	}
	
	public AutoCrafter getCrafter(Location loc) {
		for(AutoCrafter crafter : this.crafters) {
			if(crafter.getLoc().equals(loc)) {
				return crafter;
			}
		}
		return null;
	}
	
	public AutoCrafter getCrafter(int id) {
		for(AutoCrafter crafter : this.crafters) {
			if(crafter.getId() == id) {
				return crafter;
			}
		}
		return null;
	}
	
	public boolean isAutoCrafter(Location loc) {
		for(AutoCrafter crafter : this.crafters) {
			if(crafter.getLoc().equals(loc)) {
				return true;
			}
		}
		return false;
	}
	

}
