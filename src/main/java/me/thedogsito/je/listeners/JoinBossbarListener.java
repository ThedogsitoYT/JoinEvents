package me.thedogsito.je.listeners;

import me.thedogsito.je.Main;
import me.thedogsito.je.config.MainConfigManager;
import me.thedogsito.je.utils.MessageUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinBossbarListener implements Listener {
    private Main plugin;
    public JoinBossbarListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public boolean onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        MainConfigManager mainConfigManager = plugin.getMainConfigManager();

        if(mainConfigManager.isBossbarEnabled()) {
            String msg = mainConfigManager.getBossbarText().replace("%player%", p.getName());

            if (plugin.getServer().getVersion().contains("1.8")) {
                Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages("&b&lJoinEvents &3&l>> &c&lThis bossbar not supported on 1.8"));
                return true;
            }

            p.sendActionBar(MessageUtil.GetColoredMessages(msg));
        }
        return true;
    }
}
