package me.vaape.customgod;

import com.codingforcookies.armorequip.ArmorEquipEvent;
import me.vaape.enchantformat.EnchantFormat;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class ItemUpdater implements Listener {

	@EventHandler
	public void onInventoryClick (InventoryClickEvent event) {

		if (event.getWhoClicked() instanceof Player player) {

			if (event.getInventory().getType() != InventoryType.CRAFTING) return;

			if (event.getSlot() <= 39 && event.getSlot() >=36) return; //Armor slots (if they click them it dupes armor

			PlayerInventory inventory = event.getWhoClicked().getInventory();

			ItemStack helmet = inventory.getHelmet();
			ItemStack chest = inventory.getChestplate();
			ItemStack leggings = inventory.getLeggings();
			ItemStack boots = inventory.getBoots();

			if (helmet != null) {
				//Update God
				if (GodItems.isGod(helmet) && GodItems.getGodName(helmet) != null) { //Check if name is null because colored snowy gear is null
					inventory.setHelmet(EnchantFormat.plugin.fixFormat(getUpdatedItem(helmet)));
				}
				else {
					if (helmet.getType() != Material.NETHERITE_HELMET && helmet.getType() != Material.DIAMOND_HELMET) return;
					if (helmet.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 4) {
						helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
						inventory.setHelmet(EnchantFormat.plugin.fixFormat(helmet));
					}
				}


			}

			if (chest != null) {
				//Update God
				if (GodItems.isGod(chest) && GodItems.getGodName(chest) != null) {
					inventory.setChestplate(EnchantFormat.plugin.fixFormat(getUpdatedItem(chest)));
				}
				else {
					if (chest.getType() != Material.NETHERITE_CHESTPLATE && chest.getType() != Material.DIAMOND_CHESTPLATE) return;

					if (chest.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 4) {
						chest.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
						chest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
						inventory.setChestplate(EnchantFormat.plugin.fixFormat(chest));
					}
				}
			}
			if (leggings != null) {
				//Update God
				if (GodItems.isGod(leggings) && GodItems.getGodName(leggings) != null) {
					inventory.setLeggings(EnchantFormat.plugin.fixFormat(getUpdatedItem(leggings)));
				}
				else {
					if (leggings.getType() != Material.NETHERITE_LEGGINGS && leggings.getType() != Material.DIAMOND_LEGGINGS) return;
					if (leggings.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 4) {
						leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
						inventory.setLeggings(EnchantFormat.plugin.fixFormat(leggings));
					}
				}
			}
			if (boots != null) {
				//Update God
				if (GodItems.isGod(boots) && GodItems.getGodName(boots) != null) {
					inventory.setBoots(EnchantFormat.plugin.fixFormat(getUpdatedItem(boots)));
				}
				else {
					// Love you billy bob: from doug xoxo
					if (boots.getType() != Material.NETHERITE_BOOTS && boots.getType() != Material.DIAMOND_BOOTS) return;
					if (boots.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 4) {
						boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
						inventory.setBoots(EnchantFormat.plugin.fixFormat(boots));
					}
				}
			}
		}
	}

	@EventHandler
	public void onInteract (PlayerInteractEvent event) {
		if (event.getClickedBlock() == null) return;

		if (event.getHand() == EquipmentSlot.OFF_HAND) return;

		if (event.getClickedBlock().getType() != Material.SHROOMLIGHT) return;

		Location blockLocation = event.getClickedBlock().getLocation();
		if (blockLocation.getX() > -50 && blockLocation.getX() < 50 && blockLocation.getZ() < 250 && blockLocation.getZ() > 150) {
			Player player = event.getPlayer();
			ItemStack hand = player.getInventory().getItemInMainHand();

			if (GodItems.isGod(hand)) {
				if (GodItems.isUnique(hand)) {
					player.getInventory().setItemInMainHand(getUpdatedItem(hand));
					player.sendMessage(ChatColor.BLUE + "Your " + ChatColor.BOLD + ChatColor.GOLD + GodItems.getGodName(hand) + ChatColor.BLUE + " has been updated.");
				} else {
					player.getInventory().setItemInMainHand(EnchantFormat.plugin.fixFormat(getUpdatedItem(hand)));
					player.sendMessage(ChatColor.BLUE + "Your " + ChatColor.BOLD + GodItems.getGodName(hand) + ChatColor.BLUE + " has been updated.");
				}
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 1f, 1.5f);
				Vector direction = player.getLocation().getDirection();
				player.setVelocity(direction.multiply(-1).multiply(new Vector(1, 0, 1))); //To make it not affect y
			}
			else {
				player.sendMessage(ChatColor.RED + "Only God items can be updated.");
			}
		}
	}

	@EventHandler
	public void onClick (PlayerInteractEvent event) {
		ItemStack hand = event.getPlayer().getInventory().getItemInMainHand();
		if (hand == null) return;
		if (hand.getType() == Material.AIR) return;
		if (!GodItems.isGod(hand)) return;
		if (!GodItems.isGodWeapon(hand)) return;

		String godWeapon = GodItems.getGodName(hand);
		int sharpness = hand.getEnchantmentLevel(Enchantment.DAMAGE_ALL);

		switch (godWeapon) {
			case "Sulfuron": {
				if (sharpness > 21) {
					event.getPlayer().getInventory().setItemInMainHand(EnchantFormat.plugin.fixFormat(getUpdatedItem(hand)));
					event.getPlayer().sendMessage(ChatColor.BLUE + "Your " + godWeapon + " has been updated.");
				}
				break;
				}
				case "Anduril": {
					if (sharpness > 20) {
						event.getPlayer().getInventory().setItemInMainHand(EnchantFormat.plugin.fixFormat(getUpdatedItem(hand)));
						event.getPlayer().sendMessage(ChatColor.BLUE + "Your " + godWeapon + " has been updated.");
					}
					break;
			}
			case "Shadow's Edge": {
				if (sharpness > 20) {
					event.getPlayer().getInventory().setItemInMainHand(EnchantFormat.plugin.fixFormat(getUpdatedItem(hand)));
					event.getPlayer().sendMessage(ChatColor.BLUE + "Your " + godWeapon + " has been updated.");
				}
				break;
			}
			case "Shadow Seeker": {
				if (sharpness > 18) {
					event.getPlayer().getInventory().setItemInMainHand(EnchantFormat.plugin.fixFormat(getUpdatedItem(hand)));
					event.getPlayer().sendMessage(ChatColor.BLUE + "Your " + godWeapon + " has been updated.");
				}
				break;
			}
			case "Glacial Brand": {
				if (sharpness > 19) {
					event.getPlayer().getInventory().setItemInMainHand(EnchantFormat.plugin.fixFormat(getUpdatedItem(hand)));
					event.getPlayer().sendMessage(ChatColor.BLUE + "Your " + godWeapon + " has been updated.");
				}
				break;
			}
			case "Ice Pick": {
				if (sharpness > 20) {
					event.getPlayer().getInventory().setItemInMainHand(EnchantFormat.plugin.fixFormat(getUpdatedItem(hand)));
					event.getPlayer().sendMessage(ChatColor.BLUE + "Your " + godWeapon + " has been updated.");
				}
				break;
			}
		}

	}

	public ItemStack getUpdatedItem(ItemStack oldItem) {
		String name = GodItems.getGodName(oldItem);
		if (name == null) return null;
		ItemStack newItem = CustomGod.godItems.get(name);
		ItemMeta newItemMeta = newItem.getItemMeta();

		List<String> oldItemLore = oldItem.getItemMeta().getLore();
		List<String> newItemLore = newItemMeta.getLore();

		//Check if has durability
		if (oldItem.getItemMeta() instanceof Damageable oldItemMeta) {
			((Damageable) newItemMeta).setDamage(oldItemMeta.getDamage());
		}

		//Update name
		String oldName = oldItem.getItemMeta().getDisplayName();
		newItemMeta.setDisplayName(oldName);

		//Update Times repairs and Kills
		int oldLoreIndex = 0;
		for (String oldItemLine : oldItemLore) { //Loop through each line of old lore

			if (oldItemLine.contains("Times repaired")) { //If line of old lore contains Times repaired, the index of it is oldLoreIndex

				int newLoreIndex = 0; //new lore index may be different from old lore index
				for (String newItemLine : newItemLore) {

					if (newItemLine.contains("Times repaired")) {
						newItemLore.set(newLoreIndex, oldItemLore.get(oldLoreIndex)); //Replace new Times repaired line with old one
						newItemMeta.setLore(newItemLore);
					}
					newLoreIndex = newLoreIndex + 1;
				}
			}

			if (oldItemLine.contains("Kills:")) {

				int newLoreIndex = 0;
				for (String newItemLine : newItemLore) {
					if (newItemLine.contains("Kills:")) {
						newItemLore.set(newLoreIndex, oldItemLore.get(oldLoreIndex)); //Replace new Times repaired line with old one
						newItemMeta.setLore(newItemLore);
					}
					newLoreIndex = newLoreIndex + 1;
				}
			}

			oldLoreIndex = oldLoreIndex + 1;
		}

		newItem.setItemMeta(newItemMeta);
		return newItem;
	}
}
