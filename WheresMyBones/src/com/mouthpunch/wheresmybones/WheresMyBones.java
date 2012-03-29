package com.mouthpunch.wheresmybones;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class WheresMyBones extends JavaPlugin {

private final Map<String, Locations> store = new HashMap<String, Locations>();

	public boolean checkPermission(Player player, String permission) {
		return player.hasPermission(permission);
	}

	public Locations loadBonesLocations(String playerName) {
		return getLocationsInternal(playerName);
	}

	public void onDisable() {
		System.out.println("Where's my bones, checking out!");
	}

	public void onEnable() {
		
		new WheresMyBonesEntityListener(this);

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
