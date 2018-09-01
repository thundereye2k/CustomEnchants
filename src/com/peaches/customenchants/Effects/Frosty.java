package com.peaches.customenchants.Effects;

import com.peaches.customenchants.main.Main;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class Frosty extends Effects{

    public static Frosty getInstance;

    public Frosty() {
        getInstance = this;
        EffectManager.effects.add(this);
    }

    @Override
    public void add(Player p, Player o, Block b, Projectile projectile, String[] Effect, ItemStack item, String Enchant) {
        Main.Instance.addsnow(p.getName());
    }
    @Override
    public void remove(Player p, Player o, String[] Effect, String Enchant) {
        Main.Instance.removensnow(p.getName());
    }

    @Override
    public String getname(){
        return "FROSTY";
    }
}
