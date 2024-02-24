package me.thedogsito.je.listeners;

import me.thedogsito.je.Main;
import me.thedogsito.je.config.MainConfigManager;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinTitlesListener implements Listener {
    private Main plugin;
    public JoinTitlesListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        MainConfigManager mainConfigManager = plugin.getMainConfigManager();

        if(mainConfigManager.isTitleEnabled()) {

            String Title = mainConfigManager.getTitle().replace("%player%", p.getName());
            String SubTitle = mainConfigManager.getSubTitle().replace("%player%", p.getName());

            p.sendTitle(MessageUtil.GetColoredMessages(Title, p), MessageUtil.GetColoredMessages(SubTitle, p));
        }
    }
}
