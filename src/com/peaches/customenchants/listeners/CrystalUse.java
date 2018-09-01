
package com.peaches.customenchants.listeners;

import com.peaches.customenchants.api.CustomEnchantEnchantEvent;
import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.CrystalEnchantMenu;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CrystalUse implements org.bukkit.event.Listener {
    private static Main plugin;
    private static Utils utils;
    private final FileConfiguration enchants = ConfigManager.getInstance().getCustomEncants();
    private final FileConfiguration messages = ConfigManager.getInstance().getMessages();

    public CrystalUse(Main pl, Utils u) {
        plugin = pl;
        utils = u;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        if (utils.isCrystal(item)) {
            org.bukkit.event.block.Action a = e.getAction();
            if ((a == Action.PHYSICAL) || (item == null) || (item.getType() == Material.AIR)) {
                return;
            }
            String itemname = ChatColor.stripColor(item.getItemMeta().getDisplayName().replace(" ", ""));
            if ((!plugin.getConfig().getBoolean("Enabled." + itemname))
                    && (plugin.getConfig().contains("Enabled." + itemname))) {
                e.getPlayer().sendMessage(utils.prefix()
                        + ChatColor.translateAlternateColorCodes('&', messages.getString("EnchantDisabled").toLowerCase().replace("%enchant%", itemname)));
                return;
            }
            String Enchantname = itemname.replace("I", "").replace("V", "").replace("X", "").replace(" ", "");
            if ((!enchants.getBoolean("Enchantments." + Enchantname + ".Enabled"))
                    && (enchants.contains("Enchantments." + Enchantname + ".Enabled"))) {
                e.getPlayer().sendMessage(utils.prefix()
                        + ChatColor.translateAlternateColorCodes('&', messages.getString("EnchantDisabled").toLowerCase().replace("%enchant%", itemname)));
                return;
            }
            int total = 0;
            Player p = e.getPlayer();
            CrystalEnchantMenu menu = new CrystalEnchantMenu(item, utils);
            for (int i = 0; i < e.getPlayer().getInventory().getContents().length; i++) {
                ItemStack itm = e.getPlayer().getInventory().getContents()[i];
                String x = ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                        .replace(" Enchant", "").toUpperCase());

                if (x.toLowerCase().contains("bow")) {
                    if (itm != null) {
                        if ((itm.getType() != null) && (!itm.getType().equals(Material.AIR))
                                && (itm.getType().name().toLowerCase().contains("bow"))
                                && (!itm.getType().name().toLowerCase()
                                .endsWith("pick" + ChatColor
                                        .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                                .toLowerCase().replace(" enchant", ""))))) {
                            if (i < 36) {
                                menu.inv.setItem(i, itm);
                                menu.addItem();
                                total++;
                            }
                        }
                    }
                }
                if (x.toLowerCase().contains("armor")) {
                    if (itm != null) {
                        if ((itm.getType() != null) && (!itm.getType().equals(Material.AIR))
                                && (itm.getType().name().toLowerCase().contains("boots"))
                                && (!itm.getType().name().toLowerCase()
                                .endsWith("pick" + ChatColor
                                        .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                                .toLowerCase().replace(" enchant", ""))))) {
                            if (i < 36) {
                                menu.inv.setItem(i, itm);
                                menu.addItem();
                                total++;
                            }
                        }

                        if ((itm.getType() != null) && (!itm.getType().equals(Material.AIR))
                                && (itm.getType().name().toLowerCase().contains("leggings"))
                                && (!itm.getType().name().toLowerCase()
                                .endsWith("pick" + ChatColor
                                        .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                                .toLowerCase().replace(" enchant", ""))))) {
                            if (i < 36) {
                                menu.inv.setItem(i, itm);
                                menu.addItem();
                                total++;
                            }
                        }
                    }

                    if (itm != null) {
                        if ((itm.getType() != null) && (!itm.getType().equals(Material.AIR))
                                && (itm.getType().name().toLowerCase().contains("chestplate"))
                                && (!itm.getType().name().toLowerCase()
                                .endsWith("pick" + ChatColor
                                        .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                                .toLowerCase().replace(" enchant", ""))))) {
                            if (i < 36) {
                                menu.inv.setItem(i, itm);
                                menu.addItem();
                                total++;
                            }
                        }
                    }

                    if (itm != null) {
                        if ((itm.getType() != null) && (!itm.getType().equals(Material.AIR))
                                && (itm.getType().name().toLowerCase().contains("helmet"))
                                && (!itm.getType().name().toLowerCase()
                                .endsWith("pick" + ChatColor
                                        .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                                .toLowerCase().replace(" enchant", ""))))) {
                            if (i < 36) {
                                menu.inv.setItem(i, itm);
                                menu.addItem();
                                total++;
                            }
                        }
                    }

                }
                if (x.toLowerCase().contains("tools")) {
                    if (itm != null) {
                        if ((itm.getType() != null) && (!itm.getType().equals(Material.AIR))
                                && (itm.getType().name().toLowerCase().contains("spade"))
                                && (!itm.getType().name().toLowerCase()
                                .endsWith("pick" + ChatColor
                                        .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                                .toLowerCase().replace(" enchant", ""))))) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }

                    }

                    if (itm != null) {
                        if ((itm.getType() != null) && (!itm.getType().equals(Material.AIR))
                                && (itm.getType().name().toLowerCase().contains("axe"))
                                && (!itm.getType().name().toLowerCase()
                                .endsWith("pick" + ChatColor
                                        .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                                .toLowerCase().replace(" enchant", ""))))) {
                            if (i < 36) {
                                menu.inv.setItem(i, itm);
                                menu.addItem();
                                total++;
                            }
                        }
                    }

                }
                if (x.toLowerCase().contains("weapons")) {
                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("sword"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }

                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("axe"))
                            && (!itm.getType().name().toLowerCase().contains("pick"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }

                }
                if ((itm != null) && (!itm.getType().equals(Material.AIR))
                        && (itm.getType().name().contains(x))) {
                    if (!itm.getType().name().toLowerCase().endsWith(
                            "pick" + ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                    .toLowerCase().replace(" enchant", "")))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }
                }
            }
            if (total == 0) {
                p.sendMessage(
                        utils.prefix() + ChatColor.translateAlternateColorCodes('&', messages.getString("NoItems")));
                return;
            }
            menu.show(p);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickItem(InventoryClickEvent e) {
        if (e.getInventory().getTitle() != null) {
            if (e.getInventory().getTitle().contains(utils.format("&8CustomEnchants"))) {
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().equals(utils.getair())) {
                    return;
                }
                if (e.getWhoClicked().getItemInHand() == null) {
                    e.getWhoClicked().closeInventory();
                    return;
                }
                if (!utils.isCrystal(e.getWhoClicked().getItemInHand())) {
                    e.getWhoClicked().closeInventory();
                    return;
                }
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();
                if (!e.getClickedInventory().getTitle().contains(utils.format("&8CustomEnchants"))) {
                    return;
                }
                String c = ChatColor.stripColor(p.getItemInHand().getItemMeta().getDisplayName());
                if (p.getInventory().getItem(e.getSlot()) != null) {
                    if ((p.getInventory().getItem(e.getSlot()).hasItemMeta())
                            && (p.getInventory().getItem(e.getSlot()).getItemMeta().hasLore())) {
                        List<String> lore = p.getInventory().getItem(e.getSlot()).getItemMeta().getLore();
                        int size = lore.size();
                        String[] name = p.getItemInHand().getItemMeta().getDisplayName().split(" ");
                        for (String Lore1 : lore) {
                            if (Lore1.contains(ChatColor.GRAY + ChatColor.stripColor(name[0]))) {
                                size--;
                            }
                        }
                        if (size >= plugin.getConfig().getInt("Options.MaxEnchants")) {
                            e.setCancelled(true);
                            p.sendMessage(utils.prefix()
                                    + ChatColor.translateAlternateColorCodes('&', messages.getString("ToMuchEnchants")));
                            return;
                        }
                    }
                    p.getInventory().setItem(e.getSlot(), utils.addGlow(utils.addLore(item, c)));
                    p.closeInventory();

                    CustomEnchantEnchantEvent event = new CustomEnchantEnchantEvent(p);
                    Bukkit.getServer().getPluginManager().callEvent(event);
                    if (p.getItemInHand().getAmount() > 0) {
                        if (p.getItemInHand().getAmount() == 1) {
                            p.setItemInHand(utils.getair());
                        } else {
                            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
                        }
                    }
                    p.updateInventory();
                }
            }

        }
    }
}