package me.vaape.customgod;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class GodItems {
	
	public static boolean isGod(ItemStack stack){
		if (stack != null && stack.getType() != Material.AIR) {
			if (stack.hasItemMeta()) {
				if (stack.getItemMeta().hasLore()) {
					List<String> lore = stack.getItemMeta().getLore();
					if (lore.contains(ChatColor.ITALIC + "Only the worthy") || //Mjolnir
						lore.contains(ChatColor.ITALIC + "Fire can be seen") || //Sulfuron
						lore.contains(ChatColor.ITALIC + "Shows signs of being") || //Anduril
						lore.contains(ChatColor.ITALIC + "Consumes the souls") || //Shadow's Edge
						lore.contains(ChatColor.ITALIC + "Found in the woods") || //Medusa
						lore.contains(ChatColor.ITALIC + "of the phoenix Al'ar,") || //Flameheart
						lore.contains(ChatColor.ITALIC + "Used by ancient Colombian") || //Kamatayon
						lore.contains(ChatColor.ITALIC + "Taken from a dying priest") || //Daedric Helm
						lore.contains(ChatColor.ITALIC + "Said to have belonged to an") || //Theseus
						lore.contains(ChatColor.ITALIC + "Found on the corpse of an") || //Warlord Cuirass
						lore.contains(ChatColor.ITALIC + "Worn by the Greek") || //Hermes Leggings
						lore.contains(ChatColor.ITALIC + "'As light as a feather, as silent") || //Brightwing
						lore.contains(ChatColor.ITALIC + "Ancient untranslatable runes") || //Shadow Seeker
						lore.contains(ChatColor.ITALIC + "Found at the bottom of") || //Fisherman's Friend
						lore.contains(ChatColor.ITALIC + "Said to have corrupted") || //Cane of Zefeus
						lore.contains(ChatColor.ITALIC + "Found under the corpse of")) { //Dalabrus
						return true;
					}
					else {
						return false;
					}
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	public static boolean isLegendary(ItemStack stack){
		if (stack != null && stack.getType() != Material.AIR) {
			if (stack.hasItemMeta()) {
				if (stack.getItemMeta().hasLore()) {
					List<String> lore = stack.getItemMeta().getLore();
					if (lore.contains(ChatColor.ITALIC + "Only the worthy")) { //Mjolnir
						return true;
					}
					else {
						return false;
					}
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	public static boolean isGodWeapon(ItemStack stack) {
		if (!isGod(stack)) {
			return false;
		}
		if (getGodName(stack).equals("mjolnir") ||
			getGodName(stack).equals("sulfuron") ||
			getGodName(stack).equals("anduril") ||
			getGodName(stack).equals("shadow's edge") ||
			getGodName(stack).equals("medusa") ||
			getGodName(stack).equals("flameheart") ||
			getGodName(stack).equals("kamatayan") ||
			getGodName(stack).equals("shadow seeker") ||
			getGodName(stack).equals("fisherman's friend") ||
			getGodName(stack).equals("cane of zefeus") ||
			getGodName(stack).equals("dalabrus")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static String getGodName(ItemStack stack){
		if (stack != null && stack.getType() != Material.AIR) {
			if (stack.hasItemMeta()) {
				if (stack.getItemMeta().hasLore()) {
					List<String> lore = stack.getItemMeta().getLore();
					if (lore.contains(ChatColor.ITALIC + "Only the worthy")) {
						return "mjolnir";
					}
					else if (lore.contains(ChatColor.ITALIC + "Fire can be seen")) {
						return "sulfuron";
					}
					else if (lore.contains(ChatColor.ITALIC + "Shows signs of being")) {
						return "anduril";
					}
					else if (lore.contains(ChatColor.ITALIC + "Consumes the souls")) {
						return "shadow's edge";
					}
					else if (lore.contains(ChatColor.ITALIC + "Found in the woods")) {
						return "medusa";
					}
					else if (lore.contains(ChatColor.ITALIC + "of the phoenix Al'ar,")) {
						return "flameheart";
					}
					else if (lore.contains(ChatColor.ITALIC + "Used by ancient Colombian")) {
						return "kamatayan";
					}
					else if (lore.contains(ChatColor.ITALIC + "Ancient untranslatable runes")) {
						return "shadow seeker";
					}
					else if (lore.contains(ChatColor.ITALIC + "Found at the bottom of")) {
						return "fisherman's friend";
					}
					else if (lore.contains(ChatColor.ITALIC + "Said to have corrupted")) {
						return "cane of zefeus";
					}
					else if (lore.contains(ChatColor.ITALIC + "Found under the corpse of")) {
						return "dalabrus";
					}
					//Armour
					else if (lore.contains(ChatColor.ITALIC + "Taken from a dying priest")) {
						return "daedric helm";
					}
					else if (lore.contains(ChatColor.ITALIC + "Said to have belonged to an")) {
						return "theseus";
					}
					else if (lore.contains(ChatColor.ITALIC + "Found on the corpse of an")) {
						return "warlord cuirass";
					}
					else if (lore.contains(ChatColor.ITALIC + "Worn by the Greek")) {
						return "hermes leggings";
					}
					else if (lore.contains(ChatColor.ITALIC + "'As light as a feather, as silent")) {
						return "brightwing";
					}
					
					
					else {
						return null;
					}
				}
				else {
					return null;
				}
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}

}
