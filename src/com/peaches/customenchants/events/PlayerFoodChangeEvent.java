package com.peaches.customenchants.events;

import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.List;

public class PlayerFoodChangeEvent implements org.bukkit.event.Listener {
    private static Main plugin;
    private static Utils utils;
    private final FileConfiguration enchants = ConfigManager.getInstance().getCustomEncants();


    public PlayerFoodChangeEvent(Main pl, Utils u) {
        plugin = pl;
        utils = u;
    }

    @EventHandler
    public void onFoodChange( FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        if ((p != null)) {
            if (p.getInventory().getHelmet() != null) {
                if (p.getInventory().getHelmet().getItemMeta() != null) {
                    if (p.getInventory().getHelmet().getItemMeta().getLore() != null) {
                        for (String Enchant : enchants.getConfigurationSection("Enchantments").getKeys(false)) {
                            if (enchants.getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("FOOD_CHANGE")) {
                                for (String i : enchants.getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getHelmet())) {
                                        if (enchants.getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                            List<String> effects = enchants.getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                            for (String effect : effects) {
                                                String[] effect1 = effect.split(":");
                                                if (effect.contains("FEED:")) {
                                                    if (p.getFoodLevel() + Integer.parseInt(effect1[1]) <= 20) {
                                                        if (e.getFoodLevel() < p.getFoodLevel()) {
                                                            e.setCancelled(true);
                                                        }
                                                        p.setFoodLevel(p.getFoodLevel() + Integer.parseInt(effect1[1]));
                                                    } else {
                                                        if (e.getFoodLevel() < p.getFoodLevel()) {
                                                            e.setCancelled(true);
                                                        }
                                                        p.setFoodLevel(20);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (p.getInventory().getChestplate() != null) {
                if (p.getInventory().getChestplate().getItemMeta() != null) {
                    if (p.getInventory().getChestplate().getItemMeta().getLore() != null) {
                        for (String Enchant : enchants.getConfigurationSection("Enchantments").getKeys(false)) {
                            if (enchants.getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("FOOD_CHANGE")) {
                                for (String i : enchants.getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getChestplate())) {
                                        if (enchants.getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                            List<String> effects = enchants.getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                            for (String effect : effects) {
                                                String[] effect1 = effect.split(":");
                                                if (effect.contains("FEED:")) {
                                                    if (p.getFoodLevel() + Integer.parseInt(effect1[1]) <= 20) {
                                                        if (e.getFoodLevel() < p.getFoodLevel()) {
                                                            e.setCancelled(true);
                                                        }
                                                        p.setFoodLevel(p.getFoodLevel() + Integer.parseInt(effect1[1]));
                                                    } else {
                                                        if (e.getFoodLevel() < p.getFoodLevel()) {
                                                            e.setCancelled(true);
                                                        }
                                                        p.setFoodLevel(20);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (p.getInventory().getLeggings() != null) {
                if (p.getInventory().getLeggings().getItemMeta() != null) {
                    if (p.getInventory().getLeggings().getItemMeta().getLore() != null) {
                        for (String Enchant : enchants.getConfigurationSection("Enchantments").getKeys(false)) {
                            if (enchants.getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("FOOD_CHANGE")) {
                                for (String i : enchants.getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getLeggings())) {
                                        if (enchants.getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                            List<String> effects = enchants.getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                            for (String effect : effects) {
                                                String[] effect1 = effect.split(":");
                                                if (effect.contains("FEED:")) {
                                                    if (p.getFoodLevel() + Integer.parseInt(effect1[1]) <= 20) {
                                                        if (e.getFoodLevel() < p.getFoodLevel()) {
                                                            e.setCancelled(true);
                                                        }
                                                        p.setFoodLevel(p.getFoodLevel() + Integer.parseInt(effect1[1]));
                                                    } else {
                                                        if (e.getFoodLevel() < p.getFoodLevel()) {
                                                            e.setCancelled(true);
                                                        }
                                                        p.setFoodLevel(20);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (p.getInventory().getBoots() != null) {
                if (p.getInventory().getBoots().getItemMeta() != null) {
                    if (p.getInventory().getBoots().getItemMeta().getLore() != null) {
                        for (String Enchant : enchants.getConfigurationSection("Enchantments").getKeys(false)) {
                            if (enchants.getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("FOOD_CHANGE")) {
                                for (String i : enchants.getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getBoots())) {
                                        if (enchants.getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                            List<String> effects = enchants.getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                            for (String effect : effects) {
                                                String[] effect1 = effect.split(":");
                                                if (effect.contains("FEED:")) {
                                                    if (p.getFoodLevel() + Integer.parseInt(effect1[1]) <= 20) {
                                                        if (e.getFoodLevel() < p.getFoodLevel()) {
                                                            e.setCancelled(true);
                                                        }
                                                        p.setFoodLevel(p.getFoodLevel() + Integer.parseInt(effect1[1]));
                                                    } else {
                                                        if (e.getFoodLevel() < p.getFoodLevel()) {
                                                            e.setCancelled(true);
                                                        }
                                                        p.setFoodLevel(20);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

