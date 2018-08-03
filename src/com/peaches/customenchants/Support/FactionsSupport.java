package com.peaches.customenchants.Support;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

class FactionsSupport
{

    public static boolean hasfaction(Player player){
        return FPlayers.getInstance().getByPlayer(player).hasFaction();
    }

    public static boolean isFriendly(Player player, Player other) {
        if ((MPlayer.get(player) == null) || (MPlayer.get(player).getFaction() == null)) {
            return false;
        }
        Faction p = MPlayer.get(player).getFaction();
        if ((MPlayer.get(other) == null) || (MPlayer.get(other).getFaction() == null)) {
            return false;
        }
        Faction o = MPlayer.get(other).getFaction();
        Rel r = MPlayer.get(player).getRelationTo(MPlayer.get(other));
        return !ChatColor.stripColor(o.getName()).equalsIgnoreCase("Wilderness") && (r.isFriend() || p == o);
    }

    public static boolean isFriendly(Faction player, Faction other) {
        Rel r = player.getRelationTo(other);
        return !ChatColor.stripColor(other.getName()).equalsIgnoreCase("Wilderness") && (r.isFriend() || player == other);
    }

    public static boolean inTerritory(Player P) {
        MPlayer player = MPlayer.get(P);
        return !ChatColor.stripColor(player.getFaction().getName()).equalsIgnoreCase("Wilderness") && player.isInOwnTerritory();
    }

    public static boolean canBreakBlock(Player player, Block block)
    {
        Faction P = MPlayer.get(player).getFaction();
        Faction B = BoardColl.get().getFactionAt(PS.valueOf(block.getLocation()));
        return (ChatColor.stripColor(B.getName()).equalsIgnoreCase("Wilderness")) || (P == B);
    }
}


