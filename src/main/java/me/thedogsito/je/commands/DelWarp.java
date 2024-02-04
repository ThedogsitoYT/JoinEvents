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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DelWarp implements CommandExecutor {
    private final Main plugin;

    public DelWarp(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            FileConfiguration config = plugin.getConfig();
            if (!(args.length >= 1)) {
                sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpError2")
                        .replace("%prefix%", config.getString("Config.Prefix"))));
                return true;
            } else if (args.length >= 1) {
                String Name = args[0];

                if (config.contains("Config.Commands.Warps." + Name)) {
                    config.set("Config.Commands.Warps." + Name, null);
                    config.set("Config.Commands.Warps." + Name + ".X", null);
                    config.set("Config.Commands.Warps." + Name + ".Y", null);
                    config.set("Config.Commands.Warps." + Name + ".Z", null);
                    config.set("Config.Commands.Warps." + Name + ".Yaw", null);
                    config.set("Config.Commands.Warps." + Name + ".Pitch", null);
                    config.set("Config.Commands.Warps." + Name + ".World", null);

                    sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getDelWarp()
                            .replace("%prefix%", plugin.getMainConfigManager().getPrefix())
                            .replace("%name%", Name)));
                    plugin.saveConfig();
                    return true;
                } else {
                    sender.sendMessage("No existe XDD");
                    return true;
                }
            }
            return true;
        }

        FileConfiguration config = plugin.getConfig();
        Player p = (Player) sender;

        if (!p.hasPermission("je.setwarp" + "je.*")) {
            sender.sendMessage(MessageUtil.GetColoredMessages(
                    plugin.getMainConfigManager().getNotPermission()
                            .replace("%player%", p.getName())));
            return true;
        }

        if (!(args.length >= 1)) {
            sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpError2")
                    .replace("%prefix%", config.getString("Config.Prefix"))));
            return true;
        } else if (args.length >= 1) {
            String Name = args[0];

            if (config.contains("Config.Commands.Warps." + Name)) {
                config.set("Config.Commands.Warps." + Name, null);
                config.set("Config.Commands.Warps." + Name + ".X", null);
                config.set("Config.Commands.Warps." + Name + ".Y", null);
                config.set("Config.Commands.Warps." + Name + ".Z", null);
                config.set("Config.Commands.Warps." + Name + ".Yaw", null);
                config.set("Config.Commands.Warps." + Name + ".Pitch", null);
                config.set("Config.Commands.Warps." + Name + ".World", null);

                sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getDelWarp()
                        .replace("%prefix%", plugin.getMainConfigManager().getPrefix())
                        .replace("%name%", Name)));
                plugin.saveConfig();
                return true;
            }else {
                sender.sendMessage("No existe XDD");
                return true;
            }
        }
        return true;
    }
}
