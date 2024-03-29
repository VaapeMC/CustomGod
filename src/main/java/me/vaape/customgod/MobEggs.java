package me.vaape.customgod;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class MobEggs {

    public ItemStack getMobEggFromMob(LivingEntity livingEntity) {
        EntityType type = livingEntity.getType();
        switch (type) {
            case AXOLOTL:       return new ItemStack(Material.AXOLOTL_SPAWN_EGG);
            case BAT:   return new ItemStack(Material.BAT_SPAWN_EGG);
            case BEE:       return new ItemStack(Material.BEE_SPAWN_EGG);
            case BLAZE:       return new ItemStack(Material.BLAZE_SPAWN_EGG);
            case CAT:   return new ItemStack(Material.CAT_SPAWN_EGG);
            case CAVE_SPIDER:       return new ItemStack(Material.CAVE_SPIDER_SPAWN_EGG);
            case CHICKEN:       return new ItemStack(Material.CHICKEN_SPAWN_EGG);
            case COD:   return new ItemStack(Material.COD_SPAWN_EGG);
            case COW:       return new ItemStack(Material.COW_SPAWN_EGG);
            case CREEPER:       return new ItemStack(Material.CREEPER_SPAWN_EGG);
            case DOLPHIN:   return new ItemStack(Material.DOLPHIN_SPAWN_EGG);
            case DONKEY:       return new ItemStack(Material.DONKEY_SPAWN_EGG);
            case DROWNED:       return new ItemStack(Material.DROWNED_SPAWN_EGG);
            case ELDER_GUARDIAN:   return new ItemStack(Material.ELDER_GUARDIAN_SPAWN_EGG);
            case ENDERMAN:       return new ItemStack(Material.ENDERMAN_SPAWN_EGG);
            case ENDERMITE:       return new ItemStack(Material.ENDERMITE_SPAWN_EGG);
            case ENDER_DRAGON:   return new ItemStack(Material.DRAGON_EGG);
            case EVOKER:       return new ItemStack(Material.EVOKER_SPAWN_EGG);
            case FOX:       return new ItemStack(Material.FOX_SPAWN_EGG);
            case GHAST:   return new ItemStack(Material.GHAST_SPAWN_EGG);
            case GIANT:       return new ItemStack(Material.DRAGON_EGG);
            case GLOW_SQUID:       return new ItemStack(Material.GLOW_SQUID_SPAWN_EGG);
            case GOAT:   return new ItemStack(Material.GOAT_SPAWN_EGG);
            case GUARDIAN:       return new ItemStack(Material.GUARDIAN_SPAWN_EGG);
            case HOGLIN:       return new ItemStack(Material.HOGLIN_SPAWN_EGG);
            case HORSE:   return new ItemStack(Material.HORSE_SPAWN_EGG);
            case HUSK:       return new ItemStack(Material.HUSK_SPAWN_EGG);
            case ILLUSIONER:       return new ItemStack(Material.EVOKER_SPAWN_EGG);
            case LLAMA:   return new ItemStack(Material.LLAMA_SPAWN_EGG);
            case MAGMA_CUBE:       return new ItemStack(Material.MAGMA_CUBE_SPAWN_EGG);
            case IRON_GOLEM:       return new ItemStack(Material.IRON_BLOCK);
            case MULE:   return new ItemStack(Material.MULE_SPAWN_EGG);
            case MUSHROOM_COW:       return new ItemStack(Material.MOOSHROOM_SPAWN_EGG);
            case OCELOT:       return new ItemStack(Material.OCELOT_SPAWN_EGG);
            case PANDA:   return new ItemStack(Material.PANDA_SPAWN_EGG);
            case PARROT:       return new ItemStack(Material.PARROT_SPAWN_EGG);
            case PHANTOM:       return new ItemStack(Material.PHANTOM_SPAWN_EGG);
            case PIG:   return new ItemStack(Material.PIG_SPAWN_EGG);
            case PIGLIN:       return new ItemStack(Material.PIGLIN_SPAWN_EGG);
            case PIGLIN_BRUTE:       return new ItemStack(Material.PIGLIN_BRUTE_SPAWN_EGG);
            case PILLAGER:   return new ItemStack(Material.PILLAGER_SPAWN_EGG);
            case POLAR_BEAR:       return new ItemStack(Material.POLAR_BEAR_SPAWN_EGG);
            case RABBIT:       return new ItemStack(Material.RABBIT_SPAWN_EGG);
            case RAVAGER:   return new ItemStack(Material.RAVAGER_SPAWN_EGG);
            case SALMON:       return new ItemStack(Material.SALMON_SPAWN_EGG);
            case PUFFERFISH:       return new ItemStack(Material.PUFFERFISH_SPAWN_EGG);
            case SHEEP:   return new ItemStack(Material.SHEEP_SPAWN_EGG);
            case SHULKER:       return new ItemStack(Material.SHULKER_SPAWN_EGG);
            case SILVERFISH:       return new ItemStack(Material.SILVERFISH_SPAWN_EGG);
            case SKELETON:   return new ItemStack(Material.SKELETON_SPAWN_EGG);
            case SKELETON_HORSE:       return new ItemStack(Material.SKELETON_HORSE_SPAWN_EGG);
            case SLIME:       return new ItemStack(Material.SLIME_SPAWN_EGG);
            case SNOWMAN:   return new ItemStack(Material.PUMPKIN);
            case SPIDER:       return new ItemStack(Material.SPIDER_SPAWN_EGG);
            case SQUID:       return new ItemStack(Material.SQUID_SPAWN_EGG);
            case STRAY:   return new ItemStack(Material.STRAY_SPAWN_EGG);
            case STRIDER:       return new ItemStack(Material.STRIDER_SPAWN_EGG);
            case TURTLE:       return new ItemStack(Material.TURTLE_SPAWN_EGG);
            case VEX:   return new ItemStack(Material.VEX_SPAWN_EGG);
            case VILLAGER:       return new ItemStack(Material.VILLAGER_SPAWN_EGG);
            case TRADER_LLAMA:   return new ItemStack(Material.TRADER_LLAMA_SPAWN_EGG);
            case VINDICATOR:       return new ItemStack(Material.VINDICATOR_SPAWN_EGG);
            case WANDERING_TRADER:       return new ItemStack(Material.WANDERING_TRADER_SPAWN_EGG);
            case WITCH:   return new ItemStack(Material.WITCH_SPAWN_EGG);
            case TROPICAL_FISH:       return new ItemStack(Material.TROPICAL_FISH_SPAWN_EGG);
            case WITHER_SKELETON:       return new ItemStack(Material.WITHER_SKELETON_SPAWN_EGG);
            case WITHER:   return new ItemStack(Material.WITHER_SKELETON_SKULL);
            case ZOGLIN:       return new ItemStack(Material.ZOGLIN_SPAWN_EGG);
            case ZOMBIE:       return new ItemStack(Material.ZOMBIE_SPAWN_EGG);
            case ZOMBIE_HORSE:       return new ItemStack(Material.ZOMBIE_HORSE_SPAWN_EGG);
            case ZOMBIE_VILLAGER:       return new ItemStack(Material.ZOMBIE_VILLAGER_SPAWN_EGG);
            case ZOMBIFIED_PIGLIN:       return new ItemStack(Material.ZOMBIFIED_PIGLIN_SPAWN_EGG);
            default: return null;
        }
    }
}
