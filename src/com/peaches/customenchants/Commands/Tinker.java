package com.peaches.customenchants.Commands;

import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Tinker implements CommandExecutor {
    private static Utils utils;

    private final FileConfiguration messages = ConfigManager.getInstance().getMessages();

    public Tinker(Utils u) {
        utils = u;
    }

    public boolean onCommand(CommandSender cs, Command cmd, String String, String[] args) {
        if ((cs instanceof Player)) {
            Player p = (Player) cs;
            p.openInventory(utils.Tinkerer());
        }
        return false;
    }
}
