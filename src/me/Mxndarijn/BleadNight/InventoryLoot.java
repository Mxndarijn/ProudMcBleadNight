package me.Mxndarijn.BleadNight;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
public class InventoryLoot implements Listener {
	public HashMap<Player, Boolean> ChangeAantal = new HashMap<Player, Boolean>();
	public HashMap<Player, Boolean> ChangeNaam = new HashMap<Player, Boolean>();
	
	public int ItemStacks = BleadNight.Main.getConfig().getInt("ItemStacks.Totaal");
	public ItemMeta LootIM = null;
	public ItemStack LootIS = null;
	public int Aantal = 0;
	public String Naam = null;
	public Double Procent = 0.0;
	
	static void InventoryCreate(Player p) {
		Inventory inv = Bukkit.getServer().createInventory(null, 27, "§7BleadNight §cLoot§c");
		ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
		ItemMeta im = is.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.setDisplayName("§aLoot Toevoegen");
		ArrayList<String> lore = new ArrayList<String>();
	    lore.add("§cHoud het item in je hand wat je wilt toevoegen.");
	    im.setLore(lore);
		is.setItemMeta(im);
		inv.setItem(10, is);
		
		lore.clear();
		is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		im = is.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.setDisplayName("§cLoot verwijderen");
		lore.add("§cKrijg een lijst en selecteer welke je wilt verwijderen");
	    im.setLore(lore);
		is.setItemMeta(im);
		inv.setItem(16, is);
		
		lore.clear();
		is = new ItemStack(Material.PAPER);
		im = is.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.setDisplayName("§7Loot Lijst");
		lore.add("§cKrijg een lijst van alle loot items");
	    im.setLore(lore);
		is.setItemMeta(im);
		inv.setItem(13, is);
		p.openInventory(inv);
		
	}
	
	@EventHandler
	public void InventoryListener(InventoryClickEvent e) {
		if(e.getInventory().getName().contains("§7BleadNight §cLoot§c")) {
			Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			if(e.getSlot() == 10) {
				LootIM = p.getItemInHand().getItemMeta();
				LootIS = p.getItemInHand();
				LootIS.setItemMeta(LootIM);
				p.sendMessage("§cBleadNight §7Typ het aantal in, het aantal van hoeveel kans het heeft (%) (Stel je telt alle aantallen bij elkaar op en dat is 1000 is 1 gelijk aan 0.1% bijv, bij invoeren moet je hele getallen gebruiken.)");
				p.closeInventory();
				ChangeAantal.put(p, true);
			}
			if(e.getSlot() == 16) {
				List<String> LootItems = BleadNight.Main.getConfig().getStringList("LootItems");
				Inventory inv = Bukkit.getServer().createInventory(null, 54, "§7BleadNight §cLoot Lijst");
				ItemStack is = new ItemStack(Material.PAPER);
				ItemMeta im = is.getItemMeta();
				im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				im.setDisplayName("§cKlik op een item om hem te verwijderen.");
				ArrayList<String> lore = new ArrayList<String>();
			    
			    im.setLore(lore);
				is.setItemMeta(im);
				inv.setItem(4, is);
				
				lore.clear();
				for(int i = 0; i < LootItems.size(); i++) {
					String s = LootItems.get(i);
					String string[] = s.split(";-;");
					//int ii = Integer.parseInt(string[1]);
					int n = Integer.parseInt(string[2]);
					ItemStack iss = BleadNight.Main.getConfig().getItemStack("ItemStacks." + n);
					lore = (ArrayList<String>) iss.getItemMeta().getLore();
					lore.add("§cConfig: ");
					inv.setItem(18 + i, iss);
				}
				p.openInventory(inv);
			}
			if(e.getSlot() == 13) {
				Double Optelsom = 0.0;
				List<String> LootItems = BleadNight.Main.getConfig().getStringList("LootItems");
				for(int i = 0; i < LootItems.size(); i++) {
					String s = LootItems.get(i);
					String string[] = s.split(";-;");
					int ii = Integer.parseInt(string[1]);
					Optelsom = Optelsom + ii;
				}
				Procent = 100 / Optelsom;
				DecimalFormat df = new DecimalFormat("###.##");
				
				
				
				Inventory inv = Bukkit.getServer().createInventory(null, 54, "§7BleadNight §cLoot Lijst");
				ItemStack is = new ItemStack(Material.PAPER);
				ItemMeta im = is.getItemMeta();
				im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				im.setDisplayName("§cLoot Lijst");
				ArrayList<String> lore = new ArrayList<String>();
			    lore.add("§cPercentages§7:");
			    
				for(int i = 0; i < LootItems.size(); i++) {
					String s = LootItems.get(i);
					String string[] = s.split(";-;");
					int ii = Integer.parseInt(string[1]);
					lore.add(" §c-§7 " + string[0] + "§c:" + df.format(Procent * ii) + "%");
				}
			    
			    im.setLore(lore);
				is.setItemMeta(im);
				inv.setItem(4, is);
				
				lore.clear();
				for(int i = 0; i < LootItems.size(); i++) {
					String s = LootItems.get(i);
					String string[] = s.split(";-;");
					int ii = Integer.parseInt(string[1]);
					int n = Integer.parseInt(string[2]);
					ItemStack iss = BleadNight.Main.getConfig().getItemStack("ItemStacks." + n);
					lore.add(" §c-§7 " + string[0] + "§c:" + df.format(Procent * ii) + "%");
					if(i < 9) inv.setItem(18 + i, iss);
					if(i > 9 && i < 18) inv.setItem(26 + i, iss);
					
					is = new ItemStack(Material.PAPER);
					im = is.getItemMeta();
					im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					im.setDisplayName("§cItem " + (1+i));
					lore.clear();
				    lore.add("§cNaam§7: " + string[0]);
				    lore.add("§cAantal§7: " + ii);
				    lore.add("§cItemStackNummer§7: " + n);
				    im.setLore(lore);
				    is.setItemMeta(im);
				    if(i < 9) inv.setItem(27 + i, is);
					if(i > 9 && i < 18) inv.setItem(35 + i, is);
				}
				
				p.openInventory(inv);	
			}
		}
	}
	@EventHandler
	public void ChatEvent(PlayerChatEvent e) {
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
				p.sendMessage("§cBleadNight §7Typ het aantal in, het aantal van hoeveel kans het heeft (%) (Stel je telt alle aantallen bij elkaar op en dat is 1000 is 1 gelijk aan 0.1% bijv, bij invoeren moet je hele getallen gebruiken.)");
				ChangeAantal.put(p, true);
				return;
			}
			Aantal = i;
			p.sendMessage("§cBleadNight §7aantal is verzet naar §c" + i);
			ChangeNaam.put(p, true);
			p.sendMessage("§cBleadNight §7Typ de naam van het item (Hoe het in de lijst moet worden genoemd)§c");
			return;
		}
		if(ChangeNaam.containsKey(e.getPlayer())) {
			e.setCancelled(true);
			Player p = e.getPlayer();
			ChangeNaam.remove(p);
			if(e.getMessage().equalsIgnoreCase("cancel")) {
				p.sendMessage("§cBleadNight §7canceled");
				return;
			}
			String mes = e.getMessage();
			Naam = mes;
			p.sendMessage("§cBleadNight §7naam is verzet naar §c" + mes);
			
			List<String> LootItems = BleadNight.Main.getConfig().getStringList("LootItems");
			if(LootItems == null) LootItems = new ArrayList<String>();
			ItemStacks = ItemStacks + 1;
			LootItems.add(Naam + ";-;" + Aantal + ";-;" + ItemStacks);
			BleadNight.Main.getConfig().set("ItemStacks.Totaal", ItemStacks);
			BleadNight.Main.getConfig().set("LootItems", LootItems);
			BleadNight.Main.getConfig().set("ItemStacks." + ItemStacks, LootIS);
			BleadNight.Main.saveConfig();
			e.getPlayer().sendMessage("§cBleadNight §7LootItem toegevoegd (§c" + Naam + ", " + Aantal + ", " + LootIS.getType() + "§7)");
			
			
		}
	}
	@EventHandler
	public void Click(InventoryClickEvent e) {
		if(e.getInventory().getName().contains("§7BleadNight §cLoot Lijst")) {
			if(e.getSlot() > 8) {	
				e.setCancelled(true);
				if(e.getCurrentItem().getType() == Material.AIR) return;
				if(e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().hasLore()) {
					if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Item") &&e.getCurrentItem().getItemMeta().getLore().contains("Naam")) {
						return;
					}
				}
				Inventory inv = Bukkit.getServer().createInventory(null, 27, "§7BleadNight §cItem Verwijderen");
				ItemStack is = new ItemStack(Material.BARRIER);
				ItemMeta im = is.getItemMeta();
				im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				im.setDisplayName("§cVerwijder het item?");
				ArrayList<String> lore = new ArrayList<String>();
			    im.setLore(lore);
				is.setItemMeta(im);
				inv.setItem(4, is);
				
			}
		}
	}
	

}
