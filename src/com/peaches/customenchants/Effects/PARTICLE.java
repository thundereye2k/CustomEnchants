package com.peaches.customenchants.Effects;

import com.peaches.customenchants.main.Main;
import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class PARTICLE extends Effects {

    public static PARTICLE getInstance;

    public PARTICLE() {
        getInstance = this;
        EffectManager.effects.add(this);
    }

    @Override
    public void add(Player p, Player o, Block b, Projectile projectile, String[] Effect, ItemStack item, String Enchant) {
        if(b != null){
            if(org.bukkit.Effect.getByName(Effect[1]) != null){
                b.getLocation().getWorld().playEffect(b.getLocation(), org.bukkit.Effect.getByName(Effect[1]), 5);
                return;
            }
        }
        if(projectile != null){
            Main.Instance.Particle.put(projectile, Effect[1]);
            return;
        }
        if (Effect[1] != null) {
            Main.Instance.ParticleEffects.put(p, Effect[1]);
            return;
        }
    }

    @Override
    public void remove(Player p, Player o, String[] Effect, String Enchant) {
        if (Main.Instance.ParticleEffects.containsKey(p)) {
            Main.Instance.ParticleEffects.remove(p);
        }
    }

    @Override
    public String getname() {
        return "PARTICLE";
    }
}
