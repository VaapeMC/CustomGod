package me.vaape.customgod;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;


public class DamageListener implements Listener {

    WorldGuardPlugin worldGuard = getWorldGuard();

    private WorldGuardPlugin getWorldGuard() {
        Plugin worldGuardPlugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        if (!(worldGuardPlugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) worldGuardPlugin;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {

        Entity defender = event.getEntity();
        Entity attacker = event.getDamager();

        World world = Bukkit.getServer().getWorld("world");
        org.bukkit.Location BukkitdLoc = defender.getLocation();

        Location WEdLoc = new Location(BukkitAdapter.adapt(world), BukkitdLoc.getX(), BukkitdLoc.getY(),
                                       BukkitdLoc.getZ());

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(WEdLoc);

        if (set != null) {
            if (!set.testState(null, Flags.PVP)) {
                return;
            }
        }

        if (attacker instanceof Player player && defender instanceof LivingEntity livingDefender) {

            ItemStack hand = player.getInventory().getItemInMainHand();

            if (hand.getType() != Material.AIR) {

                if (GodItems.isUnique(hand) && hand.getType() == Material.GOLDEN_SWORD) {
                    livingDefender.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 3 * 20, 0), true);
                    livingDefender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3 * 20, 1), true);
                    livingDefender.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 3 * 20, 0), true);
                    livingDefender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 3 * 20, 0), true);
                    livingDefender.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 3 * 20, 0), true);
                }

                if (GodItems.isGod(hand)) {
                    if (GodItems.getGodName(hand).equalsIgnoreCase("Shadow's Edge")) {

                        if (hand.getLore().contains(ChatColor.AQUA + "- Weakens the enemy")) { //Old Shadow's edge
                            // before reward will have this, reworked one will not
                            livingDefender.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 4 * 20, 1),
                                                           true);
                        }
                        if (hand.getLore().contains(ChatColor.AQUA + "- Withers the enemy")) { //Old Shadow's edge
                            // before reward will have this, reworked one will not
                            livingDefender.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 4 * 20, 1), true);
                        }
                    } else if (GodItems.getGodName(hand).equalsIgnoreCase("Anduril")) {
                        livingDefender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3 * 20, 0), true);
                    } else if (GodItems.getGodName(hand).equalsIgnoreCase("Kamatayon")) {
                        livingDefender.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 4 * 20, 0), true);
                        livingDefender.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 4 * 20, 2), true);
                        if (!isRoyalty(livingDefender)) {
                            livingDefender.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 5 * 20, 4), true);
                        }
                    } else if (GodItems.getGodName(hand).equalsIgnoreCase("Shadow Seeker")) {
                        UUID UUID = livingDefender.getUniqueId();

                        //If Bukkit.getPlauer(UUID) == null it's an animal and gives an NPE
                        if (Bukkit.getPlayer(UUID) == null) {
                            return;
                        }

                        Bukkit.getPlayer(UUID).setCooldown(Material.ENDER_PEARL, 10 * 20);

                    } else if (GodItems.getGodName(hand).equalsIgnoreCase("Glacial Brand")) {
                        livingDefender.setFreezeTicks(50);
                    }
                }
            }
        }

        if (attacker instanceof Arrow && defender instanceof LivingEntity) {
            Arrow arrow = (Arrow) attacker;
            LivingEntity livingDefender = (LivingEntity) defender;
            if (arrow.getCustomName() != null) {
                if (arrow.getCustomName().contains("Medusa")) {
                    livingDefender.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 4 * 20, 3), true);
                } else if (arrow.getCustomName().contains("Glacial Bow")) {

                    org.bukkit.Location location = defender.getLocation();

                    if (inSpawn(location)) {
                        return;
                    }

                    location.getBlock().setType(Material.ICE);
                    location.clone().add(1, 0, 0).getBlock().setType(Material.ICE);
                    location.clone().add(0, 0, 1).getBlock().setType(Material.ICE);
                    location.clone().add(1, 0, 1).getBlock().setType(Material.ICE);
                    location.clone().add(0, 1, 0).getBlock().setType(Material.ICE);
                    location.clone().add(1, 1, 0).getBlock().setType(Material.ICE);
                    location.clone().add(0, 1, 1).getBlock().setType(Material.ICE);
                    location.clone().add(1, 1, 1).getBlock().setType(Material.ICE);
                }
            }
        }
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {

        if (event.getEntity() instanceof Arrow arrow) {
            if (arrow.getCustomName() != null) {

                if (arrow.getCustomName().equals("Glacial Bow")) {

                    org.bukkit.Location location = event.getEntity().getLocation();

                    if (inSpawn(location)) {
                        return;
                    }

                    location.getBlock().setType(Material.ICE);
                    location.clone().add(1, 0, 0).getBlock().setType(Material.ICE);
                    location.clone().add(0, 0, 1).getBlock().setType(Material.ICE);
                    location.clone().add(1, 0, 1).getBlock().setType(Material.ICE);
                    location.clone().add(0, 1, 0).getBlock().setType(Material.ICE);
                    location.clone().add(1, 1, 0).getBlock().setType(Material.ICE);
                    location.clone().add(0, 1, 1).getBlock().setType(Material.ICE);
                    location.clone().add(1, 1, 1).getBlock().setType(Material.ICE);
                }
            }
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player entity) {
            Player player = event.getPlayer();
            UUID UUID = player.getUniqueId();

            ItemStack hand = player.getInventory().getItemInMainHand();

            //Do nothing if pvp flag is off
            World world = Bukkit.getServer().getWorld("world");
            org.bukkit.Location BukkitdLoc = entity.getLocation();

            Location WEdLoc = new Location(BukkitAdapter.adapt(world), BukkitdLoc.getX(), BukkitdLoc.getY(),
                                           BukkitdLoc.getZ());

            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(WEdLoc);

            if (set != null) {
                if (!set.testState(null, Flags.PVP)) {
                    return;
                }
            }

            //Event is fires twice, one for each hand, this if statement catches the off hand and stops the event
            if (event.getHand() == EquipmentSlot.OFF_HAND) {
                return;
            }

            if (hand != null && hand.getType() != Material.AIR) {
                if (GodItems.isGod(hand)) {
                    if (GodItems.getGodName(hand).equalsIgnoreCase("Cane of Zefeus")) {

                        if (player.hasCooldown(Material.BLAZE_ROD)) {
                            Bukkit.getPlayer(UUID).sendMessage(ChatColor.RED + "Your cane is still on cooldown.");
                            return;
                        }

                        if (player.getLevel() < 3) {
                            Bukkit.getPlayer(UUID).sendMessage(ChatColor.RED + "You need 3 experience levels to use your cane...");
                            return;
                        }
                        player.setLevel(player.getLevel() - 3);

                        PlayerInventory inventory = entity.getInventory();
                        ItemStack[] contents = inventory.getContents();

                        //Select random item from inventory
                        Random random = new Random();
                        int index = random.nextInt(27) + 9; //Inner inventory is 27 slots, from slot 9-35

                        ItemStack swappedItem = contents[index];
                        ItemStack entityHand = inventory.getItemInMainHand();

                        inventory.setItemInMainHand(swappedItem);
                        inventory.setItem(index, entityHand);

                        entity.getWorld().playSound(player.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1, 2F);
                        entity.sendMessage(ChatColor.of("#9932d1") + "You have been struck by " + player.getName() +
                                                   "'s Cane...");

                        player.setCooldown(Material.BLAZE_ROD, 30 * 20);
                    }
                }
            }
        }
    }

    //Mjolnir handler
    @EventHandler
    public void onRightClickBlock(PlayerInteractEvent event) {

        //Event is fires twice, one for each hand, this if statement catches the off hand and stops the event
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack hand = player.getInventory().getItemInMainHand();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (hand.getType() != Material.AIR) {
                if (GodItems.isGod(hand)) {

                    if (GodItems.getGodName(hand).equalsIgnoreCase("Mjolnir")) {

                        if (player.hasCooldown(Material.IRON_AXE)) player.sendMessage(ChatColor.RED + "You can't use lightning strike yet.");
                        else {
                            Bukkit.getServer().getWorld("world").strikeLightning(event.getClickedBlock().getLocation());
                            Damageable handMeta = (Damageable) hand.getItemMeta();
                            handMeta.setDamage(handMeta.getDamage() + 1);
                            hand.setItemMeta(handMeta);
                            player.setCooldown(Material.IRON_AXE, 30 * 20);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getCause() == DamageCause.LIGHTNING) {

            //Check if PvP allowed
            World world = Bukkit.getServer().getWorld("world");
            org.bukkit.Location BukkitdLoc = event.getEntity().getLocation();

            Location WEdLoc = new Location(BukkitAdapter.adapt(world), BukkitdLoc.getX(), BukkitdLoc.getY(),
                                           BukkitdLoc.getZ());

            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(WEdLoc);

            if (set != null) {
                if (!set.testState(null, Flags.PVP)) {
                    event.setCancelled(true);
                    return;
                }
            }

            event.setDamage(event.getDamage() * 1);

            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if (isRoyalty(player)) {
                    event.setCancelled(true);
                }
                ItemStack mainHand = player.getInventory().getItemInMainHand();
                if (mainHand != null && mainHand.getType() != Material.AIR) {
                    if (GodItems.isGod(mainHand)) {
                        if (GodItems.getGodName(mainHand).equalsIgnoreCase("Mjolnir")) {
                            event.setCancelled(true);
                        }
                    }
                }
                ItemStack offHand = player.getInventory().getItemInOffHand();
                if (offHand != null && offHand.getType() != Material.AIR) {
                    if (GodItems.isGod(offHand)) {
                        if (GodItems.getGodName(offHand).equalsIgnoreCase("Mjolnir")) {
                            event.setCancelled(true);
                        }
                    }
                }
            }

            if (event.getEntity() instanceof Villager) {
                event.setCancelled(true);
            }
        }
    }

    public boolean isRoyalty(LivingEntity livingEntity) {
        if (livingEntity.hasMetadata("royal")) {
            return true;
        }
        return false;
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        ItemStack bow = event.getBow();
        if (GodItems.isGod(bow)) {
            if (bow.hasItemMeta()) {
                if (GodItems.getGodName(bow).equalsIgnoreCase("Medusa")) {
                    event.getProjectile().setCustomName("Medusa");
                } else if (GodItems.getGodName(bow).equalsIgnoreCase("Glacial Bow")) {
                    event.getProjectile().setCustomName("Glacial Bow");
                }
            }
        }
    }

    //Disable the vanilla default enderpearl cooldown
    @EventHandler
    public void onPearl(PlayerLaunchProjectileEvent event) {
        if (event.getProjectile() instanceof EnderPearl) {
            Player player = event.getPlayer();
            UUID UUID = player.getUniqueId();

            new BukkitRunnable() {

                @Override
                public void run() {
                    player.setCooldown(Material.ENDER_PEARL, 0);
                }
            }.runTaskLater(CustomGod.getInstance(), 1);
        }

    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause().equals(TeleportCause.ENDER_PEARL)) {
            Player player = event.getPlayer();
            UUID UUID = player.getUniqueId();
            if (player.hasCooldown(Material.ENDER_PEARL)) {
                player.sendMessage(ChatColor.RED + "You are currently pearl bound.");
                event.setCancelled(true);
            }
        }
    }

    public static boolean inSpawn(org.bukkit.Location location) {
        World world = location.getWorld();

        com.sk89q.worldedit.util.Location WElocation = new com.sk89q.worldedit.util.Location(BukkitAdapter.adapt(world), location.getX(), location.getY(), location.getZ());

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(WElocation);

        for (ProtectedRegion region : set) {
            if (region.getId().equalsIgnoreCase("spawn")) {
                return true;
            }
        }
        return false;
    }
}
