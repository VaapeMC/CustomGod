package me.vaape.customgod;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class KillListener implements Listener {

    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        Player defender = event.getEntity();
        if (defender.getKiller() instanceof Player) {
            Player killer = (Player) defender.getKiller();
            ItemStack hand = killer.getInventory().getItemInMainHand();
            ItemMeta meta = hand.getItemMeta();
            if (meta != null) {
                if (meta.hasLore()) {
                    if (GodItems.isGodWeapon(hand)) {
                        List<String> lore = meta.getLore();
                        String[] killLine = null;

                        //Make unique's kills gold, uniques don't have times repaired
                        if (GodItems.isUnique(hand)) {
                            killLine = lore.get(lore.size() - 1).split("\\s");
                            int kills = Integer.parseInt(killLine[1]);
                            String newKillLine = ChatColor.of("#ffbf00") + killLine[0] + " " + (kills + 1);
                            lore.set(lore.size() - 1, newKillLine);
                        }
                        else {
                            killLine = lore.get(lore.size() - 2).split("\\s");
                            int kills = Integer.parseInt(killLine[1]);
                            String newKillLine = ChatColor.GRAY + killLine[0] + " " + (kills + 1);
                            lore.set(lore.size() - 2, newKillLine);
                        }
                        meta.setLore(lore);
                        hand.setItemMeta(meta);
                    }
                    //Executioner Wasp Stinger
                    if (hand == null) return;
                    if (hand.getType() != Material.END_ROD) return;
                    if (!hand.getLore().contains("Poison runs down the tip...")) return;

                    List<String> lore = meta.getLore();
                    String[] killLine = null;
                    killLine = lore.get(lore.size() - 1).split("\\s");
                    int kills = Integer.parseInt(killLine[1]);
                    String newKillLine = ChatColor.of("#ffbf00") + killLine[0] + " " + (kills + 1);
                    lore.set(lore.size() - 1, newKillLine);
                    meta.setLore(lore);
                    hand.setItemMeta(meta);
                }
            }
        }
    }

}
