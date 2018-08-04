package com.peaches.customenchants.listeners;

import com.peaches.customenchants.Support.Vault;
import com.peaches.customenchants.Support.Version;
import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class TierChoose implements org.bukkit.event.Listener {
    private static Main plugin;
    private static Utils utils;

    private final FileConfiguration messages = ConfigManager.getInstance().getMessages();

    public TierChoose(Main pl, Utils u) {
        plugin = pl;
        utils = u;
    }

    @EventHandler
    public void CeList( InventoryClickEvent e) {
        if (e.getInventory().getTitle().equals(utils.getTitle())) {
            if (e.getCurrentItem() == null) {
                return;
            }
            Player p = (Player) e.getWhoClicked();
            if ((e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE))) {
                for (int counter = 1; counter <= 200; counter++) {
                    if (e.getClickedInventory() != null) {
                        if (e.getClickedInventory().getItem(8).equals(utils.showList(counter).getItem(8))) {
                            ItemStack item = e.getCurrentItem();
                            if (item.equals(utils.makeItem(Material.STAINED_GLASS_PANE, 1, 14,
                                    utils.PreviousPage()))) {
                                e.setCancelled(true);
                                if (counter != 1) {
                                    e.getWhoClicked().closeInventory();
                                    e.getWhoClicked().openInventory(utils.showList(counter - 1));
                                    return;
                                }
                                return;
                            }
                            if (item.equals(utils.makeItem(Material.STAINED_GLASS_PANE, 1, 5,
                                    utils.NextPage()))) {
                                e.setCancelled(true);
                                if (utils.showList(counter + 1).getItem(0) != null) {
                                    if (utils.showList(counter + 1).getItem(0).getType() != Material.AIR) {
                                        e.getWhoClicked().closeInventory();
                                        e.getWhoClicked().openInventory(utils.showList(counter + 1));
                                    }
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInvClick( InventoryClickEvent e) {
        if (e.getInventory().getTitle().equals(utils.getTitle())) {
            Player p = (Player) e.getWhoClicked();
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            ItemStack item = e.getCurrentItem();
            if ((item == null | Objects.requireNonNull(item).getType().equals(Material.AIR))) return;
            for (int i = 1; i <= plugin.getConfig().getInt("Options.TierAmount"); i++) {
                if (item.equals(utils.getTier(i))) {
                    if (e.getClick().equals(ClickType.MIDDLE)) {
                        p.openInventory(utils.showTierList(0, i));
                        return;
                    }
                    if (utils.Vault()) {
                        if (item.equals(utils.getTier(i))) {
                            if ((Vault.getbal(p) < utils.getTierCost(i)) && !(p.getGameMode() == GameMode.CREATIVE)) {
                                p.sendMessage(utils.prefix() +
                                        ChatColor.translateAlternateColorCodes('&', this.messages.getString("NeedMoreXp")));
                                p.closeInventory();
                                return;
                            }
                            if (p.getGameMode() != GameMode.CREATIVE) {
                                Vault.removebal(p, (double) utils.getTierCost(i));
                            }
                        }
                    }
                    if (utils.Xp()) {
                        if (item.equals(utils.getTier(i))) {
                            if ((p.getTotalExperience() < utils.getTierCost(i)) && !(p.getGameMode() == GameMode.CREATIVE)) {
                                p.sendMessage(utils.prefix() +
                                        ChatColor.translateAlternateColorCodes('&', this.messages.getString("NeedMoreXp")));
                                p.closeInventory();
                                return;
                            }
                            if (p.getGameMode() != GameMode.CREATIVE) {
                                utils.RemoveXP(p, utils.getTierCost(i));
                            }
                        }
                    }
                    if (utils.XpLevels()) {
                        if (item.equals(utils.getTier(i))) {
                            if ((p.getLevel() < utils.getTierCost(i)) && !(p.getGameMode() == GameMode.CREATIVE)) {
                                p.sendMessage(utils.prefix() +
                                        ChatColor.translateAlternateColorCodes('&', this.messages.getString("NeedMoreXp")));
                                p.closeInventory();
                                return;
                            }
                            if (p.getGameMode() != GameMode.CREATIVE) {
                                p.setLevel(p.getLevel() - utils.getTierCost(i));
                            }
                        }
                    }
                    Object enchants = plugin.getConfig().getStringList("Tiers.Tier" + i);
                    Random object = new Random();
                    int random = object.nextInt(enchants != null ? ((List<?>) enchants).size() : 0);
                    String Enchant = (String) ((List<?>) Objects.requireNonNull(enchants)).get(random);
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
                            "ce give " + p.getName() + " " + Enchant);
                    int length = Enchant.length();
                    String newEnchant = Enchant;
                    for (int counter1 = 1; counter1 <= length; counter1++) {
                        length = newEnchant.length();
                        newEnchant = Enchant.substring(0, length - 1);
                        if (utils.type.containsKey(newEnchant.toLowerCase())) {
                            break;
                        }
                    }
                    newEnchant = newEnchant.substring(0, 1).toUpperCase() + newEnchant.substring(1).toLowerCase();
                    if (plugin.getConfig().getString("Translate." + newEnchant) == null) {
                        p.sendMessage(utils.prefix() +

                                ChatColor.translateAlternateColorCodes('&', this.messages.getString("GiveEnchant")
                                        .replace("%tier%", plugin.getConfig().getString("Options.Tier" + i + "MessageName")).replace("%enchant%", Enchant)));
                    } else {
                        p.sendMessage(utils.prefix() +
                                ChatColor.translateAlternateColorCodes('&', this.messages.getString("GiveEnchant")
                                        .replace("%tier%", plugin.getConfig().getString("Options.Tier" + i + "MessageName")).replace("%enchant%", java.lang.String.valueOf(plugin.getConfig().getString("Translate." + newEnchant)) + " " + Enchant.substring(newEnchant.length()))));
                    }
                    if ((Version.getVersion().equals(Version.v1_7_R4)) || (Version.getVersion().equals(Version.v1_8_R1)) || (Version.getVersion().equals(Version.v1_8_R2)) || (Version.getVersion().equals(Version.v1_8_R3))) {
                        p.playSound(p.getLocation(), Sound.valueOf("LEVEL_UP"), 10.0F, 10.0F);
                    }else{
                        p.playSound(p.getLocation(), Sound.valueOf("ENTITY_LEVEL_UP"), 10.0F, 10.0F);
                    }
                }
            }
        }
    }
}


