package com.peaches.customenchants.events;

import com.peaches.customenchants.Effects.EffectManager;
import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class PlayerDeathEvent implements org.bukkit.event.Listener {
    private static Utils utils;

    public PlayerDeathEvent(Utils u) {
        utils = u;
    }

    @EventHandler
    public void PlayerDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            if ((e.getEntity().getKiller() != null)) {
                Player damager = e.getEntity().getKiller();
                for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
                    if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("MOB_KILL")) {
                        for (String i : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                            if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), damager.getItemInHand()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), damager.getInventory().getHelmet()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), damager.getInventory().getChestplate()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), damager.getInventory().getLeggings()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), damager.getInventory().getBoots())) {
                                if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                    List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                    for (String effect : effects) {
                                        String[] effect1 = effect.split(":");
                                        if (effect.contains("XP:")) {
                                            e.setDroppedExp(e.getDroppedExp() * Integer.parseInt(effect1[1]));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if ((e.getEntity().getKiller() != null)) {
            Player damager = e.getEntity().getKiller();
            Player player = (Player) e.getEntity();
            for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
                if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("PLAYER_KILL")) {
                    for (String i : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                        if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".Player") && ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Player").equalsIgnoreCase("%victim%")) {
                            if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getItemInHand()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getHelmet()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getChestplate()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getLeggings()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getBoots())) {
                                if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                    List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                    for (String effect : effects) {
                                        String[] effect1 = effect.split(":");
                                        EffectManager.getInstance.add(damager, player, null, null, effect1, player.getItemInHand(), Enchant);
                                        if (effect.contains("DROPHEAD:")) {
                                            Bukkit.broadcastMessage(effect1[1].toUpperCase());
                                            if (effect1[1].toUpperCase().contains("%VICTIM%")) {
                                                ItemStack head = utils.makeItem("397:3", 1);
                                                SkullMeta m = (SkullMeta) head.getItemMeta();
                                                m.setOwner(player.getName());
                                                head.setItemMeta(m);
                                                e.getDrops().add(head);
                                            } else if (effect1[1].toUpperCase().contains("%ATTACKER%")) {
                                                ItemStack head = utils.makeItem("397:3", 1);
                                                SkullMeta m = (SkullMeta) head.getItemMeta();
                                                m.setOwner(damager.getName());
                                                head.setItemMeta(m);
                                                e.getDrops().add(head);
                                            } else {
                                                ItemStack head = utils.makeItem("397:3", 1);
                                                SkullMeta m = (SkullMeta) head.getItemMeta();
                                                m.setOwner(effect1[1]);
                                                head.setItemMeta(m);
                                                e.getDrops().add(head);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), damager.getItemInHand()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), damager.getInventory().getHelmet()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), damager.getInventory().getChestplate()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), damager.getInventory().getLeggings()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), damager.getInventory().getBoots())) {
                                if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                    List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                    for (String effect : effects) {
                                        String[] effect1 = effect.split(":");
                                        EffectManager.getInstance.add(damager, player, null, null, effect1, damager.getItemInHand(), Enchant);
//                                        if (effect.contains("POTION:")) {
//                                            damager.addPotionEffect(
//                                                    new PotionEffect(PotionEffectType.getByName(effect1[1].toUpperCase()), Integer.parseInt(effect1[3]) * 20, -1 + Integer.parseInt(effect1[2]), false));
//                                        }
                                        if (effect.contains("DROPHEAD:")) {
                                            if (effect1[1].toUpperCase().contains("%VICTIM%")) {
                                                ItemStack head = utils.makeItem("397:3", 1);
                                                SkullMeta m = (SkullMeta) head.getItemMeta();
                                                m.setOwner(player.getName());
                                                head.setItemMeta(m);
                                                e.getDrops().add(head);
                                            } else if (effect1[1].toUpperCase().contains("%ATTACKER%")) {
                                                ItemStack head = utils.makeItem("397:3", 1);
                                                SkullMeta m = (SkullMeta) head.getItemMeta();
                                                m.setOwner(damager.getName());
                                                head.setItemMeta(m);
                                                e.getDrops().add(head);
                                            } else {
                                                ItemStack head = utils.makeItem("397:3", 1);
                                                SkullMeta m = (SkullMeta) head.getItemMeta();
                                                m.setOwner(effect1[1]);
                                                head.setItemMeta(m);
                                                e.getDrops().add(head);
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
