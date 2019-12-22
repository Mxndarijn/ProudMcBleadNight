package me.Mxndarijn.BleadNight;

import org.bukkit.Bukkit;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BossBarCreate implements Listener {
	public static BossBar bar = null;
	public static void createbossbar() {
		int aantal = BleadNight.Main.getConfig().getInt("Bandieten.AantalErSpawnen");
		 bar = Bukkit.createBossBar("§c"+ aantal + " §7Bandieten over", BarColor.YELLOW, BarStyle.SOLID);
         for(Player p : Bukkit.getOnlinePlayers()) {
        	 bar.addPlayer(p);
         }
	}
	@EventHandler
	public static void dood(EntityDeathEvent e) {
		if(e.getEntityType() == EntityType.ZOMBIE) {
			if(e.getEntity().getName().contains("Bandiet")) {
				BleadNight.Main.getConfig().set("Bandieten.AantalOver", BleadNight.Main.getConfig().getInt("Bandieten.AantalOver")-1);
				BleadNight.Main.saveConfig();
				int aantal = BleadNight.Main.getConfig().getInt("Bandieten.AantalOver");
				if(aantal == 0) {
					Einde();
				}
				bar.setTitle("§c"+ aantal + " §7Bandieten over");
			}
		}

	}
	
	public static void updatebossbar() {
		int aantal = BleadNight.Main.getConfig().getInt("Bandieten.AantalOver");
		 bar = Bukkit.createBossBar("§c"+ aantal + " §7Bandieten over", BarColor.RED, BarStyle.SOLID);
	}
	@EventHandler
	public void join(PlayerJoinEvent e) {
		if(BleadNight.BleadNightE == true) {
			bar.addPlayer(e.getPlayer());
		}
	}
	@EventHandler
	public void leave(PlayerQuitEvent e) {
		if(BleadNight.BleadNightE == true) {
			bar.removePlayer(e.getPlayer());
		}
		
		
	}
	public static void Einde() {
		BleadNight.BleadNightE = false;
		for(Player p : Bukkit.getOnlinePlayers()) {
			bar.removePlayer(p);
			p.sendMessage("§4§m-----------------------------------");
			p.sendMessage("§cBleadnight §7is §cgeeindigd.");
			p.sendMessage("§4§m-----------------------------------");
		}
		Bukkit.getWorlds()
        .forEach(world -> world.getLivingEntities().stream()
                .filter(entity -> (entity instanceof Zombie))
                .forEach(LivingEntity::remove));
		
	}

}
