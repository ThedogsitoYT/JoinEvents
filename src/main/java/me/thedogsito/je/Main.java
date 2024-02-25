package me.thedogsito.je;

import me.thedogsito.je.commands.*;
import me.thedogsito.je.config.MainConfigManager;
import me.thedogsito.je.listeners.*;
import me.thedogsito.je.utils.MessageUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main extends JavaPlugin {
    public static String prefix = "&3&l[&b&lJoinEvents&3&l] ";
    public String version = getDescription().getVersion();
    public String latestversion;
    private MainConfigManager mainConfigManager;
    private FileConfiguration items = null;
    private File itemsFile = null;

    public void onEnable() {
        mainConfigManager = new MainConfigManager(this);
        Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                prefix + "&f&lHas been enabled &3&l(&b&lVersion: " + version + "&3&l)", null));
        updateChecker();
        registerEvents();
        registerCommands();
        registerItems();
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                prefix + "&f&lGood bay :)", null));
    }

    public boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public void BStats() {
        int pluginId = 21116;
        Metrics metrics = new Metrics(this, pluginId);
    }

    public void registerCommands() {
        this.getCommand("je").setExecutor(new Je(this));
        this.getCommand("sethub").setExecutor(new SetHub(this));
        this.getCommand("delhub").setExecutor(new DelHub(this));
        this.getCommand("hub").setExecutor(new Hub(this));
        this.getCommand("setwarp").setExecutor(new SetWarp(this));
        this.getCommand("delwarp").setExecutor(new DelWarp(this));
        this.getCommand("warplist").setExecutor(new WarpList(this));
        this.getCommand("warp").setExecutor(new Warp(this));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new JoinMessageListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinTitlesListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinActionBarListener(this), this);
        getServer().getPluginManager().registerEvents(new FireworksJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new TextPermissionsListener(this), this);
        getServer().getPluginManager().registerEvents(new TeleportHub(this), this);
        getServer().getPluginManager().registerEvents(new IpProtect(this), this);
        getServer().getPluginManager().registerEvents(new CustomItems(this), this);
        getServer().getPluginManager().registerEvents(new DropOrMoveInInventory(this), this);
    }

    public FileConfiguration getItems() {
        if (this.items == null)
            reloadItems();
        return this.items;
    }

    public void reloadItems() {
        if (this.items == null)
            this.itemsFile = new File(getDataFolder(), "items.yml");
        this.items = (FileConfiguration)YamlConfiguration.loadConfiguration(this.itemsFile);
        try {
            Reader defConfigStream = new InputStreamReader(getResource("items.yml"), "UTF8");
            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                this.items.setDefaults((Configuration)defConfig);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void saveItems() {
        try {
            this.items.save(this.itemsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerItems() {
        this.itemsFile = new File(getDataFolder(), "items.yml");
        if (!this.itemsFile.exists()) {
            getItems().options().copyDefaults(true);
            saveItems();
        }
    }

    public void updateChecker() {
        try {
            URL url = new URL("https://raw.githubusercontent.com/ThedogsitoYT/JoinEvents/master/VersionController");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(1250);
            con.setReadTimeout(1250);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                latestversion = reader.readLine();
                if (latestversion != null && !getVersion().equals(latestversion)) {

                    Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                            "&b&lJoinEvents &3&l>> &c&lThere is a new version available.", null));
                    Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                            "&b&lJoinEvents &3&l>> &c&lYou can download it at: &b&lhttps://hangar.papermc.io/ThedogsitoYT/JoinEvents/versions", null));
                }
            }
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                    "&b&lJoinEvents &3&l>> &c&lError while checking for updates.", null));
        }
    }

    public String getVersion(){
        return this.version;
    }

    public String getLatestversion(){
        return this.latestversion;
    }

    public MainConfigManager getMainConfigManager() {
        return mainConfigManager;
    }
}
