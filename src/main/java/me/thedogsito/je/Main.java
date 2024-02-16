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
        registerEvents();
        registerCommands();
        updateChecker();
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                prefix + "&f&lGood bay :)"));
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

    public void updateChecker(){
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(
                    "https://raw.githubusercontent.com/ThedogsitoYT/JoinEvents/master/VersionController").openConnection();
            int timed_out = 1250;
            con.setConnectTimeout(timed_out);
            con.setReadTimeout(timed_out);
            latestversion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if (latestversion.length() <= 7) {
                if(!version.equals(latestversion)){
                    Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                            "&b&lJoinEvents &3&l>> &c&lThere is a new version available."));
                    Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                            "&b&lJoinEvents &3&l>> &b&lYou can dowload in: https://hangar.papermc.io/ThedogsitoYT/JoinEvents/versions"));
                }
            }
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                    "&b&lJoinEvents &3&l>> &c&lError while checking update."));
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
