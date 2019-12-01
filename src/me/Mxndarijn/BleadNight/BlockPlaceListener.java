package me.Mxndarijn.BleadNight;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void BlockPlace(BlockPlaceEvent e) {
		if(e.getPlayer().hasPermission("BleadNight.spBandiet")) {
			if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§cBleadNight Spawnpoint Bandieten")) {
				Location loc = e.getBlockPlaced().getLocation();
				List<String> Spawnpoints = BleadNight.Main.getConfig().getStringList("SpawnPoints.Bandieten.SpawnPoints");
				if(Spawnpoints == null) Spawnpoints = new ArrayList<String>();
				Spawnpoints.add(loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getWorld().getName());
				BleadNight.Main.getConfig().set("SpawnPoints.Bandieten.SpawnPoints", Spawnpoints);
				BleadNight.Main.saveConfig();
				e.getPlayer().sendMessage("§cBleadNight §7Spawnpoint toegevoegd (§c" + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getWorld().getName() + "§7)");
				
			}
		}
	}

}
