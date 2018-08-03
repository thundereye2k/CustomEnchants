package com.peaches.customenchants.Support;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Relation;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

class FactionsUUID
{
    public static boolean isEnabled()
    {
        return org.bukkit.Bukkit.getServer().getPluginManager().isPluginEnabled("Factions");
    }

    public static boolean hasfaction(Player player){
        return FPlayers.getInstance().getByPlayer(player).hasFaction();
    }

    public static boolean isFriendly(Player player, Player other) {
        Faction p = FPlayers.getInstance().getByPlayer(player).getFaction();
        Faction o = FPlayers.getInstance().getByPlayer(other).getFaction();
        Relation r = FPlayers.getInstance().getByPlayer(player).getRelationTo(FPlayers.getInstance().getByPlayer(other));
        if (ChatColor.stripColor(o.getTag()).equalsIgnoreCase("Wilderness")) {
            return false;
        }
        return p == o || r.isAlly() && r.isAlly();
    }

    public static boolean inTerritory(Player P) {
        if (ChatColor.stripColor(FPlayers.getInstance().getByPlayer(P).getFaction().getTag()).equalsIgnoreCase("Wilderness")) {
            return false;
        }
        return ChatColor.stripColor(FPlayers.getInstance().getByPlayer(P).getFaction().getTag()).equalsIgnoreCase("SafeZone") || FPlayers.getInstance().getByPlayer(P).isInOwnTerritory() || FPlayers.getInstance().getByPlayer(P).isInAllyTerritory();
    }

    public static boolean inWarzone(Player p) {
        return false;
    }

    public static boolean canBreakBlock(Player player, Block block)
    {
        Faction P = FPlayers.getInstance().getByPlayer(player).getFaction();
        FLocation loc = new FLocation(block.getLocation());
        Faction B = Board.getInstance().getFactionAt(loc);
        return (ChatColor.stripColor(B.getTag()).equalsIgnoreCase("Wilderness")) || (P == B);
    }
}


