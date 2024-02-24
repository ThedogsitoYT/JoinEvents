package me.thedogsito.je.commands;

import me.thedogsito.je.Main;
import me.thedogsito.je.utils.MessageUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Set;

public class WarpList implements CommandExecutor {
    private final Main plugin;

    public WarpList(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            FileConfiguration config = plugin.getConfig();
            ConfigurationSection warpsSection = config.getConfigurationSection("Config.Commands.Warps");

            if (warpsSection != null) {
                Set<String> warpNames = warpsSection.getKeys(false);

                sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpListLineOne"), null));
                sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpListUpText"), null));
                for (String warpName : warpNames) {
                    String path = "Config.Commands.Warps." + warpName + ".Name";
                    String warpDisplayName = config.getString(path);

                    sender.sendMessage(MessageUtil.GetColoredMessages(warpDisplayName, null));
                }
                sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpListLineTwo"), null));
            }
        }

        Player p = (Player) sender;

        if (!p.hasPermission("je.warplist") && !p.hasPermission("je.*")) {
            p.sendMessage(MessageUtil.GetColoredMessages(
                    plugin.getMainConfigManager().getNotPermission()
                            .replace("%player%", p.getName()), p));
            return true;
        }

        FileConfiguration config = plugin.getConfig();

        ConfigurationSection warpsSection = config.getConfigurationSection("Config.Commands.Warps");

        if (warpsSection != null) {
            Set<String> warpNames = warpsSection.getKeys(false);

            p.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpListLineOne"), null));
            p.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpListUpText"), null));
            for (String warpName : warpNames) {
                String path = "Config.Commands.Warps." + warpName + ".Name";
                String warpDisplayName = config.getString(path);

                ComponentBuilder warpComponent = new ComponentBuilder(MessageUtil.GetColoredMessages(warpDisplayName, null));
                warpComponent.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/warp " + warpName));
                warpComponent.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder(MessageUtil.GetColoredMessages("&3&lTp to"+ warpDisplayName, null)).create()));

                p.spigot().sendMessage(warpComponent.create());
            }
            p.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpListLineTwo"), null));
        }
        return true;
    }
}
