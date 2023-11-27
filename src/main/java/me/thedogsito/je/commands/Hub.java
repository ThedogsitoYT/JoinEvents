package me.thedogsito.je.commands;

import me.thedogsito.je.Main;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Hub implements CommandExecutor {
    private Main plugin;
    public Hub(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l[&b&lJoinEvents&3&l] &c&lThis command not support on console"));
            return true;
        }
        FileConfiguration config = plugin.getConfig();
        Player p = (Player)sender;

        if(!p.hasPermission("je.hub" + "je.*")) {
            sender.sendMessage(MessageUtil.GetColoredMessages(
                    plugin.getMainConfigManager().getNotPermission()
                            .replace("%player%", p.getName())));
            return true;
        }

        if (config.contains("Config.Commands.Hub.X")) {
            double x = Double.valueOf(config.getString("Config.Commands.Hub.X")).doubleValue();
            double y = Double.valueOf(config.getString("Config.Commands.Hub.Y")).doubleValue();
            double z = Double.valueOf(config.getString("Config.Commands.Hub.Z")).doubleValue();
            float yaw = Float.valueOf(config.getString("Config.Commands.Yaw")).floatValue();
            float pitch = Float.valueOf(config.getString("Config.Commands.Hub.Pitch")).floatValue();

            World world = this.plugin.getServer().getWorld(config.getString("Lobby.world"));
            Location l = new Location(world, x, y, z, yaw, pitch);
            p.teleport(l);
        }
        return true;
    }
}
