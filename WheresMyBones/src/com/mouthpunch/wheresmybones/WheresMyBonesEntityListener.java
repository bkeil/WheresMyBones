package com.mouthpunch.wheresmybones;

import org.bukkit.entity.Entity;
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
		if (entity instanceof Player) {
			plugin.storeBonesLocation(((Player) entity).getName(),
					entity.getLocation());
		}
	}
}
