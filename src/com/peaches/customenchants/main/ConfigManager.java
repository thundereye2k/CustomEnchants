package com.peaches.customenchants.main;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.*;

public class ConfigManager {

    private static final ConfigManager instance = new ConfigManager();

    public static ConfigManager getInstance() {
        return instance;
    }

    private Plugin p;

    private FileConfiguration tinker;
    private File tfile;

    private FileConfiguration Messages;
    private File mfile;

    private FileConfiguration Gkits;
    private File gfile;

    private FileConfiguration CustomEncants;
    private File cefile;

    public void setup( Plugin p) {
        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }

        this.tfile = new File(p.getDataFolder(), "Tinker.yml");
        if (!this.tfile.exists()) {
            try {
                File en = new File(p.getDataFolder(), "/Tinker.yml");
                InputStream E = getClass().getResourceAsStream("/Tinker.yml");

                saveResource(E, en);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.tinker = YamlConfiguration.loadConfiguration(this.tfile);

        this.mfile = new File(p.getDataFolder(), "Messages.yml");
        if (!this.mfile.exists()) {
            try {
                File en = new File(p.getDataFolder(), "/Messages.yml");
                InputStream E = getClass().getResourceAsStream("/Messages.yml");

                saveResource(E, en);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.Messages = YamlConfiguration.loadConfiguration(this.mfile);

        this.gfile = new File(p.getDataFolder(), "GKits.yml");
        if (!this.gfile.exists()) {
            try {
                File en = new File(p.getDataFolder(), "/GKits.yml");
                InputStream E = getClass().getResourceAsStream("/GKits.yml");

                saveResource(E, en);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.Gkits = YamlConfiguration.loadConfiguration(this.gfile);
        this.cefile = new File(p.getDataFolder(), "CustomEnchants.yml");
        if (!this.cefile.exists()) {
            try {
                File en = new File(p.getDataFolder(), "/CustomEnchants.yml");
                InputStream E = getClass().getResourceAsStream("/CustomEnchants.yml");

                saveResource(E, en);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.CustomEncants = YamlConfiguration.loadConfiguration(this.cefile);
    }

    public FileConfiguration getTinker() {
        return this.tinker;
    }

    public void reloadTinker() {
        this.tinker = YamlConfiguration.loadConfiguration(this.tfile);
    }


    public FileConfiguration getMessages() {
        return this.Messages;
    }

    public void reloadMessages() {
        this.Messages = YamlConfiguration.loadConfiguration(this.mfile);
    }

    public void checkMessages() {
        InputStream E = getClass().getResourceAsStream("/Messages.yml");
        if (E == null) return;
        InputStreamReader strR = new InputStreamReader(E);
        FileConfiguration defaults = YamlConfiguration.loadConfiguration(strR);
        for (String path : defaults.getKeys(true)) {
            if (!this.Messages.contains(path)) {
                this.Messages.set(path, defaults.get(path));
            }
        }
        try {
            this.Messages.save(this.mfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public FileConfiguration getGKits() {
        return this.Gkits;
    }

    public void reloadGkits() {
        this.Gkits = YamlConfiguration.loadConfiguration(this.gfile);
    }


    public FileConfiguration getCustomEncants() {
        return this.CustomEncants;
    }

    public void reloadCustomEnchants() {
        this.CustomEncants = YamlConfiguration.loadConfiguration(this.cefile);
    }


    public PluginDescriptionFile getDesc() {
        return this.p.getDescription();
    }

    private void saveResource(InputStream in,  File out)
            throws Exception {
        try (FileOutputStream fos = new FileOutputStream(out)) {
            byte[] buf = new byte['?'];
            int i;
            while ((i = in.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } finally {
            if (in != null) {
                in.close();
            }

        }
    }
}
