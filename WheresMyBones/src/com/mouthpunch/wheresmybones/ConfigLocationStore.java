package com.mouthpunch.wheresmybones;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.plugin.Plugin;

public class ConfigLocationStore implements LocationStore {
	private final MemoryLocationStore delegate = new MemoryLocationStore();

	public Locations getLocations(String playerName) {
		return delegate.getLocations(playerName);
	}

	public void load(Plugin plugin) {
		final MemorySection config = plugin.getConfig();
		final ConfigurationSection locations = config.getConfigurationSection("locations");

		if (locations == null)
			return;
		
		final ConfigurationSection worlds = locations.getConfigurationSection("worlds");
		final ConfigurationSection coords = locations.getConfigurationSection("coords");
		final ConfigurationSection orients = locations.getConfigurationSection("orients");

		for (String playerName : worlds.getKeys(false)) {
			List<String> pWorlds = worlds.getStringList(playerName);
			List<Double> pCoords = coords.getDoubleList(playerName);
			List<Float> pOrients = orients.getFloatList(playerName);
			for (int i = 0; i < pWorlds.size(); ++i) {
				try {
					World w = plugin.getServer().getWorld(UUID.fromString(pWorlds.get(i)));
					double x = pCoords.get(i * 3), y = pCoords.get(i * 3 + 1), z = pCoords.get(i * 3 + 2);
					float pitch = pOrients.get(i * 2), yaw = pOrients.get(i * 2 + 1);
					delegate.getLocations(playerName).add(new Location(w, x, y, z, yaw, pitch));
				} catch (Exception e) {
					plugin.getLogger().log(Level.WARNING, "There was an error loading a location for " + playerName, e);
				}
			}
		}
	}

	public void note(String playerName, Location location) {
		delegate.note(playerName, location);
	}

	public void save(Plugin plugin) {
		final MemorySection config = plugin.getConfig();
		for (String playerName : delegate.getPlayerLocations().keySet()) {
			final List<String> worlds = new ArrayList<String>(5);
			final List<Double> coords = new ArrayList<Double>(15);
			final List<Float> orients = new ArrayList<Float>(10);
			for (Location loc : delegate.getLocations(playerName)) {
				worlds.add(loc.getWorld().getUID().toString());
				coords.add(loc.getX());
				coords.add(loc.getY());
				coords.add(loc.getZ());
				orients.add(loc.getPitch());
				orients.add(loc.getYaw());
			}
			config.set("locations.worlds." + playerName, worlds);
			config.set("locations.coords." + playerName, coords);
			config.set("locations.orients." + playerName, orients);
		}
		plugin.saveConfig();
	}
}
