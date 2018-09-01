package com.peaches.customenchants.Effects;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class MAXHP_INCREASE extends Effects{

    public static MAXHP_INCREASE getInstance;

    public MAXHP_INCREASE() {
        getInstance = this;
        EffectManager.effects.add(this);
    }

    @Override
    public void add(Player p, Player o, Block b, Projectile projectile, String[] Effect, ItemStack item, String Enchant) {
        p.setMaxHealth(p.getMaxHealth() + Integer.parseInt(Effect[1]));
    }
    @Override
    public void remove(Player p, Player o, String[] Effect, String Enchant) {
        p.setMaxHealth(p.getMaxHealth() - Integer.parseInt(Effect[1]));
    }

    @Override
    public String getname(){
        return "MAXHP_INCREASE";
    }
}
