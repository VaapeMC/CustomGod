package me.vaape.customgod;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codingforcookies.armorequip.ArmorEquipEvent;

import net.md_5.bungee.api.ChatColor;

public class ArmourListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onArmorEquip (ArmorEquipEvent event) {
		//Equip
		if (event.getNewArmorPiece() != null && event.getNewArmorPiece().getType() != Material.AIR) {
			ItemStack armor = event.getNewArmorPiece();
			Player player = event.getPlayer();
			if (GodItems.isGod(armor)) {
				if (GodItems.getGodName(armor).equalsIgnoreCase("Daedric Helm")) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
				}
				if (GodItems.getGodName(armor).equalsIgnoreCase("Theseus")) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
				}
				if (GodItems.getGodName(armor).equalsIgnoreCase("Warlord Cuirass")) {
				}
				if (GodItems.getGodName(armor).equalsIgnoreCase("Hermes Leggings")) {
					//player.removePotionEffect(PotionEffectType.SPEED);
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
				}
				if (GodItems.getGodName(armor).equalsIgnoreCase("Brightwing")) {
				}
				//SNOWY
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Snowy Helm")) {
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Green Snowy Chest") || GodItems.getGodName(armor).equalsIgnoreCase("Red Snowy Chest")) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Green Snowy Legs") || GodItems.getGodName(armor).equalsIgnoreCase("Red Snowy Legs")) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Green Snowy Boots")) {
				}
				//VALENTINES
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Valentine's Helm")) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Cupid's Wings")) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1));
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Valentine's Legs")) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2));
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Easter Boots")) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1));
				}
			}
		}
		//Unequip
		if (event.getOldArmorPiece() != null && event.getOldArmorPiece().getType() != Material.AIR) {
			ItemStack armor = event.getOldArmorPiece();
			Player player = event.getPlayer();
			double maxHealth = player.getMaxHealth();
			if (GodItems.isGod(armor)) {
				if (GodItems.getGodName(armor).equalsIgnoreCase("Daedric Helm")) {
					player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);					
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Theseus")) {
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Warlord Cuirass")) {
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Hermes Leggings")) {
					player.removePotionEffect(PotionEffectType.SPEED);
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Brightwing")) {
				}
				//SNOWY
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Snowy Helm")) {
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Green Snowy Chest") || GodItems.getGodName(armor).equalsIgnoreCase("Red Snowy Chest")) {
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Green Snowy Legs") || GodItems.getGodName(armor).equalsIgnoreCase("Red Snowy Legs")) {
					player.removePotionEffect(PotionEffectType.SPEED);
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("snowy boots")) {
				}
				//VALENTINE'S
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Valentine's Helm")) {
					player.removePotionEffect(PotionEffectType.REGENERATION);
					player.removePotionEffect(PotionEffectType.NIGHT_VISION);
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Cupid's Wings")) {
					player.removePotionEffect(PotionEffectType.FAST_DIGGING);
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Valentine's Legs")) {
					player.removePotionEffect(PotionEffectType.SPEED);
					player.removePotionEffect(PotionEffectType.JUMP);
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("Easter Boots")) {
					player.removePotionEffect(PotionEffectType.JUMP);
				}
			}
		}
	}
	
	@EventHandler
	public void onDamage (EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			PlayerInventory inv = player.getInventory();
			ItemStack boots = inv.getBoots();
			if (event.getCause() == DamageCause.FALL ) {
				if (boots != null && boots.getType() != Material.AIR) {
					if (GodItems.isGod(boots)) {
						if (GodItems.getGodName(boots).equalsIgnoreCase("Brightwing")) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
	
	//Prevent hearts stacking from daedric helm
	@EventHandler
	public void onHatCommand (PlayerCommandPreprocessEvent event) {
		
		if (event.getMessage().toLowerCase().startsWith("/hat ") || event.getMessage().toLowerCase().equalsIgnoreCase("/hat")) {

			if (event.getPlayer().getInventory().getHelmet() != null) {
				event.getPlayer().sendMessage(ChatColor.RED + "You must take off your current hat before putting on a new one.");
				event.setCancelled(true);
			}
		}
	}

}
