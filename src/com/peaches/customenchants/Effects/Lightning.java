package com.peaches.customenchants.Effects;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class Lightning extends Effects{

    public static Lightning getInstance;

    public Lightning() {
        getInstance = this;
        EffectManager.effects.add(this);
    }

    @Override
    public void add(Player p, Player o, Block b, Projectile projectile, String[] Effect, ItemStack item, String Enchant) {
        if (Effect[1].equalsIgnoreCase("%attacker%")) {
            p.getWorld().strikeLightning(p.getLocation());
        } else {
            o.getWorld().strikeLightning(o.getLocation());
        }
    }

    @Override
    public String getname(){
        return "LIGHTNING";
    }
}
