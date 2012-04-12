package com.mouthpunch.wheresmybones;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class WheresMyBones extends JavaPlugin {

	private static final String STORAGE = "storage";
	private static final String NOTIFICATION_AVAILABILTY_MESSAGE = "notification.availabilty.message";
	private static final String NOTIFICATION_AVAILABILTY_ACTIVE = "notification.availabilty.active";
	private static final String USE_PERMISSIONS = "usePermissions";

	private LocationStore store;

	public boolean checkPermission(Player player, String permission) {
		return (!getConfig().getBoolean(USE_PERMISSIONS)) || player.hasPermission(permission);
	}

	public String getAvailibilityNotification() {
		return getConfig().getString(NOTIFICATION_AVAILABILTY_MESSAGE);
	}

	public boolean isNotifyingAvailability() {
		return getConfig().getBoolean(NOTIFICATION_AVAILABILTY_ACTIVE);
	}

	public Locations loadBonesLocations(String playerName) {
		return store.getLocations(playerName);
	}

	public void loadConfiguration() {
		getConfig().addDefault(STORAGE, "memory");
		getConfig().addDefault(USE_PERMISSIONS, true);
		getConfig().addDefault(NOTIFICATION_AVAILABILTY_ACTIVE, false);
		getConfig().addDefault(NOTIFICATION_AVAILABILTY_MESSAGE, "You can find your belongings using /mybones");
		getConfig().options().copyDefaults(true);
		saveConfig();

		if (getConfig().getString(STORAGE).equalsIgnoreCase("config")) {
			getLogger().info("Where's My Bones: Using config file for storage");
			store = new ConfigLocationStore();
		} else {
			getLogger().info("Where's My Bones: Using memory for storage");
			store = new MemoryLocationStore();
		}

		store.load(this);
	}

	public void onDisable() {
		getLogger().info("Where's my bones, checking out!");
		store.save((Plugin) this);
	}

	public void onEnable() {

		loadConfiguration();

		new WheresMyBonesEntityListener(this);

		getCommand("mybones").setExecutor(new MyBonesCommand(this));

		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

	public void storeBonesLocation(String playerName, Location location) {
		store.note(playerName, location);
	}
}
