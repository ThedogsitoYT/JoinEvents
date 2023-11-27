package me.thedogsito.je.listeners;

import me.thedogsito.je.Main;
import me.thedogsito.je.config.MainConfigManager;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class TextPermissionsListener implements Listener {

    private Main plugin;
    public TextPermissionsListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        String message = null;

        Player p = e.getPlayer();
        MainConfigManager mainConfigManager = plugin.getMainConfigManager();

        if(mainConfigManager.isMessageWithPermissionEnabled()) {
            List<String> messages = mainConfigManager.getMessageWithPermissionText();
            List<String> permissions = mainConfigManager.getMessageWithPermission();

            for(int i = 0; i < permissions.size(); i++) {
                String perm = permissions.get(i);
                String msg = messages.get(i);

                if (p.hasPermission(perm)) {
                    message = msg;
                }
            }
            if(message != null) {
                e.setJoinMessage(MessageUtil.GetColoredMessages(message)
                        .replace("%player%", p.getName()));
            }else {
                e.setJoinMessage(null);
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
                e.setQuitMessage(MessageUtil.GetColoredMessages(message)
                        .replace("%player%", p.getName()));
            }else {
                e.setQuitMessage(null);
            }
        }
    }
}
