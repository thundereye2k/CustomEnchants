package com.peaches.customenchants.events;

import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

import java.util.List;

public class PlayerItemDamagetake implements Listener {
    private static Utils utils;

    public PlayerItemDamagetake(Utils u) {
        utils = u;
    }

    public void onDmg( PlayerItemDamageEvent e) {
        Player p = e.getPlayer();
        for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
            if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("ITEM_DAMAGE")) {
                for (String i : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getItemInHand()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getHelmet()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getChestplate()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getLeggings()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getBoots())) {
                        if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                            List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                            for (String effect : effects) {
                                String[] effect1 = effect.split(":");
                                if (effect.toUpperCase().contains("ARMOR_DAMAGE_DECREASE:")) {
                                    e.setDamage(e.getDamage() / Integer.parseInt(effect1[1]));
                                }
                                if (effect.toUpperCase().contains("ARMOR_DAMAGE_INCREASE:")) {
                                    e.setDamage(e.getDamage() * Integer.parseInt(effect1[1]));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
