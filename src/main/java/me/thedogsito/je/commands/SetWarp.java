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
import java.util.Formatter;
import java.util.regex.Pattern;

public class SetWarp implements CommandExecutor {
    private final Main plugin;

    public SetWarp(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l[&b&lJoinEvents&3&l] &c&lThis command not support on console", null));
            return true;
        }
        FileConfiguration config = plugin.getConfig();
        Player p = (Player) sender;

        if (!p.hasPermission("je.setwarp") && !p.hasPermission("je.*")) {
            sender.sendMessage(MessageUtil.GetColoredMessages(
                    plugin.getMainConfigManager().getNotPermission()
                            .replace("%player%", p.getName()), p));
            return true;
        }

        if (!(args.length >= 1)) {
            sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpError")
                    .replace("%prefix%", config.getString("Config.Prefix")), p));
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

            String LowerCaseName = Name.toLowerCase();
            String CodeName = MessageUtil.replaceHexColors(LowerCaseName);
            String NHCodeName = CodeName
                    .replace("&0", "")
                    .replace("&1", "")
                    .replace("&2", "")
                    .replace("&3", "")
                    .replace("&4", "")
                    .replace("&5", "")
                    .replace("&6", "")
                    .replace("&7", "")
                    .replace("&8", "")
                    .replace("&9", "")
                    .replace("&a", "")
                    .replace("&b", "")
                    .replace("&c", "")
                    .replace("&d", "")
                    .replace("&e", "")
                    .replace("&f", "")

                    .replace("&k", "")
                    .replace("&l", "")
                    .replace("&m", "")
                    .replace("&n", "")
                    .replace("&o", "")
                    .replace("&r", "");

            if (!(Name.contains("&"))) {
                config.set("Config.Commands.Warps." + NHCodeName + ".Name", "&b&l" + Name);
                config.set("Config.Commands.Warps." + NHCodeName + ".X", x);
                config.set("Config.Commands.Warps." + NHCodeName + ".Y", y);
                config.set("Config.Commands.Warps." + NHCodeName + ".Z", z);
                config.set("Config.Commands.Warps." + NHCodeName + ".Yaw", yaw);
                config.set("Config.Commands.Warps." + NHCodeName + ".Pitch", pitch);
                config.set("Config.Commands.Warps." + NHCodeName + ".World", world);
            }else {
                config.set("Config.Commands.Warps." + NHCodeName + ".Name", Name);
                config.set("Config.Commands.Warps." + NHCodeName + ".X", x);
                config.set("Config.Commands.Warps." + NHCodeName + ".Y", y);
                config.set("Config.Commands.Warps." + NHCodeName + ".Z", z);
                config.set("Config.Commands.Warps." + NHCodeName + ".Yaw", yaw);
                config.set("Config.Commands.Warps." + NHCodeName + ".Pitch", pitch);
                config.set("Config.Commands.Warps." + NHCodeName + ".World", world);
            }

            sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getSetWarp()
                    .replace("%prefix%", plugin.getMainConfigManager().getPrefix())
                    .replace("%cords%", x + " " + y + " " + z)
                    .replace("%name%", Name), p));
            plugin.saveConfig();
            return true;
        }
        return true;
    }
}
