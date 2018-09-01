package com.peaches.customenchants.Effects;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class Heal extends Effects {

    public static Heal getInstance;

    public Heal() {
        getInstance = this;
        EffectManager.effects.add(this);
    }

    @Override
    public void add(Player p, Player o, Block b, Projectile projectile, String[] Effect, ItemStack item, String Enchant) {
        if (Effect[1].equalsIgnoreCase("%attacker%")) {
            if (p.getHealth() + Integer.parseInt(Effect[1]) > p.getMaxHealth()) {
                p.setHealth(p.getHealth() + Integer.parseInt(Effect[1]));
            } else {
                p.setHealth(p.getMaxHealth());
            }
        } else {
            if (o.getHealth() + Integer.parseInt(Effect[1]) > o.getMaxHealth()) {
                o.setHealth(o.getHealth() + Integer.parseInt(Effect[1]));
            } else {
                o.setHealth(o.getMaxHealth());
            }
        }
    }

    @Override
    public String getname() {
        return "HEAL";
    }
}
