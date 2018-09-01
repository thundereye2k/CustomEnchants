package com.peaches.customenchants.events;

import com.peaches.customenchants.Effects.EffectManager;
import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

public class OnShootBow implements org.bukkit.event.Listener {
    private static Main plugin;
    private static Utils utils;

    public OnShootBow(Main pl, Utils u) {
        plugin = pl;
        utils = u;
    }

    @EventHandler
    public void onShootBow(EntityShootBowEvent e) {
        Random r = new Random();
        if (e.isCancelled()) {
            return;
        }
        if (e.getBow() == null) {
            return;
        }
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        final ItemStack bow = e.getBow();
        final Player shooter = (Player) e.getEntity();
        Vector velocity = e.getProjectile().getVelocity().clone();
        final int fireTicks = e.getProjectile().getFireTicks();
        final double speed = velocity.length();
        final Vector direction = new Vector(velocity.getX() / speed, velocity.getY() / speed, velocity.getZ() / speed);

        for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
            if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("BOW_FIRE")) {
                for (String i : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), shooter.getItemInHand()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), shooter.getInventory().getHelmet()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), shooter.getInventory().getChestplate()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), shooter.getInventory().getLeggings()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), shooter.getInventory().getBoots())) {
                        if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                            List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                            if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                    for (String effect : effects) {
                                        String[] effect1 = effect.split(":");
                                        if (effect.contains("MULTISHOT:")) {
                                            for (int i1 = 0; i1 < Integer.parseInt(effect1[1]); i1++) {
                                                if ((shooter.getGameMode() == org.bukkit.GameMode.CREATIVE) ||
                                                        (bow.containsEnchantment(org.bukkit.enchantments.Enchantment.ARROW_INFINITE))) {
                                                    Arrow arrow = shooter.launchProjectile(Arrow.class);
                                                    arrow.setFireTicks(fireTicks);
                                                    arrow.setBounce(false);
                                                    arrow.setVelocity(new Vector(direction.getX() + (Math.random() - 0.5D) / 3.5D,
                                                            direction.getY() + (Math.random() - 0.5D) / 3.5D,
                                                            direction.getZ() + (Math.random() - 0.5D) / 3.5D).normalize().multiply(speed));
                                                    arrow.setShooter(shooter);
                                                    arrow.setMetadata("Multishot", new FixedMetadataValue(
                                                            org.bukkit.Bukkit.getServer().getPluginManager().getPlugin("CustomEnchants"), Boolean.TRUE));
                                                    EffectManager.getInstance.add(shooter, null, null, arrow, effect1, e.getBow(), Enchant);
                                                    return;
                                                } else {
                                                    ItemStack item = new ItemStack(Material.ARROW);
                                                    if (shooter.getInventory().containsAtLeast(item, 1)) {
                                                        shooter.getInventory().removeItem(item);
                                                        Arrow arrow = shooter.launchProjectile(Arrow.class);
                                                        arrow.setFireTicks(fireTicks);
                                                        arrow.setBounce(false);
                                                        arrow.setVelocity(new Vector(direction.getX() + (Math.random() - 0.5D) / 3.5D,
                                                                direction.getY() + (Math.random() - 0.5D) / 3.5D,
                                                                direction.getZ() + (Math.random() - 0.5D) / 3.5D).normalize()
                                                                .multiply(speed));
                                                        arrow.setShooter(shooter);
                                                        EffectManager.getInstance.add(shooter, null, null, arrow, effect1, e.getBow(), Enchant);
                                                        return;
                                                    }
                                                }
                                            }
                                        }
                                        EffectManager.getInstance.add(shooter, null, null, (Projectile) e.getProjectile(), effect1, e.getBow(), Enchant);
                                    }
                                }
                            } else {
                                for (String effect : effects) {
                                    String[] effect1 = effect.split(":");
                                    if (effect.contains("MULTISHOT:")) {
                                        for (int i1 = 0; i1 < Integer.parseInt(effect1[1]); i1++) {
                                            if ((shooter.getGameMode() == org.bukkit.GameMode.CREATIVE) ||
                                                    (bow.containsEnchantment(org.bukkit.enchantments.Enchantment.ARROW_INFINITE))) {
                                                Arrow arrow = shooter.launchProjectile(Arrow.class);
                                                arrow.setFireTicks(fireTicks);
                                                arrow.setBounce(false);
                                                arrow.setVelocity(new Vector(direction.getX() + (Math.random() - 0.5D) / 3.5D,
                                                        direction.getY() + (Math.random() - 0.5D) / 3.5D,
                                                        direction.getZ() + (Math.random() - 0.5D) / 3.5D).normalize().multiply(speed));
                                                arrow.setShooter(shooter);
                                                arrow.setMetadata("Multishot", new FixedMetadataValue(
                                                        org.bukkit.Bukkit.getServer().getPluginManager().getPlugin("CustomEnchants"), Boolean.TRUE));
                                                EffectManager.getInstance.add(shooter, null, null, arrow, effect1, e.getBow(), Enchant);
                                                return;
                                            } else {
                                                ItemStack item = new ItemStack(Material.ARROW);
                                                if (shooter.getInventory().containsAtLeast(item, 1)) {
                                                    shooter.getInventory().removeItem(item);
                                                    Arrow arrow = shooter.launchProjectile(Arrow.class);
                                                    arrow.setFireTicks(fireTicks);
                                                    arrow.setBounce(false);
                                                    arrow.setVelocity(new Vector(direction.getX() + (Math.random() - 0.5D) / 3.5D,
                                                            direction.getY() + (Math.random() - 0.5D) / 3.5D,
                                                            direction.getZ() + (Math.random() - 0.5D) / 3.5D).normalize()
                                                            .multiply(speed));
                                                    arrow.setShooter(shooter);
                                                    EffectManager.getInstance.add(shooter, null, null, arrow, effect1, e.getBow(), Enchant);
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                    EffectManager.getInstance.add(shooter, null, null, (Projectile) e.getProjectile(), effect1, e.getBow(), Enchant);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onpickup(PlayerPickupItemEvent e) {
        org.bukkit.entity.Item item = e.getItem();
        if (item.hasMetadata("Multishot")) {
            e.setCancelled(true);
        }
    }
}


