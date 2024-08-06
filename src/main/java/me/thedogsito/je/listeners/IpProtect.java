package me.thedogsito.je.listeners;

import me.thedogsito.je.Main;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class IpProtect implements Listener {
    private Main plugin;

    public IpProtect(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void ipProtect(AsyncPlayerPreLoginEvent e) {
        FileConfiguration config = this.plugin.getConfig();
        String Name = e.getName();
        String Ip = e.getAddress().getHostAddress();
        String IpsList = "Config.IpsProtected.Ips";
        String NamesList = "Config.IpsProtected.Names";
        List<String> Ips = config.getStringList(IpsList);
        List<String> Names = config.getStringList(NamesList);
        if (Ips.size() == Names.size())
            for (int i = 0; i < Ips.size(); i++) {
                String ips = Ips.get(i);
                String names = Names.get(i);
                if (Name.equalsIgnoreCase(names) && !ips.contains(Ip)) {
                    e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                    String KickMessage = "Config.IpsProtected.KickMessage";
                    e.setKickMessage(MessageUtil.GetColoredMessages(config.getString(KickMessage).replace("%player%", e.getName()), null));
                    Bukkit.getConsoleSender().sendMessage("Name attacker:" + e.getName() + "IP attacker = " + Ip);
                }
            }
    }
}
