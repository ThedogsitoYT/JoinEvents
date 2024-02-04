package me.thedogsito.je.commands;

import me.thedogsito.je.Main;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class SetWarp implements CommandExecutor {
    private final Main plugin;

    public SetWarp(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l[&b&lJoinEvents&3&l] &c&lThis command not support on console"));
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
            sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpError")
                    .replace("%prefix%", config.getString("Config.Prefix"))));
            return true;
        } else if (args.length >= 1) {
            String Name = args[0];

            Location l = p.getLocation();
            double X = l.getX();
            double Y = l.getY();
            double Z = l.getZ();
            float Yaw = l.getYaw();
            float Pitch = l.getPitch();

            DecimalFormat f = new DecimalFormat("#.###");

            String x = String.valueOf(f.format(X));
            String y = String.valueOf(f.format(Y));
            String z = String.valueOf(f.format(Z));
            String yaw = String.valueOf(f.format(Yaw));
            String pitch = String.valueOf(f.format(Pitch));
            String world = l.getWorld().getName();

            config.set("Config.Commands.Warps." + Name + ".X", x);
            config.set("Config.Commands.Warps." + Name + ".Y", y);
            config.set("Config.Commands.Warps." + Name + ".Z", z);
            config.set("Config.Commands.Warps." + Name + ".Yaw", yaw);
            config.set("Config.Commands.Warps." + Name + ".Pitch", pitch);
            config.set("Config.Commands.Warps." + Name + ".World", world);

            sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getSetWarp()
                    .replace("%prefix%", plugin.getMainConfigManager().getPrefix())
                    .replace("%cords%", x + " " + y + " " + z)
                    .replace("%name%", Name)));
            plugin.saveConfig();
            return true;
        }
        return true;
    }
}
