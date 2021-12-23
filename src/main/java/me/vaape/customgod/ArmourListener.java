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
			double maxHealth = player.getMaxHealth();
			if (GodItems.isGod(armor)) {
				if (GodItems.getGodName(armor).equalsIgnoreCase("daedric helm")) {
					player.setMaxHealth(maxHealth + 4);
					//player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
					player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
				}
				if (GodItems.getGodName(armor).equalsIgnoreCase("theseus")) {
					//player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
				}
				if (GodItems.getGodName(armor).equalsIgnoreCase("warlord cuirass")) {
					player.setMaxHealth(maxHealth + 8);
				}
				if (GodItems.getGodName(armor).equalsIgnoreCase("hermes leggings")) {
					player.setMaxHealth(maxHealth + 4);
					//player.removePotionEffect(PotionEffectType.SPEED);
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
				}
				if (GodItems.getGodName(armor).equalsIgnoreCase("brightwing")) {
					player.setMaxHealth(maxHealth + 4);
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("snowy helm")) {
					player.setMaxHealth(maxHealth + 6);
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("snowy chest")) {
					player.setMaxHealth(maxHealth + 6);
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("snowy legs")) {
					player.setMaxHealth(maxHealth + 6);
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("snowy boots")) {
					player.setMaxHealth(maxHealth + 6);
				}
			}
		}
		//Unequip
		if (event.getOldArmorPiece() != null && event.getOldArmorPiece().getType() != Material.AIR) {
			ItemStack armor = event.getOldArmorPiece();
			Player player = event.getPlayer();
			double maxHealth = player.getMaxHealth();
			if (GodItems.isGod(armor)) {
				if (GodItems.getGodName(armor).equalsIgnoreCase("daedric helm")) {
					player.setMaxHealth(maxHealth - 4);
					player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);					
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("theseus")) {
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("warlord cuirass")) {
					player.setMaxHealth(maxHealth - 8);
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("hermes leggings")) {
					player.setMaxHealth(maxHealth - 4);
					player.removePotionEffect(PotionEffectType.SPEED);
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("brightwing")) {
					player.setMaxHealth(maxHealth - 4);
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("snowy helm")) {
					player.setMaxHealth(maxHealth - 6);
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("snowy chest")) {
					player.setMaxHealth(maxHealth - 6);
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("snowy legs")) {
					player.setMaxHealth(maxHealth - 6);
					player.removePotionEffect(PotionEffectType.SPEED);
				}
				else if (GodItems.getGodName(armor).equalsIgnoreCase("snowy boots")) {
					player.setMaxHealth(maxHealth - 6);
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
						if (GodItems.getGodName(boots).equalsIgnoreCase("brightwing")) {
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
