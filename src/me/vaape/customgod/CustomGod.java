package me.vaape.customgod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.SmithingInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class CustomGod extends JavaPlugin implements Listener {
	
	private static DamageListener damageListener;
	private static KillListener killListener;
	private static ForgeListener forgeListener;
	private static ArmourListener armourListener;
	
	public Permission useGForge = new Permission("customgod.forge.use");
	public Permission gForgeRename = new Permission("customgod.forge.rename");
	public Permission gForgeRepair = new Permission("customgod.forge.repair");
	public Permission gForgeRepairUnlimited = new Permission("customgod.forge.repair.unlimited");
	
	private FileConfiguration config = this.getConfig();
	
	public static CustomGod instance;
	
	public static CustomGod getInstance() {
		return instance;
	}
	
	public void onEnable() {
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		loadConfiguration();
		getLogger().info(ChatColor.GREEN + "CustomGod has been enabled!");
		
		damageListener = new DamageListener();
	    this.getServer().getPluginManager().registerEvents(damageListener, instance);
	    killListener = new KillListener();
	    this.getServer().getPluginManager().registerEvents(killListener, instance);
	    forgeListener = new ForgeListener();
	    this.getServer().getPluginManager().registerEvents(forgeListener, instance);
	    armourListener = new ArmourListener();
	    this.getServer().getPluginManager().registerEvents(armourListener, instance);
	    
	    Date timeToAddRepair = (Date) config.get("time to add repair");
		long timeToAddRepairInMillis = timeToAddRepair.getTime();
		Date now = new Date();
		long nowInMillis = now.getTime();
		
		if (timeToAddRepairInMillis <= nowInMillis) { //True if time is in the past
			addGlobalRepairs(20);
			getLogger().info(ChatColor.YELLOW + "God repairs have been granted...");
		}
		else {
			//Create the timer to refresh kits saved by config			
			long timeUntilAddRepairInMillis = timeToAddRepairInMillis - nowInMillis; //Get the difference in milliseconds from now until when it should add repair
			long timeUntilAddRepairInSeconds = timeUntilAddRepairInMillis/1000;
			
			addGlobalRepairs((int) timeUntilAddRepairInSeconds * 20);
		}
	}
	
	public void loadConfiguration() {
		final FileConfiguration config = this.getConfig();
		config.set(("time of server start"), new Date());
		config.options().copyDefaults(true);
		saveConfig();
	}
	
	public void onDisable(){
		instance = null;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("giverepair")) {
			if (sender.hasPermission("customgod.giverepair")) {
				if (args.length > 0) {
					if (Bukkit.getServer().getOfflinePlayer(args[0]) == null) {
						sender.sendMessage(ChatColor.RED + "Couldn't find player " + args[0]);
					}
					else {
						addRepair(Bukkit.getServer().getOfflinePlayer(args[0]).getUniqueId().toString());
						sender.sendMessage(ChatColor.GREEN + "God repair successfully given to " + Bukkit.getServer().getOfflinePlayer(args[0]).getName());
						return true;
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "Wrong usage, try /giverepair [player]");
					return false;
				}
			}
		}
		else if (cmd.getName().equalsIgnoreCase("removerepair")) {
			if (sender.hasPermission("customgod.removerepair")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "Wrong usage, try /removerepair [player]");
				}
				else {
					if (Bukkit.getServer().getOfflinePlayer(args[0]) == null) {
						sender.sendMessage(ChatColor.RED + "Couldn't find player " + args[0]);
					}
					else {
						removeRepair(Bukkit.getServer().getOfflinePlayer(args[0]).getUniqueId().toString());
						sender.sendMessage(ChatColor.GREEN + "God repair successfully removed from " + Bukkit.getServer().getOfflinePlayer(args[0]).getName());
					}
				}
			}
		}
		else if (cmd.getName().equalsIgnoreCase("grepair") || cmd.getName().equalsIgnoreCase("godrepair") ) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (config.get("repairs." + player.getUniqueId().toString()) == null || config.getInt("repairs." + player.getUniqueId().toString()) < 1) {
					player.sendMessage(ChatColor.RED + "You have depleted your weekly God Repair. It will replenish on Sunday evening at midnight.");
				}
				else {
					player.sendMessage(ChatColor.BLUE + "Your God Repair is ready to use.");
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "You must be a player to see how many God repairs you have.");
			}
		}

		else if (cmd.getName().equalsIgnoreCase("givegod")) {
			
			if (sender instanceof Player) {
				Player player = (Player) sender;
				
				if (player.hasPermission("customgod.givegod")) {
					
					player.setMaxHealth(20);
					player.getInventory().clear();
					
					//Mjolnir
					ItemStack mjolnir = new ItemStack(Material.IRON_AXE);
					mjolnir.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 9);
					mjolnir.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);
					mjolnir.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
					mjolnir.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 3);
					mjolnir.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 10);
					mjolnir.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 10);
					ItemMeta mjolnirMeta = mjolnir.getItemMeta();
					mjolnirMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Mjölnir");
					List<String> mjolnirLore = new ArrayList<String>();
					mjolnirLore.add("");
					mjolnirLore.add(ChatColor.ITALIC + "Only the worthy");
					mjolnirLore.add(ChatColor.ITALIC + "may wield this hammer...");
					mjolnirLore.add(ChatColor.AQUA + "- Summons lightning");
					mjolnirLore.add(ChatColor.AQUA + "- Wielder is immune to thunder");
					mjolnirLore.add("");
					mjolnirLore.add(ChatColor.GRAY + "Kills: " + 0);
					mjolnirLore.add(ChatColor.GRAY + "Times repaired: " + 0);
					mjolnirMeta.setLore(mjolnirLore);
					mjolnir.setItemMeta(mjolnirMeta);
					player.getInventory().addItem(mjolnir);
					
					//Sulfuron
					ItemStack sulfuron = new ItemStack(Material.NETHERITE_SWORD);
					sulfuron.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 9);
					sulfuron.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
					sulfuron.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
					sulfuron.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 6);
					sulfuron.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 6);
					ItemMeta sulfuronMeta = sulfuron.getItemMeta();
					sulfuronMeta.setDisplayName(ChatColor.RED + "Sulfuron");
					List<String> sulfuronLore = new ArrayList<String>();
					sulfuronLore.add("");
					sulfuronLore.add(ChatColor.ITALIC + "Fire can be seen");
					sulfuronLore.add(ChatColor.ITALIC + "running through the");
					sulfuronLore.add(ChatColor.ITALIC + "sword's blade...");
					sulfuronLore.add("");
					sulfuronLore.add(ChatColor.GRAY + "Kills: " + 0);
					sulfuronLore.add(ChatColor.GRAY + "Times repaired: " + 0);
					sulfuronMeta.setLore(sulfuronLore);
					sulfuron.setItemMeta(sulfuronMeta);
					player.getInventory().addItem(sulfuron);
					
					//Anduril
					ItemStack anduril = new ItemStack(Material.DIAMOND_SWORD);
					anduril.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 7);
					anduril.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
					anduril.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 4);
					anduril.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 4);
					ItemMeta andurilMeta = anduril.getItemMeta();
					andurilMeta.setDisplayName(ChatColor.RED + "Anduril");
					List<String> andurilLore = new ArrayList<String>();
					andurilLore.add("");
					andurilLore.add(ChatColor.ITALIC + "Shows signs of being");
					andurilLore.add(ChatColor.ITALIC + "reforged not long ago...");
					andurilLore.add(ChatColor.AQUA + "- Slows the enemy");
					andurilLore.add("");
					andurilLore.add(ChatColor.GRAY + "Kills: " + 0);
					andurilLore.add(ChatColor.GRAY + "Times repaired: " + 0);
					andurilMeta.setLore(andurilLore);
					anduril.setItemMeta(andurilMeta);
					player.getInventory().addItem(anduril);
					
					//Shadow's Edge
					ItemStack shadowsEdge = new ItemStack(Material.NETHERITE_AXE);
					shadowsEdge.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 8);
					shadowsEdge.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
					shadowsEdge.addUnsafeEnchantment(Enchantment.DIG_SPEED, 6);
					shadowsEdge.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 5);
					ItemMeta shadowsEdgeMeta = shadowsEdge.getItemMeta();
					shadowsEdgeMeta.setDisplayName(ChatColor.RED + "Shadow's Edge");
					List<String> shadowsEdgeLore = new ArrayList<String>();
					shadowsEdgeLore.add("");
					shadowsEdgeLore.add(ChatColor.ITALIC + "Consumes the souls");
					shadowsEdgeLore.add(ChatColor.ITALIC + "of its victims...");
					shadowsEdgeLore.add(ChatColor.AQUA +"- Weakens the enemy");
					shadowsEdgeLore.add("");
					shadowsEdgeLore.add(ChatColor.GRAY + "Kills: " + 0);
					shadowsEdgeLore.add(ChatColor.GRAY + "Times repaired: " + 0);
					shadowsEdgeMeta.setLore(shadowsEdgeLore);
					shadowsEdge.setItemMeta(shadowsEdgeMeta);
					player.getInventory().addItem(shadowsEdge);
					
					//Medusa
					ItemStack medusa = new ItemStack(Material.BOW);
					medusa.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 7);
					medusa.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
					medusa.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
					medusa.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
					ItemMeta medusaMeta = medusa.getItemMeta();
					medusaMeta.setDisplayName(ChatColor.RED + "Medusa");
					List<String> medusaLore = new ArrayList<String>();
					medusaLore.add("");
					medusaLore.add(ChatColor.ITALIC + "Found in the woods");
					medusaLore.add(ChatColor.ITALIC + "surrounded by statues...");
					medusaLore.add(ChatColor.AQUA + "- Poisons the enemy");
					medusaLore.add("");
					medusaLore.add(ChatColor.GRAY + "Kills: " + 0);
					medusaLore.add(ChatColor.GRAY + "Times repaired: " + 0);
					medusaMeta.setLore(medusaLore);
					medusa.setItemMeta(medusaMeta);
					player.getInventory().addItem(medusa);
					
					//Flameheart
					ItemStack flameheart = new ItemStack(Material.BOW);
					flameheart.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 8);
					flameheart.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
					flameheart.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
					flameheart.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 6);
					flameheart.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 4);
					ItemMeta flameheartMeta = flameheart.getItemMeta();
					flameheartMeta.setDisplayName(ChatColor.RED + "Flameheart");
					List<String> flameheartLore = new ArrayList<String>();
					flameheartLore.add("");
					flameheartLore.add(ChatColor.ITALIC + "Crafted using the fire");
					flameheartLore.add(ChatColor.ITALIC + "of the phoenix Al'ar,");
					flameheartLore.add(ChatColor.ITALIC + "causing the string to");
					flameheartLore.add(ChatColor.ITALIC + "fire searing arrows...");
					flameheartLore.add("");
					flameheartLore.add(ChatColor.GRAY + "Kills: " + 0);
					flameheartLore.add(ChatColor.GRAY + "Times repaired: " + 0);
					flameheartMeta.setLore(flameheartLore);
					flameheart.setItemMeta(flameheartMeta);
					player.getInventory().addItem(flameheart);
					
					//Kamatayon
					ItemStack kamatayon = new ItemStack(Material.DIAMOND_HOE);
					kamatayon.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
					kamatayon.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
					kamatayon.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 10);
					ItemMeta kamatayonMeta = kamatayon.getItemMeta();
					kamatayonMeta.setDisplayName(ChatColor.RED + "Kamatayon");
					List<String> kamatayonLore = new ArrayList<String>();
					kamatayonLore.add("");
					kamatayonLore.add(ChatColor.ITALIC + "Used by ancient Colombian");
					kamatayonLore.add(ChatColor.ITALIC + "tribes long ago...");
					kamatayonLore.add(ChatColor.AQUA + "- Confuses the enemy");
					kamatayonLore.add(ChatColor.AQUA + "- Withers the enemy");
					kamatayonLore.add("");
					kamatayonLore.add(ChatColor.GRAY + "Kills: " + 0);
					kamatayonLore.add(ChatColor.GRAY + "Times repaired: " + 0);
					kamatayonMeta.setLore(kamatayonLore);
					kamatayon.setItemMeta(kamatayonMeta);
					player.getInventory().addItem(kamatayon);
					
					//Armor
					
					//Daedric Helm
					ItemStack daedricHelm = new ItemStack(Material.DIAMOND_HELMET);
					daedricHelm.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6);
					daedricHelm.addUnsafeEnchantment(Enchantment.WATER_WORKER, 1);
					daedricHelm.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
					daedricHelm.addUnsafeEnchantment(Enchantment.OXYGEN, 5);
					ItemMeta daedricHelmMeta = daedricHelm.getItemMeta();
					daedricHelmMeta.setDisplayName(ChatColor.RED + "Daedric Helm");
					List<String> daedricHelmLore = new ArrayList<String>();
					daedricHelmLore.add("");
					daedricHelmLore.add(ChatColor.ITALIC + "Taken from a dying priest");
					daedricHelmLore.add(ChatColor.ITALIC + "muttering 'bloodmoon'...");
					daedricHelmLore.add(ChatColor.AQUA + "- Increase health by 20%");
					daedricHelmLore.add(ChatColor.AQUA + "- Grants the user fire resistance");
					daedricHelmLore.add("");
					daedricHelmLore.add(ChatColor.GRAY + "Times repaired: " + 0);
					daedricHelmMeta.setLore(daedricHelmLore);
					daedricHelm.setItemMeta(daedricHelmMeta);
					player.getInventory().addItem(daedricHelm);
					
					//Theseus
					ItemStack theseus = new ItemStack(Material.DIAMOND_CHESTPLATE);
					theseus.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6);
					theseus.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 9);
					theseus.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 8);
					theseus.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 5);
					theseus.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
					ItemMeta theseusMeta = theseus.getItemMeta();
					theseusMeta.setDisplayName(ChatColor.RED + "Theseus");
					List<String> theseusLore = new ArrayList<String>();
					theseusLore.add("");
					theseusLore.add(ChatColor.ITALIC + "Said to have belonged to an");
					theseusLore.add(ChatColor.ITALIC + "ancient warrior long ago...");
					theseusLore.add(ChatColor.AQUA + "- Grants the user Strength II");
					theseusLore.add("");
					theseusLore.add(ChatColor.GRAY + "Times repaired: " + 0);
					theseusMeta.setLore(theseusLore);
					theseus.setItemMeta(theseusMeta);
					player.getInventory().addItem(theseus);
					
					//Warlord Cuirass
					ItemStack warlordCuirass = new ItemStack(Material.NETHERITE_CHESTPLATE);
					warlordCuirass.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6);
					warlordCuirass.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 7);
					warlordCuirass.addUnsafeEnchantment(Enchantment.THORNS, 4);
					warlordCuirass.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
					ItemMeta warlordCuirassMeta = warlordCuirass.getItemMeta();
					warlordCuirassMeta.setDisplayName(ChatColor.RED + "Warlord Cuirass");
					List<String> warlordCuirassLore = new ArrayList<String>();
					warlordCuirassLore.add("");
					warlordCuirassLore.add(ChatColor.ITALIC + "Found on the corpse of an");
					warlordCuirassLore.add(ChatColor.ITALIC + "orc with the name 'Orgim'");
					warlordCuirassLore.add(ChatColor.ITALIC + "engraved on the breastplate...");
					warlordCuirassLore.add(ChatColor.AQUA + "- Increase health by 40%");
					warlordCuirassLore.add("");
					warlordCuirassLore.add(ChatColor.GRAY + "Times repaired: " + 0);
					warlordCuirassMeta.setLore(warlordCuirassLore);
					warlordCuirass.setItemMeta(warlordCuirassMeta);
					player.getInventory().addItem(warlordCuirass);
					
					//Hermes Leggings
					ItemStack hermesLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);
					hermesLeggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6);
					hermesLeggings.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 6);
					hermesLeggings.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 5);
					hermesLeggings.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
					ItemMeta hermesLeggingsMeta = hermesLeggings.getItemMeta();
					hermesLeggingsMeta.setDisplayName(ChatColor.RED + "Hermes Leggings");
					List<String> hermesLeggingsLore = new ArrayList<String>();
					hermesLeggingsLore.add("");
					hermesLeggingsLore.add(ChatColor.ITALIC + "Worn by the Greek");
					hermesLeggingsLore.add(ChatColor.ITALIC + "God of trade...");
					hermesLeggingsLore.add(ChatColor.AQUA + "- Grants the user Speed II");
					hermesLeggingsLore.add(ChatColor.AQUA + "- Increase health by 20%");
					hermesLeggingsLore.add("");
					hermesLeggingsLore.add(ChatColor.GRAY + "Times repaired: " + 0);
					hermesLeggingsMeta.setLore(hermesLeggingsLore);
					hermesLeggings.setItemMeta(hermesLeggingsMeta);
					player.getInventory().addItem(hermesLeggings);
					
					//Brightwing
					ItemStack brightwing = new ItemStack(Material.DIAMOND_BOOTS);
					brightwing.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6);
					brightwing.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
					brightwing.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 2);
					brightwing.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
					ItemMeta brightwingMeta = brightwing.getItemMeta();
					brightwingMeta.setDisplayName(ChatColor.RED + "Brightwing");
					List<String> brightwingLore = new ArrayList<String>();
					brightwingLore.add("");
					brightwingLore.add(ChatColor.ITALIC + "'As light as a feather, as silent");
					brightwingLore.add(ChatColor.ITALIC + "as a mouse - H.B.' is engraved");
					brightwingLore.add(ChatColor.ITALIC + "on the bottom of the left boot...");
					brightwingLore.add(ChatColor.AQUA + "- Invulnerable to fall damage");
					brightwingLore.add(ChatColor.AQUA + "- Increase health by 20%");
					brightwingLore.add("");
					brightwingLore.add(ChatColor.GRAY + "Times repaired: " + 0);
					brightwingMeta.setLore(brightwingLore);
					brightwing.setItemMeta(brightwingMeta);
					player.getInventory().addItem(brightwing);
					
					
					
					//Shadow Seeker
					ItemStack shadowSeeker = new ItemStack(Material.STONE_SWORD);
					shadowSeeker.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
					shadowSeeker.addUnsafeEnchantment(Enchantment.DURABILITY, 30);
					ItemMeta shadowSeekerMeta = shadowSeeker.getItemMeta();
					shadowSeekerMeta.setDisplayName(ChatColor.RED + "Shadow Seeker");
					List<String> shadowSeekerLore = new ArrayList<String>();
					shadowSeekerLore.add("");
					shadowSeekerLore.add(ChatColor.ITALIC + "Ancient untranslatable runes");
					shadowSeekerLore.add(ChatColor.ITALIC + "run down the side of the blade");
					shadowSeekerLore.add(ChatColor.ITALIC + "in the midst of battle...");
					shadowSeekerLore.add(ChatColor.AQUA + "- Grounds the enemy");
					shadowSeekerLore.add("");
					shadowSeekerLore.add(ChatColor.GRAY + "Kills: " + 0);
					shadowSeekerLore.add(ChatColor.GRAY + "Times repaired: " + 0);
					shadowSeekerMeta.setLore(shadowSeekerLore);
					shadowSeeker.setItemMeta(shadowSeekerMeta);
					player.getInventory().addItem(shadowSeeker);
					
					//Fisherman's Friend
					ItemStack fishermansFriend = new ItemStack(Material.FISHING_ROD);
					fishermansFriend.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
					fishermansFriend.addUnsafeEnchantment(Enchantment.LUCK, 6);
					fishermansFriend.addUnsafeEnchantment(Enchantment.LURE, 5);
					fishermansFriend.addUnsafeEnchantment(Enchantment.DURABILITY, 9);
					ItemMeta fishermansFriendMeta = fishermansFriend.getItemMeta();
					fishermansFriendMeta.setDisplayName(ChatColor.RED + "Fisherman's Friend");
					List<String> fishermansFriendLore = new ArrayList<String>();
					fishermansFriendLore.add("");
					fishermansFriendLore.add(ChatColor.ITALIC + "Found at the bottom of");
					fishermansFriendLore.add(ChatColor.ITALIC + "Trader's Bay Lake...");
					fishermansFriendLore.add("");
					fishermansFriendLore.add(ChatColor.GRAY + "Kills: " + 0);
					fishermansFriendLore.add(ChatColor.GRAY + "Times repaired: " + 0);
					fishermansFriendMeta.setLore(fishermansFriendLore);
					fishermansFriend.setItemMeta(fishermansFriendMeta);
					player.getInventory().addItem(fishermansFriend);
					
					//Cane of Zefeus
					ItemStack caneOfZefeus = new ItemStack(Material.BLAZE_ROD);
					caneOfZefeus.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
					caneOfZefeus.addUnsafeEnchantment(Enchantment.KNOCKBACK, 10);
					ItemMeta caneOfZefeusMeta = caneOfZefeus.getItemMeta();
					caneOfZefeusMeta.setDisplayName(ChatColor.RED + "Cane of Zefeus");
					List<String> caneOfZefeusLore = new ArrayList<String>();
					caneOfZefeusLore.add("");
					caneOfZefeusLore.add(ChatColor.ITALIC + "Said to have corrupted");
					caneOfZefeusLore.add(ChatColor.ITALIC + "the powerful wizard");
					caneOfZefeusLore.add(ChatColor.ITALIC + "Zefeus...");
					caneOfZefeusLore.add("");
					caneOfZefeusLore.add(ChatColor.GRAY + "Kills: " + 0);
					caneOfZefeusLore.add("");
					caneOfZefeusMeta.setLore(caneOfZefeusLore);
					caneOfZefeus.setItemMeta(caneOfZefeusMeta);
					player.getInventory().addItem(caneOfZefeus);
					
					//Legendary Key
					ItemStack legendaryKey = new ItemStack(Material.TRIPWIRE_HOOK);
					legendaryKey.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1);
					ItemMeta legendaryKeyMeta = legendaryKey.getItemMeta();
					legendaryKeyMeta.setDisplayName("" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Legendary Key");
					List<String> legendaryKeyLore = new ArrayList<String>();
					legendaryKeyLore.add("Opens Legendary Chest");
					legendaryKeyMeta.setLore(legendaryKeyLore);
					legendaryKey.setItemMeta(legendaryKeyMeta);
					player.getInventory().addItem(legendaryKey);
					
					//Okeanos Soul Fragment
					ItemStack okeanos = new ItemStack(Material.COD);
					okeanos.addUnsafeEnchantment(Enchantment.WATER_WORKER, 1);
					ItemMeta okeanosMeta = okeanos.getItemMeta();
					okeanosMeta.setDisplayName(ChatColor.of("#00bdff") + "" + ChatColor.BOLD + "O" + ChatColor.of("#00b2ff") + ChatColor.BOLD + "k" + ChatColor.of("#00a7fe") + ChatColor.BOLD + "e" + ChatColor.of("#009cff") + ChatColor.BOLD + "a" + ChatColor.of("#0091ff") + ChatColor.BOLD + "n" + ChatColor.of("#0086ff") + ChatColor.BOLD + "o" + ChatColor.of("#007bff") + ChatColor.BOLD + "s");
					List<String> okeanosLore = new ArrayList<String>();
					okeanosLore.add(ChatColor.of("#6dafd0") + "Soul fragment of the primordial");
					okeanosLore.add(ChatColor.of("#6dafd0") + "Titan god, Okeanos");
					okeanosMeta.setLore(okeanosLore);
					okeanos.setItemMeta(okeanosMeta);
					player.getInventory().addItem(okeanos);	

					//Dalabrus
					ItemStack dalabrus = new ItemStack(Material.DIAMOND_PICKAXE);
					dalabrus.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
					dalabrus.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 5);
					dalabrus.addUnsafeEnchantment(Enchantment.DIG_SPEED, 7);
					dalabrus.addUnsafeEnchantment(Enchantment.DURABILITY, 9);
					ItemMeta dalabrusMeta = dalabrus.getItemMeta();
					dalabrusMeta.setDisplayName(ChatColor.RED + "Dalabrus");
					List<String> dalabrusLore = new ArrayList<String>();
					dalabrusLore.add("");
					dalabrusLore.add(ChatColor.ITALIC + "Found under the corpse of");
					dalabrusLore.add(ChatColor.ITALIC + "a Dwarf large in stature in");
					dalabrusLore.add(ChatColor.ITALIC + "the deepest caves of Moria...");
					dalabrusLore.add("");
					dalabrusLore.add(ChatColor.GRAY + "Kills: " + 0);
					dalabrusLore.add(ChatColor.GRAY + "Times repaired: " + 0);
					dalabrusMeta.setLore(dalabrusLore);
					dalabrus.setItemMeta(dalabrusMeta);
					player.getInventory().addItem(dalabrus);
					
					return true;

				}
				else {
					player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "You must be a player to do that.");
			}
		}
		else if (cmd.getName().equalsIgnoreCase("giverepair")) {
			
			if (sender.hasPermission("customgod.giverepair")) {
				
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "Incorrect usage, try /giverepair [username]");
				}
				else {
					String username = args[0];
					if (Bukkit.getServer().getOfflinePlayer(username) != null) {
						OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(username);
						UUID uuid = offlinePlayer.getUniqueId();
						addRepair(uuid.toString());
						sender.sendMessage(ChatColor.GREEN + "Given God Repair to " + username);
					}
					else {
						sender.sendMessage(ChatColor.RED + "Couldn't find player " + username);
					}
				}
			}
		}
		return false;
	}
	
	private void addGlobalRepairs(int ticks) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
			
			@Override
			public void run() {
				//Loop through list of people with god repairs, add 1 if they have 0
				Set<String> uuids = config.getConfigurationSection("repairs").getKeys(false);
				for (String uuid : uuids) {
					int numberOfRepairs = config.getInt("repairs." + uuid);
					if (numberOfRepairs == 0) {
						OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
						addRepair(uuid);
					}
				}
				
				//Set new date for repairing 1 week in future
				Date now = new Date();
				Date nextGlobalRepairDate = addDaysToJavaUtilDate(now, 7);
				long nextGlobalRepairDateInMillis = nextGlobalRepairDate.getTime();
				long nextGlobalRepairDateInSeconds = nextGlobalRepairDateInMillis / 1000;
				
				config.set("time to add repair", nextGlobalRepairDate);
				saveConfig();
				
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "[God Forge] " + ChatColor.BLUE + ChatColor.BOLD + "God repairs have been refreshed!"); 
			}		
		}, ticks);
	}
	
	
	
	private void addRepair(String uuid) {
		if (config.get("repairs." + uuid) == null) {
			config.set("repairs." + uuid, 1);
		}
		else {
			config.set("repairs." + uuid, config.getInt("repairs." + uuid) + 1);
		}
		saveConfig();
		
		OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(UUID.fromString(uuid));
		if (offlinePlayer.isOnline()) {
			Player player = (Player) offlinePlayer;
			player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1f, 1f);
			player.sendMessage(ChatColor.RED + "[God Forge] " + ChatColor.BLUE + "Your God Repair has been refreshed.");
		}
	}
	
	private void removeRepair(String uuid) {
		if (config.get("repairs." + uuid) == null) {
			return;
		}
		else {
			config.set("repairs." + uuid, null);
		}
		saveConfig();
	}
	
	public Date addDaysToJavaUtilDate(Date date, int days) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.DAY_OF_YEAR, days);
	    return calendar.getTime();
	}
	
	//Disable upgrade to netherite in smithing table
	@EventHandler
	public void onInventoryClick (InventoryClickEvent event) {
		if (event.getInventory() instanceof SmithingInventory) {
			
			Player player = (Player) event.getWhoClicked();
			
			SmithingInventory smithingTable = (SmithingInventory) event.getInventory();
			ItemStack input = smithingTable.getItem(0);
			ItemStack addition = smithingTable.getItem(1);
			ItemStack result = smithingTable.getItem(2);
			int rawSlot = event.getRawSlot();
			
			//Early cancel so can artificially build the event
			if (rawSlot == 2 && input != null && addition != null && result != null) {
				if (GodItems.isGod(input)) { //If player clicks the result box
	        		event.setCancelled(true);
	        		player.sendMessage(ChatColor.RED + "You cannot upgrade God items.");
	        		player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
	        	}
			}
		}
	}
	
}
