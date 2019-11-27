package me.Mxndarijn.BleadNight;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BleadNightCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg0 instanceof Player) {
			Inventory inv = Bukkit.getServer().createInventory(null, 27, "§7BleadNight§f");
			ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
			ItemMeta im = is.getItemMeta();
			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			im.setDisplayName("§aEnable");
			is.setItemMeta(im);
			inv.setItem(10, is);
			
			is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
			im = is.getItemMeta();
			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			im.setDisplayName("§cDisable");
			is.setItemMeta(im);
			inv.setItem(16, is);
			
			is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
			im = is.getItemMeta();
			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			im.setDisplayName("§7Settings");
			is.setItemMeta(im);
			inv.setItem(13, is);
			
			Player player = (Player) arg0;
			player.openInventory(inv);
		}
		return true;
	}

}
