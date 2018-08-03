package com.peaches.customenchants.Support.nms;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;


public class NMS_v1_13_R1 {

    public static org.bukkit.inventory.ItemStack addGlow(org.bukkit.inventory.ItemStack item) {
        if(item == null){
            return null;
        }
        if(!item.getItemMeta().hasEnchants()){
            item.addUnsafeEnchantment(Enchantment.LUCK, 1);
            ItemMeta meta = item.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }
        return item;
    }

//    public static void sendParticle(Particle type, Location loc, float xOffset, float yOffset, float zOffset, float speed, int count) {
//        float x = (float) loc.getX();
//        float y = (float) loc.getY();
//        float z = (float) loc.getZ();
//        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, true, x, y, z, xOffset, yOffset,
//                zOffset, speed, count);
//        for (org.bukkit.entity.Player p : org.bukkit.Bukkit.getOnlinePlayers()) {
//            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
//        }
//    }
}


