package de.yellowphoenix18.autocrafting.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	
	public File file;
	public FileConfiguration cfg;
	
	public Config(File file) {
		this.file = file;
		this.cfg = YamlConfiguration.loadConfiguration(file);
	}
	
	public Object setDefault(String path, Object obj) {
		if(this.contains(path)) {
			return this.get(path);
		} else {
			this.set(path, obj);
			return obj;
		}
	}
	
	public boolean contains(String path) {
		return this.cfg.contains(path);
	}
	
	public void set(String path, Object obj) {
		this.cfg.set(path, obj);
		this.save();
	}
	
	public Object get(String path) {
		return this.cfg.get(path);
	}
	
	private void save() {
		try {
			this.cfg.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
