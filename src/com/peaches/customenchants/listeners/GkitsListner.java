package com.peaches.customenchants.listeners;

import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("unchecked")
public class GkitsListner implements Listener {
    private static Main plugin;
    private static Utils utils;

    private final FileConfiguration gkit = ConfigManager.getInstance().getGKits();

    public GkitsListner(Main pl, Utils u) {
        plugin = pl;
        utils = u;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getInventory().getTitle() != null) {
            if (e.getInventory().getTitle().equals(utils.getTitle())) {
                if (e.getCurrentItem() == null) {
                    return;
                }
                Player p = (Player) e.getWhoClicked();
                if ((e.getInventory().getItem(0).equals(utils.Gkits(p).getItem(0)))) {
                    for (String Kit : gkit.getKeys(false)) {
                        if (gkit.getInt(Kit + ".Slot") == e.getSlot()) {
                            if ((plugin.Gkits.containsKey(p.getName() + ":" + Kit))
                                    || (!p.hasPermission("CustomEnchants.Gkits." + Kit))) {
                                e.setCancelled(true);
                                return;
                            }
                            for (String item : gkit.getConfigurationSection(Kit + ".Kit").getKeys(false)) {
                                e.setCancelled(true);
                                ItemStack items = utils.makeItem(
                                        Material.getMaterial(gkit.getString(Kit + ".Kit." + item + ".Item").toUpperCase()),
                                        gkit.getInt(Kit + ".Kit." + item + ".Amount"), 0,
                                        gkit.getString(Kit + ".Kit." + item + ".Name"));
                                ArrayList<String> Enchants = (ArrayList) gkit.getStringList(Kit + ".Kit." + item + ".Enchants");
                                ArrayList<String> lore = new ArrayList();
                                if (Enchants != null) {
                                    for (String Enchant : Enchants) {
                                        if (Enchant.contains(":")) {
                                            String[] Enchants2 = Enchant.split(":");
                                            int chance = Integer.parseInt(Enchants2[1]);
                                            String[] Enchants1 = Enchants2[0].split(" ");
                                            Random r = new Random();
                                            int i = 1 + r.nextInt(100);
                                            if (i <= chance) {
                                                //LoreDupe Checker
                                                ArrayList<String> removelore = new ArrayList();
                                                if (!lore.isEmpty()) {
                                                    for (String lore1 : lore) {
                                                        if (ChatColor.stripColor(lore1).contains(ChatColor.stripColor(Enchants1[0]))) {
                                                            removelore.add(lore1);
                                                        }
                                                    }
                                                }
                                                for (String lore1 : removelore) {
                                                    lore.remove(lore1);
                                                }

                                                if (org.bukkit.enchantments.Enchantment.getByName(Enchants1[0]) == null) {
                                                    if (plugin.getConfig().getList("Tiers.Tier3")
                                                            .contains(ChatColor.stripColor(Enchants2[0].replace(" ", "")))) {
                                                        if (!lore.contains(plugin.getConfig().getString("Options.Tier3LoreFormat")
                                                                + Enchant)) {
                                                            lore.add(utils
                                                                    .color(plugin.getConfig().getString("Options.Tier3LoreFormat")
                                                                            + Enchants2[0]));
                                                        }
                                                    } else if (plugin.getConfig().getList("Tiers.Tier2")
                                                            .contains(ChatColor.stripColor(Enchants2[0].replace(" ", "")))) {
                                                        if (!lore.contains(plugin.getConfig().getString("Options.Tier2LoreFormat")
                                                                + Enchant)) {
                                                            lore.add(utils
                                                                    .color(plugin.getConfig().getString("Options.Tier2LoreFormat")
                                                                            + Enchants2[0]));
                                                        }
                                                    } else if (plugin.getConfig().getList("Tiers.Tier1")
                                                            .contains(ChatColor.stripColor(Enchants2[0].replace(" ", "")))) {
                                                        if (!lore.contains(plugin.getConfig().getString("Options.Tier1LoreFormat")
                                                                + Enchant)) {
                                                            lore.add(utils
                                                                    .color(plugin.getConfig().getString("Options.Tier1LoreFormat")
                                                                            + Enchants2[0]));
                                                        }
                                                    }
                                                } else {
                                                    try {
                                                        int level = Integer.parseInt(Enchants1[1]);
                                                        items.addUnsafeEnchantment(
                                                                org.bukkit.enchantments.Enchantment.getByName(Enchants1[0]),
                                                                Integer.parseInt(Enchants1[1]));
                                                    } catch (Exception exeption) {
                                                        try {
                                                            int level = utils.convertPowertoInt(Enchants1[1]);
                                                            items.addUnsafeEnchantment(
                                                                    org.bukkit.enchantments.Enchantment.getByName(Enchants1[0]),
                                                                    Integer.parseInt(Enchants1[1]));
                                                        } catch (Exception exeption2) {

                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            String[] Enchants1 = Enchant.split(" ");
                                            if (org.bukkit.enchantments.Enchantment.getByName(Enchants1[0]) == null) {
                                                if (plugin.getConfig().getList("Tiers.Tier3")
                                                        .contains(ChatColor.stripColor(Enchant.replace(" ", "")))) {
                                                    if (!lore.contains(plugin.getConfig().getString("Options.Tier3LoreFormat")
                                                            + Enchant)) {
                                                        lore.add(utils
                                                                .color(plugin.getConfig().getString("Options.Tier3LoreFormat")
                                                                        + Enchant));
                                                    }
                                                } else if (plugin.getConfig().getList("Tiers.Tier2")
                                                        .contains(ChatColor.stripColor(Enchant.replace(" ", "")))) {
                                                    if (!lore.contains(plugin.getConfig().getString("Options.Tier2LoreFormat")
                                                            + Enchant)) {
                                                        lore.add(utils
                                                                .color(plugin.getConfig().getString("Options.Tier2LoreFormat")
                                                                        + Enchant));
                                                    }
                                                } else if (plugin.getConfig().getList("Tiers.Tier1")
                                                        .contains(ChatColor.stripColor(Enchant.replace(" ", "")))) {
                                                    if (!lore.contains(plugin.getConfig().getString("Options.Tier1LoreFormat")
                                                            + Enchant)) {
                                                        lore.add(utils
                                                                .color(plugin.getConfig().getString("Options.Tier1LoreFormat")
                                                                        + Enchant));
                                                    }
                                                } else {
                                                    lore.add(utils
                                                            .color(plugin.getConfig().getString("Options.Tier3LoreFormat")
                                                                    + Enchant));
                                                }
                                            } else {
                                                try {
                                                    int level = Integer.parseInt(Enchants1[1]);
                                                    items.addUnsafeEnchantment(
                                                            org.bukkit.enchantments.Enchantment.getByName(Enchants1[0]),
                                                            Integer.parseInt(Enchants1[1]));
                                                } catch (Exception exeption) {
                                                    try {
                                                        int level = utils.convertPowertoInt(Enchants1[1]);
                                                        items.addUnsafeEnchantment(
                                                                org.bukkit.enchantments.Enchantment.getByName(Enchants1[0]),
                                                                Integer.parseInt(Enchants1[1]));
                                                    } catch (Exception exeption2) {

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                ItemMeta meta = items.getItemMeta();
                                meta.setLore(lore);
                                items.setItemMeta(meta);

                                if (Enchants != null) {
                                    p.getInventory().addItem(utils.addGlow(items));
                                } else {
                                    p.getInventory().addItem(items);
                                }
                            }
                            p.closeInventory();
                            plugin.Gkits.put(p.getName() + ":" + Kit, 259200);
                        }
                    }
                }
            }
        }
    }

}
