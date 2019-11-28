package me.Mxndarijn.BleadNight;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("deprecation")
public class InventoryListener implements Listener {
	public HashMap<Player, Boolean> ChangeHealth = new HashMap<Player, Boolean>();
	public HashMap<Player, Boolean> ChangeDamage = new HashMap<Player, Boolean>();
	public HashMap<Player, Boolean> ChangeAantal = new HashMap<Player, Boolean>();
	
	//BleadNight Main Menu
	@EventHandler
	public void ItemClick(InventoryClickEvent e) {
		if(e.getInventory().getName().contains("§7BleadNight§f")) {
			Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			if(e.getSlot() == 10) {
				//Enable BleadNight
				p.closeInventory();
			}
			if(e.getSlot() == 13) {
				//Settings BleadNight
				Inventory inv = Bukkit.getServer().createInventory(null, 27, "§7BleadNight Settings§f");
				ItemStack is = new ItemStack(Material.POTION, 1, (short) 5);
				ItemMeta im = is.getItemMeta();
				im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				im.setDisplayName("§cHealth");
				ArrayList<String> lore = new ArrayList<String>();
	    	    lore.add("§cHealth§8: §7" + BleadNight.Main.getConfig().getInt("Bandieten.Health"));
	    	    im.setLore(lore);
				is.setItemMeta(im);
				inv.setItem(9, is);
				
				is = new ItemStack(Material.BEDROCK, 1, (short) 0);
				im = is.getItemMeta();
				im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				im.setDisplayName("§cSpawnPoints");
				is.setItemMeta(im);
				inv.setItem(11, is);
				
				is = new ItemStack(Material.IRON_SWORD, 1, (short) 0);
				im = is.getItemMeta();
				im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				im.setDisplayName("§7Damage");
				lore.clear();
				lore.add("§cDamage§8: §7" + BleadNight.Main.getConfig().getInt("Bandieten.Damage"));
	    	    im.setLore(lore);
				is.setItemMeta(im);
				inv.setItem(13, is);
				
				is = new ItemStack(Material.CHEST, 1, (short) 0);
				im = is.getItemMeta();
				im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				im.setDisplayName("§7Loot");
				is.setItemMeta(im);
				inv.setItem(15, is);
				
				is = new ItemStack(Material.MONSTER_EGG, 1, (short) 2);
				im = is.getItemMeta();
				im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				im.setDisplayName("§7Aantal er Spawnen");
				lore.clear();
				lore.add("§cAantal Er Spawnen§8: §7" + BleadNight.Main.getConfig().getInt("Bandieten.AantalErSpawnen"));
	    	    im.setLore(lore);
				is.setItemMeta(im);
				inv.setItem(17, is);
				
				p.openInventory(inv);
				
			}
			if(e.getSlot() == 16) {
				//Disable BleadNight
				p.closeInventory();
			}
		}
	}
	//BleadNight Main Menu
		@EventHandler
		public void ItemClick1(InventoryClickEvent e) {
			if(e.getInventory().getName().contains("§7BleadNight Settings§f")) {
				Player p = (Player) e.getWhoClicked();
				e.setCancelled(true);
				if(e.getSlot() == 9) {
					p.closeInventory();
					p.sendMessage("§cBleadNight §7Typ de health die de bandieten krijgen. (Hele getallen bv. 221) of typ§c cancel");
					ChangeHealth.put(p, true);
				}
				if(e.getSlot() == 11) {
					p.sendMessage("§cBleadNight §7Plaats dit block om een spawnpoint te zetten.");
					ItemStack is = new ItemStack(Material.BEDROCK, 1, (short) 0);
					ItemMeta im = is.getItemMeta();
					im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					im.setDisplayName("§cBleadNight Spawnpoint Bandieten");
					is.setItemMeta(im);
					e.getWhoClicked().getInventory().addItem(is);
					p.closeInventory();
				}
				if(e.getSlot() == 13) {
					p.closeInventory();
					p.sendMessage("§cBleadNight §7Typ de damage die de bandieten doen. (Hele getallen bv. 221) of typ§c cancel");
					ChangeDamage.put(p, true);
				}
				if(e.getSlot() == 17) {
					p.closeInventory();
					p.sendMessage("§cBleadNight §7Typ hoeveel bandieten er spawnen. (Hele getallen bv. 221) of typ§c cancel");
					ChangeAantal.put(p, true);
				}
			}
		}
		@EventHandler
		public void ChatEvent(PlayerChatEvent e) {
			if(ChangeHealth.containsKey(e.getPlayer())) {
				e.setCancelled(true);
				Player p = e.getPlayer();
				ChangeHealth.remove(p);
				int i = 1;
				if(e.getMessage().equalsIgnoreCase("cancel")) {
					p.sendMessage("§cBleadNight §7canceled");
					return;
				}
				try {
					String mes = e.getMessage();
					i = (int) Integer.parseInt(mes);
				}catch(NumberFormatException ee) {
					p.sendMessage("§cBleadNight §7Typ de health die de bandieten krijgen. (Hele getallen bv. 221)");
					ChangeHealth.put(p, true);
					return;
				}
				BleadNight.Main.getConfig().set("Bandieten.Health", i);
				BleadNight.Main.saveConfig();
				p.sendMessage("§cBleadNight §7Health is verzet naar §c" + i + "❤");
				
				
			}
			if(ChangeDamage.containsKey(e.getPlayer())) {
				e.setCancelled(true);
				Player p = e.getPlayer();
				ChangeDamage.remove(p);
				int i = 1;
				if(e.getMessage().equalsIgnoreCase("cancel")) {
					p.sendMessage("§cBleadNight §7canceled");
					return;
				}
				try {
					String mes = e.getMessage();
					i = (int) Integer.parseInt(mes);
				}catch(NumberFormatException ee) {
					p.sendMessage("§cBleadNight §7Typ de health die de bandieten krijgen. (Hele getallen bv. 221)");
					ChangeDamage.put(p, true);
					return;
				}
				BleadNight.Main.getConfig().set("Bandieten.Damage", i);
				BleadNight.Main.saveConfig();
				p.sendMessage("§cBleadNight §7Damage is verzet naar §c" + i);
				
				
			}
			if(ChangeAantal.containsKey(e.getPlayer())) {
				e.setCancelled(true);
				Player p = e.getPlayer();
				ChangeAantal.remove(p);
				int i = 1;
				if(e.getMessage().equalsIgnoreCase("cancel")) {
					p.sendMessage("§cBleadNight §7canceled");
					return;
				}
				try {
					String mes = e.getMessage();
					i = (int) Integer.parseInt(mes);
				}catch(NumberFormatException ee) {
					p.sendMessage("§cBleadNight §7Typ hoeveel bandieten er spawnen. (Hele getallen bv. 221)");
					ChangeAantal.put(p, true);
					return;
				}
				BleadNight.Main.getConfig().set("Bandieten.AantalErSpawnen", i);
				BleadNight.Main.saveConfig();
				p.sendMessage("§cBleadNight §7Aantal is verzet naar §c" + i);
				
				
			}
			
		}

}
