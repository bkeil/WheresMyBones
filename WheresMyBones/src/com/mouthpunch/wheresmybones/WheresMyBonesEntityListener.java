package com.mouthpunch.wheresmybones;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class WheresMyBonesEntityListener implements Listener {

	private final WheresMyBones plugin;

	public WheresMyBonesEntityListener(WheresMyBones plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDeath(EntityDeathEvent event) {
		final Entity entity = event.getEntity();
		if (entity instanceof Player) {
			final Player player = (Player) entity;
			if (plugin.isNotifyingAvailability()) {
				player.sendMessage(plugin.getAvailibilityNotification());
			}
			plugin.storeBonesLocation(player.getName(), entity.getLocation());
		}
	}
}
