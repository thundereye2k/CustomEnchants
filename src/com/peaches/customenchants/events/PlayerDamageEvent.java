package com.peaches.customenchants.events;

import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
    public void OnPlayerDamage( EntityDamageByEntityEvent e) {
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
                                for (String effect : effects) {
                                    String[] effect1 = effect.split(":");
                                    if (effect.contains("POTION:")) {
                                        if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                            if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                                if (PotionEffectType.getByName(effect1[1].toUpperCase()) != null) {
                                                    if (effect1[4] != null && effect1[4].equalsIgnoreCase("%victim%")) {
                                                        player.addPotionEffect(
                                                                new PotionEffect(PotionEffectType.getByName(effect1[1].toUpperCase()), Integer.parseInt(effect1[3]) * 20, -1 + Integer.parseInt(effect1[2]), false));
                                                    } else {
                                                        p.addPotionEffect(
                                                                new PotionEffect(PotionEffectType.getByName(effect1[1].toUpperCase()), Integer.parseInt(effect1[3]) * 20, -1 + Integer.parseInt(effect1[2]), false));
                                                    }
                                                } else {
                                                    System.out.print("Unknown Potion effect " + effect1[1].toUpperCase());
                                                }
                                            }
                                        } else {
                                            if (PotionEffectType.getByName(effect1[1].toUpperCase()) != null) {
                                                if (effect1[4] != null && effect1[4].equalsIgnoreCase("%victim%")) {
                                                    player.addPotionEffect(
                                                            new PotionEffect(PotionEffectType.getByName(effect1[1].toUpperCase()), Integer.parseInt(effect1[3]), -1 + Integer.parseInt(effect1[2]), false));
                                                } else {
                                                    p.addPotionEffect(
                                                            new PotionEffect(PotionEffectType.getByName(effect1[1].toUpperCase()), Integer.parseInt(effect1[3]), -1 + Integer.parseInt(effect1[2]), false));
                                                }
                                            } else {
                                                System.out.print("Unknown Potion effect " + effect1[1].toUpperCase());
                                            }
                                        }
                                    }
                                    if (effect.contains("SETDAMAGE:")) {
                                        if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + "levels." + i + ".chance")) {
                                            if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                                e.setDamage(Integer.parseInt(effect1[0]));
                                            }
                                        } else {
                                            e.setDamage(Integer.parseInt(effect1[0]));
                                        }
                                    }
                                    if (effect.contains("LIGHTNING:")) {
                                        if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                            if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                                if (effect1[1].equalsIgnoreCase("%attacker%")) {
                                                    p.getWorld().strikeLightning(p.getLocation());
                                                } else {
                                                    player.getWorld().strikeLightning(player.getLocation());
                                                }
                                            }
                                        } else {
                                            if (effect1[1].equalsIgnoreCase("%attacker%")) {
                                                p.getWorld().strikeLightning(p.getLocation());
                                            } else {
                                                player.getWorld().strikeLightning(player.getLocation());
                                            }
                                        }
                                    }
                                    if (effect.contains("FIRE:")) {
                                        if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                            if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                                if (effect1[2].equalsIgnoreCase("%attacker%")) {
                                                    p.setFireTicks(100);
                                                } else {
                                                    player.setFireTicks(Integer.parseInt(effect1[1]));
                                                }
                                            }
                                        } else {
                                            if (effect1[2].equalsIgnoreCase("%attacker%")) {
                                                p.setFireTicks(100);
                                            } else {
                                                player.setFireTicks(Integer.parseInt(effect1[1]));
                                            }
                                        }
                                    }
                                    if (effect.contains("HEAL:")) {
                                        if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                            if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                                if (effect1[2].equalsIgnoreCase("%attacker%")) {
                                                    if (p.getHealth() + Integer.parseInt(effect1[1]) > p.getMaxHealth()) {
                                                        p.setHealth(p.getHealth() + Integer.parseInt(effect1[1]));
                                                    } else {
                                                        p.setHealth(p.getMaxHealth());
                                                    }
                                                } else {
                                                    if (pl.getHealth() + Integer.parseInt(effect1[1]) > pl.getMaxHealth()) {
                                                        pl.setHealth(pl.getHealth() + Integer.parseInt(effect1[1]));
                                                    } else {
                                                        pl.setHealth(pl.getMaxHealth());
                                                    }
                                                }
                                            }
                                        } else {
                                            if (effect1[2].equalsIgnoreCase("%attacker%")) {
                                                if (p.getHealth() + Integer.parseInt(effect1[1]) > p.getMaxHealth()) {
                                                    p.setHealth(p.getHealth() + Integer.parseInt(effect1[1]));
                                                } else {
                                                    p.setHealth(p.getMaxHealth());
                                                }
                                            } else {
                                                if (pl.getHealth() + Integer.parseInt(effect1[1]) > pl.getMaxHealth()) {
                                                    pl.setHealth(pl.getHealth() + Integer.parseInt(effect1[1]));
                                                } else {
                                                    pl.setHealth(pl.getMaxHealth());
                                                }
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
                                for (String effect : effects) {
                                    String[] effect1 = effect.split(":");
                                    if (effect.contains("POTION:")) {
                                        if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                            if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                                if (PotionEffectType.getByName(effect1[1].toUpperCase()) != null) {
                                                    if (effect1[4] != null && effect1[4].equalsIgnoreCase("%victim%")) {
                                                        player.addPotionEffect(
                                                                new PotionEffect(PotionEffectType.getByName(effect1[1].toUpperCase()), Integer.parseInt(effect1[3]) * 20, -1 + Integer.parseInt(effect1[2]), false));
                                                    } else {
                                                        p.addPotionEffect(
                                                                new PotionEffect(PotionEffectType.getByName(effect1[1].toUpperCase()), Integer.parseInt(effect1[3]) * 20, -1 + Integer.parseInt(effect1[2]), false));
                                                    }
                                                } else {
                                                    System.out.print("Unknown Potion effect " + effect1[1].toUpperCase());
                                                }
                                            }
                                        } else {
                                            if (PotionEffectType.getByName(effect1[1].toUpperCase()) != null) {
                                                if (effect1[4] != null && effect1[4].equalsIgnoreCase("%victim%")) {
                                                    player.addPotionEffect(
                                                            new PotionEffect(PotionEffectType.getByName(effect1[1].toUpperCase()), Integer.parseInt(effect1[3]), -1 + Integer.parseInt(effect1[2]), false));
                                                } else {
                                                    p.addPotionEffect(
                                                            new PotionEffect(PotionEffectType.getByName(effect1[1].toUpperCase()), Integer.parseInt(effect1[3]), -1 + Integer.parseInt(effect1[2]), false));
                                                }
                                            } else {
                                                System.out.print("Unknown Potion effect " + effect1[1].toUpperCase());
                                            }
                                        }
                                    }
                                    if (effect.contains("SETDAMAGE:")) {
                                        if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                            if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                                e.setDamage(Integer.parseInt(effect1[0]));
                                            }
                                        } else {
                                            e.setDamage(Integer.parseInt(effect1[0]));
                                        }
                                    }
                                    if (effect.contains("LIGHTNING:")) {
                                        if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                            if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                                if (effect1[1].equalsIgnoreCase("%attacker%")) {
                                                    p.getWorld().strikeLightning(p.getLocation());
                                                } else {
                                                    player.getWorld().strikeLightning(player.getLocation());
                                                }
                                            }
                                        } else {
                                            if (effect1[1].equalsIgnoreCase("%attacker%")) {
                                                p.getWorld().strikeLightning(p.getLocation());
                                            } else {
                                                player.getWorld().strikeLightning(player.getLocation());
                                            }
                                        }
                                    }
                                    if (effect.contains("FIRE:")) {
                                        if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                            if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                                if (effect1[2].equalsIgnoreCase("%attacker%")) {
                                                    p.setFireTicks(100);
                                                } else {
                                                    player.setFireTicks(Integer.parseInt(effect1[1]));
                                                }
                                            }
                                        } else {
                                            if (effect1[2].equalsIgnoreCase("%attacker%")) {
                                                p.setFireTicks(100);
                                            } else {
                                                player.setFireTicks(Integer.parseInt(effect1[1]));
                                            }
                                        }
                                    }
                                    if (effect.contains("HEAL:")) {
                                        if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                            if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                                if (effect1[2].equalsIgnoreCase("%attacker%")) {
                                                    if (p.getHealth() + Integer.parseInt(effect1[1]) > p.getMaxHealth()) {
                                                        p.setHealth(p.getHealth() + Integer.parseInt(effect1[1]));
                                                    } else {
                                                        p.setHealth(p.getMaxHealth());
                                                    }
                                                } else {
                                                    if (pl.getHealth() + Integer.parseInt(effect1[1]) > pl.getMaxHealth()) {
                                                        pl.setHealth(pl.getHealth() + Integer.parseInt(effect1[1]));
                                                    } else {
                                                        pl.setHealth(pl.getMaxHealth());
                                                    }
                                                }
                                            }
                                        } else {
                                            if (effect1[2].equalsIgnoreCase("%attacker%")) {
                                                if (p.getHealth() + Integer.parseInt(effect1[1]) > p.getMaxHealth()) {
                                                    p.setHealth(p.getHealth() + Integer.parseInt(effect1[1]));
                                                } else {
                                                    p.setHealth(p.getMaxHealth());
                                                }
                                            } else {
                                                if (pl.getHealth() + Integer.parseInt(effect1[1]) > pl.getMaxHealth()) {
                                                    pl.setHealth(pl.getHealth() + Integer.parseInt(effect1[1]));
                                                } else {
                                                    pl.setHealth(pl.getMaxHealth());
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


//        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Headshot"); counter++) {
//            if (utils.hasenchant(
//                    plugin.getConfig().getString("Translate.Headshot") + " " + utils.convertPower(counter),
//                    player.getInventory().getChestplate()) && plugin.getConfig().getBoolean("Enabled.Headshot" + utils.convertPower(counter))) {
//                if (e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
//                    Projectile projectile = (Projectile) e.getDamager();
//                    Player shooter = (Player) projectile.getShooter();
//                    if ((shooter != null)) {
//                        Entity damaged = e.getEntity();
//                        double projectileY = projectile.getLocation().getY();
//                        double damagedY = damaged.getLocation().getY();
//                        if ((damaged instanceof Player) && (projectileY - damagedY > 1.35D)) {
//                            e.setDamage(e.getDamage() * (counter + 1));
//                        }
//                    }
//                }
//            }
//        }
//
//
//        if ((player.getInventory().getChestplate() != null)
//                && (player.getInventory().getChestplate().getItemMeta() != null)
//                && (player.getInventory().getChestplate().getItemMeta().getLore() != null)) {
//            if (utils.hasenchant(plugin.getConfig().getString("Translate.Ignition") + " I",
//                    player.getInventory().getChestplate()) && plugin.getConfig().getBoolean("Enabled.IgnitionI")) {
//                Random object = new Random();
//                int i = 1 + object.nextInt(100);
//                if (i < plugin.getConfig().getInt("Chances.IgnitionI")) {
//                    p.setFireTicks(100);
//                }
//            }
//
//            for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Enlightened"); counter++) {
//                if (utils.hasenchant(
//                        plugin.getConfig().getString("Translate.Enlightened") + " " + utils.convertPower(counter),
//                        player.getInventory().getChestplate()) && plugin.getConfig().getBoolean("Enabled.Enlightened" + utils.convertPower(counter))) {
//                    Random object = new Random();
//
//                    int i = 1 + object.nextInt(100);
//                    if (!plugin.getConfig().contains("Chances.Enlightened" + utils.convertPower(counter))) {
//                        plugin.getConfig().set("Chances.Enlightened" + utils.convertPower(counter), 10);
//                    }
//                    plugin.saveConfig();
//                    plugin.reloadConfig();
//                    if (i < plugin.getConfig().getInt("Chances.Enlightened" + utils.convertPower(counter))) {
//                        if ((player.getHealth() + (counter * 2)) <= player.getMaxHealth()) {
//                            player.setHealth(player.getHealth() + (counter));
//                        }
//                    }
//                }
//            }
//
//            for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Evade"); counter++) {
//                if (utils.hasenchant(
//                        plugin.getConfig().getString("Translate.Evade") + " " + utils.convertPower(counter),
//                        player.getInventory().getChestplate()) && plugin.getConfig().getBoolean("Enabled.Evade" + utils.convertPower(counter))) {
//                    Random object = new Random();
//
//                    int i = 1 + object.nextInt(100);
//                    if (!plugin.getConfig().contains("Chances.Evade" + utils.convertPower(counter))) {
//                        plugin.getConfig().set("Chances.Evade" + utils.convertPower(counter), 10);
//                    }
//                    plugin.saveConfig();
//                    plugin.reloadConfig();
//                    if (i < plugin.getConfig().getInt("Chances.Evade" + utils.convertPower(counter))) {
//                        e.setDamage(0.00);
//                    }
//                }
//            }
//        }
//
//        if (p.getItemInHand() == null) {
//            return;
//        }
//        if (p.getItemInHand().getItemMeta() == null) {
//            return;
//        }
//        if (p.getItemInHand().getItemMeta().getLore() == null) {
//            return;
//        }
//
//        if (utils.hasenchant(plugin.getConfig().getString("Translate.ThunderusBlow") + " I",
//                p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.ThunderusBlowI")) {
//            Random object = new Random();
//            for (int counter = 1; counter <= 1; counter++) {
//                int i = 1 + object.nextInt(100);
//                player.getWorld().strikeLightning(player.getLocation());
//            }
//        }
//
//
//        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Warden"); counter++) {
//            if (utils.hasenchant(plugin.getConfig().getString("Translate.Warden") + " " + utils.convertPower(counter),
//                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Warden" + utils.convertPower(counter))) {
//                Random object = new Random();
//                int i = 1 + object.nextInt(100);
//                if (!plugin.getConfig().contains("Chances.Warden" + utils.convertPower(counter))) {
//                    plugin.getConfig().set("Chances.Warden" + utils.convertPower(counter), 5);
//                }
//                plugin.saveConfig();
//                plugin.reloadConfig();
//                if (i < plugin.getConfig().getInt("Chances.Warden" + utils.convertPower(counter))) {
//                    p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 10, -1 + counter, false));
//                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 10, -1 + counter, false));
//                }
//            }
//        }
//        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Frostbite"); counter++) {
//            if (utils.hasenchant(plugin.getConfig().getString("Translate.Frostbite") + " " + utils.convertPower(counter),
//                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Frostbite" + utils.convertPower(counter))) {
//                Random object = new Random();
//                int i = 1 + object.nextInt(100);
//                if (!plugin.getConfig().contains("Chances.Frostbite" + utils.convertPower(counter))) {
//                    plugin.getConfig().set("Chances.Frostbite" + utils.convertPower(counter), 5);
//                }
//                plugin.saveConfig();
//                plugin.reloadConfig();
//                if (i < plugin.getConfig().getInt("Chances.Frostbite" + utils.convertPower(counter))) {
//                    plugin.FrostBite(player, 60 + (40 * counter));
//                }
//            }
//        }
//        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Fury"); counter++) {
//            if (utils.hasenchant(plugin.getConfig().getString("Translate.Fury") + " " + utils.convertPower(counter),
//                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Fury" + utils.convertPower(counter))) {
//                if (plugin.FuryData.containsKey(p.getName())) {
//                    plugin.FuryData.put(p.getName(), plugin.FuryData.get(p.getName()) + 1);
//                    plugin.FuryTime.put(p.getName(), 2);
//                } else {
//                    plugin.FuryData.put(p.getName(), 1);
//                    plugin.FuryTime.put(p.getName(), 2);
//                }
//                e.setDamage(e.getDamage() * (1 + plugin.FuryData.get(p.getName())));
//            }
//        }
//        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Penetrate"); counter++) {
//            if (utils.hasenchant(plugin.getConfig().getString("Translate.Penetrate") + " " + utils.convertPower(counter), p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Penetrate" + utils.convertPower(counter))) {
//                plugin.addpenetrate(player.getName(), counter);
//            }
//        }
//        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Paralyze"); counter++) {
//            if (utils.hasenchant(plugin.getConfig().getString("Translate.Paralyze") + " " + utils.convertPower(counter),
//                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Paralyze" + utils.convertPower(counter))) {
//                Random object = new Random();
//
//                int i = 1 + object.nextInt(100);
//                if (!plugin.getConfig().contains("Chances.Paralyze" + utils.convertPower(counter))) {
//                    plugin.getConfig().set("Chances.Paralyze" + utils.convertPower(counter), 10);
//                }
//                plugin.saveConfig();
//                plugin.reloadConfig();
//                if (i < plugin.getConfig().getInt("Chances.Paralyze" + utils.convertPower(counter))) {
//                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (4 + counter * 2) * 20, 10, false));
//                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (4 + counter * 2) * 20, -10, false));
//                }
//            }
//        }
//        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Blindness"); counter++) {
//            if (utils.hasenchant(
//                    plugin.getConfig().getString("Translate.Blindness") + " " + utils.convertPower(counter),
//                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Blindness" + utils.convertPower(counter))) {
//                Random object = new Random();
//
//                int i = 1 + object.nextInt(100);
//                if (!plugin.getConfig().contains("Chances.Blindness" + utils.convertPower(counter))) {
//                    plugin.getConfig().set("Chances.Blindness" + utils.convertPower(counter), 10);
//                }
//                plugin.saveConfig();
//                plugin.reloadConfig();
//                if (i < plugin.getConfig().getInt("Chances.Blindness" + utils.convertPower(counter))) {
//                    player.addPotionEffect(
//                            new PotionEffect(PotionEffectType.BLINDNESS, (4 + counter * 2) * 20, 0, false));
//                }
//            }
//        }
//        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Poison"); counter++) {
//            if (utils.hasenchant(plugin.getConfig().getString("Translate.Poison") + " " + utils.convertPower(counter),
//                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Poison" + utils.convertPower(counter))) {
//                Random object = new Random();
//
//                int i = 1 + object.nextInt(100);
//                if (!plugin.getConfig().contains("Chances.Poison" + utils.convertPower(counter))) {
//                    plugin.getConfig().set("Chances.Poison" + utils.convertPower(counter), 10);
//                }
//                plugin.saveConfig();
//                plugin.reloadConfig();
//                if (i < plugin.getConfig().getInt("Chances.Poison" + utils.convertPower(counter))) {
//                    player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, counter - 1, false));
//                }
//            }
//        }
//        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Wither"); counter++) {
//            if (utils.hasenchant(plugin.getConfig().getString("Translate.Wither") + " " + utils.convertPower(counter),
//                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Wither" + utils.convertPower(counter))) {
//                Random object = new Random();
//
//                int i = 1 + object.nextInt(100);
//                if (!plugin.getConfig().contains("Chances.Wither" + utils.convertPower(counter))) {
//                    plugin.getConfig().set("Chances.Wither" + utils.convertPower(counter), 10);
//                }
//                plugin.saveConfig();
//                plugin.reloadConfig();
//                if (i < plugin.getConfig().getInt("Chances.Wither" + utils.convertPower(counter))) {
//                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, counter - 1, false));
//                }
//            }
//        }
//        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Nausea"); counter++) {
//            if (utils.hasenchant(plugin.getConfig().getString("Translate.Nausea") + " " + utils.convertPower(counter),
//                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Nausea" + utils.convertPower(counter))) {
//                Random object = new Random();
//
//                int i = 1 + object.nextInt(100);
//                if (!plugin.getConfig().contains("Chances.Nausea" + utils.convertPower(counter))) {
//                    plugin.getConfig().set("Chances.Nausea" + utils.convertPower(counter), 10);
//                }
//                plugin.saveConfig();
//                plugin.reloadConfig();
//                if (i < plugin.getConfig().getInt("Chances.Nausea" + utils.convertPower(counter))) {
//                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (8 + counter * 2) * 20, 1, false));
//                }
//            }
//        }
//        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Slowness"); counter++) {
//            if (utils.hasenchant(plugin.getConfig().getString("Translate.Slowness") + " " + utils.convertPower(counter),
//                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Slowness" + utils.convertPower(counter))) {
//                Random object = new Random();
//
//                int i = 1 + object.nextInt(100);
//                if (!plugin.getConfig().contains("Chances.Slowness" + utils.convertPower(counter))) {
//                    plugin.getConfig().set("Chances.Slowness" + utils.convertPower(counter), 10);
//                }
//                plugin.saveConfig();
//                plugin.reloadConfig();
//                if (i < plugin.getConfig().getInt("Chances.Slowness" + utils.convertPower(counter))) {
//                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, counter - 1, false));
//                }
//            }
//        }
//        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Lifesteal"); counter++) {
//            if (utils.hasenchant(
//                    plugin.getConfig().getString("Translate.Lifesteal") + " " + utils.convertPower(counter),
//                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Lifesteal" + utils.convertPower(counter))) {
//                Random object = new Random();
//
//                int i = 1 + object.nextInt(100);
//                if (!plugin.getConfig().contains("Chances.Lifesteal" + utils.convertPower(counter))) {
//                    plugin.getConfig().set("Chances.Lifesteal" + utils.convertPower(counter), 10);
//                }
//                plugin.saveConfig();
//                plugin.reloadConfig();
//                if (i < plugin.getConfig().getInt("Chances.Lifesteal" + utils.convertPower(counter))) {
//                    if (pl.getHealth() < 20 - counter * 2) {
//                        pl.setHealth(pl.getHealth() + counter * 2);
//                    } else {
//                        pl.setHealth(20.0D);
//                    }
//                }
//            }
//        }
//        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Cursed"); counter++) {
//            if (utils.hasenchant(plugin.getConfig().getString("Translate.Cursed") + " " + utils.convertPower(counter),
//                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Cursed" + utils.convertPower(counter))) {
//                Random object = new Random();
//
//                int i = 1 + object.nextInt(100);
//                if (!plugin.getConfig().contains("Chances.Cursed" + utils.convertPower(counter))) {
//                    plugin.getConfig().set("Chances.Cursed" + utils.convertPower(counter), 10);
//                }
//                plugin.saveConfig();
//                plugin.reloadConfig();
//                if (i < plugin.getConfig().getInt("Chances.Cursed" + utils.convertPower(counter))) {
//                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1 + counter * 2 * 20, 0, false));
//                }
//            }
//        }
//        for (String Enchant : enchants.getConfigurationSection("Enchantments").getKeys(false)) {
//            for (int counter = 0; counter <= enchants.getInt("Enchantments." + Enchant + ".MaxPower"); counter++) {
//
//                if (utils.hasenchant(Enchant + " " + utils.convertPower(counter), p.getItemInHand())) {
//                    ArrayList<String> Potions = (ArrayList<String>) enchants
//                            .getList("Enchantments." + Enchant + ".Options.PotionEffects");
//                    for (String Potion : Potions) {
//                        if (1 + r.nextInt(100) < enchants.getInt("Enchantments." + Enchant + ".Chance")) {
//                            player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(Potion),
//                                    enchants.getInt("Enchantments." + Enchant + ".Options.PotionTime") * 20,
//                                    -1 + counter * enchants.getInt("Enchantments." + Enchant + ".Options.PowerIncrease")
//                                            + (enchants.getInt("Enchantments." + Enchant + ".StartingPower") - 1),
//                                    false));
//                        }
//                    }
//                    if (1 + r.nextInt(100) <= enchants.getInt("Enchantments." + Enchant + ".Chance")) {
//                        e.setDamage(e.getDamage()
//                                * enchants.getDouble("Enchantments." + Enchant + ".Options.DamageModifier"));
//                    }
//                }
//            }
//        }
    }
}






