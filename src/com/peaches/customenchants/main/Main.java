package com.peaches.customenchants.main;

import ca.thederpygolems.armorequip.ArmorEquipEvent;
import ca.thederpygolems.armorequip.ArmorListener;
import ca.thederpygolems.armorequip.ArmorType;
import com.peaches.customenchants.Commands.CustomEnchants;
import com.peaches.customenchants.Commands.Tinker;
import com.peaches.customenchants.Commands.gkits;
import com.peaches.customenchants.Effects.*;
import com.peaches.customenchants.ParticleEffects.Frosty_Particle;
import com.peaches.customenchants.Support.*;
import com.peaches.customenchants.events.*;
import com.peaches.customenchants.listeners.CrystalUse;
import com.peaches.customenchants.listeners.GkitsListner;
import com.peaches.customenchants.listeners.TierChoose;
import com.peaches.customenchants.listeners.TinkererListner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class Main extends org.bukkit.plugin.java.JavaPlugin implements Listener {
    public final ArrayList<Projectile> Explode = new ArrayList<>();
    public final HashMap<Projectile, String> Particle = new HashMap<>();
    public final String UserID = "%%__USER__%%";
    public final HashMap<String, Integer> Gkits = new HashMap<>();
    private final HashMap<String, Integer> FuryTime = new HashMap<>();
    private final HashMap<String, Integer> Penetrate = new HashMap<>();
    private final ArrayList<String> fly = new ArrayList<>();
    private final ArrayList<String> snow = new ArrayList<>();
    private final HashMap<Location, BukkitRunnable> BlockTask = new HashMap<>();
    private final HashMap<Location, Material> BlockData = new HashMap<>();
    public final HashMap<Location, BlockState> BlockState = new HashMap<>();
    public final HashMap<Player, String> ParticleEffects = new HashMap<>();
    public final Utils utils = new Utils(this);

    public static Main Instance;

    public Main(Main pl) {
    }

    public Main() {
        PluginDescriptionFile pdf = getDescription();
    }

    public void onEnable() {
        saveDefaultConfig();
        ConfigManager.getInstance().setup(this);
        checkconfig();
        registerEffects();
        registerExplode();
        registerPaticle();
        registerPaticleEffects();
        registerSnow(this);
        registerItemHold();
        Inventory(this);
        Inventory1(this);
        GkitsCooldown();
        Saviour();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        getCommand("gkits").setExecutor(new gkits(this, utils));
        getCommand("tinkerer").setExecutor(new Tinker(utils));
        getCommand("customenchants").setExecutor(new CustomEnchants(this, utils));
        Version.getVersion();
        settype();
        loadbal();
        try {
            URL checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=38733");
            URLConnection con = checkURL.openConnection();
            String newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if (!getDescription().getVersion().equals(newVersion)) {
                update();
            }
        } catch (IOException ignored) {
        }
        Metrics metrics = new Metrics(this);
        reloadConfig();
        registerEvents();
        JacketFix();
        System.out.print("-------------------------------");
        System.out.print("");
        System.out.print("CustomEnchants Enabled!");
        System.out.print("");
        System.out.print("-------------------------------");
        Instance = this;
    }

    public void onDisable() {
        for (Location loc : BlockState.keySet()) {
            Block b = loc.getBlock();
            BlockState.get(loc).update(true, false);
        }
        System.out.print("-------------------------------");
        System.out.print("");
        System.out.print("CustomEnchants Disabled!");
        System.out.print("");
        System.out.print("-------------------------------");
        savebal();
    }

    @EventHandler
    public void onquit(PlayerQuitEvent e) {
        if (snow.contains(e.getPlayer().getName())) {
            snow.remove(e.getPlayer().getName());
        }
    }

    public void settype() {
        saveConfig();
        FileConfiguration enchants = ConfigManager.getInstance().getCustomEncants();
        utils.type.clear();
        for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
            utils.type.put(Enchant, ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Type"));
        }
    }

    private void registerEffects() {
        new EffectManager();
        new Frosty();
        new Potion();
        new Fly();
        new PARTICLE();
        new MAXHP_INCREASE();
        new Smelt();
        new Message();
        new Explode();
        new Player_Command();
        new Console_Command();
        new Repair();
        new Lightning();
        new Fire();
        new Heal();
        new Frostbite();
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new Utils(this), this);
        pm.registerEvents(new CrystalUse(this, utils), this);
        pm.registerEvents(new TierChoose(this, utils), this);
        pm.registerEvents(new GkitsListner(this, utils), this);
        pm.registerEvents(new TinkererListner(this, utils), this);
        pm.registerEvents(new ArmorListener(), this);
        pm.registerEvents(new ArmorEquipt(this, utils), this);
        pm.registerEvents(new PlayerDeathEvent(utils), this);
        pm.registerEvents(new PlayerDamageEvent(this, utils), this);
        pm.registerEvents(new PlayerFoodChangeEvent(utils), this);
        pm.registerEvents(new OnShootBow(this, utils), this);
        pm.registerEvents(new OnBlockBreak(this, utils), this);
        pm.registerEvents(new CrystalSaftey(utils), this);
        pm.registerEvents(new PlayerItemDamagetake(utils), this);
        pm.registerEvents(new PlayerFish(utils), this);
        pm.registerEvents(new OnItemEnchant(), this);
        if (Support.AAC()) {
            pm.registerEvents(new AACSupport(), this);
        }
        if (Support.MobCoins()) {
            pm.registerEvents(new MobCoinsSupport(this, utils), this);
        }
        if (Support.Spartan()) {
            pm.registerEvents(new SpartanSupport(), this);
        }
        if (Support.PAC()) {
            pm.registerEvents(new PACSupport(), this);
        }
    }

    private void addblock(Block b, Material m, boolean proximity) {
        int taskID;
        final BlockState state = b.getState();
        b.setType(m); // Stop item drops from spawning.
        BlockState.put(b.getLocation(), state);

        Random r = new Random();

        int delay = r.nextInt(20) + 1;

        if ((b.getType() == Material.SAND) || (b.getType() == Material.GRAVEL)) {
            delay += 1;
        }

        removeblock(b, state, proximity, delay, m);
    }

    private void removeblock(Block b, BlockState state, boolean proximity, Integer delay, Material m) {

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
            if (proximity) {
                boolean i = true;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (b.getLocation().getWorld().equals(p.getLocation().getWorld())) {
                        if ((Math.sqrt(p.getLocation().distanceSquared(
                                b.getLocation())) < 4.0D)) {
                            if (snow.contains(p.getName())) {
                                i = false;
                            }
                        }
                    }
                }
                if (i && b.getType().equals(m)) {
                    state.update(true, false);
                    BlockState.remove(b.getLocation());
                } else {
                    removeblock(b, state, proximity, delay, m);
                }
            }
        }, delay);
    }


    private void addblock(Material a, Material m, Location loc, Integer time, boolean proximity) {
        if (!BlockData.containsKey(loc) && !BlockTask.containsKey(loc)) {
            if (loc.getBlock().getType().equals(a)) {
                return;
            }
            loc.getBlock().setType(a);
            BlockData.put(loc, m);
            BlockTask.put(loc, new BukkitRunnable() {
                public void run() {
                    if (proximity) {
                        boolean i = true;
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.getLocation().getWorld().equals(loc.getWorld())) {
                                if (!(Math.sqrt(p.getLocation().distanceSquared(
                                        loc)) < 4.0D)) {
                                    i = true;
                                }
                            } else {
                                i = true;
                            }
                        }
                        if (i) {
                            loc.getBlock().setType(BlockData.get(loc));
                            BlockData.remove(loc);
                            BlockTask.get(loc).cancel();
                            BlockTask.remove(loc);
                        }
                    } else {
                        loc.getBlock().setType(BlockData.get(loc));
                        BlockData.remove(loc);
                        BlockTask.get(loc).cancel();
                        BlockTask.remove(loc);
                    }
                }
            });
            BlockTask.get(loc)
                    .runTaskTimer(Bukkit.getPluginManager()
                            .getPlugin("CustomEnchants"), time, time);
        }
    }

    public void FrostBite(Player p, int time) {
        if (Support.canBreakBlock(p, p.getLocation().clone().add(0, -1, 0).getBlock())) {
            addblock(Material.PACKED_ICE, p.getLocation().clone().add(0, -1, 0).getBlock().getType(), p.getLocation().clone().add(0, -1, 0), time, false);
        }
        if (Support.canBreakBlock(p, p.getLocation().clone().add(0, 2, 0).getBlock())) {
            addblock(Material.PACKED_ICE, p.getLocation().clone().add(0, 2, 0).getBlock().getType(), p.getLocation().clone().add(0, 2, 0), time, false);
        }
        if (Support.canBreakBlock(p, p.getLocation().clone().add(1, 0, 0).getBlock())) {
            addblock(Material.PACKED_ICE, p.getLocation().clone().add(1, 0, 0).getBlock().getType(), p.getLocation().clone().add(1, 0, 0), time, false);
        }
        if (Support.canBreakBlock(p, p.getLocation().clone().add(-1, 0, 0).getBlock())) {
            addblock(Material.PACKED_ICE, p.getLocation().clone().add(-1, 0, 0).getBlock().getType(), p.getLocation().clone().add(-1, 0, 0), time, false);
        }
        if (Support.canBreakBlock(p, p.getLocation().clone().add(0, 0, 1).getBlock())) {
            addblock(Material.PACKED_ICE, p.getLocation().clone().add(0, 0, 1).getBlock().getType(), p.getLocation().clone().add(0, 0, 1), time, false);
        }
        if (Support.canBreakBlock(p, p.getLocation().clone().add(0, 0, -1).getBlock())) {
            addblock(Material.PACKED_ICE, p.getLocation().clone().add(0, 0, -1).getBlock().getType(), p.getLocation().clone().add(0, 0, -1), time, false);
        }

    }

    public void addpenetrate(String player, int i) {
        Penetrate.put(player, i);
    }

    public void removepenetrate(String player) {
        Penetrate.remove(player);
    }

    public boolean containspenetrate(String player) {
        return Penetrate.containsKey(player);
    }

    public int getpenetrate(String player) {
        return Penetrate.get(player);
    }

    public void addfly(String player) {
        fly.add(player);
    }

    public void removefly(String player) {
        fly.remove(player);
    }

    public boolean containsfly(String player) {
        return fly.contains(player);
    }

    private void Saviour() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
                    if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("EQUIPT")) {
                        for (String i : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                            if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getItemInHand()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getHelmet()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getChestplate()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getLeggings()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getBoots())) {
                                if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                    List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                    for (String effect : effects) {
                                        String[] effect1 = effect.split(":");
                                        if (effect.contains("ALLIES:POTION:")) {
                                            int radius = getConfig().getInt("Options.AllyRadius");
                                            PotionEffectType pt = PotionEffectType.getByName(effect1[2].toUpperCase());
                                            for (Entity en : p.getNearbyEntities(radius, radius, radius)) {
                                                if (en instanceof Player) {
                                                    Player o = (Player) en;
                                                    if (Support.isFriendly(p, o)) {
                                                        if (o.hasPotionEffect(pt)) {
                                                            o.removePotionEffect(pt);
                                                        }
                                                        o.addPotionEffect(new PotionEffect(pt, 5 * 20,
                                                                Integer.parseInt(effect1[3])));
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, 0, 3 * 20);

    }

    private void update() {
        Support supp = new Support();
        supp.Update();
    }

    private void checkconfig() {
        Configuration config = getConfig().getDefaults();
        for (String path : config.getKeys(true)) {
            if (!getConfig().contains(path)) {
                getConfig().set(path, config.get(path));
            }
        }
        saveConfig();
        ConfigManager.getInstance().checkMessages();
    }

    private void loadbal() {
        File gkitsCooldown = new File("plugins//CustomEnchants//GKitsCooldown.yml");
        YamlConfiguration gkit = YamlConfiguration.loadConfiguration(gkitsCooldown);
        for (String name : gkit.getKeys(false)) {
            Gkits.put(name, gkit.getInt(name));
            gkit.set(name, null);
        }
        try {
            gkit.save(gkitsCooldown);
        } catch (IOException ignored) {
        }
    }

    private void savebal() {
        File gkitsCooldown = new File("plugins//CustomEnchants//GKitsCooldown.yml");
        if (!gkitsCooldown.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                gkitsCooldown.createNewFile();
            } catch (IOException ignored) {
            }
        }
        YamlConfiguration gkit = YamlConfiguration.loadConfiguration(gkitsCooldown);
        for (String name : Gkits.keySet()) {
            gkit.set(name, Gkits.get(name));
        }
        try {
            gkit.save(gkitsCooldown);
        } catch (IOException ignored) {
        }
    }

    public void addsnow(String player) {
        snow.add(player);
    }

    public void removensnow(String player) {
        snow.remove(player);
    }

    public void removeblocktask(Location loc) {
        if (BlockTask.containsKey(loc)) {
            BlockTask.remove(loc);
            BlockData.remove(loc);
        }
    }

    public boolean containsblocktask(Location loc) {
        return BlockTask.containsKey(loc);
    }

    public boolean containsblockstate(Location loc) {
        return BlockState.containsKey(loc);
    }

    private void JacketFix() {
        boolean disable = true;
        for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
            if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("EQUIPT")) {
                for (String i : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                    if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                        List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                        for (String effect : effects) {
                            String[] effect1 = effect.split(":");
                            if (effect.contains("MAXHP_INCREASE:")) {
                                disable = false;
                            }
                        }
                    }
                }
            }
        }
        if (disable) {
            return;
        }
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (Bukkit.getServer().getOnlinePlayers() == null) {
                return;
            }
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                double i = getConfig().getDouble("Options.DefaultHearts");
                if ((p.getInventory().getBoots() != null)
                        && (p.getInventory().getBoots().getItemMeta().getLore() != null)) {
                    for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
                        if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("EQUIPT")) {
                            for (String i1 : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                                if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i1)), p.getInventory().getBoots())) {
                                        List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i1 + ".effects");
                                        for (String effect : effects) {
                                            String[] effect1 = effect.split(":");
                                            if (effect.contains("MAXHP_INCREASE:")) {
                                                i = i + Integer.parseInt(effect1[1]);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

                if ((p.getInventory().getLeggings() != null)
                        && (p.getInventory().getLeggings().getItemMeta().getLore() != null)) {
                    for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
                        if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("EQUIPT")) {
                            for (String i1 : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                                if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i1)), p.getInventory().getLeggings())) {
                                        List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i1 + ".effects");
                                        for (String effect : effects) {
                                            String[] effect1 = effect.split(":");
                                            if (effect.contains("MAXHP_INCREASE:")) {
                                                i = i + Integer.parseInt(effect1[1]);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if ((p.getInventory().getChestplate() != null)
                        && (p.getInventory().getChestplate().getItemMeta().getLore() != null)) {
                    for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
                        if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("EQUIPT")) {
                            for (String i1 : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                                if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i1)), p.getInventory().getChestplate())) {
                                        List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i1 + ".effects");
                                        for (String effect : effects) {
                                            String[] effect1 = effect.split(":");
                                            if (effect.contains("MAXHP_INCREASE:")) {
                                                i = i + Integer.parseInt(effect1[1]);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if ((p.getInventory().getHelmet() != null)
                        && (p.getInventory().getHelmet().getItemMeta().getLore() != null)) {
                    for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
                        if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("EQUIPT")) {
                            for (String i1 : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                                if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i1)), p.getInventory().getHelmet())) {
                                        List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i1 + ".effects");
                                        for (String effect : effects) {
                                            String[] effect1 = effect.split(":");
                                            if (effect.contains("MAXHP_INCREASE:")) {
                                                i = i + Integer.parseInt(effect1[1]);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (p.getMaxHealth() != i) {
                    p.setMaxHealth(i);
                }
            }
        }, 0L, 200L);
    }

    private void registerPaticle() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (!Particle.isEmpty() && Particle != null) {
                for (Projectile arrow : Particle.keySet()) {
                    if (!arrow.isDead() && !arrow.isOnGround()) {
                        utils.sendParticle(arrow.getLocation(), Particle.get(arrow));
                    }
                }
            }
        }, 0L, 10L);
    }


    private void registerPaticleEffects() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (!ParticleEffects.isEmpty() && ParticleEffects != null) {
                for (Player p : ParticleEffects.keySet()) {
                    if (ParticleEffects.get(p).equalsIgnoreCase("FROSTY")) {
                        Frosty_Particle.getInstance().display(p);
                    }
                }
            }
        }, 0L, 5L);
    }

    private void registerExplode() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (!Explode.isEmpty() && Explode != null) {
                for (Projectile arrow : Explode) {
                    if (arrow.isDead() || arrow.isOnGround()) {
                        arrow.getWorld().createExplosion(arrow.getLocation().getX(), arrow.getLocation().getY(),
                                arrow.getLocation().getZ(), 5.0F, false, false);
                        Explode.remove(arrow);
                        return;
                    }
                }
            }
        }, 0L, 2L);
    }

    private void registerTerritory() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (Bukkit.getOnlinePlayers() != null) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
                        if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("EQUIPT")) {
                            for (String i : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                                if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getItemInHand()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getHelmet()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getChestplate()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getLeggings()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getBoots())) {
                                    if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                        List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                        for (String effect : effects) {
                                            if (effect.toUpperCase().contains("FLY")) {
                                                if (fly.contains(p.getName())) {
                                                    if (!Support.inTerritory(p)) {
                                                        fly.remove(p.getName());
                                                        p.setAllowFlight(false);
                                                        p.setFlying(false);
                                                    }

                                                } else {
                                                    if (Support.inTerritory(p) && Support.hasfaction(p)) {
                                                        fly.add(p.getName());
                                                        p.setAllowFlight(true);
                                                        p.setFlying(true);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }, 0L, 20L);
    }

    private void registerSnow(JavaPlugin plugin) {

        final Timer timer = new Timer(true); // We use a timer cause the Bukkit scheduler is affected by server lags
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!plugin.isEnabled()) { // Plugin was disabled
                    timer.cancel();
                    return;
                }
                // Nevertheless we want our code to run in the Bukkit main thread, so we have to use the Bukkit scheduler
                Bukkit.getScheduler().runTask(plugin, () -> {
                    for (String player : snow) {
                        Player p = Bukkit.getPlayer(player);
                        if (p != null) {
                            Location loc = p.getLocation().add(0.0D, -1.0D, 0.0D);
                            List<Block> blocks = getSquare(loc, p);
                            for (final Block block : blocks)
                                if ((block.getType() != Material.AIR) && (block.getType() != Material.SNOW)
                                        && (block.getType() != Material.ICE) && (block.getType() != null)) {
                                    Location l = block.getLocation().clone().add(0.0D, 1.0D, 0.0D);
                                    if (block.isLiquid()) {
                                        if ((block.getType() != Material.LAVA)
                                                && (block.getType() != Material.STATIONARY_LAVA)) {
                                            if ((Support.allowsBreak(block.getLocation(), p) && Support.canBreakBlock(p, block))) {
                                                addblock(block, Material.PACKED_ICE, true);
                                            }
                                        }
                                    } else {
                                        if ((Support.allowsBreak(block.getLocation(), p) && Support.canBreakBlock(p, block))) {
                                            Location a = l.clone().add(0.0D, -1.0D, 0.0D);
                                            if (a.getBlock().getType() != Material.SNOW
                                                    && a.getBlock().getType() != Material.AIR
                                                    && a.getBlock().getType() != Material.WATER
                                                    && a.getBlock().getType() != Material.ICE
                                                    && a.getBlock().getType() != Material.PACKED_ICE
                                                    && a.getBlock().getType() != Material.YELLOW_FLOWER
                                                    && a.getBlock().getType() != Material.DEAD_BUSH
                                                    && a.getBlock().getType() != Material.LONG_GRASS
                                                    && !a.getBlock().getType().name().contains("SLAB")
                                                    && !a.getBlock().getType().name().contains("STAIRS")
                                                    && a.getBlock().getType() != Material.SAPLING
                                                    && a.getBlock().getType() != Material.WEB
                                                    && a.getBlock().getType() != Material.RED_ROSE
                                                    && a.getBlock().getType() != Material.TORCH
                                                    && a.getBlock().getType() != Material.CARPET
                                                    && a.getBlock().getType() != Material.VINE
                                                    && Support.allowsBreak(a, p)
                                                    && Support.canBreakBlock(p, a.getBlock())) {
                                                addblock(l.getBlock(), Material.SNOW, true);
                                            }
                                        }
                                    }

                                }
                        }
                    }
                });
            }
        }, 1000, 100);
    }


    private List<Block> getSquare(Location loc, Player p) {
        List<Block> blocks = com.google.common.collect.Lists.newArrayList();
        for (int x = 2 * -1; x <= 2; x++) {
            for (int y = 2 * -1; y <= 2; y++) {
                for (int z = 2 * -1; z <= 2; z++) {
                    Block b = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                    if (Support.canBreakBlock(p, b)) {
                        Location location = b.getLocation().add(0.0D, 1.0D, 0.0D);
                        if ((location.getBlock().getType().equals(Material.AIR))
                                && (!b.getType().equals(Material.AIR))) {
                            blocks.add(b);
                        }
                    }
                }
            }
        }
        return blocks;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.SHIFT_CLICK, ArmorType.BOOTS, p.getInventory().getBoots(), null));
        Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.SHIFT_CLICK, ArmorType.LEGGINGS, p.getInventory().getLeggings(), null));
        Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.SHIFT_CLICK, ArmorType.CHESTPLATE, p.getInventory().getChestplate(), null));
        Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.SHIFT_CLICK, ArmorType.HELMET, p.getInventory().getHelmet(), null));
    }

    private void Inventory(JavaPlugin plugin) {
        final Timer timer = new Timer(true); // We use a timer cause the Bukkit scheduler is affected by server lags
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!plugin.isEnabled()) { // Plugin was disabled
                    timer.cancel();
                    return;
                }
                // Nevertheless we want our code to run in the Bukkit main thread, so we have to use the Bukkit scheduler
                // Don't be afraid! The connection to the bStats server is still async, only the stats collection is sync ;)
                Bukkit.getScheduler().runTask(plugin, () -> {
                    if (!FuryTime.isEmpty()) {
                        for (String player : FuryTime.keySet()) {
                            if (FuryTime.get(player) == 1) {
                                FuryTime.remove(player);
                            } else {
                                FuryTime.put(player, FuryTime.get(player) - 1);
                            }
                        }

                    }
                    if (Bukkit.getOnlinePlayers() == null)
                        return;
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        for (int i = 0; i <= p.getOpenInventory().getTopInventory().getSize() - 1; i++) {

                            if (p.getOpenInventory().getTopInventory() != null) {
                                if (p.getOpenInventory().getTopInventory().getItem(i) != null) {
                                    if (p.getOpenInventory().getTitle().equals(utils.getTitle())) {
                                        if (p.getOpenInventory().getTopInventory() != null) {
                                            if (p.getOpenInventory().getTopInventory().getItem(i).getData() != null) {
                                                if (p.getOpenInventory().getTopInventory().getItem(i).getData().toString()
                                                        .equals(utils.makeItem(Material.STAINED_GLASS_PANE, 1, 9, " ")
                                                                .getData().toString())) {
                                                    p.getOpenInventory().getTopInventory().setItem(i,
                                                            utils.makeItem(Material.STAINED_GLASS_PANE, 1, 10, " "));

                                                } else if (p.getOpenInventory().getTopInventory().getItem(i).getData()
                                                        .toString()
                                                        .equals(utils.makeItem(Material.STAINED_GLASS_PANE, 1, 10, " ")
                                                                .getData().toString())) {
                                                    p.getOpenInventory().getTopInventory().setItem(i,
                                                            utils.makeItem(Material.STAINED_GLASS_PANE, 1, 9, " "));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }, 0L, 1000L);
    }

    private void Inventory1(JavaPlugin plugin) {
        final Timer timer = new Timer(true); // We use a timer cause the Bukkit scheduler is affected by server lags
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!plugin.isEnabled()) { // Plugin was disabled
                    timer.cancel();
                    return;
                }
                // Nevertheless we want our code to run in the Bukkit main thread, so we have to use the Bukkit scheduler
                // Don't be afraid! The connection to the bStats server is still async, only the stats collection is sync ;)
                Bukkit.getScheduler().runTask(plugin, () -> {
                    if (Bukkit.getOnlinePlayers() == null)
                        return;
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        for (int i = 0; i <= p.getOpenInventory().getTopInventory().getSize() - 1; i++) {
                            if (p.getOpenInventory().getTopInventory() != null) {
                                if (p.getOpenInventory().getTopInventory().getItem(i) != null) {
                                    if (p.getOpenInventory().getTitle().equals(utils.getTitle())) {
                                        if (p.getOpenInventory().getTopInventory() != null) {
                                            if (p.getOpenInventory().getTopInventory().getItem(i).getData() != null) {
                                                if (p.getOpenInventory().getTopInventory().getItem(i).getData().toString()
                                                        .equals(utils.makeItem(Material.STAINED_GLASS_PANE, 1, 3, " ")
                                                                .getData().toString())) {
                                                    p.getOpenInventory().getTopInventory().setItem(i,
                                                            utils.makeItem(Material.STAINED_GLASS_PANE, 1, 11, " "));

                                                } else if (p.getOpenInventory().getTopInventory().getItem(i).getData()
                                                        .toString()
                                                        .equals(utils.makeItem(Material.STAINED_GLASS_PANE, 1, 11, " ")
                                                                .getData().toString())) {
                                                    p.getOpenInventory().getTopInventory().setItem(i,
                                                            utils.makeItem(Material.STAINED_GLASS_PANE, 1, 3, " "));
                                                }

                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                });
            }
        }, 0L, 500L);
    }

    private void GkitsCooldown() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (String name : Gkits.keySet()) {
                if (Gkits.get(name) == 1) {
                    Gkits.remove(name);
                } else {
                    Gkits.put(name, Gkits.get(name) - 1);
                }
            }
//            for(String name : Abilities.keySet()){
//                if (Abilities.get(name) == 1) {
//                    Abilities.remove(name);
//                }
//                Abilities.put(name, Abilities.get(name) - 1);
//            }
        }, 0L, 20L);
    }

    private void registerItemHold() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p != null) {
                        for (String Enchant : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments").getKeys(false)) {
                            if (ConfigManager.getInstance().getCustomEncants().getString("Enchantments." + Enchant + ".Trigger").equalsIgnoreCase("ITEM_HOLD")) {
                                for (String i : ConfigManager.getInstance().getCustomEncants().getConfigurationSection("Enchantments." + Enchant + ".levels").getKeys(false)) {
                                    if (utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getItemInHand()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getHelmet()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getChestplate()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getLeggings()) || utils.hasenchant(Enchant + " " + utils.convertPower(Integer.parseInt(i)), p.getInventory().getBoots())) {
                                        if (ConfigManager.getInstance().getCustomEncants().getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                            List<String> effects = ConfigManager.getInstance().getCustomEncants().getStringList("Enchantments." + Enchant + ".levels." + i + ".effects");
                                            for (String effect : effects) {
                                                String[] effect1 = effect.split(":");
                                                if (effect.contains("POTION:")) {
                                                    if (PotionEffectType.getByName(effect1[1].toUpperCase()) != null) {
                                                        if (p.hasPotionEffect(PotionEffectType.getByName(effect1[1]))) {
                                                            p.removePotionEffect(PotionEffectType.getByName(effect1[1]));
                                                        }
                                                        p.addPotionEffect(
                                                                new PotionEffect(PotionEffectType.getByName(effect1[1].toUpperCase()), 10 * 20, -1 + Integer.parseInt(effect1[2]), false));
                                                    } else {
                                                        System.out.print("Unknown Potion effect " + effect1[1].toUpperCase());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }, 0L, 60L);
    }
}
