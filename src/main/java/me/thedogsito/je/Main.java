package me.thedogsito.je;

import me.thedogsito.je.commands.Je;
import me.thedogsito.je.config.MainConfigManager;
import me.thedogsito.je.listeners.JoinMessageListener;
import me.thedogsito.je.listeners.JoinTitlesListener;
import me.thedogsito.je.listeners.TextPermissionsListener;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static String prefix = "&3&l[&b&lJoinEvents&3&l] ";
    public String version = getDescription().getVersion();
    private MainConfigManager mainConfigManager;

    public void onEnable() {
        mainConfigManager = new MainConfigManager(this);
        registerEvents();
        registerCommands();
        Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                prefix + "&b&lhas been enabled &3&l(&b&lVersion: " + version + "&3&l)"));
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                prefix + "&c&lhas been disabled"));
    }

    public void registerCommands() {
        this.getCommand("je").setExecutor(new Je(this));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new JoinMessageListener(this), this);
        getServer().getPluginManager().registerEvents(new JeoinTitlesListener(this), this);
        getServer().getPluginManager().registerEvents(new TextPermissionsListener(this), this);
    }

    public MainConfigManager getMainConfigManager() {
        return mainConfigManager;
    }
}
