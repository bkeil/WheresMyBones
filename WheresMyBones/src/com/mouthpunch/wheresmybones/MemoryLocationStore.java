package com.mouthpunch.wheresmybones;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class MemoryLocationStore implements LocationStore {

	private final Map<String, Locations> playerLocations = new LinkedHashMap<String, Locations>();
	
	@Override
	public Locations getLocations(String playerName) {
		Locations l = playerLocations.get(playerName);
		if (l == null) {
			l = new Locations();
			playerLocations.put(playerName, l);
		}
		return l;
	}

	@Override
	public void load(Plugin plugin) {
		// Nothing to do.
	}

	@Override
	public void note(String playerName, Location location) {
		getLocations(playerName).add(location);
	}

	@Override
	public void save(Plugin plugin) {
		// Nothing to do		
	}

	Map<String, Locations> getPlayerLocations() {
		return playerLocations;
	}
}
