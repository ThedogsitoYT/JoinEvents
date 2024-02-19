package me.thedogsito.je;

import me.thedogsito.je.commands.*;
import me.thedogsito.je.config.MainConfigManager;
import me.thedogsito.je.listeners.JoinMessageListener;
import me.thedogsito.je.listeners.JoinTitlesListener;
import me.thedogsito.je.listeners.TeleportHub;
import me.thedogsito.je.listeners.TextPermissionsListener;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main extends JavaPlugin {
    public static String prefix = "&3&l[&b&lJoinEvents&3&l] ";
    public String version = getDescription().getVersion();
    public String latestversion;
    private MainConfigManager mainConfigManager;

    public void onEnable() {
        mainConfigManager = new MainConfigManager(this);
        Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                prefix + "&f&lHas been enabled &3&l(&b&lVersion: " + version + "&3&l)"));
        updateChecker();
        registerEvents();
        registerCommands();
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                prefix + "&f&lGood bay :)"));
    }

    public boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
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
        getServer().getPluginManager().registerEvents(new TextPermissionsListener(this), this);
        getServer().getPluginManager().registerEvents(new TeleportHub(this), this);
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
                    // Actualización disponible
                    Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                            "&b&lJoinEvents &3&l>> &c&lThere is a new version available."));
                    Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                            "&b&lJoinEvents &3&l>> &c&lYou can download it at: &b&lhttps://hangar.papermc.io/ThedogsitoYT/JoinEvents/versions"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                    "&b&lJoinEvents &3&l>> &c&lError while checking for updates."));
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
