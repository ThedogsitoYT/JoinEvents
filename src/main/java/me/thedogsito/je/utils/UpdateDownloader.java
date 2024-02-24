package me.thedogsito.je.utils;

import me.thedogsito.je.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class UpdateDownloader {
    private static Main plugin;

    public UpdateDownloader(Main plugin) {
        this.plugin = plugin;
    }

    public void downloadAndUpdate(String downloadUrl, Player player) {
        String destination = "plugins/JoinEvents.jar";

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    downloadFile(downloadUrl, destination, player);
                } catch (IOException e) {
                    e.printStackTrace();
                    stopUpdateActionBar(player, "Error.");
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    private void downloadFile(String url, String destination, Player player) throws IOException {
        URL urlObj = new URL(url);
        URLConnection connection = urlObj.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

        String fileName = url.substring(url.lastIndexOf('/') + 1);

        File file = new File(destination);

        try (InputStream in = connection.getInputStream();
             OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int totalBytesRead = 0;
            int fileSize = connection.getContentLength();

            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                totalBytesRead += bytesRead;

                double progress = (double) totalBytesRead / fileSize;
                int percentage = (int) (progress * 100);
            }

            Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages("&b&lJoinEvents &3&l>> &b&lDowloading &3&l" + fileName, null));

        } catch (IOException e) {
            throw new IOException(MessageUtil.GetColoredMessages("&b&lJoinEvents &3&l>> &c&lError 403", null));
        }
    }

    private void stopUpdateActionBar(Player player, String message) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            }
        }.runTask(plugin);
    }
}
