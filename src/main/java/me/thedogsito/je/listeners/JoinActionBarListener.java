package me.thedogsito.je.listeners;

import me.thedogsito.je.Main;
import me.thedogsito.je.config.MainConfigManager;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinActionBarListener implements Listener {
    private Main plugin;
    public JoinActionBarListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public boolean onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        MainConfigManager mainConfigManager = plugin.getMainConfigManager();

        if(mainConfigManager.isActionBarEnabled()) {
            String msg = mainConfigManager.getActionBarText().replace("%player%", p.getName());

            if (plugin.getServer().getVersion().contains("1.8")) {
                Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages("&b&lJoinEvents &3&l>> &c&lThis bossbar not supported on 1.8"));
                return true;
            }

            p.sendActionBar(MessageUtil.GetColoredMessages(msg));
        }
        return true;
    }
}
