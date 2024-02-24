package me.thedogsito.je.commands;

import me.thedogsito.je.Main;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

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
                        .replace("%prefix%", config.getString("Config.Prefix")), null));
                return true;
            } else if (args.length >= 1) {
                String Name = args[0];

                if (config.contains("Config.Commands.Warps." + Name)) {
                    config.set("Config.Commands.Warps." + Name, null);
                    config.set("Config.Commands.Warps." + Name + ".Name", null);
                    config.set("Config.Commands.Warps." + Name + ".X", null);
                    config.set("Config.Commands.Warps." + Name + ".Y", null);
                    config.set("Config.Commands.Warps." + Name + ".Z", null);
                    config.set("Config.Commands.Warps." + Name + ".Yaw", null);
                    config.set("Config.Commands.Warps." + Name + ".Pitch", null);
                    config.set("Config.Commands.Warps." + Name + ".World", null);

                    sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getDelWarp()
                            .replace("%prefix%", plugin.getMainConfigManager().getPrefix())
                            .replace("%name%", Name), null));
                    plugin.saveConfig();
                    return true;
                } else {
                    sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getNotExistingHub(), null));
                    return true;
                }
            }
            return true;
        }

        FileConfiguration config = plugin.getConfig();
        Player p = (Player) sender;

        if (!p.hasPermission("je.delwarp") && !p.hasPermission("je.*")) {
            sender.sendMessage(MessageUtil.GetColoredMessages(
                    plugin.getMainConfigManager().getNotPermission()
                            .replace("%player%", p.getName()), p));
            return true;
        }

        if (!(args.length >= 1)) {
            sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpError2")
                    .replace("%prefix%", config.getString("Config.Prefix")), p));
            return true;
        } else if (args.length >= 1) {
            String CodeName = args[0].toLowerCase();
            String Name = config.getString("Config.Commands.Warps." + CodeName + ".Name");

            if (config.contains("Config.Commands.Warps." + CodeName)) {
                config.set("Config.Commands.Warps." + CodeName, null);
                config.set("Config.Commands.Warps." + CodeName + ".Name", null);
                config.set("Config.Commands.Warps." + CodeName + ".X", null);
                config.set("Config.Commands.Warps." + CodeName + ".Y", null);
                config.set("Config.Commands.Warps." + CodeName + ".Z", null);
                config.set("Config.Commands.Warps." + CodeName + ".Yaw", null);
                config.set("Config.Commands.Warps." + CodeName + ".Pitch", null);
                config.set("Config.Commands.Warps." + CodeName + ".World", null);

                sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getDelWarp()
                        .replace("%prefix%", plugin.getMainConfigManager().getPrefix())
                        .replace("%name%", Name), p));
                plugin.saveConfig();
                return true;
            }else {

                sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getNotExistingWarp(), p));
                return true;
            }
        }
        return true;
    }
}
