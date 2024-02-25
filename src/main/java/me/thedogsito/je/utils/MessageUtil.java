package me.thedogsito.je.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {
    public static String GetColoredMessages(String message, Player p) {
        if (Bukkit.getVersion().matches("1.(1[6-9]|2[0-0]).*")) {
            Pattern ptn = Pattern.compile("&#[a-fA-F0-9]{6}");
            Matcher m = ptn.matcher(message);

            while (m.find()) {
                String color = message.substring(m.start(), m.end());
                message = message.replace(color, ChatColor.of("#" + color.substring(2))+"");

                m = ptn.matcher(message);
            }
        }

        if (message != null) {
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                if (p != null) {
                    return ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(p, message));
                } else {
                    return ChatColor.translateAlternateColorCodes('&', message);
                }
            } else {
                return ChatColor.translateAlternateColorCodes('&', message);
            }
        }
        return " ";
    }
}