package me.thedogsito.je.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageUtil {
    public static String GetColoredMessages(String message) {
        if (message != null) {
            return ChatColor.translateAlternateColorCodes('&', message);
        }
        return "";
    }
}