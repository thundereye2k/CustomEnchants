package com.peaches.customenchants.Support;

import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import com.peaches.mobcoins.MobCoinsGiveEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MobCoinsSupport implements Listener {

    private static Main plugin;
    private static Utils utils;

    public MobCoinsSupport(Main pl, Utils u) {
        plugin = pl;
        utils = u;
    }

    @EventHandler
    public void onMobCoins(MobCoinsGiveEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand() != null) {
            if (p.getItemInHand().hasItemMeta()) {
                if (p.getItemInHand().getItemMeta().hasLore()) {
                    for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Coins"); counter++) {
                        if (utils.hasenchant(plugin.getConfig().getString("Translate.Coins") + " " + utils.convertPower(counter), p.getItemInHand())) {
                            e.setAmount(1 + counter);
                        }
                    }
                }
            }
        }
    }

}
