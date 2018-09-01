package com.peaches.customenchants.Commands;

import com.peaches.customenchants.Support.Support;
import com.peaches.customenchants.Support.Vault;
import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CustomEnchants implements org.bukkit.command.CommandExecutor {
    private static Main plugin;
    private static Utils utils;

    private final FileConfiguration messages = ConfigManager.getInstance().getMessages();

    public CustomEnchants(Main pl, Utils u) {
        plugin = pl;
        utils = u;
    }

    public boolean onCommand(CommandSender cs, org.bukkit.command.Command cmd, String String, String[] args) {
        if (args.length == 0) {

            if (cs.hasPermission("customenchants.command") || !plugin.getConfig().getBoolean("Options.GUIPerm")) {
                if (utils.Tier()) {
                    if (cs instanceof Player) {
                        Player p = (Player) cs;
                        p.openInventory(utils.showInventory(p));
                        return false;
                    }
                }
            }
        }


        if (cs instanceof Player && args.length >= 1) {
            Player p = (Player) cs;
            for (int i = 1; i <= plugin.getConfig().getInt("Options.TierAmount"); i++) {
                if (args[0].equalsIgnoreCase("tier" + i)) {
                    if (utils.Vault()) {
                        if (args[0].equalsIgnoreCase("tier" + i)) {
                            if ((Vault.getbal(p) < utils.getTierCost(i)) && !(p.getGameMode() == GameMode.CREATIVE)) {
                                p.sendMessage(utils.prefix() +
                                        ChatColor.translateAlternateColorCodes('&', this.messages.getString("NeedMoreXp")));
                                p.closeInventory();
                                return false;
                            }
                            if (p.getGameMode() != GameMode.CREATIVE) {
                                Vault.removebal(p, (double) utils.getTierCost(i));
                            }
                        }
                    }
                    if (utils.Xp()) {
                        if (args[0].equalsIgnoreCase("tier" + i)) {
                            if ((p.getTotalExperience() < utils.getTierCost(i)) && !(p.getGameMode() == GameMode.CREATIVE)) {
                                p.sendMessage(utils.prefix() +
                                        ChatColor.translateAlternateColorCodes('&', this.messages.getString("NeedMoreXp")));
                                p.closeInventory();
                                return false;
                            }
                            if (p.getGameMode() != GameMode.CREATIVE) {
                                utils.RemoveXP(p, utils.getTierCost(i));
                            }
                        }
                    }
                    if (utils.XpLevels()) {
                        if (args[0].equalsIgnoreCase("tier" + i)) {
                            if ((p.getLevel() < utils.getTierCost(i)) && !(p.getGameMode() == GameMode.CREATIVE)) {
                                p.sendMessage(utils.prefix() +
                                        ChatColor.translateAlternateColorCodes('&', this.messages.getString("NeedMoreXp")));
                                p.closeInventory();
                                return false;
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
                    return false;
                }
            }
        }
        if ((args.length == 1) && (args[0].equalsIgnoreCase("update"))) {
            if (cs.hasPermission("customenchants.update")) {
                Support supp = new Support();
                supp.Update();
            }
        }
        if ((args.length == 1) && (args[0].equalsIgnoreCase("about"))) {
            cs.sendMessage(ChatColor.DARK_GRAY + "Plugin Name: " + ChatColor.GRAY + plugin.getName());
            cs.sendMessage(ChatColor.DARK_GRAY + "Plugin Version: " + ChatColor.GRAY
                    + plugin.getDescription().getVersion());
            cs.sendMessage(ChatColor.DARK_GRAY + "Plugin Author: " + ChatColor.GRAY + "Peaches_MLG");
            cs.sendMessage(ChatColor.DARK_GRAY + "UserID: " + ChatColor.GRAY + plugin.UserID);
            return false;
        }

        if ((args.length == 1) && (args[0].equalsIgnoreCase("list"))) {
            if ((cs instanceof Player)) {
                Player p = (Player) cs;
                p.openInventory(utils.showList(1));
                return false;
            }

            return false;
        }
        java.text.DecimalFormat localDecimalFormat;
        if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {
            if (!cs.hasPermission("customenchants.reload")) {
                cs.sendMessage(utils.prefix()
                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("NoPermission")));
                return false;
            }
            long l1 = System.currentTimeMillis();
            cs.sendMessage(utils.prefix()
                    + ChatColor.translateAlternateColorCodes('&', this.messages.getString("StartReload")));
            ConfigManager.getInstance().setup(plugin);
            plugin.reloadConfig();
            plugin.getConfig().options().copyDefaults(true);
            plugin.saveDefaultConfig();
            plugin.settype();
            plugin.saveConfig();
            long l2 = System.currentTimeMillis() - l1;
            localDecimalFormat = new java.text.DecimalFormat("###.##");
            cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                    this.messages.getString("EndReload").replace("%ms%", localDecimalFormat.format(l2))));
            return false;
        }
        if (args.length == 2) {
            String arg1 = args[1];
            if (args[0].equalsIgnoreCase("enable")) {
                if (!cs.hasPermission("customenchants.enable")) {
                    cs.sendMessage(utils.prefix()
                            + ChatColor.translateAlternateColorCodes('&', this.messages.getString("NoPermission")));
                    return false;
                }
                if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + args[1])) {
                    ConfigManager.getInstance().getCustomEncants().set("Enchantments." + args[1] + ".Enabled", true);
                    cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                            this.messages.getString("DisableEnchant").replace("%enchant%", arg1)));
                }
                cs.sendMessage(utils.prefix()
                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("UnknownEnchant")));
                return false;
            }
            if (args[0].equalsIgnoreCase("disable")) {
                if (!cs.hasPermission("customenchants.disable")) {
                    cs.sendMessage(utils.prefix()
                            + ChatColor.translateAlternateColorCodes('&', this.messages.getString("NoPermission")));
                    return false;
                }
                if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + args[1])) {
                    ConfigManager.getInstance().getCustomEncants().set("Enchantments." + args[1] + ".Enabled", false);
                    cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                            this.messages.getString("DisableEnchant").replace("%enchant%", arg1)));
                }
                cs.sendMessage(utils.prefix()
                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("UnknownEnchant")));
                return false;
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("give")) {
                if (!cs.hasPermission("customenchants.give")) {
                    cs.sendMessage(utils.prefix()
                            + ChatColor.translateAlternateColorCodes('&', this.messages.getString("NoPermission")));
                    return false;
                }

                FileConfiguration enchants = ConfigManager.getInstance().getCustomEncants();
                for (String Enchant : utils.type.keySet()) {
                    for (String i : enchants.getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                        if (args[2].equalsIgnoreCase(Enchant + utils.convertPower(Integer.parseInt(i))) || args[2].equalsIgnoreCase(Enchant + i)) {
                            if (enchants.getBoolean("Enchantments." + Enchant + ".Enabled")) {

                                Player p = Bukkit.getServer().getPlayer(args[1]);
                                if (p == null) {
                                    cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                            this.messages.getString("UnknownPlayer")));
                                    return false;
                                }
                                cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                        this.messages.getString("GivePlayerEnchant")
                                                .replace("%player%", args[1]).replace("%enchant%",
                                                java.lang.String.valueOf(Enchant) +
                                                        utils.convertPower(
                                                                Integer.parseInt(i)))));
                                if (utils.hasOpenSlot(p.getInventory())) {
                                    p.getInventory()
                                            .addItem(utils.createCrystalItem(Enchant,
                                                    utils.convertPower(Integer.parseInt(i)),
                                                    utils.type.get(Enchant)));
                                    return false;
                                }
                                p.getWorld().dropItem(p.getLocation(),
                                        utils.createCrystalItem(Enchant,
                                                utils.convertPower(Integer.parseInt(i)), utils.type.get(Enchant)));
                                return false;

                            }
                        }
                    }
                }
//                for (String Enchant : utils.type.keySet()) {
//                    if (plugin.getConfig().contains("Translate." + Enchant)) {
//                        if (args[2].toLowerCase()
//                                .contains(plugin.getConfig().getString("Translate." + Enchant).toLowerCase())) {
//                            if (plugin.getConfig().contains("MaxPower." + Enchant)) {
//                                for (int counter = 1; counter <= plugin.getConfig()
//                                        .getInt("MaxPower." + Enchant) + 1; counter++) {
//                                    if ((args[2].equalsIgnoreCase(plugin.getConfig().getString("Translate." + Enchant)
//                                            + utils.convertPower(counter)))
//                                            || (args[2].equalsIgnoreCase(
//                                            plugin.getConfig().getString("Translate." + Enchant)
//                                                    + Integer.toString(counter)))) {
//                                        if (!plugin.getConfig()
//                                                .getBoolean("Enabled." + Enchant + utils.convertPower(counter))) {
//                                            if (plugin.getConfig()
//                                                    .contains("Enabled." + Enchant + utils.convertPower(counter))) {
//                                                cs.sendMessage(
//                                                        utils.prefix() + ChatColor.translateAlternateColorCodes('&',
//                                                                this.messages.getString("EnchantDisabled").toLowerCase().replace(
//                                                                        "%enchant%",
//                                                                        Enchant + utils.convertPower(counter))));
//                                                return false;
//                                            }
//                                            plugin.getConfig().set("Enabled." + Enchant + utils.convertPower(counter),
//                                                    Boolean.TRUE);
//                                            plugin.saveConfig();
//                                            plugin.reloadConfig();
//                                        }
//                                        Player p = Bukkit.getServer().getPlayer(args[1]);
//                                        if (p == null) {
//                                            cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&',
//                                                    this.messages.getString("UnknownPlayer")));
//                                            return false;
//                                        }
//                                        if ((cs instanceof Player)) {
//                                            cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&',
//                                                    this.messages.getString("GivePlayerEnchant").toLowerCase()
//                                                            .replace("%player%", args[1]).replace("%enchant%",
//                                                            java.lang.String.valueOf(plugin
//                                                                    .getConfig()
//                                                                    .getString("Translate." +
//                                                                            Enchant)) +
//                                                                    utils.convertPower(
//                                                                            counter))));
//                                        }
//                                        if (utils.hasOpenSlot(p.getInventory())) {
//                                            p.getInventory()
//                                                    .addItem(utils.createCrystalItem(
//                                                            plugin.getConfig().getString("Translate." + Enchant),
//                                                            utils.convertPower(counter),
//                                                            utils.type.get(Enchant)));
//                                            return false;
//                                        }
//                                        p.getWorld().dropItem(p.getLocation(),
//                                                utils.createCrystalItem(
//                                                        plugin.getConfig().getString("Translate." + Enchant),
//                                                        utils.convertPower(counter), utils.type.get(Enchant)));
//                                        return false;
//                                    }
//                                }
//                            } else {
//                                Player p = Bukkit.getServer().getPlayer(args[1]);
//                                if (p == null) {
//                                    cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&',
//                                            this.messages.getString("UnknownPlayer")));
//                                    return false;
//                                }
//                                if ((cs instanceof Player)) {
//                                    cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&',
//                                            this.messages.getString("GivePlayerEnchant").toLowerCase().replace("%player%", args[1])
//                                                    .replace("%enchant%", java.lang.String.valueOf(plugin.getConfig()
//                                                            .getString("Translate." +
//                                                                    Enchant)) +
//                                                            utils.convertPower(1))));
//                                }
//                                if (utils.hasOpenSlot(p.getInventory())) {
//                                    p.getInventory()
//                                            .addItem(utils.createCrystalItem(
//                                                    plugin.getConfig().getString("Translate." + Enchant),
//                                                    utils.convertPower(1), utils.type.get(Enchant)));
//                                    return false;
//                                }
//                                p.getWorld().dropItem(p.getLocation(),
//                                        utils.createCrystalItem(plugin.getConfig().getString("Translate." + Enchant),
//                                                utils.convertPower(1), utils.type.get(Enchant)));
//                                return false;
//                            }
//                        }
//                    }
//                    if (args[2].toLowerCase().contains(Enchant.toLowerCase())) {
//                        if (plugin.getConfig().contains("MaxPower." + Enchant)) {
//                            for (int counter = 1; counter <= plugin.getConfig()
//                                    .getInt("MaxPower." + Enchant); counter++) {
//                                if ((args[2].equalsIgnoreCase(Enchant + utils.convertPower(counter)))
//                                        || (args[2].equalsIgnoreCase(Enchant + Integer.toString(counter)))) {
//                                    if (!plugin.getConfig()
//                                            .getBoolean("Enabled." + Enchant + utils.convertPower(counter))) {
//                                        if (plugin.getConfig()
//                                                .contains("Enabled." + Enchant + utils.convertPower(counter))) {
//                                            cs.sendMessage(
//                                                    utils.prefix() + ChatColor.translateAlternateColorCodes('&',
//                                                            this.messages.getString("EnchantDisabled").toLowerCase().replace(
//                                                                    "%enchant%",
//                                                                    Enchant + utils.convertPower(counter))));
//                                            return false;
//                                        }
//                                        plugin.getConfig().set("Enabled." + Enchant + utils.convertPower(counter),
//                                                Boolean.TRUE);
//                                        plugin.saveConfig();
//                                        plugin.reloadConfig();
//                                    }
//                                    Player p = Bukkit.getServer().getPlayer(args[1]);
//                                    if (p == null) {
//                                        cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&',
//                                                this.messages.getString("UnknownPlayer")));
//                                        return false;
//                                    }
//                                    if ((cs instanceof Player)) {
//                                        cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&',
//                                                this.messages.getString("GivePlayerEnchant").toLowerCase()
//                                                        .replace("%player%", args[1]).replace("%enchant%",
//                                                        java.lang.String.valueOf(plugin
//                                                                .getConfig()
//                                                                .getString("Translate." +
//                                                                        Enchant)) +
//                                                                utils.convertPower(
//                                                                        counter))));
//                                    }
//                                    if (utils.hasOpenSlot(p.getInventory())) {
//                                        p.getInventory()
//                                                .addItem(utils.createCrystalItem(
//                                                        plugin.getConfig().getString("Translate." + Enchant),
//                                                        utils.convertPower(counter),
//                                                        utils.type.get(Enchant)));
//                                        return false;
//                                    }
//                                    p.getWorld().dropItem(p.getLocation(),
//                                            utils.createCrystalItem(
//                                                    plugin.getConfig().getString("Translate." + Enchant),
//                                                    utils.convertPower(counter), utils.type.get(Enchant)));
//                                    return false;
//                                }
//                            }
//                        } else {
//                            Player p = Bukkit.getServer().getPlayer(args[1]);
//                            if (p == null) {
//                                cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&',
//                                        this.messages.getString("UnknownPlayer")));
//                                return false;
//                            }
//                            if ((cs instanceof Player)) {
//                                cs.sendMessage(utils.prefix() + ChatColor.translateAlternateColorCodes('&',
//                                        this.messages.getString("GivePlayerEnchant").toLowerCase().replace("%player%", args[1])
//                                                .replace("%enchant%", java.lang.String.valueOf(plugin.getConfig()
//                                                        .getString("Translate." +
//                                                                Enchant)) +
//                                                        utils.convertPower(1))));
//                            }
//                            if (utils.hasOpenSlot(p.getInventory())) {
//                                p.getInventory()
//                                        .addItem(utils.createCrystalItem(
//                                                plugin.getConfig().getString("Translate." + Enchant),
//                                                utils.convertPower(1), utils.type.get(Enchant)));
//                                return false;
//                            }
//                            p.getWorld().dropItem(p.getLocation(),
//                                    utils.createCrystalItem(plugin.getConfig().getString("Translate." + Enchant),
//                                            utils.convertPower(1), utils.type.get(Enchant)));
//                            return false;
//                        }
//                    }
//                }

                cs.sendMessage(utils.prefix()
                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("UnknownEnchant").replace("%enchant%", args[2])));
            }
        } else {
            if (utils.Tier()) {
                cs.sendMessage(ChatColor.RED + "/ce" + ChatColor.RED + ": " + ChatColor.GRAY
                        + "Opens The Enchantment GUI");
            }
            cs.sendMessage(
                    ChatColor.RED + "/ce list" + ChatColor.RED + ": " + ChatColor.GRAY + "Lists The Enchants");
            if (cs.hasPermission("customenchants.reload")) {
                cs.sendMessage(ChatColor.RED + "/ce reload " + ChatColor.RED + ": " + ChatColor.GRAY
                        + "Reloads The Plugin");
            }
            cs.sendMessage(ChatColor.RED + "/ce update " + ChatColor.RED + ": " + ChatColor.GRAY
                    + "Updates The Plugin (Requires MvDW Updater)");
            cs.sendMessage(ChatColor.RED + "/ce about  " + ChatColor.RED + ": " + ChatColor.GRAY
                    + "tells you information about the plugin");

            if (cs.hasPermission("customenchants.give")) {
                cs.sendMessage(ChatColor.RED + "/ce give <name> <ce name>" + ChatColor.RED + ": " + ChatColor.GRAY
                        + "Give A Player An Enchant Crystal");
            }
            cs.sendMessage(ChatColor.RED + "/ce help " + ChatColor.RED + ": " + ChatColor.GRAY
                    + "View a helpful list of commands!");

            if (cs.hasPermission("customenchants.enable")) {
                cs.sendMessage(ChatColor.RED + "/ce enable <Enchant> " + ChatColor.RED + ": " + ChatColor.GRAY
                        + "Enable an enchant!");
            }
            if (cs.hasPermission("customenchants.disable")) {
                cs.sendMessage(ChatColor.RED + "/ce disable <Enchant> " + ChatColor.RED + ": " + ChatColor.GRAY
                        + "Disable an enchant!");
            }
            cs.sendMessage(
                    ChatColor.RED + "/Gkits " + ChatColor.RED + ": " + ChatColor.GRAY + "Opens The Gkits GUI!");
            if (cs.hasPermission("customenchants.gkits.reset")) {
                cs.sendMessage(ChatColor.RED + "/Gkits Reset <Name> <Kit> " + ChatColor.RED + ": " + ChatColor.GRAY
                        + "Reset a kit cooldown for a player");
            }
            cs.sendMessage(
                    ChatColor.RED + "/Tinkerer " + ChatColor.RED + ": " + ChatColor.GRAY + "Opens The Tinkerer GUI!");

        }
        return false;
    }
}
