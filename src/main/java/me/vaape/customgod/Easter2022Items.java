package me.vaape.customgod;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import com.github.crashdemons.playerheads.api.PlayerHeads;
import com.github.crashdemons.playerheads.api.PlayerHeadsAPI;
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
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.*;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;


public class Easter2022Items implements Listener {

    WorldGuardPlugin worldGuard = getWorldGuard();
    MobEggs mobEggs = new MobEggs();

    public CustomGod plugin;

    public Easter2022Items(CustomGod passedPlugin) {
        plugin = passedPlugin;
    }

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

        }

        if (attacker instanceof Egg && defender instanceof LivingEntity livingDefender) {
            Egg egg = (Egg) attacker;
            if (egg.getCustomName() != null) {
                if (egg.getCustomName().contains("easter egg")) {
                    livingDefender.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 4 * 20, 1), true);
                    livingDefender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20, 10), true);
                    Vector direction = livingDefender.getLocation().getDirection();
                    livingDefender.setVelocity(direction.multiply(-10));
                }
            }
        }
    }



    //XP gain
    @EventHandler
    public void onXPGain(PlayerPickupExperienceEvent event) {
        double multiplier = 1 + (0.5 * getNumberOfEquippedXPItems(event.getPlayer()));
        ExperienceOrb xp = event.getExperienceOrb();
        xp.setExperience((int) (xp.getExperience() * multiplier));
    }

    public int getNumberOfEquippedXPItems(Player player) {
        int number = 0;
        PlayerInventory inv = player.getInventory();
        if (inv.getHelmet() != null) {
            if (GodItems.isGod(inv.getHelmet())) {
                if (GodItems.getGodName(inv.getHelmet()).equalsIgnoreCase("Bunny Head")) {
                    number = number + 1;
                }
            }
        }
        if (inv.getChestplate() != null) {
            if (GodItems.isGod(inv.getChestplate())) {
                if (GodItems.getGodName(inv.getChestplate()).equalsIgnoreCase("Easter Chest")) {
                    number = number + 1;
                }
            }
        }
        if (inv.getLeggings() != null) {
            if (GodItems.isGod(inv.getLeggings())) {
                if (GodItems.getGodName(inv.getLeggings()).equalsIgnoreCase("Easter Legs")) {
                    number = number + 1;
                }
            }
        }
        if (inv.getBoots() != null) {
            if (GodItems.isGod(inv.getBoots())) {
                if (GodItems.getGodName(inv.getBoots()).equalsIgnoreCase("Easter Boots")) {
                    number = number + 1;
                }
            }
        }
        return number;
    }



    //Executioner Wasp Stinger
    @EventHandler
    public void onDamagePlayer(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity defender && event.getDamager() instanceof Player attacker) {
            ItemStack hand = attacker.getInventory().getItemInMainHand();
            if (hand == null) return;
            if (hand.getType() != Material.END_ROD) return;
            if (!hand.getLore().contains("Poison runs down the tip...")) return;
            defender.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 2 * 20, 30));
            defender.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1 * 10, 1));
        }
    }



    //Bunny Hat
    @EventHandler
    public void onTarget(EntityTargetLivingEntityEvent event) {
        if (!(event.getTarget() instanceof Player player)) return;
        PlayerInventory inv = player.getInventory();
        if (inv.getHelmet() != null) {
            if (GodItems.isGod(inv.getHelmet())) {
                if (GodItems.getGodName(inv.getHelmet()).equalsIgnoreCase("Bunny Head")) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        if (item.getType() != Material.PLAYER_HEAD) return;
        if (!item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Hxnigma's Head")) return;

        ItemStack bunnyHead = CustomGod.godItems.get("Bunny Head");
        item.setData(bunnyHead.getData());
        item.setItemMeta(bunnyHead.getItemMeta());
    }



    //Chest and Legs range increase
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player player)) return;
        if (!(event.getEntity() instanceof LivingEntity livingEntity)) return;

        if (event.getEntity().hasMetadata("inRadiusAttack")) { //Stop infinite event loop
            event.getEntity().removeMetadata("inRadiusAttack", plugin);
            return;
        }

        int numberOfRadiusItems = getNumberOfEquippedRadiusItems(player);
        if (numberOfRadiusItems == 0) return;
        double damage = event.getDamage();
        org.bukkit.Location location = livingEntity.getLocation();
        Collection<Entity> nearbyEntities = location.getNearbyEntities(numberOfRadiusItems * 1.5, 1, numberOfRadiusItems * 1.5); //1.5 radius or 3 radius
        for (Entity entity : nearbyEntities) {
            if (!(entity instanceof LivingEntity)) continue;
            if (entity == player) continue;
            if (entity == livingEntity) continue;

            entity.setMetadata("inRadiusAttack", new FixedMetadataValue(plugin, "radius")); //Stop infinite event loop

            ((LivingEntity) entity).damage(damage, player);

            spawnRadiusParticles(entity.getLocation(), player.getLocation(), 12);
        }
    }

    public void spawnRadiusParticles(org.bukkit.Location entityLoc, org.bukkit.Location playerLoc, int n) {
        for (int i = 0; i < n; i++) {
            spawnRadiusParticle(entityLoc, playerLoc);
        }
    }

    public void spawnRadiusParticle(org.bukkit.Location entityLoc, org.bukkit.Location playerLoc) {
        playerLoc.add(0, 1, 0);
        entityLoc.add(0, 1, 0);
        double randomx1 = (Math.random() - 0.5) / 0.6;
        double randomy1 = (Math.random() - 0.5) / 0.4;
        double randomz1 = (Math.random() - 0.5) / 0.6;

        playerLoc.getWorld().spawnParticle(Particle.REDSTONE, entityLoc.clone().add(randomx1, randomy1, randomz1), 1,
                new Particle.DustOptions(Color.fromRGB(
                        new Random().nextInt(256),
                        new Random().nextInt(256),
                        new Random().nextInt(256)
                ), 1));
    }

    public int getNumberOfEquippedRadiusItems(Player player) {
        int number = 0;
        PlayerInventory inv = player.getInventory();
        if (inv.getChestplate() != null) {
            if (GodItems.isGod(inv.getChestplate())) {
                if (GodItems.getGodName(inv.getChestplate()).equalsIgnoreCase("Easter Chest")) {
                    number = number + 1;
                }
            }
        }
        if (inv.getLeggings() != null) {
            if (GodItems.isGod(inv.getLeggings())) {
                if (GodItems.getGodName(inv.getLeggings()).equalsIgnoreCase("Easter Legs")) {
                    number = number + 1;
                }
            }
        }
        return number;
    }


    //Easter's Vengeance
    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ItemStack bow = event.getBow();
        if (GodItems.getGodName(bow) == null) return;
        if (GodItems.getGodName(bow).equalsIgnoreCase("Easter's Vengeance")) {
            PlayerInventory inventory = player.getInventory();
            if (!inventory.contains(Material.EGG, 6)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You need 4 eggs to harness Easter's Vengeance.");
                return;
            }
            inventory.removeItemAnySlot(new ItemStack(Material.EGG, 4));
            fireEggs(player, event.getProjectile().getVelocity(), 6);
            event.getProjectile().remove();
        }
    }

    public void fireEggs(Player player, Vector initialVector, Integer numberOfEggs) {
        for (int i = 0; i < numberOfEggs; i++) {
            double randomx = (Math.random() - 0.5) / 2;
            double randomy = (Math.random() - 0.5) / 2;
            double randomz = (Math.random() - 0.5) / 2;
            Egg egg = player.launchProjectile(Egg.class, initialVector.clone().add(new Vector(randomx, randomy, randomz)));
            egg.setCustomName("easter's vengeance");
        }
    }

    @EventHandler
    public void onEggHit (ProjectileHitEvent event) {
        if (event.getEntity() instanceof Egg egg) {
            if (egg.getCustomName() == null) return;
            if (!egg.getCustomName().equalsIgnoreCase("easter's vengeance")) return;
            egg.getLocation().createExplosion(egg, 5f, true, true);
        }
    }

    @EventHandler
    public void onEntityDamage (EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getCause() == DamageCause.ENTITY_EXPLOSION) {
            if (event.getDamager().getType() == EntityType.EGG) {
            Bukkit.getServer().broadcastMessage("egg");
            event.setDamage(0);
            }
        }
    }

    //Easter Eggs
    @EventHandler
    public void onEggThrow (ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof Egg egg)) return;
        if (!egg.getItem().hasItemMeta()) return;
        if (!egg.getItem().getLore().contains(ChatColor.GREEN + "Easter 2022")) return;
        event.getEntity().setCustomName("easter egg");
    }
    @EventHandler
    public void onEggHitMob (EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Egg egg)) return;
        if (egg.getCustomName() == null) return;
        if (!egg.getCustomName().equalsIgnoreCase("easter egg")) return;
        if (!(event.getEntity() instanceof LivingEntity livingEntity)) return;
        if (livingEntity.isGlowing()) {
            livingEntity.getLocation().getWorld().playSound(livingEntity.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 10f, 2f);
            return;
        }
        if (livingEntity.getType() != EntityType.ENDER_DRAGON) {
            livingEntity.remove();
        }
        ItemStack mobEgg = mobEggs.getMobEggFromMob(livingEntity);
        if (mobEgg == null) return;
        livingEntity.getLocation().getWorld().dropItemNaturally(livingEntity.getLocation(), mobEgg);
        livingEntity.getLocation().getWorld().playSound(livingEntity.getLocation(), Sound.ITEM_TRIDENT_RETURN, 10f, 0.7f);

    }
}
