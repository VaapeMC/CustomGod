package me.vaape.customgod;

import java.util.List;

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
                        String[] killLine = lore.get(lore.size() - 2).split("\\s");
                        int kills = Integer.parseInt(killLine[1]);
                        String newKillLine = ChatColor.GRAY + killLine[0] + " " + (kills + 1);
                        lore.set(lore.size() - 2, newKillLine);
                        meta.setLore(lore);
                        hand.setItemMeta(meta);
                    }
                }
            }
        }
    }

}
