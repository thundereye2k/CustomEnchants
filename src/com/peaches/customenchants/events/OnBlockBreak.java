package com.peaches.customenchants.events;

import com.google.common.collect.Lists;
import com.peaches.customenchants.Effects.EffectManager;
import com.peaches.customenchants.Support.Support;
import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OnBlockBreak implements Listener {
    private static Main plugin;
    private static Utils utils;
    private final ArrayList<Location> blocklist = new ArrayList<>();

    public OnBlockBreak(Main pl, Utils u) {
        plugin = pl;
        utils = u;
    }


    private List<Block> getCube(Location loc, Integer radius) {
        List<Block> blocks = Lists.newArrayList();
        for (int x = radius * -1; x <= radius; x++) {
            for (int y = radius * -1; y <= radius; y++) {
                for (int z = radius * -1; z <= radius; z++) {
                    Block b = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                    if (!b.getType().equals(Material.AIR)) {
                        blocks.add(b);
                    }
                }
            }
        }
        return blocks;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Random r = new Random();
        if (e.isCancelled()) {
            return;
        }
        if (blocklist.contains(e.getBlock().getLocation())) {
            blocklist.remove(e.getBlock().getLocation());
            return;
        }
        Player player = e.getPlayer();
        ItemStack item = player.getItemInHand();
        Block block1 = e.getBlock();
        if (plugin.containsblocktask(block1.getLocation())) {
            e.setCancelled(true);
            block1.setType(Material.AIR);
            plugin.removeblocktask(block1.getLocation());
        }
        for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
            if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("BLOCK_BREAK")) {
                for (String i : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getItemInHand()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getHelmet()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getChestplate()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getLeggings()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getBoots())) {
                        if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                            List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                            if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                if (1 + r.nextInt(100) < ConfigManager.getInstance().getCustomEncants().getInt("Enchantments." + Enchant + ".levels." + i + ".chance")) {
                                    for (String effect : effects) {
                                        String[] effect1 = effect.split(":");
                                        if (effect.toUpperCase().contains("INFUSION:")) {
                                            if (!e.getPlayer().isSneaking()) {
                                                e.setCancelled(true);
                                                Location loc = e.getBlock().getLocation();
                                                List<Block> blocks = getCube(loc, Integer.parseInt(effect1[1]));
                                                if (blocks.isEmpty()) {
                                                    blocks.add(e.getBlock());
                                                }
                                                for (Block block : blocks) {
                                                    if (block != null && block.getType() != Material.AIR && block.getType() != Material.BEDROCK && block.getType() != Material.STATIONARY_LAVA && block.getType() != Material.AIR && block.getType() != null && block.getType() != Material.STATIONARY_WATER && block.getType() != Material.WATER && block.getType() != Material.LAVA && Support.canBreakBlock(player, block) && Support.allowsBreak(block.getLocation(), player)) {
                                                        if (plugin.getConfig().getBoolean("Options.UseBlockBreakEvent")) {
                                                            blocklist.add(block.getLocation());
                                                            Support.AddExemption(player);
                                                            BlockBreakEvent event = new BlockBreakEvent(block, player);
                                                            Bukkit.getPluginManager().callEvent(event);
                                                            if (!event.isCancelled()) {
                                                                BreakBlock(block, player);
                                                            }
                                                        } else {
                                                            BreakBlock(block, player);
                                                        }
                                                    }
                                                }
                                                Support.RemoveExemption(player);
                                                return;
                                            }
                                        }
                                    }
                                    if (Support.canBreakBlock(player, block1) && Support.allowsBreak(block1.getLocation(), player)) {
                                        BreakBlock(block1, player);
                                    }
                                }
                            }else{
                                for (String effect : effects) {
                                    String[] effect1 = effect.split(":");
                                    if (effect.toUpperCase().contains("INFUSION:")) {
                                        if (!e.getPlayer().isSneaking()) {
                                            e.setCancelled(true);
                                            Location loc = e.getBlock().getLocation();
                                            List<Block> blocks = getCube(loc, Integer.parseInt(effect1[1]));
                                            if (blocks.isEmpty()) {
                                                blocks.add(e.getBlock());
                                            }
                                            for (Block block : blocks) {
                                                if (block != null && block.getType() != Material.AIR && block.getType() != Material.BEDROCK && block.getType() != Material.STATIONARY_LAVA && block.getType() != Material.AIR && block.getType() != null && block.getType() != Material.STATIONARY_WATER && block.getType() != Material.WATER && block.getType() != Material.LAVA && Support.canBreakBlock(player, block) && Support.allowsBreak(block.getLocation(), player)) {
                                                    if (plugin.getConfig().getBoolean("Options.UseBlockBreakEvent")) {
                                                        blocklist.add(block.getLocation());
                                                        Support.AddExemption(player);
                                                        BlockBreakEvent event = new BlockBreakEvent(block, player);
                                                        Bukkit.getPluginManager().callEvent(event);
                                                        if (!event.isCancelled()) {
                                                            BreakBlock(block, player);
                                                        }
                                                    } else {
                                                        BreakBlock(block, player);
                                                    }
                                                }
                                            }
                                            Support.RemoveExemption(player);
                                            return;
                                        }
                                    }
                                }
                                if (Support.canBreakBlock(player, block1) && Support.allowsBreak(block1.getLocation(), player)) {
                                    BreakBlock(block1, player);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void BreakBlock(Block b, Player player) {
        for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
            if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("BLOCK_BREAK")) {
                for (String i : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getItemInHand()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getHelmet()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getChestplate()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getLeggings()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getBoots())) {
                        if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                            List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                            for (String effect : effects) {
                                String[] effect1 = effect.split(":");
                                EffectManager.getInstance.add(player, null, b, null, effect1, player.getItemInHand(), Enchant);
                            }
                        }
                    }
                }
            }
        }
        b.breakNaturally();
    }


    @EventHandler
    public void onDamage(BlockDamageEvent e) {
        Player player = e.getPlayer();
        for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
            if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("BLOCK_BREAK")) {
                for (String i : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getItemInHand()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getHelmet()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getChestplate()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getLeggings()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), player.getInventory().getBoots())) {
                        if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                            List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                            for (String effect : effects) {
                                String[] effect1 = effect.split(":");
                                if (effect.toUpperCase().contains("INSTANT_BREAK")) {
                                    if (ConfigManager.getInstance().getCustomEncants().contains("Enchantments." + Enchant + ".levels." + i + ".condition")) {
                                        if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".levels." + i + ".condition").startsWith("BLOCKTYPE = ")) {
                                            if (e.getBlock().getType().name().equalsIgnoreCase(ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".levels." + i + ".condition").replace("BLOCKTYPE = ", ""))) {
                                                if (plugin.getConfig().getBoolean("Options.UseBlockBreakEvent")) {
                                                    Support.AddExemption(player);
                                                    BlockBreakEvent event = new BlockBreakEvent(e.getBlock(), player);
                                                    Bukkit.getPluginManager().callEvent(event);
                                                    if (!event.isCancelled()) {
                                                        BreakBlock(e.getBlock(), player);
                                                    }
                                                } else {
                                                    BreakBlock(e.getBlock(), player);
                                                }
                                            }
                                        }
                                    } else {
                                        if (plugin.getConfig().getBoolean("Options.UseBlockBreakEvent")) {
                                            Support.AddExemption(player);
                                            BlockBreakEvent event = new BlockBreakEvent(e.getBlock(), player);
                                            Bukkit.getPluginManager().callEvent(event);
                                            if (!event.isCancelled()) {
                                                BreakBlock(e.getBlock(), player);
                                            }
                                        } else {
                                            BreakBlock(e.getBlock(), player);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        if (e.getBlock().getType().equals(Material.SPONGE)) {
            BlockBreakEvent event = new BlockBreakEvent(e.getBlock(), e.getPlayer());
            Bukkit.getPluginManager().callEvent(event);
            e.getBlock().breakNaturally();
        }

    }
}
