package me.thedogsito.je.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.logging.Level;

public class PlaceholderExpansion extends me.clip.placeholderapi.expansion.PlaceholderExpansion {
    private JavaPlugin plugin;
    private Connection connection;

    public PlaceholderExpansion(JavaPlugin plugin) {
        this.plugin = plugin;
        this.connection = connectDatabase();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "YourName";
    }

    @Override
    public String getIdentifier() {
        return "je";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }

        if (identifier.equals("playercount")) {
            Collection<? extends Player> players = Bukkit.getOnlinePlayers();
            return String.valueOf(players.size());
        }

        if (identifier.equals("lastlogin")) {
            return getLastLogin(player);
        }

        if (identifier.equals("joindate")) {
            return getJoinDate(player);
        }

        if (identifier.equals("total_playtime")) {
            return getPlaytime(player);
        }

        return null;
    }

    private String getLastLogin(Player player) {
        String lastLogin = "";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT last_login FROM player_data WHERE uuid = ?");
            statement.setString(1, player.getUniqueId().toString());
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp("last_login");
                if (timestamp != null) {
                    lastLogin = timestamp.toString();
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Error getting last login for player " + player.getName(), ex);
        } finally {
            closeResultSetAndStatement(resultSet, statement);
        }

        return lastLogin;
    }

    private String getJoinDate(Player player) {
        String joinDate = "";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT join_date FROM player_data WHERE uuid = ?");
            statement.setString(1, player.getUniqueId().toString());
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp("join_date");
                if (timestamp != null) {
                    LocalDateTime localDateTime = timestamp.toLocalDateTime();
                    joinDate = localDateTime.toLocalDate().toString();
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Error getting join date for player " + player.getName(), ex);
        } finally {
            closeResultSetAndStatement(resultSet, statement);
        }

        return joinDate;
    }

    private String getPlaytime(Player player) {
        String playtime = "";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT last_login FROM player_data WHERE uuid = ?");
            statement.setString(1, player.getUniqueId().toString());
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Timestamp lastLogin = resultSet.getTimestamp("last_login");
                LocalDateTime loginTime = lastLogin.toLocalDateTime();

                LocalDateTime currentTime = LocalDateTime.now();

                Duration duration = Duration.between(loginTime, currentTime);
                long hours = duration.toHours();
                long minutes = duration.minusHours(hours).toMinutes();

                playtime = String.format("%02d:%02d", hours, minutes);
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Error getting playtime for player " + player.getName(), ex);
        } finally {
            closeResultSetAndStatement(resultSet, statement);
        }

        return playtime;
    }

    private String formatTime(long millis) {
        Duration duration = Duration.ofMillis(millis);
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private Connection connectDatabase() {
        Connection conn = null;
        try {
            File dataFolder = plugin.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }
            String dbUrl = "jdbc:sqlite:" + dataFolder.getAbsolutePath() + File.separator + "players.db";
            conn = DriverManager.getConnection(dbUrl);
            try (PreparedStatement statement = conn.prepareStatement("CREATE TABLE IF NOT EXISTS player_data (uuid TEXT PRIMARY KEY, join_date TIMESTAMP, last_login TIMESTAMP)")) {
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Error connecting to database", ex);
        }
        return conn;
    }

    private void closeResultSetAndStatement(ResultSet resultSet, PreparedStatement statement) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.WARNING, "Error closing ResultSet", ex);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.WARNING, "Error closing PreparedStatement", ex);
            }
        }
    }
}