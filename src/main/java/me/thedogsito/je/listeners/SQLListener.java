package me.thedogsito.je.listeners;

import me.thedogsito.je.Main;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SQLListener implements Listener {

    private Main plugin;
    private Connection connection;

    public SQLListener(Main plugin, Connection connection) {
        this.plugin = plugin;
        this.connection = connection;
    }

    private Map<UUID, LocalDateTime> loginTimes = new HashMap<>();

    @EventHandler
    public void JoinTime(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        LocalDateTime now = LocalDateTime.now();
        loginTimes.put(player.getUniqueId(), now);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT OR IGNORE INTO player_data (uuid, join_date, last_login) VALUES (?, ?, ?)");
            statement.setString(1, player.getUniqueId().toString());
            statement.setTimestamp(2, currentTime);
            statement.setTimestamp(3, currentTime);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException ex) {
        }
    }
}

