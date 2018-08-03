package com.peaches.customenchants.Support;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

class ASkyblockSupport {

    private static final ASkyBlockAPI SkyBlock = ASkyBlockAPI.getInstance();

    public static Boolean inTerritory(Player player) {
        return SkyBlock.playerIsOnIsland(player);
    }

    public static Boolean isFriendly(Player player, Player other) {
        return SkyBlock.inTeam(player.getUniqueId()) && SkyBlock.inTeam(other.getUniqueId()) && SkyBlock.getTeamMembers(player.getUniqueId()).contains(other.getUniqueId());
    }

    public static boolean canBreakBlock(Player player, Block block) {
        return SkyBlock.getIslandAt(block.getLocation()).equals(SkyBlock.getIslandOwnedBy(player.getUniqueId()));
    }
}


