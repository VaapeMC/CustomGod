package com.codingforcookies.armorequip;

import com.codingforcookies.armorequip.ArmorEquipEvent.EquipMethod;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Arnah
 * @since Jul 30, 2015
 */
public class ArmorListener implements Listener{

	private final List<String> blockedMaterials;

	public ArmorListener(List<String> blockedMaterials){
		this.blockedMaterials = blockedMaterials;
	}
	//Event Priority is highest because other plugins might cancel the events before we check.

	@EventHandler(priority =  EventPriority.HIGHEST, ignoreCancelled = true)
	public final void inventoryClick(final InventoryClickEvent e){
		boolean shift = false, numberkey = false;
		if(e.isCancelled()) return;
		if(e.getAction() == InventoryAction.NOTHING) return;// Why does this get called if nothing happens??
		if(e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)){
			shift = true;
		}
		if(e.getClick().equals(ClickType.NUMBER_KEY)){
			numberkey = true;
		}
		if(e.getSlotType() != SlotType.ARMOR && e.getSlotType() != SlotType.QUICKBAR && e.getSlotType() != SlotType.CONTAINER) return;
		if(e.getClickedInventory() != null && !e.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;
		if (!e.getInventory().getType().equals(InventoryType.CRAFTING) && !e.getInventory().getType().equals(InventoryType.PLAYER)) return;
		if(!(e.getWhoClicked() instanceof Player)) return;
		ArmorType newArmorType = ArmorType.matchType(shift ? e.getCurrentItem() : e.getCursor());
		if(!shift && newArmorType != null && e.getRawSlot() != newArmorType.getSlot()){
			// Used for drag and drop checking to make sure you aren't trying to place a helmet in the boots slot.
			return;
		}
		if(shift){
			newArmorType = ArmorType.matchType(e.getCurrentItem());
			if(newArmorType != null){
				boolean equipping = true;
				if(e.getRawSlot() == newArmorType.getSlot()){
					equipping = false;
				}
				if(newArmorType.equals(ArmorType.HELMET) && (equipping ? isAirOrNull(e.getWhoClicked().getInventory().getHelmet()) : !isAirOrNull(e.getWhoClicked().getInventory().getHelmet())) || newArmorType.equals(ArmorType.CHESTPLATE) && (equipping ? isAirOrNull(e.getWhoClicked().getInventory().getChestplate()) : !isAirOrNull(e.getWhoClicked().getInventory().getChestplate())) || newArmorType.equals(ArmorType.LEGGINGS) && (equipping ? isAirOrNull(e.getWhoClicked().getInventory().getLeggings()) : !isAirOrNull(e.getWhoClicked().getInventory().getLeggings())) || newArmorType.equals(ArmorType.BOOTS) && (equipping ? isAirOrNull(e.getWhoClicked().getInventory().getBoots()) : !isAirOrNull(e.getWhoClicked().getInventory().getBoots()))){
					ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) e.getWhoClicked(), EquipMethod.SHIFT_CLICK, newArmorType, equipping ? null : e.getCurrentItem(), equipping ? e.getCurrentItem() : null);
					Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
					if(armorEquipEvent.isCancelled()){
						e.setCancelled(true);
					}
				}
			}
		}else{
			ItemStack newArmorPiece = e.getCursor();
			ItemStack oldArmorPiece = e.getCurrentItem();
			if(numberkey){
				if(e.getClickedInventory().getType().equals(InventoryType.PLAYER)){// Prevents shit in the 2by2 crafting
					// e.getClickedInventory() == The players inventory
					// e.getHotBarButton() == key people are pressing to equip or unequip the item to or from.
					// e.getRawSlot() == The slot the item is going to.
					// e.getSlot() == Armor slot, can't use e.getRawSlot() as that gives a hotbar slot ;-;
					ItemStack hotbarItem = e.getClickedInventory().getItem(e.getHotbarButton());
					if(!isAirOrNull(hotbarItem)){// Equipping
						newArmorType = ArmorType.matchType(hotbarItem);
						newArmorPiece = hotbarItem;
						oldArmorPiece = e.getClickedInventory().getItem(e.getSlot());
					}else{// Unequipping
						newArmorType = ArmorType.matchType(!isAirOrNull(e.getCurrentItem()) ? e.getCurrentItem() : e.getCursor());
					}
				}
			}else{
				if(isAirOrNull(e.getCursor()) && !isAirOrNull(e.getCurrentItem())){// unequip with no new item going into the slot.
					newArmorType = ArmorType.matchType(e.getCurrentItem());
				}
				// e.getCurrentItem() == Unequip
				// e.getCursor() == Equip
				// newArmorType = ArmorType.matchType(!isAirOrNull(e.getCurrentItem()) ? e.getCurrentItem() : e.getCursor());
			}
			if(newArmorType != null && e.getRawSlot() == newArmorType.getSlot()){
				EquipMethod method = EquipMethod.PICK_DROP;
				if(e.getAction().equals(InventoryAction.HOTBAR_SWAP) || numberkey) method = EquipMethod.HOTBAR_SWAP;
				ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) e.getWhoClicked(), method, newArmorType, oldArmorPiece, newArmorPiece);
				Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
				if(armorEquipEvent.isCancelled()){
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority =  EventPriority.HIGHEST)
	public void playerInteractEvent(PlayerInteractEvent e){
		boolean shift = false;
		if (e.getPlayer().isSneaking()) shift = true;
		if(e.useItemInHand().equals(Result.DENY))return;
		//
		if(e.getAction() == Action.PHYSICAL) return;
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if (e.getClickedBlock() != null) {
				Material blockType = e.getClickedBlock().getType();
				if (!shift && (blockType.equals(Material.CRAFTING_TABLE) ||
					blockType.equals(Material.CHEST) ||
					blockType.equals(Material.ENDER_CHEST) ||
					blockType.equals(Material.LOOM) ||
					blockType.equals(Material.WATER_CAULDRON) ||
					blockType.equals(Material.DROPPER) ||
					blockType.equals(Material.DISPENSER) ||
					blockType.equals(Material.HOPPER) ||
					blockType.equals(Material.SMITHING_TABLE) ||
					blockType.equals(Material.JUKEBOX) ||
					blockType.equals(Material.FURNACE) ||
					blockType.equals(Material.CHIPPED_ANVIL) ||
					blockType.equals(Material.DAMAGED_ANVIL) ||
					blockType.equals(Material.ENCHANTING_TABLE) ||
					blockType.equals(Material.NOTE_BLOCK) ||
					blockType.equals(Material.SHULKER_BOX) ||
					blockType.equals(Material.LODESTONE) ||
					blockType.equals(Material.RESPAWN_ANCHOR) ||
					blockType.equals(Material.BLACK_SHULKER_BOX) ||
					blockType.equals(Material.BLUE_SHULKER_BOX) ||
					blockType.equals(Material.BROWN_SHULKER_BOX) ||
					blockType.equals(Material.CYAN_SHULKER_BOX) ||
					blockType.equals(Material.GRAY_SHULKER_BOX) ||
					blockType.equals(Material.GREEN_SHULKER_BOX) ||
					blockType.equals(Material.LIGHT_BLUE_SHULKER_BOX) ||
					blockType.equals(Material.LIGHT_GRAY_SHULKER_BOX) ||
					blockType.equals(Material.LIME_SHULKER_BOX) ||
					blockType.equals(Material.MAGENTA_SHULKER_BOX) ||
					blockType.equals(Material.ORANGE_SHULKER_BOX) ||
					blockType.equals(Material.PINK_SHULKER_BOX) ||
					blockType.equals(Material.PURPLE_SHULKER_BOX) ||
					blockType.equals(Material.RED_SHULKER_BOX) ||
					blockType.equals(Material.YELLOW_SHULKER_BOX) ||
					blockType.equals(Material.WHITE_SHULKER_BOX) ||
					blockType.equals(Material.BLACK_BED) ||
					blockType.equals(Material.BLUE_BED) ||
					blockType.equals(Material.BROWN_BED) ||
					blockType.equals(Material.CYAN_BED) ||
					blockType.equals(Material.GRAY_BED) ||
					blockType.equals(Material.GREEN_BED) ||
					blockType.equals(Material.LIGHT_BLUE_BED) ||
					blockType.equals(Material.LIGHT_GRAY_BED) ||
					blockType.equals(Material.LIME_BED) ||
					blockType.equals(Material.MAGENTA_BED) ||
					blockType.equals(Material.ORANGE_BED) ||
					blockType.equals(Material.PINK_BED) ||
					blockType.equals(Material.PURPLE_BED) ||
					blockType.equals(Material.RED_BED) ||
					blockType.equals(Material.YELLOW_BED) ||
					blockType.equals(Material.WHITE_BED) ||
					blockType.equals(Material.GRINDSTONE) ||
					blockType.equals(Material.BARREL) ||
					blockType.equals(Material.BLAST_FURNACE) ||
					blockType.equals(Material.SMOKER) ||
					blockType.equals(Material.FLETCHING_TABLE) ||
					blockType.equals(Material.CARTOGRAPHY_TABLE) ||
					blockType.equals(Material.ACACIA_BUTTON) ||
					blockType.equals(Material.BIRCH_BUTTON) ||
					blockType.equals(Material.CRIMSON_BUTTON) ||
					blockType.equals(Material.JUNGLE_BUTTON) ||
					blockType.equals(Material.DARK_OAK_BUTTON) ||
					blockType.equals(Material.OAK_BUTTON) ||
					blockType.equals(Material.POLISHED_BLACKSTONE_BUTTON) ||
					blockType.equals(Material.SPRUCE_BUTTON) ||
					blockType.equals(Material.STONE_BUTTON) ||
					blockType.equals(Material.WARPED_BUTTON) ||
					blockType.equals(Material.REPEATER) ||
					blockType.equals(Material.COMPARATOR) ||
					blockType.equals(Material.LEVER) ||
					blockType.equals(Material.DARK_OAK_DOOR) ||
					blockType.equals(Material.ACACIA_DOOR) ||
					blockType.equals(Material.BIRCH_DOOR) ||
					blockType.equals(Material.CRIMSON_DOOR) ||
					blockType.equals(Material.JUNGLE_DOOR) ||
					blockType.equals(Material.OAK_DOOR) ||
					blockType.equals(Material.SPRUCE_DOOR) ||
					blockType.equals(Material.WARPED_DOOR) ||
					blockType.equals(Material.DARK_OAK_TRAPDOOR) ||
					blockType.equals(Material.ACACIA_TRAPDOOR) ||
					blockType.equals(Material.BIRCH_TRAPDOOR) ||
					blockType.equals(Material.CRIMSON_TRAPDOOR) ||
					blockType.equals(Material.JUNGLE_TRAPDOOR) ||
					blockType.equals(Material.OAK_TRAPDOOR) ||
					blockType.equals(Material.SPRUCE_TRAPDOOR) ||
					blockType.equals(Material.WARPED_TRAPDOOR) ||
					blockType.equals(Material.ACACIA_FENCE_GATE) ||
					blockType.equals(Material.BIRCH_FENCE_GATE) ||
					blockType.equals(Material.CRIMSON_FENCE_GATE) ||
					blockType.equals(Material.DARK_OAK_FENCE_GATE) ||
					blockType.equals(Material.JUNGLE_FENCE_GATE) ||
					blockType.equals(Material.OAK_FENCE_GATE) ||
					blockType.equals(Material.SPRUCE_FENCE_GATE) ||
					blockType.equals(Material.WARPED_FENCE_GATE) ||
					blockType.equals(Material.CAKE) ||
					blockType.equals(Material.CANDLE_CAKE) ||
					blockType.equals(Material.BLACK_CANDLE_CAKE) ||
					blockType.equals(Material.BLUE_CANDLE_CAKE) ||
					blockType.equals(Material.CYAN_CANDLE_CAKE) ||
					blockType.equals(Material.BROWN_CANDLE_CAKE) ||
					blockType.equals(Material.GRAY_CANDLE_CAKE) ||
					blockType.equals(Material.GREEN_CANDLE_CAKE) ||
					blockType.equals(Material.LIGHT_BLUE_CANDLE_CAKE) ||
					blockType.equals(Material.LIGHT_GRAY_CANDLE_CAKE) ||
					blockType.equals(Material.LIME_CANDLE_CAKE) ||
					blockType.equals(Material.MAGENTA_CANDLE_CAKE) ||
					blockType.equals(Material.ORANGE_CANDLE_CAKE) ||
					blockType.equals(Material.PINK_CANDLE_CAKE) ||
					blockType.equals(Material.PURPLE_CANDLE_CAKE) ||
					blockType.equals(Material.RED_CANDLE_CAKE) ||
					blockType.equals(Material.WHITE_CANDLE_CAKE) ||
					blockType.equals(Material.YELLOW_CANDLE_CAKE))
				) {
					return;
				}
			}
			Player player = e.getPlayer();
			if(!e.useInteractedBlock().equals(Result.DENY)){
				if(e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK && !player.isSneaking()){// Having both of these checks is useless, might as well do it though.
					// Some blocks have actions when you right click them which stops the client from equipping the armor in hand.
					Material mat = e.getClickedBlock().getType();
					for(String s : blockedMaterials){
						if(mat.name().equalsIgnoreCase(s)) return;
					}
				}
			}
			ArmorType newArmorType = ArmorType.matchType(e.getItem());
			if(newArmorType != null){
				if(newArmorType.equals(ArmorType.HELMET) && isAirOrNull(e.getPlayer().getInventory().getHelmet()) || newArmorType.equals(ArmorType.CHESTPLATE) && isAirOrNull(e.getPlayer().getInventory().getChestplate()) || newArmorType.equals(ArmorType.LEGGINGS) && isAirOrNull(e.getPlayer().getInventory().getLeggings()) || newArmorType.equals(ArmorType.BOOTS) && isAirOrNull(e.getPlayer().getInventory().getBoots())){
					ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(e.getPlayer(), EquipMethod.HOTBAR, ArmorType.matchType(e.getItem()), null, e.getItem());
					Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
					if(armorEquipEvent.isCancelled()){
						e.setCancelled(true);
						player.updateInventory();
					}
				}
			}
		}
	}
	
	@EventHandler(priority =  EventPriority.HIGHEST, ignoreCancelled = true)
	public void inventoryDrag(InventoryDragEvent event){
		// getType() seems to always be even.
		// Old Cursor gives the item you are equipping
		// Raw slot is the ArmorType slot
		// Can't replace armor using this method making getCursor() useless.
		ArmorType type = ArmorType.matchType(event.getOldCursor());
		if(event.getRawSlots().isEmpty()) return;// Idk if this will ever happen
		if(type != null && type.getSlot() == event.getRawSlots().stream().findFirst().orElse(0)){
			ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) event.getWhoClicked(), EquipMethod.DRAG, type, null, event.getOldCursor());
			Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
			if(armorEquipEvent.isCancelled()){
				event.setResult(Result.DENY);
				event.setCancelled(true);
			}
		}
		// Debug shit
		/*System.out.println("Slots: " + event.getInventorySlots().toString());
		System.out.println("Raw Slots: " + event.getRawSlots().toString());
		if(event.getCursor() != null){
			System.out.println("Cursor: " + event.getCursor().getType().name());
		}
		if(event.getOldCursor() != null){
			System.out.println("OldCursor: " + event.getOldCursor().getType().name());
		}
		System.out.println("Type: " + event.getType().name());*/
	}

	@EventHandler
	public void itemBreakEvent(PlayerItemBreakEvent e){
		ArmorType type = ArmorType.matchType(e.getBrokenItem());
		if(type != null){
			Player p = e.getPlayer();
			ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, EquipMethod.BROKE, type, e.getBrokenItem(), null);
			Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
			if(armorEquipEvent.isCancelled()){
				ItemStack i = e.getBrokenItem().clone();
				i.setAmount(1);
				i.setDurability((short) (i.getDurability() - 1));
				if(type.equals(ArmorType.HELMET)){
					p.getInventory().setHelmet(i);
				}else if(type.equals(ArmorType.CHESTPLATE)){
					p.getInventory().setChestplate(i);
				}else if(type.equals(ArmorType.LEGGINGS)){
					p.getInventory().setLeggings(i);
				}else if(type.equals(ArmorType.BOOTS)){
					p.getInventory().setBoots(i);
				}
			}
		}
	}

	@EventHandler
	public void playerDeathEvent(PlayerDeathEvent e){
		Player p = e.getEntity();
		if(e.getKeepInventory()) return;
		for(ItemStack i : p.getInventory().getArmorContents()){
			if(!isAirOrNull(i)){
				Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(p, EquipMethod.DEATH, ArmorType.matchType(i), i, null));
				// No way to cancel a death event.
			}
		}
	}

	/**
	 * A utility method to support versions that use null or air ItemStacks.
	 */
	public static boolean isAirOrNull(ItemStack item){
		return item == null || item.getType().equals(Material.AIR);
	}
}
