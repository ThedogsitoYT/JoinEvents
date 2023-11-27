package me.thedogsito.je.listeners;

import me.thedogsito.je.Main;
import me.thedogsito.je.config.MainConfigManager;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class JoinMessageListener implements Listener {
    private Main plugin;
    public JoinMessageListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        MainConfigManager mainConfigManager = plugin.getMainConfigManager();

        if(mainConfigManager.isWelcomeMessageEnabled()) {
            List<String> messag = mainConfigManager.getWelcomeMessageText();
            for(String m : message) {
                p.sendMessage(MessageUtil.GetColoredMessages(m)
                        .replace("%player%", p.getName()));
            }
        }
    }
}
