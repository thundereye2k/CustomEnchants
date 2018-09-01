package com.peaches.customenchants.Effects;

import com.peaches.customenchants.main.Main;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class Fly extends Effects{

    public static Fly getInstance;

    public Fly() {
        getInstance = this;
        EffectManager.effects.add(this);
    }

    @Override
    public void add(Player p, Player o, Block b, Projectile projectile, String[] Effect, ItemStack item, String Enchant) {
        Main.Instance.addfly(p.getName());
    }
    @Override
    public void remove(Player p, Player o, String[] Effect, String Enchant) {
        if (Main.Instance.containsfly(p.getName())) {
            Main.Instance.removefly(p.getName());
        }
    }

    @Override
    public String getname(){
        return "FLY";
    }
}
