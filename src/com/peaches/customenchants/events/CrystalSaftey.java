package com.peaches.customenchants.events;

import com.peaches.customenchants.main.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemFlag;


public class CrystalSaftey implements org.bukkit.event.Listener {
    private static Utils utils;

    public CrystalSaftey(Utils u) {
        utils = u;
    }

    @EventHandler
    public void onMakeBeaconWithCrystal( PrepareItemCraftEvent event) {
        if ((event.getInventory().getResult() == null) || (!event.getInventory().getResult().getType().equals(Material.BEACON))) {
            return;
        }
        if (!utils.isCrystal(event.getInventory().getItem(5))) {
            return;
        }
        event.getInventory().setResult(null);
    }

    @EventHandler
    public void onInvClick( InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        if ((!event.getInventory().getType().equals(InventoryType.ANVIL)) && (!event.getInventory().getType().equals(InventoryType.MERCHANT))) {
            return;
        }
        if ((!utils.isCrystal(event.getCursor())) && (!utils.isCrystal(event.getCurrentItem()))) {
            return;
        }
        event.setCancelled(true);
        if ((event.getInventory().getItem(0) == null) && (event.getInventory().getItem(1) == null)) {
            event.getWhoClicked().closeInventory();
        }
        event.getWhoClicked().sendMessage(utils.prefix() + org.bukkit.ChatColor.GRAY + " Right click with the crystal in your hand to use it!");
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent e){
        if(e.getItem().getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS)){
            e.getItem().getItemMeta().removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
    }

}


