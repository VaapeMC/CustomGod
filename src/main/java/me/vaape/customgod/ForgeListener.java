package me.vaape.customgod;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Async;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ForgeListener implements Listener {

    private static final CustomGod plugin = CustomGod.getInstance();

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) {
            return;
        }
        if (!event.getClickedBlock().getType().equals(Material.ANVIL)) {
            return;
        }
        if (event.getHand() == EquipmentSlot.OFF_HAND) { //Event is fires twice, one for each hand, this if statement catches the off hand and stops the event
            return;
        }

        Location location = event.getClickedBlock().getLocation();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        if ((x > 43 && x < 63) && (y > 208 && y < 228) && (z > 205 && z < 225)) { //cords of God Forge
            event.setCancelled(true);
            Player player = event.getPlayer();
            PlayerInventory inventory = player.getInventory();
            if (inventory.getItemInMainHand() == null || inventory.getItemInOffHand() == null) {
                player.sendMessage(ChatColor.RED + "To use the God Forge, hold a God item in your main hand. Hold dragon eggs, a nametag, or Reforging Steel in your off hand.");
                player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                return;
            }
            if (!GodItems.isGod(player.getInventory().getItemInMainHand())) {
                player.sendMessage(ChatColor.RED + "To use the God Forge, hold a God item in your main hand. Hold dragon eggs, a nametag, or Reforging Steel in your off hand.");
                player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                return;
            }
            if (GodItems.isUnique(player.getInventory().getItemInMainHand())) {
                player.sendMessage(ChatColor.RED + "You cannot rename unique items or repair them at the God Forge.");
                player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                return;
            }
            if (player.getLevel() < 30) {
                player.sendMessage(ChatColor.RED + "You must be at least level 30 to use the God Forge.");
                player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                return;
            }

            ItemStack godItem = inventory.getItemInMainHand();
            ItemMeta itemMeta = godItem.getItemMeta();

            //Repairing wtih deggs
            if (inventory.getItemInOffHand().getType().equals(Material.DRAGON_EGG)) {
                List<String> lore = itemMeta.getLore();
                String[] repairLine = lore.get(lore.size() - 1).split("\\s"); //Get "Times repaired: x" line of lore
                if (Arrays.asList(repairLine).contains("repaired:")) { //If repairable, it will have "Times repaired:" line
                    Damageable damagableMeta = (Damageable) itemMeta; //If repairable, it will be damagable
                    int timesRepaired = Integer.parseInt(repairLine[2]);
                    int deggsNeeded = timesRepaired * 3;
                    if (deggsNeeded < 5) deggsNeeded = 5; //First repair
                    if (inventory.getItemInOffHand().getAmount() < deggsNeeded) {
                        player.sendMessage(ChatColor.RED + "You need " + deggsNeeded + " dragon eggs to repair " + itemMeta.getDisplayName() + ChatColor.RED + ".");
                        player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                        return;
                    }
                    else {
                        inventory.getItemInOffHand().setAmount(inventory.getItemInOffHand().getAmount() - deggsNeeded);
                        damagableMeta.setDamage(0); //Repair item
                        player.setLevel(player.getLevel() - 30);
                        //Adding 1 to Times repaired
                        String newRepairLine = ChatColor.GRAY + "Times repaired: " + (timesRepaired + 1);
                        List<String> godLore = damagableMeta.getLore();
                        godLore.set(lore.size() - 1, newRepairLine);
                        damagableMeta.setLore(godLore);
                        godItem.setItemMeta(damagableMeta);
                        Bukkit.getServer().broadcastMessage(ChatColor.RED + "[God Forge] " + ChatColor.BLUE + player.getName() + " has repaired " + damagableMeta.getDisplayName() + ChatColor.BLUE + " for " + ChatColor.BOLD + deggsNeeded + ChatColor.BLUE + " dragon eggs!");
                        player.playSound(location, Sound.ENTITY_ENDER_EYE_DEATH, 1f, 1f);
                        return;
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "This item can not be repaired.");
                    player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                    return;
                }
            }

            //Repairing with reforging steel
            if (inventory.getItemInOffHand().getType().equals(Material.NETHERITE_INGOT)) {
                if (inventory.getItemInOffHand().getItemMeta().hasLore()) {
                    if (inventory.getItemInOffHand().getLore().contains(ChatColor.of("#872B2B") + "Repairs a God item")) {
                        List<String> lore = itemMeta.getLore();
                        String[] repairLine = lore.get(lore.size() - 1).split("\\s"); //Get "Times repaired: x" line of lore
                        if (Arrays.asList(repairLine).contains("repaired:")) { //If repairable, it will have "Times repaired:" line

                            Damageable damagableMeta = (Damageable) itemMeta; //If repairable, it will be damagable
                            int timesRepaired = Integer.parseInt(repairLine[2]);

                            inventory.getItemInOffHand().setAmount(inventory.getItemInOffHand().getAmount() - 1);
                            damagableMeta.setDamage(0); //Repair item
                            player.setLevel(player.getLevel() - 30);

                            //Adding 1 to Times repaired
                            String newRepairLine = ChatColor.GRAY + "Times repaired: " + (timesRepaired + 1);
                            List<String> godLore = damagableMeta.getLore();
                            godLore.set(lore.size() - 1, newRepairLine);
                            damagableMeta.setLore(godLore);
                            godItem.setItemMeta(damagableMeta);
                            Bukkit.getServer().broadcastMessage(ChatColor.RED + "[God Forge] " + ChatColor.BLUE + player.getName() + " has repaired " + damagableMeta.getDisplayName() + ChatColor.BLUE + " using " + ChatColor.BOLD + "Reforging Steel" + ChatColor.BLUE + "!");
                            player.playSound(location, Sound.ENTITY_ENDER_EYE_DEATH, 1f, 1f);
                            return;
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "This item can not be repaired.");
                            player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                            return;
                        }
                    }
                }
            }

            //Renaming
            else if (inventory.getItemInOffHand().getType().equals(Material.NAME_TAG)) {
                if (!player.hasPermission("gforge.rename")) {
                    player.sendMessage(ChatColor.RED + "You must be at least Diamond rank to rename God items. Use /buy to see all premium features.");
                    player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                    return;
                }

                String newName = inventory.getItemInOffHand().getItemMeta().getDisplayName();
                String godPrefix = ""; //The &c prefix color code
                String godName = "";


                if (itemMeta.getDisplayName().length() >=4) {
                    if (itemMeta.getDisplayName().contains(ChatColor.COLOR_CHAR + "l")) {
                        godPrefix = itemMeta.getDisplayName().substring(0, 2) + ChatColor.COLOR_CHAR + "l"; //&c&l //Not sure why it's (0, 2) and not (0,1) but it works so fuck it
                    }
                    else {
                        godPrefix = itemMeta.getDisplayName().substring(0, 2); //&c
                    }
                }
                else {
                    godPrefix = itemMeta.getDisplayName().substring(0, 2); //&c
                }

                if (godPrefix.length() == 4) { //&c&l
                    godName = getChatColor(godPrefix.substring(0, 2)) + "" + ChatColor.BOLD + newName;
                }
                else { //&c
                  godName = getChatColor(godPrefix) + newName;
                }

                itemMeta.setDisplayName(godName);
                godItem.setItemMeta(itemMeta);
                player.setLevel(player.getLevel() - 30);
                inventory.getItemInOffHand().setAmount(inventory.getItemInOffHand().getAmount() - 1);
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "[God Forge] " + ChatColor.BLUE + player.getName() + " has named their God item " + godName + ChatColor.BLUE + "!");
                player.playSound(location, Sound.ENTITY_ENDER_EYE_DEATH, 1f, 1f);
            }
            else {
                player.sendMessage(ChatColor.RED + "To use the God Forge, hold a God item in your main hand. Hold dragon eggs, a nametag, or Reforging Steel in your off hand.");
                player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
            }
        }
    }

    public String getChatColor(String colorCode) {
        switch (colorCode) {
            case (ChatColor.COLOR_CHAR + "c"):
                return ChatColor.RED + "";
            case (ChatColor.COLOR_CHAR + "a"):
                return ChatColor.GREEN + "";
            case (ChatColor.COLOR_CHAR + "6"):
                return ChatColor.GOLD + "";
            case (ChatColor.COLOR_CHAR + "c" + ChatColor.COLOR_CHAR + "l"):
                return ChatColor.RED + "" + ChatColor.BOLD;
            case (ChatColor.COLOR_CHAR + "a" + ChatColor.COLOR_CHAR + "l"):
                return ChatColor.GREEN + "" + ChatColor.BOLD;
            case (ChatColor.COLOR_CHAR + "6" + ChatColor.COLOR_CHAR + "l"):
                return ChatColor.GOLD + "" + ChatColor.BOLD;
            default:
                return null;
        }
    }
}
