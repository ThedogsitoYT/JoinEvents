package me.thedogsito.je.listeners;

import me.thedogsito.je.Main;
import me.thedogsito.je.config.MainConfigManager;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collections;
import java.util.List;

public class TextPermissionsListener implements Listener {

    private Main plugin;
    public TextPermissionsListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        String message = null;
        String sound = null;

        Player p = e.getPlayer();
        MainConfigManager mainConfigManager = plugin.getMainConfigManager();

        if (mainConfigManager.isMessageWithPermissionEnabled()) {
            List<String> permissions = mainConfigManager.getMessageWithPermission();
            List<String> messages = mainConfigManager.getMessageWithPermissionText();
            List<String> sounds = mainConfigManager.getSoundPermission();

            for (int i = 0; i < permissions.size(); i++) {
                String perm = permissions.get(i);
                String msg = messages.get(i);
                String sd = sounds.get(i);

                if (p.hasPermission(perm)) {
                    message = msg;
                    sound = sd;
                }
            }
            if (message != null && sound != null) {
                e.setJoinMessage(MessageUtil.GetColoredMessages(message, p)
                        .replace("%player%", p.getName()));
            }else {
                e.setJoinMessage(null);
            }
            if (sound != null) {
                String[] separated = sound.split(";");
                try {
                    int volume = Integer.parseInt(separated[1]);
                    float pitch = Float.parseFloat(separated[2]);
                    Sound soundEnum = Sound.valueOf(separated[0].toUpperCase().replace(".", "_"));
                    p.playSound(p.getLocation(), soundEnum, volume, pitch);
                } catch (IllegalArgumentException ex) {
                    Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                            "&b&lJoinEvents &3&l>> &c&lThe sound: " + separated[0] + " does not exist.", p));
                    Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                            "&b&lJoinEvents &3&l>> &c&lRemember that in 1.9 onwards all Minecraft sounds have been changed", p));
                }
            }
        }
    }

    @EventHandler
    public void onExit(PlayerQuitEvent e) {
        String message = null;

        Player p = e.getPlayer();
        MainConfigManager mainConfigManager = plugin.getMainConfigManager();

        if(mainConfigManager.isMessageWithPermissionEnabled()) {
            List<String> messages = mainConfigManager.getMessageWithPermissionLeaving();
            List<String> permissions = mainConfigManager.getMessageWithPermission();

            for(int i = 0; i < permissions.size(); i++) {
                String perm = permissions.get(i);
                String msg = messages.get(i);

                if (p.hasPermission(perm)) {
                    message = msg;
                }
            }
            if(message != null) {
                e.setQuitMessage(MessageUtil.GetColoredMessages(message, p)
                        .replace("%player%", p.getName()));
            }else {
                e.setQuitMessage(null);
            }
        }
    }
}
