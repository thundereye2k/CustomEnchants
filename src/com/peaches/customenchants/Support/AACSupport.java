package com.peaches.customenchants.Support;

import me.konsolas.aac.api.HackType;
import me.konsolas.aac.api.PlayerViolationEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class AACSupport implements Listener {
    private static final ArrayList<String> AAC = new ArrayList<>();

    public static void UnExemptPlayer(Player p) {
        AAC.remove(p.getName());
    }

    public static void ExemptPlayer(Player p) {
        AAC.add(p.getName());
    }

    @EventHandler
    public void onViolation( PlayerViolationEvent e) {
        if (AAC.contains(e.getPlayer().getName())) {
            if (e.getHackType().equals(HackType.NUKER) || e.getHackType().equals(HackType.FASTBREAK) || e.getHackType().equals(HackType.BADPACKETS)) {
                e.setCancelled(true);
            }
        }
    }

}
