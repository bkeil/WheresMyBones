package com.mouthpunch.wheresmybones;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MyBonesCommand implements CommandExecutor {

	private final WheresMyBones plugin;

	public MyBonesCommand(WheresMyBones wheresMyBones) {
		this.plugin = wheresMyBones;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (!(sender instanceof Player))
			return false;

		final Player player = (Player) sender;

		Iterator<Location> i = plugin.loadBonesLocations(player.getName())
				.iterator();

		if (!i.hasNext()) {
			sender.sendMessage("Your bones are lost.");
			return true;
		}

		if (args.length == 0) {
			listBones(player, i);
			return true;
		} else {
			return orientPlayer(player, args, i);
		}
	}

	private void listBones(Player sender, Iterator<Location> i) {
		if (!plugin.checkPermission(sender, "com.mouthpunch.bones.list")) {
			sender.sendMessage("You don't have permission to list bones.");
			return;
		}

		int j = 1;
		sender.sendMessage("Your bones:");
		while (i.hasNext()) {
			final Location l = i.next();
			sender.sendMessage((j++) + ". " + " (" + l.getBlockX() + ", "
					+ l.getBlockY() + ", " + l.getBlockZ() + ") in "
					+ l.getWorld().getName());
		}
	}

	private boolean orientPlayer(Player player, String[] args,
			Iterator<Location> it) {
		final int bone;
		try {
			bone = Integer.parseInt(args[0], 10);
		} catch (NumberFormatException e) {
			return false;
		}

		if (bone == 0) {
			player.sendMessage("There is no bone 0.");
			return true;
		}

		int i = 0;
		Location loc = null;
		while (i < bone) {
			if (it.hasNext()) {
				loc = it.next();
				++i;
			} else {
				player.sendMessage("I only know about " + i + " bone"
						+ (i == 1 ? "" : "s") + " for you");
				return true;
			}
		}

		loc = loc.clone();

		final List<String> argsList = Arrays.asList(args);
		final boolean tracking;

		if (argsList.indexOf("compass") != -1) {
			if (plugin.checkPermission(player, "com.mouthpunch.bones.track")) {
				player.setCompassTarget(loc);
				player.sendMessage("Your compass will lead the way to your"
						+ " bones.");
				tracking = true;
			} else {
				player.sendMessage("You don't have permission to track bones.");
				tracking = true;
			}
		} else {
			tracking = false;
		}

		if (!plugin.checkPermission(player, "com.mouthpunch.bones.orient")) {
			if (!tracking) {
				player.sendMessage("You don't have permission to orient"
						+ " yourself towards bones.");
			}
			return true;
		}

		final Vector n = loc.subtract(player.getEyeLocation()).toVector()
				.normalize();

		float pitch = (float) Math.toDegrees(Math.asin(n.getY()));
		pitch = -pitch;

		float yaw = (float) Math.toDegrees(Math.atan2(n.getZ(), n.getX()));
		yaw -= 90f;

		final Location playerLoc = player.getLocation();
		playerLoc.setPitch(pitch);
		playerLoc.setYaw(yaw);

		player.teleport(playerLoc);

		return true;
	}
}
