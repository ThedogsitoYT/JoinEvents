package me.thedogsito.je.commands;

import me.thedogsito.je.Main;
import me.thedogsito.je.utils.MessageUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WarpList implements CommandExecutor {
    private final Main plugin;

    public WarpList(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            FileConfiguration config = plugin.getConfig();
            ConfigurationSection warpsSection = config.getConfigurationSection("Config.Commands.Warps");

            if (warpsSection != null) {
                Set<String> warpNames = warpsSection.getKeys(false);

                sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpListLineOne"), null));
                sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpListUpText"), null));
                for (String warpName : warpNames) {
                    String path = "Config.Commands.Warps." + warpName + ".Name";
                    String warpDisplayName = config.getString(path);

                    sender.sendMessage(MessageUtil.GetColoredMessages(warpDisplayName, null));
                }
                sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpListLineTwo"), null));
            }
        }

        Player p = (Player) sender;

        if (!p.hasPermission("je.warplist") || !p.hasPermission("je.*")) {
            p.sendMessage(MessageUtil.GetColoredMessages(
                    plugin.getMainConfigManager().getNotPermission()
                            .replace("%player%", p.getName()), p));
            return true;
        }

        FileConfiguration config = plugin.getConfig();

        ConfigurationSection warpsSection = config.getConfigurationSection("Config.Commands.Warps");

        if (warpsSection != null) {
            Set<String> warpNames = warpsSection.getKeys(false);

            Boolean V1_16 = Bukkit.getServer().getVersion().contains("1.16");
            Boolean V1_17 = Bukkit.getServer().getVersion().contains("1.17");
            Boolean V1_18 = Bukkit.getServer().getVersion().contains("1.18");
            Boolean V1_19 = Bukkit.getServer().getVersion().contains("1.19");
            Boolean V1_20 = Bukkit.getServer().getVersion().contains("1.20");

            p.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpListLineOne"), null));
            p.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpListUpText"), null));
            p.sendMessage(MessageUtil.GetColoredMessages("           ", null));
            for (String warpName : warpNames) {
                String path = "Config.Commands.Warps." + warpName + ".Name";
                String warpDisplayName = config.getString(path);

                if (warpDisplayName != null) {
                    p.sendMessage(MessageUtil.GetColoredMessages(warpDisplayName, p));
                }
            }
        }
        p.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpListLineTwo"), null));
        return false;
    }
}