package com.mouthpunch.wheresmybones;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public interface LocationStore {
	Locations getLocations(String playerName);
	void load(Plugin plugin);
	void note(String playerName, Location location);
	void save(Plugin plugin);
}
