package me.thedogsito.je.commands;

import com.google.common.util.concurrent.AbstractScheduledService;
import me.thedogsito.je.Main;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Warp implements CommandExecutor {
    private Main plugin;
    public Warp(Main plugin) {
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

        if(!p.hasPermission("je.warp" + "je.*")) {
            sender.sendMessage(MessageUtil.GetColoredMessages(
                    plugin.getMainConfigManager().getNotPermission()
                            .replace("%player%", p.getName())));
            return true;
        }

        if (!(args.length >= 1)) {
            sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.NotExistingWarp")
                    .replace("%prefix%", config.getString("Config.Prefix"))));
            return true;
        } else if (args.length >= 1) {
            String CodeName = args[0].toLowerCase();
            String Name = config.getString("Config.Commands.Warps." + CodeName + ".Name");
            if (config.contains("Config.Commands.Warps." + CodeName + ".X")) {
                Integer WaitingSeconds = plugin.getMainConfigManager().getWarpTeleportingSeconds();

                sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getWarpTeleporting()
                        .replace("%wait%", String.valueOf(WaitingSeconds))
                        .replace("%name%", Name)));

                double x = Double.valueOf(config.getString("Config.Commands.Warps." + CodeName + ".X").replace(',', '.')).doubleValue();
                double y = Double.valueOf(config.getString("Config.Commands.Warps." + CodeName + ".Y").replace(',', '.')).doubleValue();
                double z = Double.valueOf(config.getString("Config.Commands.Warps." + CodeName + ".Z").replace(',', '.')).doubleValue();
                float yaw = Float.valueOf(config.getString("Config.Commands.Warps." + CodeName + ".Yaw").replace(',', '.')).floatValue();
                float pitch = Float.valueOf(config.getString("Config.Commands.Warps." + CodeName + ".Pitch").replace(',', '.')).floatValue();

                BukkitScheduler scheduler = plugin.getServer().getScheduler();
                scheduler.runTaskLater(plugin, () -> {
                    World world = this.plugin.getServer().getWorld(config.getString("Config.Commands.Warps." + CodeName + ".World"));
                    Location l = new Location(world, x, y, z, yaw, pitch);
                    p.teleport(l);
                    sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getWarpTeleported()
                            .replace("%name%", Name)));
                }, WaitingSeconds * 20L);
            }else {
                sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getNotExistingHub()));
            }
        }
        return true;
    }
}

