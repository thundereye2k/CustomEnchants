package com.peaches.customenchants.Effects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class Smelt extends Effects {

    public static Smelt getInstance;

    public Smelt() {
        getInstance = this;
        EffectManager.effects.add(this);
    }

    @Override
    public void add(Player p, Player o, Block b, Projectile projectile, String[] Effect, ItemStack item, String Enchant) {
        if (b != null) {
            if (smeltedItem(b.getType()) != null) {
                b.getWorld().dropItemNaturally(b.getLocation(), smeltedItem(b.getType()));
                b.setType(Material.AIR);
                for (int i1 = 0; i1 < 10; i1 += 5) {
                    Location loc = b.getLocation();
                }
            }
        }
    }

    @Override
    public String getname() {
        return "SMELT";
    }



    private ItemStack smeltedItem(Material type) {
        if (type == Material.IRON_ORE) {
            return new ItemStack(Material.IRON_INGOT, 1);
        }
        if (type == Material.GOLD_ORE) {
            return new ItemStack(Material.GOLD_INGOT, 1);
        }
        if (type == Material.SAND) {
            return new ItemStack(Material.GLASS, 1);
        }
        if (type == Material.COBBLESTONE) {
            return new ItemStack(Material.STONE, 1);
        }
        if (type == Material.CLAY) {
            return new ItemStack(Material.HARD_CLAY, 1);
        }
        if (type == Material.NETHERRACK) {
            return new ItemStack(Material.NETHER_BRICK_ITEM, 1);
        }
        return null;
    }
}
