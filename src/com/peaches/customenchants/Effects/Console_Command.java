package com.peaches.customenchants.Effects;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class Console_Command extends Effects {

    public static Console_Command getInstance;

    public Console_Command() {
        getInstance = this;
        EffectManager.effects.add(this);
    }

    @Override
    public void add(Player p, Player o, Block b, Projectile projectile, String[] Effect, ItemStack item, String Enchant) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Effect[1]);
    }

    @Override
    public String getname() {
        return "CONSOLE_COMMAND";
    }
}
