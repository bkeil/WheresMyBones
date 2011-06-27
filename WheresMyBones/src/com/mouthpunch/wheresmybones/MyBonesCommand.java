package com.mouthpunch.wheresmybones;

import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

		Iterator<Location> i = plugin.getLocations(player.getName()).iterator();

		if (i.hasNext()) {
			int j = 1;
			sender.sendMessage("Your bones:");
			while (i.hasNext()) {
				final Location l = i.next();
				sender.sendMessage((j++) + ". " + " (" + l.getBlockX() + ", "
						+ l.getBlockY() + ", " + l.getBlockZ() + ") in "
						+ l.getWorld().getName());
			}
		} else {
			sender.sendMessage("Your bones are lost.");
		}
		return true;
	}
}
