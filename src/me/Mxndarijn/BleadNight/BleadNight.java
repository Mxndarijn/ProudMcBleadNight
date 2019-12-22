package me.Mxndarijn.BleadNight;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;



public class BleadNight extends JavaPlugin implements Listener  {
	public static BleadNight Main;
	public static Boolean BleadNightE = false;
	
	public void onEnable() {
		Main = this;
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		getServer().getPluginManager().registerEvents(new ZombieListener(), this);
		getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
		getServer().getPluginManager().registerEvents(new BossBarCreate(), this);
		getServer().getPluginManager().registerEvents(new InventoryLoot(), this);
		getCommand("bleadnight").setExecutor(new BleadNightCommand());
		System.out.println("BleadNight Online");
		
	}
	public void onDisable() {
		System.out.println("BleadNight Offline");
	}
	
}
