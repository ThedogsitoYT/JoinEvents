package me.thedogsito.je.listeners;

import me.thedogsito.je.Main;
import me.thedogsito.je.config.MainConfigManager;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
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
            List<String> message = mainConfigManager.getWelcomeMessageText();
            for(String m : message) {
                p.sendMessage(MessageUtil.GetColoredMessages(m, p)
                        .replace("%player%", p.getName()));
            }
        }

        if (plugin.isFolia()) {
            Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages(
                    "&b&lJoinEvents &3&l>> &cThis commands on join do not support a folia", p));
            return;
        }

        FileConfiguration config = this.plugin.getConfig();
        String path = "Config.Commands.OnConsoleJoin.Enabled";
        if (config.getString(path).equals("true")) {
            String messages = "Config.Commands.OnConsoleJoin.Commands";
            List<String> Messages = config.getStringList(messages);
            for (int i = 0; i < Messages.size(); i++) {
                String text = Messages.get(i);
                Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), text.replace("%player%", p.getName()));
            }
        }

        if (!p.hasPlayedBefore()) {
            Bukkit.getServer().broadcastMessage(MessageUtil.GetColoredMessages(config.getString("Config.Join.WelcomeMessage.EnterFirstTime")
                    .replace("%player%", p.getName()), p));
        }
    }
}
