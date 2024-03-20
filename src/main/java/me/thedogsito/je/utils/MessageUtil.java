package me.thedogsito.je.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {
    public static String GetColoredMessages(String message, Player p) {
        String version = Bukkit.getVersion();
        Boolean V1_16 = version.contains("1.16");
        Boolean V1_17 = version.contains("1.17");
        Boolean V1_18 = version.contains("1.18");
        Boolean V1_19 = version.contains("1.19");
        Boolean V1_20 = version.contains("1.20");
        if (V1_16 || V1_17 || V1_18 || V1_19 || V1_20) {
            Pattern ptn = Pattern.compile("&#[a-fA-F0-9]{6}");
            Matcher m = ptn.matcher(message);

            while (m.find()) {
                String color = message.substring(m.start(), m.end());
                message = message.replace(color, ChatColor.of(color.substring(1))+"");

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

    public static String replaceHexColors(String message) {
        String version = Bukkit.getVersion();
        Boolean V1_16 = version.contains("1.16");
        Boolean V1_17 = version.contains("1.17");
        Boolean V1_18 = version.contains("1.18");
        Boolean V1_19 = version.contains("1.19");
        Boolean V1_20 = version.contains("1.20");
        if (V1_16 || V1_17 || V1_18 || V1_19 || V1_20) {
            try {
                Pattern ptn = Pattern.compile("&#[a-fA-F0-9]{6}");
                Matcher matcher = ptn.matcher(message);
                String newMessage = "";

                int lastEnd = 0;
                while (matcher.find()) {
                    newMessage += message.substring(lastEnd, matcher.start()) + "";
                    lastEnd = matcher.end();
                }
                newMessage += message.substring(lastEnd);
                message = newMessage;
            } catch (Exception ex) {

            }
        }
        return message;
    }
}