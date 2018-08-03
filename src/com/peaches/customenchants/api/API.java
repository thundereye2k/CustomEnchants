package com.peaches.customenchants.api;

import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;


public class API implements Listener {

    private static Main plugin;
    private static final HandlerList handlers = new HandlerList();

    private static final API instance = new API(plugin);


    public static API getInstance() {
        return instance;
    }

    public API(Main pl) {
        plugin = pl;
    }


    public static HandlerList getHandlerList() {
        return handlers;
    }


    public HandlerList getHandlers() {
        return handlers;
    }

    public void addEnchant(String enchant, String Type, String description) {
        Utils.type.put(enchant, Type);
        Utils.desc.put(enchant, description);
    }

    public Boolean isEnchantEnabled(String Enchant) {
        return plugin.getConfig().getBoolean("Enabled."+Enchant);
    }

}
