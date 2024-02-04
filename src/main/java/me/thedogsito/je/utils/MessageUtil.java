package me.thedogsito.je.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageUtil {
    public static String GetColoredMessages(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}