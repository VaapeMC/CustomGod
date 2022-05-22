package me.vaape.customgod;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class GodItems {

    public static boolean isGod(ItemStack stack) {
        if (stack != null && stack.getType() != Material.AIR) {
            if (stack.hasItemMeta()) {
                if (stack.getItemMeta().hasLore()) {
                    List<String> lore = stack.getItemMeta().getLore();
                    //Snowy boots
                    return lore.contains(ChatColor.ITALIC + "Curses the souls of enemies") || //Runeblade
                            lore.contains(ChatColor.ITALIC + "Found under the corpse of") || //Dalabrus
                            lore.contains(ChatColor.ITALIC + "Only the worthy") || //Mjolnir
                            lore.contains(ChatColor.ITALIC + "Fire can be seen") || //Sulfuron
                            lore.contains(ChatColor.ITALIC + "Shows signs of being") || //Anduril
                            lore.contains(ChatColor.ITALIC + "Consumes the souls") || //Shadow's Edge
                            lore.contains(ChatColor.ITALIC + "Found in the woods") || //Medusa
                            lore.contains(ChatColor.ITALIC + "of the phoenix Al'ar,") || //Flameheart
                            lore.contains(ChatColor.ITALIC + "Used by ancient Colombian") || //Kamatayon
                            lore.contains(ChatColor.ITALIC + "Taken from a dying priest") || //Daedric Helm
                            lore.contains(ChatColor.ITALIC + "Said to have belonged to an") || //Theseus
                            lore.contains(ChatColor.ITALIC + "Found on the corpse of an") || //Warlord Cuirass
                            lore.contains(ChatColor.ITALIC + "Worn by the Greek") || //Hermes Leggings
                            lore.contains(ChatColor.ITALIC + "'As light as a feather, as silent") || //Brightwing
                            lore.contains(ChatColor.ITALIC + "Ancient untranslatable runes") || //Shadow Seeker
                            lore.contains(ChatColor.ITALIC + "Found at the bottom of") || //Fisherman's Friend
                            lore.contains(ChatColor.ITALIC + "Said to have corrupted") || //Cane of Zefeus
                            lore.contains(ChatColor.ITALIC + "Rapid fire eggs...") || //Easter 2nd place
                            lore.contains(ChatColor.ITALIC + "Sharp, fiery, and delicious...") || //Easter 3rd place
                            (lore.contains(ChatColor.GREEN + "Christmas 2021") && stack.getType() != Material.CLOCK && stack.getType() != Material.ENCHANTED_GOLDEN_APPLE && stack.getType() != Material.TRIPWIRE_HOOK) || //Snowy stuff
                            (lore.contains(ChatColor.GREEN + "Valentine's Day 2022") && stack.getType() != Material.CLOCK && stack.getType() != Material.ENCHANTED_GOLDEN_APPLE && stack.getType() != Material.TRIPWIRE_HOOK) || //Valentines stuff
                            (lore.contains(ChatColor.GREEN + "Easter 2022") && stack.getType() != Material.CLOCK && stack.getType() != Material.ENCHANTED_GOLDEN_APPLE && stack.getType() != Material.TRIPWIRE_HOOK && stack.getType() != Material.EGG && stack.getType() != Material.COOKIE && stack.getType() != Material.POTION); //Valentines stuff
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isLegendary(ItemStack stack) {
        if (stack != null && stack.getType() != Material.AIR) {
            if (stack.hasItemMeta()) {
                if (stack.getItemMeta().hasLore()) {
                    List<String> lore = stack.getItemMeta().getLore();
                    if (lore.contains(ChatColor.ITALIC + "Only the worthy")) { //Mjolnir
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isUnique(ItemStack stack) {
        if (stack != null && stack.getType() != Material.AIR) {
            if (stack.hasItemMeta()) {
                if (stack.getItemMeta().hasLore()) {
                    List<String> lore = stack.getItemMeta().getLore();
                    if (lore.contains(ChatColor.ITALIC + "Curses the souls of enemies")) { //Runeblade
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //All god weapons have kill count
    public static boolean isGodWeapon(ItemStack stack) {
        if (!isGod(stack)) {
            return false;
        }
        if (    getGodName(stack).equals("Runeblade") ||
                getGodName(stack).equals("Mjolnir") ||
                getGodName(stack).equals("Sulfuron") ||
                getGodName(stack).equals("Anduril") ||
                getGodName(stack).equals("Shadow's Edge") ||
                getGodName(stack).equals("Medusa") ||
                getGodName(stack).equals("Flameheart") ||
                getGodName(stack).equals("Kamatayon") ||
                getGodName(stack).equals("Shadow Seeker") ||
                getGodName(stack).equals("Fisherman's Friend") ||
                getGodName(stack).equals("Cane of Zefeus") ||
                getGodName(stack).equals("Dalabrus") ||
                getGodName(stack).equals("Glacial Brand") ||
                getGodName(stack).equals("Glacial Bow") ||
                getGodName(stack).equals("Ice Pick") ||
                getGodName(stack).equals("Cupid's Bow") ||
                getGodName(stack).equals("Bunny's Secret Snack") ||
                getGodName(stack).equals("Easter's Vengeance")) {
            return true;
        } else {
            return false;
        }
    }

    public static String getGodName(ItemStack stack) {
        if (stack != null && stack.getType() != Material.AIR) {
            if (stack.hasItemMeta()) {
                if (stack.getItemMeta().hasLore()) {
                    List<String> lore = stack.getItemMeta().getLore();
                    if (lore.contains(ChatColor.ITALIC + "Curses the souls of enemies")) {
                        return "Runeblade";
                    } else if (lore.contains(ChatColor.ITALIC + "Only the worthy")) {
                        return "Mjolnir";
                    } else if (lore.contains(ChatColor.ITALIC + "Fire can be seen")) {
                        return "Sulfuron";
                    } else if (lore.contains(ChatColor.ITALIC + "Shows signs of being")) {
                        return "Anduril";
                    } else if (lore.contains(ChatColor.ITALIC + "Consumes the souls")) {
                        return "Shadow's Edge";
                    } else if (lore.contains(ChatColor.ITALIC + "Found in the woods")) {
                        return "Medusa";
                    } else if (lore.contains(ChatColor.ITALIC + "of the phoenix Al'ar,")) {
                        return "Flameheart";
                    } else if (lore.contains(ChatColor.ITALIC + "Used by ancient Colombian")) {
                        return "Kamatayon";
                    } else if (lore.contains(ChatColor.ITALIC + "Ancient untranslatable runes")) {
                        return "Shadow Seeker";
                    } else if (lore.contains(ChatColor.ITALIC + "Found at the bottom of")) {
                        return "Fisherman's Friend";
                    } else if (lore.contains(ChatColor.ITALIC + "Said to have corrupted")) {
                        return "Cane of Zefeus";
                    } else if (lore.contains(ChatColor.ITALIC + "Found under the corpse of")) {
                        return "Dalabrus";
                    }
                    //Armour
                    else if (lore.contains(ChatColor.ITALIC + "Taken from a dying priest")) {
                        return "Daedric Helm";
                    } else if (lore.contains(ChatColor.ITALIC + "Said to have belonged to an")) {
                        return "Theseus";
                    } else if (lore.contains(ChatColor.ITALIC + "Found on the corpse of an")) {
                        return "Warlord Cuirass";
                    } else if (lore.contains(ChatColor.ITALIC + "Worn by the Greek")) {
                        return "Hermes Leggings";
                    } else if (lore.contains(ChatColor.ITALIC + "'As light as a feather, as silent")) {
                        return "Brightwing";
                    } else if (lore.contains(ChatColor.ITALIC + "Your crushing blows are")) {
                        return "Glacial Brand";
                    } else if (lore.contains(ChatColor.ITALIC + "The soils and sands must")) {
                        return "Glacial Bow";
                    } else if (lore.contains(ChatColor.ITALIC + "A climber's best friend")) {
                        return "Ice Pick";
                    } else if (lore.contains(ChatColor.GREEN + "Christmas 2021") && stack.getType() == Material.LEATHER_HELMET) {
                        LeatherArmorMeta leatherMeta = (LeatherArmorMeta) stack.getItemMeta();
                        if (leatherMeta.getColor().equals(Color.fromRGB(0x5E7C16))) {
                            return "Green Snowy Helm";
                        }
                        else if (leatherMeta.getColor().equals(Color.fromRGB(0xB02E26))) {
                            return "Red Snowy Helm";
                        }
                        return null;
                    } else if (lore.contains(ChatColor.GREEN + "Christmas 2021") && stack.getType() == Material.LEATHER_CHESTPLATE) {
                        LeatherArmorMeta leatherMeta = (LeatherArmorMeta) stack.getItemMeta();
                        if (leatherMeta.getColor().equals(Color.fromRGB(0x5E7C16))) {
                            return "Green Snowy Chest";
                        }
                        else if (leatherMeta.getColor().equals(Color.fromRGB(0xB02E26))) {
                            return "Red Snowy Chest";
                        }
                        return null;
                    } else if (lore.contains(ChatColor.GREEN + "Christmas 2021") && stack.getType() == Material.LEATHER_LEGGINGS) {
                        LeatherArmorMeta leatherMeta = (LeatherArmorMeta) stack.getItemMeta();
                        if (leatherMeta.getColor().equals(Color.fromRGB(0x5E7C16))) {
                            return "Green Snowy Legs";
                        }
                        else if (leatherMeta.getColor().equals(Color.fromRGB(0xB02E26))) {
                            return "Red Snowy Legs";
                        }
                        return null;
                    } else if (lore.contains(ChatColor.GREEN + "Christmas 2021") && stack.getType() == Material.LEATHER_BOOTS) {
                        LeatherArmorMeta leatherMeta = (LeatherArmorMeta) stack.getItemMeta();
                        if (leatherMeta.getColor().equals(Color.fromRGB(0x5E7C16))) {
                            return "Green Snowy Boots";
                        }
                        else if (leatherMeta.getColor().equals(Color.fromRGB(0xB02E26))) {
                            return "Red Snowy Boots";
                        }
                        return null;
                    }
                    //VALENTINE'S
                    else if (lore.contains(ChatColor.GREEN + "Valentine's Day 2022") && stack.getType() == Material.LEATHER_HELMET) {
                        return "Valentine's Helm";
                    } else if (lore.contains(ChatColor.GREEN + "Valentine's Day 2022") && stack.getType() == Material.ELYTRA) {
                        return "Cupid's Wings";
                    } else if (lore.contains(ChatColor.GREEN + "Valentine's Day 2022") && stack.getType() == Material.LEATHER_LEGGINGS) {
                        return "Valentine's Legs";
                    } else if (lore.contains(ChatColor.GREEN + "Valentine's Day 2022") && stack.getType() == Material.LEATHER_BOOTS) {
                        return "Valentine's Boots";
                    } else if (lore.contains(ChatColor.GREEN + "Valentine's Day 2022") && stack.getType() == Material.PAPER) {
                        return "Love Letter";
                    } else if (lore.contains(ChatColor.GREEN + "Valentine's Day 2022") && stack.getType() == Material.BOW) {
                        return "Cupid's Bow";
                    }
                    //EASTER 2022
                    else if (lore.contains(ChatColor.GREEN + "Easter 2022") && stack.getType() == Material.PLAYER_HEAD) {
                        return "Bunny Head";
                    } else if (lore.contains(ChatColor.GREEN + "Easter 2022") && stack.getType() == Material.IRON_CHESTPLATE) {
                        return "Easter Chest";
                    } else if (lore.contains(ChatColor.GREEN + "Easter 2022") && stack.getType() == Material.IRON_LEGGINGS) {
                        return "Easter Legs";
                    } else if (lore.contains(ChatColor.GREEN + "Easter 2022") && stack.getType() == Material.IRON_BOOTS) {
                        return "Easter Boots";
                    } else if (lore.contains(ChatColor.GREEN + "Easter 2022") && stack.getType() == Material.GOLDEN_CARROT) {
                        return "Bunny's Secret Snack";
                    } else if (lore.contains(ChatColor.GREEN + "Easter 2022") && stack.getType() == Material.CROSSBOW) {
                        return "Easter's Vengeance";
                    } else if (lore.contains(ChatColor.ITALIC + "Sharp, fiery, and delicious...")) {
                        return "Bunny's Secret Snack";
                    } else if (lore.contains(ChatColor.ITALIC + "Rapid fire eggs...")) {
                        return "Easter's Vengeance";
                    }
                    else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static boolean isEnhancable(ItemStack item) {
        List<String> allowedItems = new ArrayList<>();
        //Wood
        allowedItems.add("Wooden Shovel");
        allowedItems.add("Wooden Pickaxe");
        allowedItems.add("Wooden Axe");
        allowedItems.add("Wooden Hoe");
        //Stone
        allowedItems.add("Stone Shovel");
        allowedItems.add("Stone Pickaxe");
        allowedItems.add("Stone Axe");
        allowedItems.add("Stone Hoe");
        //Iron
        allowedItems.add("Iron Shovel");
        allowedItems.add("Iron Pickaxe");
        allowedItems.add("Iron Axe");
        allowedItems.add("Iron Hoe");
        allowedItems.add("Iron Helmet");
        allowedItems.add("Iron Chestplate");
        allowedItems.add("Iron Leggings");
        allowedItems.add("Iron Boots");
        //Gold
        allowedItems.add("Gold Shovel");
        allowedItems.add("Gold Pickaxe");
        allowedItems.add("Gold Axe");
        allowedItems.add("Gold Hoe");
        allowedItems.add("Gold Helmet");
        allowedItems.add("Gold Chestplate");
        allowedItems.add("Gold Leggings");
        allowedItems.add("Gold Boots");
        //Diamond
        allowedItems.add("Diamond Shovel");
        allowedItems.add("Diamond Pickaxe");
        allowedItems.add("Diamond Axe");
        allowedItems.add("Diamond Hoe");
        allowedItems.add("Diamond Helmet");
        allowedItems.add("Diamond Chestplate");
        allowedItems.add("Diamond Leggings");
        allowedItems.add("Diamond Boots");
        //Netherite
        allowedItems.add("Netherite Shovel");
        allowedItems.add("Netherite Pickaxe");
        allowedItems.add("Netherite Axe");
        allowedItems.add("Netherite Hoe");
        allowedItems.add("Netherite Helmet");
        allowedItems.add("Netherite Chestplate");
        allowedItems.add("Netherite Leggings");
        allowedItems.add("Netherite Boots");
        //Leather
        allowedItems.add("Leather Helmet");
        allowedItems.add("Leather Chestplate");
        allowedItems.add("Leather Leggings");
        allowedItems.add("Leather Boots");
        //Other
        allowedItems.add("Fishing Rod");
        allowedItems.add("Turtle Shell");
        allowedItems.add("Trident");

        if (allowedItems.contains(item.getI18NDisplayName())) return true;
        else return false;
    }

}
