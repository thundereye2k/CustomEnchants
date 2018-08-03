package com.peaches.customenchants.events;

import ca.thederpygolems.armorequip.ArmorEquipEvent;
import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class ArmorEquipt implements Listener {
    private static Main plugin;
    private static Utils utils;

    public ArmorEquipt(Main pl, Utils u) {
        plugin = pl;
        utils = u;
    }

    @SuppressWarnings("unchecked")
    @EventHandler
    public void onEquip( ArmorEquipEvent e) {
        Player p = e.getPlayer();
        ItemStack NewItem = e.getNewArmorPiece();
        ItemStack OldItem = e.getOldArmorPiece();
        for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
            if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("EQUIPT")) {
                for (String i : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), OldItem)) {
                        if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                            List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                            for (String effect : effects) {
                                String[] effect1 = effect.split(":");
                                if (effect.startsWith("POTION:")) {
                                    if (PotionEffectType.getByName(effect1[1].toUpperCase()) != null) {
                                        p.removePotionEffect(PotionEffectType.getByName(effect1[1].toUpperCase()));
                                    } else {
                                        System.out.print("Unknown Potion effect " + effect1[1].toUpperCase());
                                    }
                                }
                                if (effect.contains("FROSTY")) {
                                    plugin.removensnow(e.getPlayer().getName());
                                }
                                if (effect.contains("MAXHP_INCREASE:")) {
                                    p.setMaxHealth(p.getMaxHealth() - Integer.parseInt(effect1[1]));
                                }
                                if (effect.contains("PARTICLE:")) {
                                    if (effect1[1] != null) {
                                        if (plugin.ParticleEffects.containsKey(p)) {
                                            plugin.ParticleEffects.remove(p);
                                        }
                                    }
                                }
                                if (effect.contains("FLY")) {
                                    if (plugin.containsfly(p.getName())) {
                                        plugin.removefly(p.getName());
                                    }
                                }
                            }
                        }
                    }
                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), NewItem)) {
                        if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                            List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                            for (String effect : effects) {
                                String[] effect1 = effect.split(":");
                                if (effect.startsWith("POTION:")) {
                                    if (PotionEffectType.getByName(effect1[1].toUpperCase()) != null) {
                                        p.addPotionEffect(
                                                new PotionEffect(PotionEffectType.getByName(effect1[1].toUpperCase()), 999999, -1 + Integer.parseInt(effect1[2]), false));
                                    } else {
                                        System.out.print("Unknown Potion effect " + effect1[1].toUpperCase());
                                    }
                                }
                                if (effect.contains("FROSTY")) {
                                    plugin.addsnow(e.getPlayer().getName());
                                }
                                if (effect.contains("MAXHP_INCREASE:")) {
                                    p.setMaxHealth(p.getMaxHealth() + Integer.parseInt(effect1[1]));
                                }
                                if (effect.contains("PARTICLE:")) {
                                    if (effect1[1] != null) {
                                        plugin.ParticleEffects.put(p, effect1[1]);
                                    }
                                }
                                if (effect.contains("FLY")) {
                                    plugin.addfly(p.getName());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
