package com.mouthpunch.wheresmybones;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WheresMyBones extends JavaPlugin {
	private final WheresMyBonesEntityListener entityListener = new WheresMyBonesEntityListener(
			this);
	
	private final Map<String, Locations> store = new HashMap<String, Locations>(); 

	public Locations getLocations(String playerName) {
		return getLocationsInternal(playerName);
	}

	public void onDisable() {
		System.out.println("Where's my bones, checking out!");
	}
	
	public void onEnable() {

		final PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener,
				Priority.Monitor, this);

		getCommand("mybones").setExecutor(new MyBonesCommand(this));

		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version "
				+ pdfFile.getVersion() + " is enabled!");
	}

	public void storeBonesLocation(String playerName, Location location) {
		getLocationsInternal(playerName).add(location);
	}
	
	private Locations getLocationsInternal(String playerName) {
		Locations locations = store.get(playerName);
		if (locations == null) {
			locations = new Locations();
			store.put(playerName, locations);
		}
		return locations;
	}
}
