package com.peaches.customenchants.Effects;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class Player_Command extends Effects {

    public static Player_Command getInstance;

    public Player_Command() {
        getInstance = this;
        EffectManager.effects.add(this);
    }

    @Override
    public void add(Player p, Player o, Block b, Projectile projectile, String[] Effect, ItemStack item, String Enchant) {
        Bukkit.dispatchCommand(p, Effect[1]);
    }

    @Override
    public String getname() {
        return "PLAYER_COMMAND";
    }
}
