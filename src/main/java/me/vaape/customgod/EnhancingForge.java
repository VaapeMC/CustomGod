package me.vaape.customgod;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class EnhancingForge implements Listener {

    private static final CustomGod plugin = CustomGod.getInstance();

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        if (true) return;

        if (event.getClickedBlock() == null) {
            return;
        }
        if (!event.getClickedBlock().getType().equals(Material.END_PORTAL_FRAME)) {
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
            ItemStack hand = inventory.getItemInMainHand();
            ItemStack offHand = inventory.getItemInOffHand();
            ItemMeta handItemMeta = hand.getItemMeta();
            if (hand == null || offHand == null) {
                player.sendMessage(ChatColor.RED + "To enhance an item, hold the item in your main hand and dragon eggs in your off hand.");
                player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                return;
            }
            if (!GodItems.isEnhancable(hand)) {
                player.sendMessage(ChatColor.RED + "This item cannot be enhanced.");
                player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                return;
            }
            if (GodItems.isUnique(player.getInventory().getItemInMainHand())) {
                player.sendMessage(ChatColor.RED + "You cannot enhance unique items.");
                player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                return;
            }
            if (!offHand.getType().equals(Material.DRAGON_EGG)) {
                player.sendMessage(ChatColor.RED + "To enhance an item, hold the item in your main hand and dragon eggs in your off hand.");
                player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                return;
            }
            if (player.getLevel() < 50) {
                player.sendMessage(ChatColor.RED + "You must be at least level 50 to enhance items.");
                player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                return;
            }

            //Check enhancement level
            int enhancementLevel = 0;
            NamespacedKey enhanceKey = new NamespacedKey(CustomGod.getInstance(), "enhancement level");
            PersistentDataContainer container = handItemMeta.getPersistentDataContainer();
            if (container.has(enhanceKey, PersistentDataType.INTEGER)) {
                enhancementLevel = container.get(enhanceKey, PersistentDataType.INTEGER);
            }

            if (enhancementLevel == 0) {

            }
            else if (enhancementLevel == 1) {

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
