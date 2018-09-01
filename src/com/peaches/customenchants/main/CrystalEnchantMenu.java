package com.peaches.customenchants.main;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class CrystalEnchantMenu {
    private static Utils utils;
    public final Inventory inv;
    private int total;
    private final ItemStack crystal;

    public CrystalEnchantMenu(ItemStack crystal, Utils u) {
        utils = u;
        this.inv = org.bukkit.Bukkit.getServer().createInventory(null, 36, utils.format("&8CustomEnchants"));
        this.crystal = crystal;
        this.total = 0;
    }

    public void applyEnchants( Player player, int slot) {
        if (this.crystal.getAmount() > 1) {
            ItemStack[] aitemstack;
            int j = (aitemstack = player.getInventory().getContents()).length;
            for (int i = 0; i < j; i++) {
                ItemStack itm = aitemstack[i];
                if (utils.isCrystal(itm)) {
                    itm.setAmount(itm.getAmount() - 1);
                    break;
                }
            }
        } else {
            player.getInventory().removeItem(this.crystal);
        }
        player.closeInventory();
        player.updateInventory();
    }

    public void addItem() {
        this.total += 1;
    }

    public void show( Player p) {
        p.openInventory(this.inv);
    }
}


