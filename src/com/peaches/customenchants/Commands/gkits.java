package com.peaches.customenchants.Commands;

import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("unchecked")
public class gkits implements CommandExecutor {
    private static Main plugin;
    private static Utils utils;

    private final FileConfiguration messages = ConfigManager.getInstance().getMessages();
    private final FileConfiguration gkit = ConfigManager.getInstance().getGKits();

    public gkits(Main pl, Utils u) {
        plugin = pl;
        utils = u;
    }

    public boolean onCommand( CommandSender cs, Command cmd, String String,  String[] args) {

        if ((args.length == 3) && (args[0].equalsIgnoreCase("give"))) {
            Player p = Bukkit.getPlayer(args[1]);
            if (cs.hasPermission("customenchants.gkits.give")) {
                FileConfiguration gkit = ConfigManager.getInstance().getGKits();
                String Kit = args[2];
                for (String item : gkit.getConfigurationSection(Kit + ".Kit").getKeys(false)) {
                    ItemStack items = utils.makeItem(
                            Material.getMaterial(gkit.getString(Kit + ".Kit." + item + ".Item").toUpperCase()),
                            gkit.getInt(Kit + ".Kit." + item + ".Amount"), 0,
                            gkit.getString(Kit + ".Kit." + item + ".Name"));
                    ArrayList<String> Enchants = (ArrayList) gkit.getList(Kit + ".Kit." + item + ".Enchants");
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
                                    } else
                                        items.addEnchantment(
                                                org.bukkit.enchantments.Enchantment.getByName(Enchants1[0]),
                                                Integer.parseInt(Enchants1[1]));
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
                                    items.addEnchantment(
                                            org.bukkit.enchantments.Enchantment.getByName(Enchants1[0]),
                                            Integer.parseInt(Enchants1[1]));
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
                return false;
            }
        }
        if ((args.length == 3) && (args[0].equalsIgnoreCase("reset"))) {
            Player p = Bukkit.getPlayer(args[1]);
            if (cs.hasPermission("customenchants.gkits.reset")) {
                if (plugin.Gkits.containsKey(p.getName() + ":" + args[2])) {
                    plugin.Gkits.remove(p.getName() + ":" + args[2]);
                    cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&', this.messages
                            .getString("GkitReset").replace("%player%", p.getName()).replace("%name%", args[2])));
                    return false;

                } else {
                    boolean i = false;
                    for (String Key : gkit.getKeys(false)) {
                        if (Key.equals(args[2])) {
                            i = true;
                        }
                    }
                    if (i) {
                        cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                this.messages.getString("GkitNoCooldown").replace("%player%", p.getName()).replace("%name%", args[2])));
                        return false;
                    } else {
                        cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                this.messages.getString("UnknownGkit").replace("%name%", args[2])));
                        return false;
                    }
                }
            } else {
                cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                        this.messages.getString("NoPermission")));
                return false;
            }
        }

        if ((cs instanceof Player)) {
            Player p = (Player) cs;
            p.openInventory(utils.Gkits(p));
        }
        return false;
    }
}
