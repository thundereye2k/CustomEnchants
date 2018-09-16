package com.peaches.customenchants.Support;

import com.peaches.customenchants.Effects.EffectManager;
import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import com.peaches.mobcoins.MobCoinsGiveEvent;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MobCoinsSupport implements Listener {

    private static Main plugin;
    private static Utils utils;

    public MobCoinsSupport(Main pl, Utils u) {
        plugin = pl;
        utils = u;
    }

    @EventHandler
    public void onMobCoins(MobCoinsGiveEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand() != null) {
            if (p.getItemInHand().hasItemMeta()) {
                if (p.getItemInHand().getItemMeta().hasLore()) {

                    for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
                        if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("FISH_CATCH")) {
                            for (String i : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                                if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), e.getPlayer().getItemInHand()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), e.getPlayer().getInventory().getHelmet()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), e.getPlayer().getInventory().getChestplate()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), e.getPlayer().getInventory().getLeggings()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), e.getPlayer().getInventory().getBoots())) {
                                    if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                        List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                        for (String effect : effects) {
                                            String[] effect1 = effect.split(":");
                                            if (effect.toUpperCase().contains("MOBCOINS:SETCOINS:")) {
                                                e.setAmount(Integer.parseInt(effect1[2]));
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
