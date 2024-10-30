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

public class JoinActionBarListener implements Listener {
    private Main plugin;
    public JoinActionBarListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        MainConfigManager mainConfigManager = plugin.getMainConfigManager();

        if(mainConfigManager.isActionBarEnabled()) {
            String msg = mainConfigManager.getActionBarText().replace("%player%", p.getName());

            if (plugin.getServer().getVersion().contains("1.8")) {
                Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages("&b&lJoinEvents &3&l>> &c&lThis bossbar not supported on 1.8", p));
                return;
            }

            String version = Bukkit.getVersion();
            Boolean V1_19 = version.contains("1.19");
            Boolean V1_20 = version.contains("1.20");
            Boolean V1_21 = version.contains("1.21");

            if (V1_19 || V1_20 || V1_21) {
                p.sendActionBar(MessageUtil.GetColoredMessages(msg, p));
                return;
            }

            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageUtil.GetColoredMessages(msg, p)));
        }
    }
}
