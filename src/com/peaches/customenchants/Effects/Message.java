package com.peaches.customenchants.Effects;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class Message extends Effects {

    public static Message getInstance;

    public Message() {
        getInstance = this;
        EffectManager.effects.add(this);
    }

    @Override
    public void add(Player p, Player o, Block b, Projectile projectile, String[] Effect, ItemStack item, String Enchant) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Effect[1]));
    }

    @Override
    public String getname() {
        return "MESSAGE";
    }
}
