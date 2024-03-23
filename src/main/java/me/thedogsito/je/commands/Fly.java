package me.thedogsito.je.commands;

import me.thedogsito.je.Main;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Fly implements CommandExecutor {
    private Main plugin;
    public Fly(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission("je.fly") && !sender.hasPermission("je.*")) {
            Player p = (Player)sender;
            sender.sendMessage(MessageUtil.GetColoredMessages(
                    plugin.getMainConfigManager().getNotPermission()
                            .replace("%player%", p.getName()), p));
            return true;
        }

        FileConfiguration config = plugin.getConfig();
        Player p = null;
        if (args.length == 0) {
            if (sender instanceof Player) {
                p = (Player)sender;
            }else {
                sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Fly.ErrorNotArgument")
                        .replace("%prefix%", plugin.getMainConfigManager().getPrefix()), null));
                return true;
            }
        }else {
            p = Bukkit.getPlayer(args[0]);
            if (p == null) {
                sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Fly.NotPlayerFound")
                        .replace("%target%", args[0])
                        .replace("%prefix%", plugin.getMainConfigManager().getPrefix()), p));
                return true;
            }
        }

        if (p.getAllowFlight()) {
            p.setAllowFlight(false);
            p.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Fly.Disable")
                    .replace("%prefix%", plugin.getMainConfigManager().getPrefix()), p));

            if (!p.equals(sender)) {
                sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Fly.DisableForOther")
                        .replace("%target%", p.getName())
                        .replace("%prefix%", plugin.getMainConfigManager().getPrefix()), p));
                return true;
            }
        }else {
            p.setAllowFlight(true);

            p.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Fly.Enable")
                    .replace("%prefix%", plugin.getMainConfigManager().getPrefix()), p));

            if (!p.equals(sender)) {
                sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Fly.EnableForOther")
                        .replace("%target%", p.getName())
                        .replace("%prefix%", plugin.getMainConfigManager().getPrefix()), p));
                return true;
            }
        }
        return true;
    }
}
