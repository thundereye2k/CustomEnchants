package com.peaches.customenchants.Effects;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class Fire extends Effects {

    public static Fire getInstance;

    public Fire() {
        getInstance = this;
        EffectManager.effects.add(this);
    }

    @Override
    public void add(Player p, Player o, Block b, Projectile projectile, String[] Effect, ItemStack item, String Enchant) {
        if (Effect[1].equalsIgnoreCase("%attacker%")) {
            o.setFireTicks(Integer.parseInt(Effect[1]));
        } else {
            p.setFireTicks(Integer.parseInt(Effect[1]));
        }
    }

    @Override
    public String getname() {
        return "FIRE";
    }
}
