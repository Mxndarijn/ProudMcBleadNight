package me.Mxndarijn.BleadNight;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class ZombieListener implements Listener {
	
	static void SpawnZombies() {
		int A = BleadNight.Main.getConfig().getInt("Bandieten.AantalErSpawnen");
		Random rand = new Random();
		List<String> Spawnpoints = BleadNight.Main.getConfig().getStringList("SpawnPoints.Bandieten.SpawnPoints");
		for(int i = 0; i < A; i++) {
			String L = Spawnpoints.get(rand.nextInt(Spawnpoints.size()));
			String Lo[] = L.split(";");
			Location loc = new Location(Bukkit.getWorld(Lo[3]),Double.parseDouble(Lo[0]),Double.parseDouble(Lo[1]),Double.parseDouble(Lo[2]));
			spawnZombie(loc);
			
		}
	}
	@EventHandler
	public static void deadzombie(EntityDeathEvent e) {
		if(e.getEntityType() == EntityType.ZOMBIE)
		{
			e.setDroppedExp(0);
			e.getDrops().clear();
			
			List<String> LootItems = BleadNight.Main.getConfig().getStringList("LootItems");
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			int Totaal = 0;
			for(int i = 0; i < LootItems.size(); i++) {
				String s = LootItems.get(i);
				String string[] = s.split(";-;");
				int ii = Integer.parseInt(string[1]);
				int n = Integer.parseInt(string[2]);
				ItemStack iss = BleadNight.Main.getConfig().getItemStack("ItemStacks." + n);
				for(int u = 0; u < ii; u++) {
					items.add(iss);
					Totaal++;
				}
			}
			ItemStack itemgekozen = new ItemStack(Material.AIR);
			Random random = new Random();
			itemgekozen = items.get(random.nextInt(Totaal));
			e.getDrops().add(itemgekozen);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void spawnZombie(Location loc) {
		Zombie zombie = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
		int health = BleadNight.Main.getConfig().getInt("Bandieten.Health");
		((Damageable) zombie).setMaxHealth(health);
		zombie.setHealth(health);
		zombie.setCustomName("§7Bandiet | §c" + (zombie.getHealth()/2)+"§4❤");
		zombie.setCustomNameVisible(true);
	}
	
	@EventHandler
	public void zombi(EntityDamageEvent e) {
		if(e.getEntity().getType().equals(EntityType.ZOMBIE)) {
			Zombie zomb = (Zombie) e.getEntity();
			if(zomb.getName().contains("Bandiet")) {
				Double damage  = e.getDamage();
				DecimalFormat df = new DecimalFormat("####.##");
				Double health = ((zomb.getHealth()/2) - (damage)/2);
				if(health < 0) health = 0.00;
				zomb.setCustomName("§7Bandiet | §c" + df.format(health)+"§4❤");
			}

		}
	}
	@EventHandler
	public void zombiedamage(EntityDamageByEntityEvent e) {
		if(e.getDamager().getType() ==EntityType.ZOMBIE) {
			Zombie zomb = (Zombie) e.getDamager();
			if(zomb.getCustomName().contains("Bandiet")) {
				e.setDamage(e.getDamage() * BleadNight.Main.getConfig().getInt("Bandieten.Damage"));
			}
		}
	}
}
