package com.mouthpunch.wheresmybones;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.bukkit.Location;

public class Locations implements Iterable<Location> {

	private static final Object o = new Object();

	private final LocationCache cache = new LocationCache();

	public void add(Location location) {
		cache.put(location, o);
	}

	private static final class LocationCache extends
			LinkedHashMap<Location, Object> {
		private static final long serialVersionUID = 531776150988732942L;

		@Override
		protected boolean removeEldestEntry(Entry<Location, Object> arg0) {
			return size() > 5;
		}
	}

	@Override
	public Iterator<Location> iterator() {
		return cache.keySet().iterator();
	}
}
