package com.peaches.customenchants.events;

import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerFish implements Listener {
    private static Utils utils;

    public PlayerFish(Utils u) {
        utils = u;
    }

    @EventHandler
    public void onfish( PlayerFishEvent e) {
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            if ((e.getCaught() instanceof Item)) {
                for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
                    if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("FISH_CATCH")) {
                        for (String i : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                            if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), e.getPlayer().getItemInHand()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), e.getPlayer().getInventory().getHelmet()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), e.getPlayer().getInventory().getChestplate()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), e.getPlayer().getInventory().getLeggings()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), e.getPlayer().getInventory().getBoots())) {
                                if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                    List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                    ItemStack item = ((Item) e.getCaught()).getItemStack();
                                    for (String effect : effects) {
                                        String[] effect1 = effect.split(":");
                                        if (effect.toUpperCase().contains("COOK")) {
                                            item.setType(Material.COOKED_FISH);
                                        }
                                        if (effect.toUpperCase().contains("CATCH")) {
                                            item.setAmount(Integer.parseInt(effect1[1]) + 1);
                                        }
                                    }
                                    ((Item) e.getCaught()).setItemStack(item);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
