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
// --Commented out by Inspection START (03/08/2018 16:58):
//    public static boolean isEnabled()
//    {
//        return org.bukkit.Bukkit.getServer().getPluginManager().isPluginEnabled("Factions");
//    }
// --Commented out by Inspection STOP (03/08/2018 16:58)

    public static boolean hasfaction(Player player){
        return FPlayers.getInstance().getByPlayer(player).hasFaction();
    }

    public static boolean isFriendly(Player player, Player other) {
        Faction p = FPlayers.getInstance().getByPlayer(player).getFaction();
        Faction o = FPlayers.getInstance().getByPlayer(other).getFaction();
        Relation r = FPlayers.getInstance().getByPlayer(player).getRelationTo(FPlayers.getInstance().getByPlayer(other));
        return !ChatColor.stripColor(o.getTag()).equalsIgnoreCase("Wilderness") && (p == o || r.isAlly());
    }

    public static boolean inTerritory(Player P) {
        return !ChatColor.stripColor(FPlayers.getInstance().getByPlayer(P).getFaction().getTag()).equalsIgnoreCase("Wilderness") && (ChatColor.stripColor(FPlayers.getInstance().getByPlayer(P).getFaction().getTag()).equalsIgnoreCase("SafeZone") || FPlayers.getInstance().getByPlayer(P).isInOwnTerritory() || FPlayers.getInstance().getByPlayer(P).isInAllyTerritory());
    }

    public static boolean canBreakBlock(Player player, Block block)
    {
        Faction P = FPlayers.getInstance().getByPlayer(player).getFaction();
        FLocation loc = new FLocation(block.getLocation());
        Faction B = Board.getInstance().getFactionAt(loc);
        return (ChatColor.stripColor(B.getTag()).equalsIgnoreCase("Wilderness")) || (P == B);
    }
}


