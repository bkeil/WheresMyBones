package com.mouthpunch.wheresmybones;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class WheresMyBones extends JavaPlugin {

	private volatile PermissionHandler permissionHandler;

	private final Map<String, Locations> store = new HashMap<String, Locations>();

	public boolean checkPermission(Player player, String permission) {
		return (permissionHandler == null) ? player.isOp() : permissionHandler
				.has(player, permission);
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

		setUpPermissions();
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

	private void setUpPermissions() {
		if (permissionHandler != null) {
			return;
		}

		Plugin permissionsPlugin = this.getServer().getPluginManager()
				.getPlugin("Permissions");

		if (permissionsPlugin == null) {
			System.out
					.println("Permission system not detected, defaulting to OP");
			return;
		}

		permissionHandler = ((Permissions) permissionsPlugin).getHandler();
		System.out.println("Found and will use plugin "
				+ permissionsPlugin.getDescription().getFullName());
	}
}
