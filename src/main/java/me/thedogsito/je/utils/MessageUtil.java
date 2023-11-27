package me.thedogsito.je.utils;

import org.bukkit.ChatColor;

public class MessageUtil {
    public static String GetColoredMessages(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}