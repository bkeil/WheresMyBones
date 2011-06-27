package com.mouthpunch.wheresmybones;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

public class WheresMyBonesEntityListener extends EntityListener {

	private final WheresMyBones plugin;

	public WheresMyBonesEntityListener(WheresMyBones plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onEntityDeath(EntityDeathEvent event) {

		final Entity entity = event.getEntity();
		final Location location = entity.getLocation();

		final String name;
		if (entity instanceof HumanEntity) {
			name = ((HumanEntity) entity).getName();
		} else {
			name = entity.getClass().getSimpleName();
		}

		if (entity instanceof Player) {
			plugin.storeBonesLocation(((Player) entity).getName(),
					entity.getLocation());
		}

		plugin.getServer().broadcastMessage(
				name + " died in " + location.getWorld().getName() + " at ("
						+ location.getBlockX() + ", " + location.getBlockY()
						+ ", " + location.getBlockZ() + ")");
	}
}
