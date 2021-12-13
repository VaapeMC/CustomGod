package me.vaape.customgod;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.SmithingInventory;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class ForgeListener implements Listener {
	
	private static CustomGod plugin = CustomGod.getInstance();
	
	public static boolean xpTake30 = false;
	public static boolean xpTake50 = false;
	public static boolean is30 = false;
	public static boolean notEnoughXPMessage = false;
	public static boolean applyingEnchants = false;
	
	
	
	@EventHandler
	public static void onInventoryClick(InventoryClickEvent event){
		
		if (event.getWhoClicked() instanceof Player) {
			
			Player player = (Player) event.getWhoClicked();
			Location location = player.getLocation();
			double x = location.getX();
            double y = location.getY();
            double z = location.getZ();
			if ((x > 43 && x < 63) && (y > 208 && y < 228) && (z > 205 && z < 225)) { //cords of God Forge
				Inventory inv = event.getInventory();
				int originalXP = player.getLevel();
				
				xpTake30 = false;
				xpTake50 = false;
				is30 = false;
				notEnoughXPMessage = false;
				applyingEnchants = false; //Used to avoid sending multiple error messages
				
                if (inv instanceof AnvilInventory) {
                	AnvilInventory anvil = (AnvilInventory) inv;
    				ItemStack input = anvil.getItem(0);
    				ItemStack addition = anvil.getItem(1);
    				ItemStack result = anvil.getItem(2);
    				int rawSlot = event.getRawSlot();
    				String newName = anvil.getRenameText(); //Save rename text to apply to result later (creating an instance of this after the 1 tick delay bugs it out)
    				
    				//Early cancel so can artificially build the event
                	if (rawSlot == 2 && input != null && result != null) { //If player clicks the result box
                		event.setCancelled(true);
                	}
                	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() { //Has to be 1 tick after to update
						
						@Override
						public void run() {
							anvil.setRepairCost(30);
							if (rawSlot == 2) {
								if (GodItems.isGod(input)) {
									
									//If addition is null then no item in addition slot
									//If addition is != null then player is combining something with god item
									
									//Repair
									if (addition != null) {
										
				                    	if (addition.getEnchantments().size() > 0 || addition.getType() == Material.ENCHANTED_BOOK) {
				                    		player.sendMessage(ChatColor.RED + "You cannot apply enchants to God items.");
				                    		player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
				                    		applyingEnchants = true;
				                    	}
				                    	else {
				                    		
				                    		//Analysing repair count
				                    		if (input.getItemMeta() != null) {
				                    			ItemMeta inputMeta = input.getItemMeta();
				                    			if (inputMeta.hasLore()) {
				                    				List<String> inputLore = inputMeta.getLore();
				                            		String[] repairLine = inputLore.get(inputLore.size() - 1).split("\\s"); //Get "Times repaired: x" line of lore
				                            		if (Arrays.asList(repairLine).contains("repaired:")) {
				                            			int timesRepaired = Integer.parseInt(repairLine[2]);
				                            			
				                            			if (timesRepaired < 2) { //has been repaired 0 or 1 times, making this time the 1st or 2nd time
				                            				
				                            				if (player.hasPermission("customgod.forge.repair.2")) {
				                            					
				                            					//Adding 1 to Times repaired
				                                				String newRepairLine = ChatColor.GRAY + repairLine[0] + " " + repairLine[1] + " " + (timesRepaired + 1);
				                                				if (result != null) {
				                                					if (result.getItemMeta() != null) {
					                                					ItemMeta resultMeta = result.getItemMeta();
					                                					if (resultMeta.hasLore()) {
					                                						List<String> resultLore = resultMeta.getLore();
					                                						resultLore.set(resultLore.size() - 1, newRepairLine);
					                                						resultMeta.setLore(resultLore);
					                                						result.setItemMeta(resultMeta);
					                                						
					                                						if (event.getCursor() == null || event.getCursor().getType() == Material.AIR) { //Incase player has item on cursor already
					                                							
					                                							if (player.getLevel() >= 30) {
						                                                            xpTake30 = true; //Tells later code to execute repair and take 30 xp
					                                							}
					                                							else {
					                                								player.sendMessage(ChatColor.RED + "You must be at least level 30 to repair this item.");
					                                								player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
					                                								notEnoughXPMessage = true;
					                                							}
					                                						}
					                                					}
					                                				}
				                                				}
				                                			}
				                                			else {
				                                				player.sendMessage(ChatColor.RED + "You cannot repair God items.");
				                                				player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
				                                			}
				                            			}
				                            			
				                            			else if (timesRepaired > 1 && timesRepaired < 4){ //Has been repaired 2 or 3 times, making this time the 3rd or 4th
				                            				
				                            				if (player.hasPermission("customgod.forge.repair.4")) {
				                            					
				                            					//Adding 1 to Times repaired
				                            					String newRepairLine = ChatColor.GRAY + repairLine[0] + " " + repairLine[1] + " " + (timesRepaired + 1);
				                            					if (result != null) {
				                            						if (result.getItemMeta() != null) {
					                                					ItemMeta resultMeta = result.getItemMeta();
					                                					if (resultMeta.hasLore()) {
					                                						List<String> resultLore = resultMeta.getLore();
					                                						resultLore.set(resultLore.size() - 1, newRepairLine);
					                                						resultMeta.setLore(resultLore);
					                                						result.setItemMeta(resultMeta);
					                                						
					                                						if (event.getCursor() == null || event.getCursor().getType() == Material.AIR) {
					                                							
					                                							if (player.getLevel() >= 50) {
						                                                            xpTake50 = true; //Tells later code to execute repair and take 50 xp
					                                							}
					                                							else {
					                                								player.sendMessage(ChatColor.RED + "You must be at least level 50 to repair this item.");
					                                								player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
					                                								notEnoughXPMessage = true;
					                                							}
					                                						}
					                                					}
					                                				}
				                            					}                            					
				                            				}
				                            				else {
				                            					player.sendMessage(ChatColor.RED + "You must be Netherite rank to repair God items more than 2 times.");
				                                				player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
				                            				}
				                            			}
				                            			
				                            			//If has been repaired 4 or more times
				                            			else {
				                            				
				                            				if (player.hasPermission("customgod.forge.repair.unlimited")) {
				                            					
				                            					//Adding 1 to Times repaired
				                            					String newRepairLine = ChatColor.GRAY + repairLine[0] + " " + repairLine[1] + " " + (timesRepaired + 1);
				                            					if (result != null) {
				                            						if (result.getItemMeta() != null) {
					                                					ItemMeta resultMeta = result.getItemMeta();
					                                					if (resultMeta.hasLore()) {
					                                						List<String> resultLore = resultMeta.getLore();
					                                						resultLore.set(resultLore.size() - 1, newRepairLine);
					                                						resultMeta.setLore(resultLore);
					                                						result.setItemMeta(resultMeta);
					                                						
					                                						if (event.getCursor() == null || event.getCursor().getType() == Material.AIR) {
					                                							
					                                							if (player.getLevel() >= 50) {
						                                                            xpTake50 = true; //Tells later code to execute repair and take 50 xp
					                                							}
					                                							else {
					                                								player.sendMessage(ChatColor.RED + "You must be at least level 50 to repair this item.");
					                                								player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
					                                								notEnoughXPMessage = true;
					                                							}
					                                						}
					                                					}
					                                				}
				                            					}
				                            				}
				                            				else {
				                            					player.sendMessage(ChatColor.RED + "You cannot repair God items more than 4 times.");
				                                				player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
				                            				}
				                            			}
				                            		}
				                    			}
				                    		}
				                    	}
				                    }
									
									//Rename
									if (!applyingEnchants) {
										
										if (player.hasPermission("customgod.forge.rename")) {
											if (input != null && input.getType() != Material.AIR) {
												if (input.getItemMeta() != null) {
													if (result != null) {
														if (result.getItemMeta() != null) {
															ItemMeta resultMeta = result.getItemMeta();
															if (GodItems.isLegendary(input)) {
																resultMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + newName);
															}
															else {
																resultMeta.setDisplayName(ChatColor.RED + newName);
															}
															result.setItemMeta(resultMeta);
															
															//If repairing
															if (addition != null) {
																if (hasRepair(Bukkit.getOfflinePlayer(player.getName()))) {
																	if (xpTake30) {
																		if (player.getLevel() >= 30) {
																			event.setCursor(result);
								                							anvil.setItem(0, null);
								                							anvil.setItem(1, null);
								                                            anvil.setItem(2, null);
								                                            player.sendMessage(ChatColor.GREEN + "Successfully repaired " + newName + ".");
								                                            player.playSound(location, Sound.ENTITY_ENDER_EYE_DEATH, 1f, 1f);
								                                            player.setLevel(originalXP - 30);
																		}
																	}
																	else if (xpTake50) {
																		if (player.getLevel() >= 50) {
																			event.setCursor(result);
								                							anvil.setItem(0, null);
								                							anvil.setItem(1, null);
								                                            anvil.setItem(2, null);
								                                            player.sendMessage(ChatColor.GREEN + "Successfully repaired " + newName + ".");
								                                            player.playSound(location, Sound.ENTITY_ENDER_EYE_DEATH, 1f, 1f);
								                                            player.setLevel(originalXP - 50);
																		}
																	}
																	FileConfiguration config = CustomGod.getInstance().getConfig();
																	config.set("repairs." + Bukkit.getOfflinePlayer(player.getName()).getUniqueId(), config.getInt("repairs." + player.getUniqueId()) - 1);
																	plugin.saveConfig();
																}
																else {
																	player.sendMessage(ChatColor.RED + "Your God Repair is not ready to use yet.");
																	player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
																	return;
																}
															}
															
															//If just renaming
															else {
																if (player.getLevel() >= 30) {
																	event.setCursor(result);
						                							anvil.setItem(0, null);
						                							anvil.setItem(1, null);
						                                            anvil.setItem(2, null);
						                                            player.sendMessage(ChatColor.GREEN + "Successfully renamed " + newName + ".");
						                                            player.playSound(location, Sound.ENTITY_ENDER_EYE_DEATH, 1f, 1f);
						                                            player.setLevel(originalXP - 30);
																}
																else {
																	if (!notEnoughXPMessage) {
																		player.sendMessage(ChatColor.RED + "You must be at least level 30 to rename this item.");
																		player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
																	}
																}
															}
														}
													}
												}
											}
										}
										else {
											player.sendMessage(ChatColor.RED + "You must be at least diamond rank to rename God items.");
											player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
										}
									}
								}
								else {
									player.sendMessage(ChatColor.RED + "You can only rename/repair God items.");
									player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
								}
							}
						}
					}, 1);
                }
			}
			
			//Regular anvils
			else {
				if (event.getInventory() instanceof AnvilInventory) {
					ItemStack cursor = event.getCursor();
					ItemStack currentItem = event.getCurrentItem();
					
					if (currentItem != null) {
						if (GodItems.isGod(currentItem)) {
							event.getWhoClicked().sendMessage(ChatColor.RED + "You can only use God items in the God Forge at /warp GodForge.");
							event.setCancelled(true);
						}
					}
					
					if (cursor != null) {
						if (GodItems.isGod(currentItem)) {
							event.getWhoClicked().sendMessage(ChatColor.RED + "You can only use God items in the God Forge at /warp GodForge.");
							event.setCancelled(true);
						}
					}
					
					if (event.getClick().equals(ClickType.NUMBER_KEY)) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	private static boolean hasRepair(OfflinePlayer offlinePlayer) {
		FileConfiguration config = CustomGod.getInstance().getConfig();
		if (config.get("repairs." + offlinePlayer.getUniqueId()) == null) {
			return false;
		}
		else {
			if (config.getInt("repairs." + offlinePlayer.getUniqueId()) > 0) {
				return true;
			}
			else return false;
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickEvent (PlayerInteractEvent event) {
		if (event.getClickedBlock() != null) {
			if (event.getClickedBlock().getType().equals(Material.ANVIL)) {
				Player player = event.getPlayer();
				Location location = player.getLocation();
				double x = location.getX();
				double y = location.getY();
				double z = location.getZ();
				if ((x > 43 && x < 63) && (y > 208 && y < 228) && (z > 205 && z < 225)) {
					Block b = event.getClickedBlock();
					b.setType(Material.ANVIL); //Repairs anvil
					if (!player.hasPermission("customgod.forge.use")) {
						event.setCancelled(true);
						player.sendMessage(ChatColor.RED + "You cannot use the God Forge.");
						player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
					}
				}				
			}
		}
	}

}
