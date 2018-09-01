package com.peaches.customenchants.events;

import ca.thederpygolems.armorequip.ArmorEquipEvent;
import com.peaches.customenchants.Effects.EffectManager;
import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class ArmorEquipt implements Listener {
    private static Main plugin;
    private static Utils utils;

    public ArmorEquipt(Main pl, Utils u) {
        plugin = pl;
        utils = u;
    }

    @SuppressWarnings("unchecked")
    @EventHandler
    public void onEquip(ArmorEquipEvent e) {
        Random r = new Random();
        Player p = e.getPlayer();
        ItemStack NewItem = e.getNewArmorPiece();
        ItemStack OldItem = e.getOldArmorPiece();
        for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
            if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("EQUIPT")) {
                for (String i : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), OldItem)) {
                        if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                            if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                    List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                    for (String effect : effects) {
                                        String[] effect1 = effect.split(":");
                                        EffectManager.getInstance.remove(p, null, effect1, Enchant);
                                    }
                                }
                            } else {
                                List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                for (String effect : effects) {
                                    String[] effect1 = effect.split(":");
                                    EffectManager.getInstance.remove(p, null, effect1, Enchant);
                                }
                            }
                        }
                    }
                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), NewItem)) {
                        if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                            if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                    List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                    for (String effect : effects) {
                                        String[] effect1 = effect.split(":");
                                        EffectManager.getInstance.add(p, null, null, null, effect1, NewItem, Enchant);
                                    }
                                }
                            }else{List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                for (String effect : effects) {
                                    String[] effect1 = effect.split(":");
                                    EffectManager.getInstance.add(p, null, null, null, effect1, NewItem, Enchant);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
