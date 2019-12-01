package me.Mxndarijn.BleadNight;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class ZombieListener implements Listener {
	
	static void SpawnZombies() {
		if(10 > 1) {
			String message = "1.01,-3000.575";
			System.out.println(message);
			String[] msg = message.split(",");
			double xx = Integer.parseInt(msg[0]);
			double yy = Integer.parseInt(msg[1]);
	
			System.out.println(xx);
			System.out.println(yy);
			return;
		}
		int A = BleadNight.Main.getConfig().getInt("Bandieten.AantalErSpawnen");
		Random rand = new Random();
		List<String> Spawnpoints = BleadNight.Main.getConfig().getStringList("SpawnPoints.Bandieten.SpawnPoints");
		for(int i = 0; i < A; i++) {
			String L = Spawnpoints.get(rand.nextInt(Spawnpoints.size()));
			String Lo[] = L.split(";");
			System.out.println(Lo[0] + " " + Lo[1] + " " + Lo[2] + " " + Lo[3] + " ");
			System.out.println("|" + Lo[0] + "|");
			double x = Integer.parseInt(Lo[0]);
			double y = Integer.parseInt(Lo[1]);
			double z = Integer.parseInt(Lo[2]);
			System.out.println(x);
			Location loc = new Location(Bukkit.getWorld(Lo[3]),x,y,z);
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.teleport(loc);
			}
		}
	}
	

}
