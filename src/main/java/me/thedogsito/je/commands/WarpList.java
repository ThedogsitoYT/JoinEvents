package me.thedogsito.je.commands;

import me.thedogsito.je.Main;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.Set;

public class WarpList implements CommandExecutor {
    private final Main plugin;

    public WarpList(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (!p.hasPermission("je.listwarp" + "je.*")) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getNotPermission()
                                .replace("%player%", p.getName())));
                return true;
            }
        }
        FileConfiguration config = plugin.getConfig();

        ConfigurationSection warpsSection = config.getConfigurationSection("Config.Commands.Warps");

        if (warpsSection != null) {
            Set<String> warpNames = warpsSection.getKeys(false);

            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l-----------------"));
            sender.sendMessage(MessageUtil.GetColoredMessages("&b&l        Warps"));
            for (String warpName : warpNames) {
                String path = "Config.Commands.Warps." + warpName + ".Name";
                String warpDisplayName = config.getString(path);

                sender.sendMessage(MessageUtil.GetColoredMessages(warpDisplayName));
            }
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l-----------------"));
        } else {
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l-----------------"));
            sender.sendMessage(MessageUtil.GetColoredMessages("&c&lNot exist Warps"));
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l-----------------"));
        }
        return true;
    }
}
