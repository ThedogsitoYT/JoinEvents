package me.thedogsito.je.listeners;

import me.thedogsito.je.Main;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;
import sun.net.www.content.text.plain;

public class TeleportHub implements Listener {
    private final Main plugin;

    public TeleportHub(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerDeathEvent e) {
        FileConfiguration config = plugin.getConfig();
        Player p = e.getEntity().getPlayer();

        if (config.contains("Config.Commands.Hub.X")) {
            Integer WaitingSeconds = plugin.getMainConfigManager().getHubTeleportingSeconds();

            p.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getHubTeleporting())
                    .replace("%wait%", String.valueOf(WaitingSeconds)));

            double x = Double.valueOf(config.getString("Config.Commands.Hub.X").replace(',', '.')).doubleValue();
            double y = Double.valueOf(config.getString("Config.Commands.Hub.Y").replace(',', '.')).doubleValue();
            double z = Double.valueOf(config.getString("Config.Commands.Hub.Z").replace(',', '.')).doubleValue();
            float yaw = Float.valueOf(config.getString("Config.Commands.Hub.Yaw").replace(',', '.')).floatValue();
            float pitch = Float.valueOf(config.getString("Config.Commands.Hub.Pitch").replace(',', '.')).floatValue();

            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            scheduler.runTaskLater(plugin, () -> {
                World world = this.plugin.getServer().getWorld(config.getString("Config.Commands.Hub.World"));
                Location l = new Location(world, x, y, z, yaw, pitch);
                p.teleport(l);
            }, WaitingSeconds * 20L);
        }
    }
}
