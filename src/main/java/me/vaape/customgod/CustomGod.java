package me.vaape.customgod;

import com.codingforcookies.armorequip.ArmorListener;
import com.github.crashdemons.playerheads.api.PlayerHeads;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class CustomGod extends JavaPlugin implements Listener {

    public static HashMap<String, ItemStack> godItems = new HashMap<>();

    private FileConfiguration config = this.getConfig();

    public static CustomGod instance;

    public static CustomGod getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(this, this);
        loadConfiguration();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "CustomGod has been enabled!");

        DamageListener damageListener = new DamageListener();
        this.getServer().getPluginManager().registerEvents(damageListener, instance);
        KillListener killListener = new KillListener();
        this.getServer().getPluginManager().registerEvents(killListener, instance);
        ForgeListener forgeListener = new ForgeListener();
        this.getServer().getPluginManager().registerEvents(forgeListener, instance);
        EnhancingForge enhancingForge = new EnhancingForge();
        this.getServer().getPluginManager().registerEvents(enhancingForge, instance);
        ArmourListener armourListener = new ArmourListener();
        this.getServer().getPluginManager().registerEvents(armourListener, instance);
        armourListener = new ArmourListener();
        this.getServer().getPluginManager().registerEvents(new ArmorListener(new ArrayList<>()), instance);
        ItemUpdater itemUpdater = new ItemUpdater();
        this.getServer().getPluginManager().registerEvents(itemUpdater, instance);
        Easter2022Items easter2022Items = new Easter2022Items(instance);
        this.getServer().getPluginManager().registerEvents(easter2022Items, instance);


        loadGodItems();

    }

    public void loadConfiguration() {
        final FileConfiguration config = this.getConfig();
        config.set(("time of server start"), new Date());
        config.options().copyDefaults(true);
        saveConfig();
    }

    public void onDisable() {
        instance = null;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("givegod")) {

            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (player.hasPermission("customgod.givegod")) {

                    for (String godItemName : godItems.keySet()) {
                        //Skip snowy items
                        if (godItemName.contains("Snowy") || godItemName.contains("Glacial") || godItemName.contains("Ice Pick")) continue;
                        if (godItemName.contains("Valentine") || godItemName.contains("Cupid") || godItemName.contains("Love Letter")) continue;
                        if (godItemName.contains("Easter") || godItemName.contains("Bunny")) continue;

                        player.getInventory().addItem(godItems.get(godItemName));
                    }

                    return true;

                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You must be a player to do that.");
            }
        }

        if (cmd.getName().equalsIgnoreCase("givesnowy")) {

            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (player.hasPermission("customgod.givesnowy")) {

                    for (String godItemName : godItems.keySet()) {
                        //Only Snowy items
                        if (!(godItemName.contains("Snowy") || godItemName.contains("Glacial") || godItemName.contains("Ice Pick"))) continue;
                        player.getInventory().addItem(godItems.get(godItemName));
                    }

                    return true;

                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You must be a player to do that.");
            }
        }

        if (cmd.getName().equalsIgnoreCase("giveval")) {

            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (player.hasPermission("customgod.giveval")) {

                    for (String godItemName : godItems.keySet()) {
                        //Skip snowy items
                        if (!(godItemName.contains("Valentine") || godItemName.contains("Cupid") || godItemName.contains("Love Letter"))) continue;
                        player.getInventory().addItem(godItems.get(godItemName));
                    }

                    return true;

                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You must be a player to do that.");
            }
        }

        if (cmd.getName().equalsIgnoreCase("giveeaster")) {

            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (player.hasPermission("customgod.giveeaster")) {

                    for (String godItemName : godItems.keySet()) {
                        //Skip snowy items
                        if (!(godItemName.contains("Easter") || godItemName.contains("Bunny"))) continue;
                        player.getInventory().addItem(godItems.get(godItemName));
                    }

                    return true;

                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You must be a player to do that.");
            }
        }
        return false;
    }

    public void loadGodItems() {
        //Runeblade
        ItemStack runeblade = new ItemStack(Material.GOLDEN_SWORD);
        runeblade.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 27);
        runeblade.addUnsafeEnchantment(Enchantment.DURABILITY, 50);
        runeblade.addUnsafeEnchantment(Enchantment.MENDING, 10);
        ItemMeta runebladeMeta = runeblade.getItemMeta();
        runebladeMeta.setDisplayName(ChatColor.of("#fca800") + "" + ChatColor.BOLD + "R" + ChatColor.of("#fcad00") + ChatColor.BOLD + "u" + ChatColor.of("#fcb200") + ChatColor.BOLD + "n" + ChatColor.of("#fcb700") + ChatColor.BOLD + "e" + ChatColor.of("#fcbc00") + ChatColor.BOLD + "b" + ChatColor.of("#fcc000") + ChatColor.BOLD + "l" + ChatColor.of("#fcc500") + ChatColor.BOLD + "a" + ChatColor.of("#fcca00") + ChatColor.BOLD + "d" + ChatColor.of("#fccf00") + ChatColor.BOLD + "e");
        runebladeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS); //Hide enchants to put them in lore manually
        List<String> runebladeLore = new ArrayList<String>();
        runebladeLore.add(ChatColor.of("#ffbf00") + "" + ChatColor.ITALIC + "Sharpness XXX");
        runebladeLore.add(ChatColor.of("#ffbf00") + "" + ChatColor.ITALIC + "Unbreaking X");
        runebladeLore.add(ChatColor.of("#ffbf00") + "" + ChatColor.ITALIC + "Mending X");
        runebladeLore.add("");
        runebladeLore.add(ChatColor.DARK_PURPLE + "" + ChatColor.UNDERLINE + "The Blade of the Fallen Mortal");
        runebladeLore.add(ChatColor.ITALIC + "Curses the souls of enemies");
        runebladeLore.add(ChatColor.ITALIC + "with every strike, binding them");
        runebladeLore.add(ChatColor.ITALIC + "to a fate worse than death...");
        runebladeLore.add(ChatColor.AQUA + "- Corrupts the enemy");
        runebladeLore.add("");
        runebladeLore.add(ChatColor.of("#ffbf00") + "Kills: " + 0);
        runebladeMeta.setLore(runebladeLore);

        runeblade.setItemMeta(runebladeMeta);
        godItems.put("Runeblade", runeblade);

        //Mjolnir
        ItemStack mjolnir = new ItemStack(Material.IRON_AXE);
        mjolnir.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 15);
        mjolnir.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);
        mjolnir.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        mjolnir.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 3);
        mjolnir.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 10);
        mjolnir.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 10);
        ItemMeta mjolnirMeta = mjolnir.getItemMeta();
        mjolnirMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Mj" + "\u00F6" + "lnir");
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
        godItems.put("Mjolnir", mjolnir);

        //Sulfuron
        ItemStack sulfuron = new ItemStack(Material.NETHERITE_SWORD);
        sulfuron.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 21);
        sulfuron.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
        sulfuron.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
        sulfuron.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 6);
        sulfuron.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 4);
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
        godItems.put("Sulfuron", sulfuron);

        //Anduril
        ItemStack anduril = new ItemStack(Material.DIAMOND_SWORD);
        anduril.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 20);
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
        godItems.put("Anduril", anduril);

        //Shadow's Edge
        ItemStack shadowsEdge = new ItemStack(Material.NETHERITE_AXE);
        shadowsEdge.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 20);
        shadowsEdge.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        shadowsEdge.addUnsafeEnchantment(Enchantment.DIG_SPEED, 6);
        shadowsEdge.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 5);
        ItemMeta shadowsEdgeMeta = shadowsEdge.getItemMeta();
        shadowsEdgeMeta.setDisplayName(ChatColor.RED + "Shadow's Edge");
        List<String> shadowsEdgeLore = new ArrayList<String>();
        shadowsEdgeLore.add("");
        shadowsEdgeLore.add(ChatColor.ITALIC + "Consumes the souls");
        shadowsEdgeLore.add(ChatColor.ITALIC + "of its victims...");
        shadowsEdgeLore.add(ChatColor.AQUA + "- Withers the enemy");
        shadowsEdgeLore.add("");
        shadowsEdgeLore.add(ChatColor.GRAY + "Kills: " + 0);
        shadowsEdgeLore.add(ChatColor.GRAY + "Times repaired: " + 0);
        shadowsEdgeMeta.setLore(shadowsEdgeLore);
        shadowsEdge.setItemMeta(shadowsEdgeMeta);
        godItems.put("Shadow's Edge", shadowsEdge);

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
        godItems.put("Medusa", medusa);

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
        godItems.put("Flameheart", flameheart);

        //Kamatayon
        ItemStack kamatayon = new ItemStack(Material.DIAMOND_HOE);
        kamatayon.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 6);
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
        godItems.put("Kamatayon", kamatayon);

        //Armor

        //Daedric Helm
        ItemStack daedricHelm = new ItemStack(Material.DIAMOND_HELMET);
        daedricHelm.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        daedricHelm.addUnsafeEnchantment(Enchantment.WATER_WORKER, 2);
        daedricHelm.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        daedricHelm.addUnsafeEnchantment(Enchantment.OXYGEN, 5);
        ItemMeta daedricHelmMeta = daedricHelm.getItemMeta();
        daedricHelmMeta.setDisplayName(ChatColor.RED + "Daedric Helm");
        List<String> daedricHelmLore = new ArrayList<String>();
        daedricHelmLore.add("");
        daedricHelmLore.add(ChatColor.ITALIC + "Taken from a dying priest");
        daedricHelmLore.add(ChatColor.ITALIC + "muttering 'bloodmoon'...");
        daedricHelmLore.add(ChatColor.AQUA + "- Increase health by 20%");
        daedricHelmLore.add(ChatColor.AQUA + "- Grants the user Fire Resistance");
        daedricHelmLore.add(ChatColor.AQUA + "- Ultra durable");
        daedricHelmLore.add("");
        daedricHelmLore.add(ChatColor.GRAY + "Times repaired: " + 0);
        daedricHelmMeta.setLore(daedricHelmLore);

        AttributeModifier daedricHelmArmor = new AttributeModifier(UUID.randomUUID(), "generic.Armor", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        AttributeModifier daedricHelmMaxHealth = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        AttributeModifier daedricHelmToughness = new AttributeModifier(UUID.randomUUID(), "generic.ArmorToughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);

        daedricHelmMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, daedricHelmArmor);
        daedricHelmMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, daedricHelmToughness);
        daedricHelmMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, daedricHelmMaxHealth);

        daedricHelm.setItemMeta(daedricHelmMeta);
        godItems.put("Daedric Helm", daedricHelm);

        //Theseus
        ItemStack theseus = new ItemStack(Material.DIAMOND_CHESTPLATE);
        theseus.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        theseus.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
        theseus.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 8);
        theseus.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        ItemMeta theseusMeta = theseus.getItemMeta();
        theseusMeta.setDisplayName(ChatColor.RED + "Theseus");
        List<String> theseusLore = new ArrayList<String>();
        theseusLore.add("");
        theseusLore.add(ChatColor.ITALIC + "Said to have belonged to an");
        theseusLore.add(ChatColor.ITALIC + "ancient warrior long ago...");
        theseusLore.add(ChatColor.AQUA + "- Grants the user Strength II");
        theseusLore.add(ChatColor.AQUA + "- Ultra durable");
        theseusLore.add("");
        theseusLore.add(ChatColor.GRAY + "Times repaired: " + 0);
        theseusMeta.setLore(theseusLore);

        AttributeModifier theseusArmor = new AttributeModifier(UUID.randomUUID(), "generic.Armor", 8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        AttributeModifier theseusToughness = new AttributeModifier(UUID.randomUUID(), "generic.ArmorToughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);

        theseusMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, theseusArmor);
        theseusMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, theseusToughness);

        theseus.setItemMeta(theseusMeta);
        godItems.put("Theseus", theseus);

        //Warlord Cuirass
        ItemStack warlordCuirass = new ItemStack(Material.NETHERITE_CHESTPLATE);
        warlordCuirass.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        warlordCuirass.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
        warlordCuirass.addUnsafeEnchantment(Enchantment.THORNS, 4);
        warlordCuirass.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        ItemMeta warlordCuirassMeta = warlordCuirass.getItemMeta();
        warlordCuirassMeta.setDisplayName(ChatColor.RED + "Warlord Cuirass");
        List<String> warlordCuirassLore = new ArrayList<String>();
        warlordCuirassLore.add("");
        warlordCuirassLore.add(ChatColor.ITALIC + "Found on the corpse of an");
        warlordCuirassLore.add(ChatColor.ITALIC + "orc with the name 'Orgim'");
        warlordCuirassLore.add(ChatColor.ITALIC + "engraved on the breastplate...");
        warlordCuirassLore.add(ChatColor.AQUA + "- Increase health by 30%");
        warlordCuirassLore.add(ChatColor.AQUA + "- Ultra durable");
        warlordCuirassLore.add("");
        warlordCuirassLore.add(ChatColor.GRAY + "Times repaired: " + 0);
        warlordCuirassMeta.setLore(warlordCuirassLore);

        AttributeModifier warlordCuirassArmor = new AttributeModifier(UUID.randomUUID(), "generic.Armor", 8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        AttributeModifier warlordCuirassMaxHealth = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        AttributeModifier warlordCuirassToughness = new AttributeModifier(UUID.randomUUID(), "generic.ArmorToughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);

        warlordCuirassMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, warlordCuirassArmor);
        warlordCuirassMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, warlordCuirassToughness);
        warlordCuirassMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, warlordCuirassMaxHealth);

        warlordCuirass.setItemMeta(warlordCuirassMeta);
        godItems.put("Warlord Cuirass", warlordCuirass);

        //Hermes Leggings
        ItemStack hermesLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        hermesLeggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        hermesLeggings.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
        hermesLeggings.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 5);
        hermesLeggings.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        ItemMeta hermesLeggingsMeta = hermesLeggings.getItemMeta();
        hermesLeggingsMeta.setDisplayName(ChatColor.RED + "Hermes Leggings");
        List<String> hermesLeggingsLore = new ArrayList<String>();
        hermesLeggingsLore.add("");
        hermesLeggingsLore.add(ChatColor.ITALIC + "Worn by the Greek");
        hermesLeggingsLore.add(ChatColor.ITALIC + "God of trade...");
        hermesLeggingsLore.add(ChatColor.AQUA + "- Grants the user Speed II");
        hermesLeggingsLore.add(ChatColor.AQUA + "- Increase health by 20%");
        hermesLeggingsLore.add(ChatColor.AQUA + "- Ultra durable");
        hermesLeggingsLore.add("");
        hermesLeggingsLore.add(ChatColor.GRAY + "Times repaired: " + 0);
        hermesLeggingsMeta.setLore(hermesLeggingsLore);

        AttributeModifier hermesLeggingsArmor = new AttributeModifier(UUID.randomUUID(), "generic.Armor", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        AttributeModifier hermesLeggingsMaxHealth = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        AttributeModifier hermesLeggingsToughness = new AttributeModifier(UUID.randomUUID(), "generic.ArmorToughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);

        hermesLeggingsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, hermesLeggingsArmor);
        hermesLeggingsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, hermesLeggingsToughness);
        hermesLeggingsMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, hermesLeggingsMaxHealth);

        hermesLeggings.setItemMeta(hermesLeggingsMeta);
        godItems.put("Hermes Leggings", hermesLeggings);

        //Brightwing
        ItemStack brightwing = new ItemStack(Material.DIAMOND_BOOTS);
        brightwing.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        brightwing.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
        brightwing.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 4);
        brightwing.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        ItemMeta brightwingMeta = brightwing.getItemMeta();
        brightwingMeta.setDisplayName(ChatColor.RED + "Brightwing");
        List<String> brightwingLore = new ArrayList<String>();
        brightwingLore.add("");
        brightwingLore.add(ChatColor.ITALIC + "'As light as a feather, as silent");
        brightwingLore.add(ChatColor.ITALIC + "as a mouse - H.B.' is engraved");
        brightwingLore.add(ChatColor.ITALIC + "on the bottom of the left boot...");
        brightwingLore.add(ChatColor.AQUA + "- Invulnerable to fall damage");
        brightwingLore.add(ChatColor.AQUA + "- Increase health by 20%");
        brightwingLore.add(ChatColor.AQUA + "- Ultra durable");
        brightwingLore.add("");
        brightwingLore.add(ChatColor.GRAY + "Times repaired: " + 0);
        brightwingMeta.setLore(brightwingLore);

        AttributeModifier brightwingArmor = new AttributeModifier(UUID.randomUUID(), "generic.Armor", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        AttributeModifier brightwingMaxHealth = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        AttributeModifier brightwingToughness = new AttributeModifier(UUID.randomUUID(), "generic.ArmorToughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);

        brightwingMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, brightwingArmor);
        brightwingMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, brightwingToughness);
        brightwingMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, brightwingMaxHealth);

        brightwing.setItemMeta(brightwingMeta);
        godItems.put("Brightwing", brightwing);


        //Shadow Seeker
        ItemStack shadowSeeker = new ItemStack(Material.STONE_SWORD);
        shadowSeeker.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 18);
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
        godItems.put("Shadow Seeker", shadowSeeker);

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
        godItems.put("Fisherman's Friend", fishermansFriend);

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
        godItems.put("Cane of Zefeus", caneOfZefeus);

        //Legendary Key
        ItemStack legendaryKey = new ItemStack(Material.TRIPWIRE_HOOK);
        legendaryKey.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1);
        ItemMeta legendaryKeyMeta = legendaryKey.getItemMeta();
        legendaryKeyMeta.setDisplayName("" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Legendary Key");
        List<String> legendaryKeyLore = new ArrayList<String>();
        legendaryKeyLore.add("Opens Legendary Chest");
        legendaryKeyMeta.setLore(legendaryKeyLore);
        legendaryKey.setItemMeta(legendaryKeyMeta);
        godItems.put("Legendary Key", legendaryKey);

        //Okeanos Soul Fragment
        ItemStack okeanos = new ItemStack(Material.COD);
        okeanos.addUnsafeEnchantment(Enchantment.WATER_WORKER, 1);
        ItemMeta okeanosMeta = okeanos.getItemMeta();
        okeanosMeta.setDisplayName(ChatColor.of("#00bdff") + "" + ChatColor.BOLD + "O" + ChatColor.of(
                "#00b2ff") + ChatColor.BOLD + "k" + ChatColor.of("#00a7fe") + ChatColor.BOLD + "e" + ChatColor.of("#009cff") + ChatColor.BOLD + "a" + ChatColor.of("#0091ff") + ChatColor.BOLD + "n" + ChatColor.of("#0086ff") + ChatColor.BOLD + "o" + ChatColor.of("#007bff") + ChatColor.BOLD + "s");
        List<String> okeanosLore = new ArrayList<String>();
        okeanosLore.add(ChatColor.of("#6dafd0") + "Soul fragment of the primordial");
        okeanosLore.add(ChatColor.of("#6dafd0") + "Titan god, Okeanos");
        okeanosMeta.setLore(okeanosLore);
        okeanos.setItemMeta(okeanosMeta);
        godItems.put("Okeanos", okeanos);

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
        godItems.put("Dalabrus", dalabrus);

        //Snowy gear

        //Snowy red helm
        ItemStack snowyHelmRed = new ItemStack(Material.LEATHER_HELMET);
        snowyHelmRed.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        snowyHelmRed.addUnsafeEnchantment(Enchantment.MENDING, 1);
        snowyHelmRed.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        LeatherArmorMeta snowyHelmRedMeta = (LeatherArmorMeta) snowyHelmRed.getItemMeta();
        snowyHelmRedMeta.setColor(Color.fromRGB(0xB02E26));
        snowyHelmRedMeta.setDisplayName(ChatColor.GREEN + "Snowy Helm");
        List<String> snowyHelmRedLore = new ArrayList<String>();
        snowyHelmRedLore.add("");
        snowyHelmRedLore.add(ChatColor.AQUA + "- Increase health by 30%");
        snowyHelmRedLore.add("");
        snowyHelmRedLore.add(ChatColor.GREEN + "Christmas 2021");
        snowyHelmRedMeta.setLore(snowyHelmRedLore);

        AttributeModifier snowyHelmRedArmor = new AttributeModifier(UUID.randomUUID(), "generic.Armor", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        AttributeModifier snowyHelmRedMaxHealth = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);

        snowyHelmRedMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, snowyHelmRedArmor);
        snowyHelmRedMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, snowyHelmRedMaxHealth);

        snowyHelmRed.setItemMeta(snowyHelmRedMeta);
        godItems.put("Red Snowy Helm", snowyHelmRed);

        //Snowy red chest
        ItemStack snowyChestRed = new ItemStack(Material.LEATHER_CHESTPLATE);
        snowyChestRed.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        snowyChestRed.addUnsafeEnchantment(Enchantment.MENDING, 1);
        snowyChestRed.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        LeatherArmorMeta snowyChestRedMeta = (LeatherArmorMeta) snowyChestRed.getItemMeta();
        snowyChestRedMeta.setColor(Color.fromRGB(0xB02E26));
        snowyChestRedMeta.setDisplayName(ChatColor.GREEN + "Snowy Chest");
        List<String> snowyChestRedLore = new ArrayList<String>();
        snowyChestRedLore.add("");
        snowyChestRedLore.add(ChatColor.AQUA + "- Increase health by 30%");
        snowyChestRedLore.add(ChatColor.AQUA + "- Grants the user Strength II");
        snowyChestRedLore.add("");
        snowyChestRedLore.add(ChatColor.GREEN + "Christmas 2021");
        snowyChestRedMeta.setLore(snowyChestRedLore);

        AttributeModifier snowyChestRedArmor = new AttributeModifier(UUID.randomUUID(), "generic.Armor", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        AttributeModifier snowyChestRedMaxHealth = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);

        snowyChestRedMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, snowyChestRedArmor);
        snowyChestRedMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, snowyChestRedMaxHealth);

        snowyChestRed.setItemMeta(snowyChestRedMeta);
        godItems.put("Red Snowy Chest", snowyChestRed);

        //Snowy red legs
        ItemStack snowyLegsRed = new ItemStack(Material.LEATHER_LEGGINGS);
        snowyLegsRed.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        snowyLegsRed.addUnsafeEnchantment(Enchantment.MENDING, 1);
        snowyLegsRed.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        LeatherArmorMeta snowyLegsRedMeta = (LeatherArmorMeta) snowyLegsRed.getItemMeta();
        snowyLegsRedMeta.setColor(Color.fromRGB(0xB02E26));
        snowyLegsRedMeta.setDisplayName(ChatColor.GREEN + "Snowy Legs");
        List<String> snowyLegsRedLore = new ArrayList<String>();
        snowyLegsRedLore.add("");
        snowyLegsRedLore.add(ChatColor.AQUA + "- Increase health by 30%");
        snowyLegsRedLore.add(ChatColor.AQUA + "- Grants the user Speed II");
        snowyLegsRedLore.add("");
        snowyLegsRedLore.add(ChatColor.GREEN + "Christmas 2021");
        snowyLegsRedMeta.setLore(snowyLegsRedLore);

        AttributeModifier snowyLegsRedArmor = new AttributeModifier(UUID.randomUUID(), "generic.Armor", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        AttributeModifier snowyLegsRedMaxHealth = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);

        snowyLegsRedMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, snowyLegsRedArmor);
        snowyLegsRedMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, snowyLegsRedMaxHealth);

        snowyLegsRed.setItemMeta(snowyLegsRedMeta);
        godItems.put("Red Snowy Legs", snowyLegsRed);

        //Snowy red boots
        ItemStack snowyBootsRed = new ItemStack(Material.LEATHER_BOOTS);
        snowyBootsRed.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        snowyBootsRed.addUnsafeEnchantment(Enchantment.MENDING, 1);
        snowyBootsRed.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        LeatherArmorMeta snowyBootsRedMeta = (LeatherArmorMeta) snowyBootsRed.getItemMeta();
        snowyBootsRedMeta.setColor(Color.fromRGB(0xB02E26));
        snowyBootsRedMeta.setDisplayName(ChatColor.GREEN + "Snowy Boots");
        List<String> snowyBootsRedLore = new ArrayList<String>();
        snowyBootsRedLore.add("");
        snowyBootsRedLore.add(ChatColor.AQUA + "- Increase health by 30%");
        snowyBootsRedLore.add("");
        snowyBootsRedLore.add(ChatColor.GREEN + "Christmas 2021");
        snowyBootsRedMeta.setLore(snowyBootsRedLore);

        AttributeModifier snowyBootsRedArmor = new AttributeModifier(UUID.randomUUID(), "generic.Armor", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        AttributeModifier snowyBootsRedMaxHealth = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);

        snowyBootsRedMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, snowyBootsRedArmor);
        snowyBootsRedMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, snowyBootsRedMaxHealth);

        snowyBootsRed.setItemMeta(snowyBootsRedMeta);
        godItems.put("Red Snowy Boots", snowyBootsRed);

        //GREEN
        //Snowy Green helm
        ItemStack snowyHelmGreen = new ItemStack(Material.LEATHER_HELMET);
        snowyHelmGreen.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        snowyHelmGreen.addUnsafeEnchantment(Enchantment.MENDING, 1);
        snowyHelmGreen.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        LeatherArmorMeta snowyHelmGreenMeta = (LeatherArmorMeta) snowyHelmGreen.getItemMeta();
        snowyHelmGreenMeta.setColor(Color.fromRGB(0x5E7C16));
        snowyHelmGreenMeta.setDisplayName(ChatColor.GREEN + "Snowy Helm");
        List<String> snowyHelmGreenLore = new ArrayList<String>();
        snowyHelmGreenLore.add("");
        snowyHelmGreenLore.add(ChatColor.AQUA + "- Increase health by 30%");
        snowyHelmGreenLore.add("");
        snowyHelmGreenLore.add(ChatColor.GREEN + "Christmas 2021");
        snowyHelmGreenMeta.setLore(snowyHelmGreenLore);

        AttributeModifier snowyHelmGreenArmor = new AttributeModifier(UUID.randomUUID(), "generic.Armor", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        AttributeModifier snowyHelmGreenMaxHealth = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);

        snowyHelmGreenMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, snowyHelmGreenArmor);
        snowyHelmGreenMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, snowyHelmGreenMaxHealth);

        snowyHelmGreen.setItemMeta(snowyHelmGreenMeta);
        godItems.put("Green Snowy Helm", snowyHelmGreen);

        //Snowy Green chest
        ItemStack snowyChestGreen = new ItemStack(Material.LEATHER_CHESTPLATE);
        snowyChestGreen.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        snowyChestGreen.addUnsafeEnchantment(Enchantment.MENDING, 1);
        snowyChestGreen.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        LeatherArmorMeta snowyChestGreenMeta = (LeatherArmorMeta) snowyChestGreen.getItemMeta();
        snowyChestGreenMeta.setColor(Color.fromRGB(0x5E7C16));
        snowyChestGreenMeta.setDisplayName(ChatColor.GREEN + "Snowy Chest");
        List<String> snowyChestGreenLore = new ArrayList<String>();
        snowyChestGreenLore.add("");
        snowyChestGreenLore.add(ChatColor.AQUA + "- Increase health by 30%");
        snowyChestGreenLore.add(ChatColor.AQUA + "- Grants the user Strength II");
        snowyChestGreenLore.add("");
        snowyChestGreenLore.add(ChatColor.GREEN + "Christmas 2021");
        snowyChestGreenMeta.setLore(snowyChestGreenLore);

        AttributeModifier snowyChestGreenArmor = new AttributeModifier(UUID.randomUUID(), "generic.Armor", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        AttributeModifier snowyChestGreenMaxHealth = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);

        snowyChestGreenMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, snowyChestGreenArmor);
        snowyChestGreenMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, snowyChestGreenMaxHealth);

        snowyChestGreen.setItemMeta(snowyChestGreenMeta);
        godItems.put("Green Snowy Chest", snowyChestGreen);

        //Snowy Green legs
        ItemStack snowyLegsGreen = new ItemStack(Material.LEATHER_LEGGINGS);
        snowyLegsGreen.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        snowyLegsGreen.addUnsafeEnchantment(Enchantment.MENDING, 1);
        snowyLegsGreen.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        LeatherArmorMeta snowyLegsGreenMeta = (LeatherArmorMeta) snowyLegsGreen.getItemMeta();
        snowyLegsGreenMeta.setColor(Color.fromRGB(0x5E7C16));
        snowyLegsGreenMeta.setDisplayName(ChatColor.GREEN + "Snowy Legs");
        List<String> snowyLegsGreenLore = new ArrayList<String>();
        snowyLegsGreenLore.add("");
        snowyLegsGreenLore.add(ChatColor.AQUA + "- Increase health by 30%");
        snowyLegsGreenLore.add(ChatColor.AQUA + "- Grants the user Speed II");
        snowyLegsGreenLore.add("");
        snowyLegsGreenLore.add(ChatColor.GREEN + "Christmas 2021");
        snowyLegsGreenMeta.setLore(snowyLegsGreenLore);

        AttributeModifier snowyLegsGreenArmor = new AttributeModifier(UUID.randomUUID(), "generic.Armor", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        AttributeModifier snowyLegsGreenMaxHealth = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);

        snowyLegsGreenMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, snowyLegsGreenArmor);
        snowyLegsGreenMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, snowyLegsGreenMaxHealth);

        snowyLegsGreen.setItemMeta(snowyLegsGreenMeta);
        godItems.put("Green Snowy Legs", snowyLegsGreen);

        //Snowy Green boots
        ItemStack snowyBootsGreen = new ItemStack(Material.LEATHER_BOOTS);
        snowyBootsGreen.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        snowyBootsGreen.addUnsafeEnchantment(Enchantment.MENDING, 1);
        snowyBootsGreen.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        LeatherArmorMeta snowyBootsGreenMeta = (LeatherArmorMeta) snowyBootsGreen.getItemMeta();
        snowyBootsGreenMeta.setColor(Color.fromRGB(0x5E7C16));
        snowyBootsGreenMeta.setDisplayName(ChatColor.GREEN + "Snowy Boots");
        List<String> snowyBootsGreenLore = new ArrayList<String>();
        snowyBootsGreenLore.add("");
        snowyBootsGreenLore.add(ChatColor.AQUA + "- Increase health by 30%");
        snowyBootsGreenLore.add("");
        snowyBootsGreenLore.add(ChatColor.GREEN + "Christmas 2021");
        snowyBootsGreenMeta.setLore(snowyBootsGreenLore);

        AttributeModifier snowyBootsGreenArmor = new AttributeModifier(UUID.randomUUID(), "generic.Armor", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        AttributeModifier snowyBootsGreenMaxHealth = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);

        snowyBootsGreenMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, snowyBootsGreenArmor);
        snowyBootsGreenMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, snowyBootsGreenMaxHealth);

        snowyBootsGreen.setItemMeta(snowyBootsGreenMeta);
        godItems.put("Green Snowy Boots", snowyBootsGreen);

        //Glacial Brand
        ItemStack glacialBrand = new ItemStack(Material.DIAMOND_SWORD);
        glacialBrand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 19);
        ItemMeta glacialBrandMeta = glacialBrand.getItemMeta();
        glacialBrandMeta.setDisplayName(ChatColor.GREEN + "Glacial Brand");
        List<String> glacialBrandLore = new ArrayList<String>();
        glacialBrandLore.add("");
        glacialBrandLore.add(ChatColor.ITALIC + "Your crushing blows are");
        glacialBrandLore.add(ChatColor.ITALIC + "empowered by the storm...");
        glacialBrandLore.add("");
        glacialBrandLore.add(ChatColor.AQUA + "- Freezes the enemy");
        glacialBrandLore.add("");
        glacialBrandLore.add(ChatColor.GREEN + "Christmas 2021");
        glacialBrandLore.add("");
        glacialBrandLore.add(ChatColor.GRAY + "Kills: " + 0);
        glacialBrandLore.add(ChatColor.GRAY + "Times repaired: " + 0);
        glacialBrandMeta.setLore(glacialBrandLore);

        glacialBrand.setItemMeta(glacialBrandMeta);
        godItems.put("Glacial Brand", glacialBrand);

        //Glacial bow
        ItemStack glacialBow = new ItemStack(Material.BOW);
        glacialBow.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        ItemMeta glacialBowMeta = glacialBow.getItemMeta();
        glacialBowMeta.setDisplayName(ChatColor.GREEN + "Glacial Bow");
        List<String> glacialBowLore = new ArrayList<String>();
        glacialBowLore.add("");
        glacialBowLore.add(ChatColor.ITALIC + "The soils and sands must");
        glacialBowLore.add(ChatColor.ITALIC + "yield to icy glaciers...");
        glacialBowLore.add("");
        glacialBowLore.add(ChatColor.GREEN + "Christmas 2021");
        glacialBowLore.add("");
        glacialBowLore.add(ChatColor.GRAY + "Kills: " + 0);
        glacialBowLore.add(ChatColor.GRAY + "Times repaired: " + 0);
        glacialBowMeta.setLore(glacialBowLore);

        glacialBow.setItemMeta(glacialBowMeta);
        godItems.put("Glacial Bow", glacialBow);

        //Ice Pick
        ItemStack icePick = new ItemStack(Material.DIAMOND_PICKAXE);
        icePick.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 20);
        icePick.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 2);
        icePick.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        ItemMeta icePickMeta = icePick.getItemMeta();
        icePickMeta.setDisplayName(ChatColor.GREEN + "Ice Pick");
        List<String> icePickLore = new ArrayList<String>();
        icePickLore.add("");
        icePickLore.add(ChatColor.ITALIC + "A climber's best friend");
        icePickLore.add("");
        icePickLore.add(ChatColor.GREEN + "Christmas 2021");
        icePickLore.add("");
        icePickLore.add(ChatColor.GRAY + "Kills: " + 0);
        icePickLore.add(ChatColor.GRAY + "Times repaired: " + 0);
        icePickMeta.setLore(icePickLore);

        icePick.setItemMeta(icePickMeta);
        godItems.put("Ice Pick", icePick);

        //VALENTINE'S 2022

        //Valentine's Day Helm
        ItemStack valentinesHelm = new ItemStack(Material.LEATHER_HELMET);
        valentinesHelm.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        valentinesHelm.addUnsafeEnchantment(Enchantment.MENDING, 1);
        valentinesHelm.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        LeatherArmorMeta valentinesHelmMeta = (LeatherArmorMeta) valentinesHelm.getItemMeta();
        valentinesHelmMeta.setColor(Color.fromRGB(0xFF42E9));
        valentinesHelmMeta.setDisplayName(ChatColor.GREEN + "Valentine's Helm");
        List<String> valentinesHelmLore = new ArrayList<String>();
        valentinesHelmLore.add("");
        valentinesHelmLore.add(ChatColor.AQUA + "- Grants the user Regeneration I");
        valentinesHelmLore.add(ChatColor.AQUA + "- Grants the user Night Vision");
        valentinesHelmLore.add("");
        valentinesHelmLore.add(ChatColor.GREEN + "Valentine's Day 2022");
        valentinesHelmMeta.setLore(valentinesHelmLore);

        valentinesHelm.setItemMeta(valentinesHelmMeta);
        godItems.put("Valentine's Helm", valentinesHelm);

        //Cupid's Wings
        ItemStack cupidsWings = new ItemStack(Material.ELYTRA);
        cupidsWings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        cupidsWings.addUnsafeEnchantment(Enchantment.MENDING, 1);
        cupidsWings.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        ItemMeta cupidsWingsMeta = cupidsWings.getItemMeta();
        cupidsWingsMeta.setDisplayName(ChatColor.GREEN + "Cupid's Wings");
        List<String> cupidsWingsLore = new ArrayList<String>();
        cupidsWingsLore.add("");
        cupidsWingsLore.add(ChatColor.AQUA + "- Grants the user Haste II");
        cupidsWingsLore.add("");
        cupidsWingsLore.add(ChatColor.GREEN + "Valentine's Day 2022");
        cupidsWingsMeta.setLore(cupidsWingsLore);

        cupidsWings.setItemMeta(cupidsWingsMeta);
        godItems.put("Cupid's Wings", cupidsWings);

        //Valentine's Day Legs
        ItemStack valentinesLegs = new ItemStack(Material.LEATHER_LEGGINGS);
        valentinesLegs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        valentinesLegs.addUnsafeEnchantment(Enchantment.MENDING, 1);
        valentinesLegs.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        LeatherArmorMeta valentinesLegsMeta = (LeatherArmorMeta) valentinesLegs.getItemMeta();
        valentinesLegsMeta.setColor(Color.fromRGB(0xFF42E9));
        valentinesLegsMeta.setDisplayName(ChatColor.GREEN + "Valentine's Legs");
        List<String> valentinesLegsLore = new ArrayList<String>();
        valentinesLegsLore.add("");
        valentinesLegsLore.add(ChatColor.AQUA + "- Grants the user Speed II");
        valentinesLegsLore.add(ChatColor.AQUA + "- Grants the user Jump Boost III");
        valentinesLegsLore.add("");
        valentinesLegsLore.add(ChatColor.GREEN + "Valentine's Day 2022");
        valentinesLegsMeta.setLore(valentinesLegsLore);

        valentinesLegs.setItemMeta(valentinesLegsMeta);
        godItems.put("Valentine's Legs", valentinesLegs);

        //Valentine's Day Boots
        ItemStack valentinesBoots = new ItemStack(Material.LEATHER_BOOTS);
        valentinesBoots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        valentinesBoots.addUnsafeEnchantment(Enchantment.MENDING, 1);
        valentinesBoots.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        LeatherArmorMeta valentinesBootsMeta = (LeatherArmorMeta) valentinesBoots.getItemMeta();
        valentinesBootsMeta.setColor(Color.fromRGB(0xFF42E9));
        valentinesBootsMeta.setDisplayName(ChatColor.GREEN + "Valentine's Boots");
        List<String> valentinesBootsLore = new ArrayList<String>();
        valentinesBootsLore.add("");
        valentinesBootsLore.add(ChatColor.AQUA + "- Increase health by 60%");
        valentinesBootsLore.add("");
        valentinesBootsLore.add(ChatColor.GREEN + "Valentine's Day 2022");
        valentinesBootsMeta.setLore(valentinesBootsLore);

        AttributeModifier valentinesBootsArmor = new AttributeModifier(UUID.randomUUID(), "generic.Armor", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        AttributeModifier valentinesBootsMaxHealth = new AttributeModifier(UUID.randomUUID(), "generic.MaxHealth", 12, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);

        valentinesBootsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, valentinesBootsArmor);
        valentinesBootsMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, valentinesBootsMaxHealth);

        valentinesBoots.setItemMeta(valentinesBootsMeta);
        godItems.put("Valentine's Boots", valentinesBoots);

        //Love Letter
        ItemStack loveLetter = new ItemStack(Material.PAPER);
        loveLetter.addUnsafeEnchantment(Enchantment.LURE, 10);
        ItemMeta loveLetterMeta = loveLetter.getItemMeta();
        loveLetterMeta.setDisplayName(ChatColor.GREEN + "Love Letter");
        List<String> loveLetterLore = new ArrayList<String>();
        loveLetterLore.add("");
        loveLetterLore.add(ChatColor.AQUA + "- Increase health by 30%");
        loveLetterLore.add("");
        loveLetterLore.add(ChatColor.GREEN + "Valentine's Day 2022");
        loveLetterMeta.setLore(loveLetterLore);

        AttributeModifier loveLetterMaxHealth = new AttributeModifier(UUID.randomUUID(), "generic.MaxHealth", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND);

        loveLetterMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, loveLetterMaxHealth);

        loveLetter.setItemMeta(loveLetterMeta);
        godItems.put("Love Letter", loveLetter);

        //Cupid's Bow
        ItemStack cupidsBow = new ItemStack(Material.BOW);
        cupidsBow.addUnsafeEnchantment(Enchantment.MENDING, 1);
        cupidsBow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        cupidsBow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 6);
        ItemMeta cupidsBowMeta = cupidsBow.getItemMeta();
        cupidsBowMeta.setDisplayName(ChatColor.GREEN + "Cupid's Bow");
        List<String> cupidsBowLore = new ArrayList<String>();
        cupidsBowLore.add("");
        cupidsBowLore.add("Your heart bursts with");
        cupidsBowLore.add("affection with each shot...");
        cupidsBowLore.add("");
        cupidsBowLore.add(ChatColor.GREEN + "Valentine's Day 2022");
        cupidsBowMeta.setLore(cupidsBowLore);

        cupidsBow.setItemMeta(cupidsBowMeta);
        godItems.put("Cupid's Bow", cupidsBow);


        //EASTER 2022

        //Easter Helm
        ItemStack easterHelm = PlayerHeads.getApiInstance().getHeadItem("Hxnigma", 1, true);
        easterHelm.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        ItemMeta easterHelmMeta = easterHelm.getItemMeta();
        easterHelmMeta.setDisplayName(ChatColor.GREEN + "Bunny Head");
        List<String> easterHelmLore = new ArrayList<String>();
        easterHelmLore.add("");
        easterHelmLore.add(ChatColor.AQUA + "- Invisible to monsters");
        easterHelmLore.add(ChatColor.AQUA + "- Increase XP gain by 50%");
        easterHelmLore.add("");
        easterHelmLore.add(ChatColor.GREEN + "Easter 2022");
        easterHelmMeta.setLore(easterHelmLore);

        easterHelm.setItemMeta(easterHelmMeta);
        godItems.put("Bunny Head", easterHelm);

        //Easter Chest
        ItemStack easterChest = new ItemStack(Material.IRON_CHESTPLATE);
        easterChest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        easterChest.addUnsafeEnchantment(Enchantment.DURABILITY, 20);
        ItemMeta easterChestMeta = easterChest.getItemMeta();
        easterChestMeta.setDisplayName(ChatColor.GREEN + "Easter Chest");
        List<String> easterChestLore = new ArrayList<String>();
        easterChestLore.add("");
        easterChestLore.add(ChatColor.AQUA + "- Increase radius of attack");
        easterChestLore.add(ChatColor.AQUA + "- Increase XP gain by 50%");
        easterChestLore.add("");
        easterChestLore.add(ChatColor.GREEN + "Easter 2022");
        easterChestLore.add("");
        easterChestLore.add(ChatColor.GRAY + "Times repaired: " + 0);
        easterChestMeta.setLore(easterChestLore);

        easterChest.setItemMeta(easterChestMeta);
        godItems.put("Easter Chest", easterChest);

        //Easter Legs
        ItemStack easterLegs = new ItemStack(Material.IRON_LEGGINGS);
        easterLegs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        easterLegs.addUnsafeEnchantment(Enchantment.DURABILITY, 20);
        ItemMeta easterLegsMeta = easterLegs.getItemMeta();
        easterLegsMeta.setDisplayName(ChatColor.GREEN + "Easter Legs");
        List<String> easterLegsLore = new ArrayList<String>();
        easterLegsLore.add("");
        easterLegsLore.add(ChatColor.AQUA + "- Increase radius of attack");
        easterLegsLore.add(ChatColor.AQUA + "- Increase XP gain by 50%");
        easterLegsLore.add("");
        easterLegsLore.add(ChatColor.GREEN + "Easter 2022");
        easterLegsLore.add("");
        easterLegsLore.add(ChatColor.GRAY + "Times repaired: " + 0);
        easterLegsMeta.setLore(easterLegsLore);

        easterLegs.setItemMeta(easterLegsMeta);
        godItems.put("Easter Legs", easterLegs);

        //Easter Boots
        ItemStack easterBoots = new ItemStack(Material.IRON_BOOTS);
        easterBoots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        easterBoots.addUnsafeEnchantment(Enchantment.DURABILITY, 20);
        ItemMeta easterBootsMeta = easterBoots.getItemMeta();
        easterBootsMeta.setDisplayName(ChatColor.GREEN + "Easter Boots");
        List<String> easterBootsLore = new ArrayList<String>();
        easterBootsLore.add("");
        easterBootsLore.add(ChatColor.AQUA + "- Grants the user Jump Boost II");
        easterBootsLore.add(ChatColor.AQUA + "- Increase XP gain by 50%");
        easterBootsLore.add("");
        easterBootsLore.add(ChatColor.GREEN + "Easter 2022");
        easterBootsLore.add("");
        easterBootsLore.add(ChatColor.GRAY + "Times repaired: " + 0);
        easterBootsMeta.setLore(easterBootsLore);

        easterBoots.setItemMeta(easterBootsMeta);
        godItems.put("Easter Boots", easterBoots);

        //Easter Egg
        ItemStack easterEgg = new ItemStack(Material.EGG);
        easterEgg.addUnsafeEnchantment(Enchantment.BINDING_CURSE, 1);
        ItemMeta easterEggMeta = easterEgg.getItemMeta();
        easterEggMeta.setDisplayName(ChatColor.GREEN + "Easter Egg");
        List<String> easterEggLore = new ArrayList<String>();
        easterEggLore.add("");
        easterEggLore.add(ChatColor.ITALIC + "A feint wailing can");
        easterEggLore.add(ChatColor.ITALIC + "be heard from within...");
        easterEggLore.add(ChatColor.AQUA + "- Captures the mob it hits");
        easterEggLore.add("");
        easterEggLore.add(ChatColor.GREEN + "Easter 2022");
        easterEggMeta.setLore(easterEggLore);

        easterEgg.setItemMeta(easterEggMeta);
        godItems.put("Easter Egg", easterEgg);

        //Bunny's Secret Snack
        ItemStack secretSnack = new ItemStack(Material.GOLDEN_CARROT);
        secretSnack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 7);
        ItemMeta secretSnackMeta = secretSnack.getItemMeta();
        secretSnackMeta.setDisplayName(ChatColor.GREEN + "Bunny's Secret Snack");
        List<String> secretSnackLore = new ArrayList<String>();
        secretSnackLore.add("");
        secretSnackLore.add(ChatColor.ITALIC + "Sharp, yet delicious...");
        secretSnackLore.add("");
        secretSnackLore.add(ChatColor.GREEN + "Easter 2022");
        secretSnackLore.add("");
        secretSnackLore.add(ChatColor.GRAY + "Kills: " + 0);
        secretSnackMeta.setLore(secretSnackLore);

        secretSnack.setItemMeta(secretSnackMeta);
        godItems.put("Bunny's Secret Snack", secretSnack);

        //Easter's Vengence
        ItemStack eastersVengeance = new ItemStack(Material.CROSSBOW);
        eastersVengeance.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        ItemMeta eastersVengeanceMeta = eastersVengeance.getItemMeta();
        eastersVengeanceMeta.setDisplayName(ChatColor.GREEN + "Easter's Vengence");
        List<String> eastersVengeanceLore = new ArrayList<String>();
        eastersVengeanceLore.add("");
        eastersVengeanceLore.add(ChatColor.ITALIC + "Tales tell of Jesus using");
        eastersVengeanceLore.add(ChatColor.ITALIC + "this to escape his tomb...");
        eastersVengeanceLore.add(ChatColor.AQUA + "- Shoots explosive eggs");
        eastersVengeanceLore.add("");
        eastersVengeanceLore.add(ChatColor.GREEN + "Easter 2022");
        eastersVengeanceLore.add("");
        eastersVengeanceLore.add(ChatColor.GRAY + "Kills: " + 0);
        eastersVengeanceLore.add(ChatColor.GRAY + "Times repaired: " + 0);
        eastersVengeanceMeta.setLore(eastersVengeanceLore);

        eastersVengeance.setItemMeta(eastersVengeanceMeta);
        godItems.put("Easter's Vengeance", eastersVengeance);
    }

    public Date addDaysToJavaUtilDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    //Disable upgrade to netherite in smithing table
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
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
        else if (event.getInventory() instanceof AnvilInventory) {

            Player player = (Player) event.getWhoClicked();
            AnvilInventory anvilInventory = (AnvilInventory) event.getInventory();

            ItemStack input = anvilInventory.getItem(0);

            if (input != null) {
                if (GodItems.isGod(input)) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "You cannot upgrade God items.");
                    player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                }
            }
        }
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        //
        int spaceIndex = event.getMessage().indexOf(" ");
        String label = "";
        if (spaceIndex != -1) {
            label = event.getMessage().substring(spaceIndex);
        } else {
            label = event.getMessage();
        }
        if (label.equalsIgnoreCase("hat")) {
            ItemStack helmet = event.getPlayer().getInventory().getHelmet();
            if (GodItems.getGodName(helmet).equals("Daedric Helm") || GodItems.getGodName(helmet).equals("Red Snowy Helm") || GodItems.getGodName(helmet).equals("Green Snowy Helm")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You cannot use /hat with a God helmet on.");
            }
        }
    }

    @EventHandler
    public void onDye(PrepareItemCraftEvent event) {
        if (event.getInventory().getResult() == null) {
            return;
        }
        if (GodItems.isGod(event.getInventory().getResult())) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            if (event.getClickedBlock().getType() == Material.WATER_CAULDRON) {
                if (event.getPlayer().getInventory().getItemInMainHand() != null) {
                    if (GodItems.isGod(event.getPlayer().getInventory().getItemInMainHand())) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(ChatColor.RED + "You cannot click cauldrons with God items.");
                    }
                }
                if (event.getPlayer().getInventory().getItemInOffHand() != null) {
                    if (GodItems.isGod(event.getPlayer().getInventory().getItemInOffHand())) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(ChatColor.RED + "You cannot click cauldrons with God items.");
                    }
                }
            }
        }
    }
}
