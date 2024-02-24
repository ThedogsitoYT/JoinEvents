package me.thedogsito.je.commands;

import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
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

public class Hub implements CommandExecutor {
    private Main plugin;
    public Hub(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l[&b&lJoinEvents&3&l] &c&lThis command not support on console", null));
            return true;
        }
        FileConfiguration config = plugin.getConfig();
        Player p = (Player)sender;

        if (!p.hasPermission("je.hub") && !p.hasPermission("je.*")) {
            sender.sendMessage(MessageUtil.GetColoredMessages(
                    plugin.getMainConfigManager().getNotPermission()
                            .replace("%player%", p.getName()), p));
            return true;
        }

        if (config.contains("Config.Commands.Hub.X")) {
            Integer WaitingSeconds = plugin.getMainConfigManager().getHubTeleportingSeconds();

            sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getHubTeleporting(), p)
                    .replace("%wait%", String.valueOf(WaitingSeconds)));

            double x = Double.valueOf(config.getString("Config.Commands.Hub.X").replace(',', '.')).doubleValue();
            double y = Double.valueOf(config.getString("Config.Commands.Hub.Y").replace(',', '.')).doubleValue();
            double z = Double.valueOf(config.getString("Config.Commands.Hub.Z").replace(',', '.')).doubleValue();
            float yaw = Float.valueOf(config.getString("Config.Commands.Hub.Yaw").replace(',', '.')).floatValue();
            float pitch = Float.valueOf(config.getString("Config.Commands.Hub.Pitch").replace(',', '.')).floatValue();

            if (plugin.isFolia()) {
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                RegionScheduler scheduler = plugin.getServer().getRegionScheduler();
                World world = plugin.getServer().getWorld(config.getString("Config.Commands.Hub.World"));
                Location l = new Location(world, x, y, z, yaw, pitch);
                executor.schedule(() -> {
                    scheduler.execute(plugin, l, () -> {
                        p.teleportAsync(l).thenRunAsync(() -> {
                            sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getHubTeleported(), p));
                        });
                    });
                }, WaitingSeconds, TimeUnit.SECONDS);
                return true;
            }

            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            scheduler.runTaskLater(plugin, () -> {
                World world = this.plugin.getServer().getWorld(config.getString("Config.Commands.Hub.World"));
                Location l = new Location(world, x, y, z, yaw, pitch);
                p.teleport(l);
                sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getHubTeleported(), p));
            }, WaitingSeconds * 20L);
        }else {
            sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getNotExistingHub(), p));
        }
        return true;
    }
}
