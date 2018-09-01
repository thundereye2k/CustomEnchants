package com.peaches.customenchants.Effects;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class EffectManager {

    public static EffectManager getInstance;
    public static ArrayList<Effects> effects = new ArrayList<>();

    public EffectManager() {
        getInstance = this;
    }

    public void add(Player p, Player o, Block b, Projectile projectile, String[] Effect, ItemStack item, String Enchant) {
        for(Effects effect : effects){
            if(effect.getname().equalsIgnoreCase(Effect[0])){
                effect.add(p, o, b, projectile, Effect, item, Enchant);
            }
        }
    }

    public void remove(Player p, Player o, String[] Effect, String Enchant) {
        for(Effects effect : effects){
            if(effect.getname().equalsIgnoreCase(Effect[0])){
                effect.remove(p, o, Effect, Enchant);
            }
        }
    }
}
