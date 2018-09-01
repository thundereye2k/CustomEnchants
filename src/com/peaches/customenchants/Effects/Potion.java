package com.peaches.customenchants.Effects;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Potion extends Effects{
    public static Potion getInstance;

    public Potion() {
        getInstance = this;
        EffectManager.effects.add(this);
    }

    @Override
    public void add(Player p, Player o, Block b, Projectile projectile, String[] Effect, ItemStack item, String Enchant) {
        if(o != null){
            if (PotionEffectType.getByName(Effect[1].toUpperCase()) != null) {
                if (Effect.length==5 && Effect[4] != null && Effect[4].equalsIgnoreCase("%victim%")) {
                    o.addPotionEffect(
                            new PotionEffect(PotionEffectType.getByName(Effect[1].toUpperCase()), Integer.parseInt(Effect[3]) * 20, -1 + Integer.parseInt(Effect[2]), false));
                    return;
                } else {
                    p.addPotionEffect(
                            new PotionEffect(PotionEffectType.getByName(Effect[1].toUpperCase()), Integer.parseInt(Effect[3]) * 20, -1 + Integer.parseInt(Effect[2]), false));
                    return;
                }
            } else {
                System.out.print("Unknown Potion effect " + Effect[1].toUpperCase());
                return;
            }
        }
        if (PotionEffectType.getByName(Effect[1].toUpperCase()) != null) {
            p.addPotionEffect(
                    new PotionEffect(PotionEffectType.getByName(Effect[1].toUpperCase()), 999999, -1 + Integer.parseInt(Effect[2]), false));
            return;
        } else {
            System.out.print("Unknown Potion effect " + Effect[1].toUpperCase());
            return;
        }
    }
    @Override
    public void remove(Player p, Player o, String[] Effect, String Enchant) {
        if(o != null){
            if (PotionEffectType.getByName(Effect[1].toUpperCase()) != null) {
                o.removePotionEffect(PotionEffectType.getByName(Effect[1].toUpperCase()));
            } else {
                System.out.print("Unknown Potion Effects " + Effect[1].toUpperCase());
            }
        }
        if (PotionEffectType.getByName(Effect[1].toUpperCase()) != null) {
            p.removePotionEffect(PotionEffectType.getByName(Effect[1].toUpperCase()));
        } else {
            System.out.print("Unknown Potion Effects " + Effect[1].toUpperCase());
        }
    }

    @Override
    public String getname(){
        return "POTION";
    }
}
