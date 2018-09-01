package com.peaches.customenchants.events;

import com.peaches.customenchants.Effects.EffectManager;
import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;
import java.util.Random;

public class PlayerDamageEvent implements org.bukkit.event.Listener {
    private static Main plugin;
    private static Utils utils;
    FileConfiguration enchants = ConfigManager.getInstance().getCustomEncants();

    public PlayerDamageEvent(Main pl, Utils u) {
        plugin = pl;
        utils = u;
    }

    @SuppressWarnings({"unchecked"})
    @EventHandler
    public void OnPlayerDamage(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getDamage() == 0.0D) {
            return;
        }
        if ((e.getDamager() instanceof Arrow)) {
            Arrow arrow = (Arrow) e.getDamager();
            if (plugin.Explode.contains(arrow)) {
                plugin.Explode.remove(arrow);
                arrow.getWorld().createExplosion(arrow.getLocation().getX(), arrow.getLocation().getY(),
                        arrow.getLocation().getZ(), 5.0F, false, false);
            }
        }
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getDamager();
        Player player = (Player) e.getEntity();
        if (com.peaches.customenchants.Support.Support.isFriendly(p, player)) {
            return;
        }
        if (!com.peaches.customenchants.Support.Support.allowsPVP(p.getLocation())) {
            return;
        }
        Damageable pl = (Damageable) e.getDamager();
        Random r = new Random();

        for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
            if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("Damage_Player_Weapon")) {
                for (String i : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                    if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".Player") && ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Player").equalsIgnoreCase("%victim%")) {
                        if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getItemInHand()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getHelmet()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getChestplate()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getLeggings()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getBoots())) {
                            if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                    if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                        for (String effect : effects) {
                                            String[] effect1 = effect.split(":");
                                            EffectManager.getInstance.add(p, player, null, null, effect1, p.getItemInHand(), Enchant);
                                            if (effect.contains("SETDAMAGE:")) {
                                                if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + "levels." + i + ".chance")) {
                                                    if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                                        if(Integer.parseInt(effect1[1])==0){
                                                            e.setCancelled(true);
                                                        }
                                                        e.setDamage(Integer.parseInt(effect1[1]));
                                                    }
                                                } else {
                                                    if(Integer.parseInt(effect1[1])==0){
                                                        e.setCancelled(true);
                                                    }
                                                    e.setDamage(Integer.parseInt(effect1[1]));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for (String effect : effects) {
                                        String[] effect1 = effect.split(":");
                                        EffectManager.getInstance.add(p, player, null, null, effect1, p.getItemInHand(), Enchant);
                                        if (effect.contains("SETDAMAGE:")) {
                                            if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + "levels." + i + ".chance")) {
                                                if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                                    if(Integer.parseInt(effect1[1])==0){
                                                        e.setCancelled(true);
                                                    }
                                                    e.setDamage(Integer.parseInt(effect1[1]));
                                                }
                                            } else {
                                                if(Integer.parseInt(effect1[1])==0){
                                                    e.setCancelled(true);
                                                }
                                                e.setDamage(Integer.parseInt(effect1[1]));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getItemInHand()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getHelmet()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getChestplate()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getLeggings()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getBoots())) {
                            if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                    if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                        for (String effect : effects) {
                                            String[] effect1 = effect.split(":");
                                            EffectManager.getInstance.add(p, player, null, null, effect1, p.getItemInHand(), Enchant);
                                            if (effect.contains("SETDAMAGE:")) {
                                                if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                                    if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                                        if(Integer.parseInt(effect1[1])==0){
                                                            e.setCancelled(true);
                                                        }
                                                        e.setDamage(Integer.parseInt(effect1[1]));
                                                    }
                                                } else {
                                                    if(Integer.parseInt(effect1[1])==0){
                                                        e.setCancelled(true);
                                                    }
                                                    e.setDamage(Integer.parseInt(effect1[1]));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for (String effect : effects) {
                                        String[] effect1 = effect.split(":");
                                        EffectManager.getInstance.add(p, player, null, null, effect1, p.getItemInHand(), Enchant);
                                        if (effect.contains("SETDAMAGE:")) {
                                            if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                                if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                                    if(Integer.parseInt(effect1[1])==0){
                                                        e.setCancelled(true);
                                                    }
                                                    e.setDamage(Integer.parseInt(effect1[1]));
                                                }
                                            } else {
                                                if(Integer.parseInt(effect1[1])==0){
                                                    e.setCancelled(true);
                                                }
                                                e.setDamage(Integer.parseInt(effect1[1]));
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






