package com.peaches.customenchants.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class CustomEnchantEnchantEvent
        extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player p;

    public CustomEnchantEnchantEvent(Player cs) {
        this.p = cs;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return this.p;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}


