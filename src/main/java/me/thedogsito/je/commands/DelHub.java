package me.thedogsito.je.commands;

import me.thedogsito.je.Main;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DelHub implements CommandExecutor {
    private final Main plugin;

    public DelHub(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            FileConfiguration config = plugin.getConfig();
            Player p = (Player) sender;

            if (!p.hasPermission("je.delhub" + "je.*")) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getNotPermission()
                                .replace("%player%", p.getName())));
                return true;
            }

            if (config.contains("Config.Commands.Hub.X")) {
                config.set("Config.Commands.Hub.X", null);
                config.set("Config.Commands.Hub.Y", null);
                config.set("Config.Commands.Hub.Z", null);
                config.set("Config.Commands.Hub.Yaw", null);
                config.set("Config.Commands.Hub.Pitch", null);
                config.set("Config.Commands.Hub.World", null);

                sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.DelHub")
                        .replace("%prefix%", plugin.getMainConfigManager().getPrefix())));
                plugin.saveConfig();
                return true;
            } else {
                sender.sendMessage(MessageUtil.GetColoredMessages(config.getString(plugin.getMainConfigManager().getNotExistingHub()
                        .replace("%prefix%", plugin.getMainConfigManager().getPrefix()))));
                return true;
            }
        }

        FileConfiguration config = plugin.getConfig();
        Player p = (Player) sender;

        if (!p.hasPermission("je.setwarp" + "je.*")) {
            sender.sendMessage(MessageUtil.GetColoredMessages(
                    plugin.getMainConfigManager().getNotPermission()
                            .replace("%player%", p.getName())));
            return true;
        }

        if (config.contains("Config.Commands.Hub.X")) {
            config.set("Config.Commands.Hub.X", null);
            config.set("Config.Commands.Hub.Y", null);
            config.set("Config.Commands.Hub.Z", null);
            config.set("Config.Commands.Hub.Yaw", null);
            config.set("Config.Commands.Hub.Pitch", null);
            config.set("Config.Commands.Hub.World", null);

            sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.DelHub")
                    .replace("%prefix%", plugin.getMainConfigManager().getPrefix())));
            plugin.saveConfig();
            return true;
        } else {
            sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getNotExistingHub()));
            return true;
        }
    }
}
