package me.thedogsito.je.commands;

import me.thedogsito.je.Main;
import me.thedogsito.je.utils.MessageUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Set;

public class WarpList implements CommandExecutor {
    private final Main plugin;

    public WarpList(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission("je.warplist" + "je.*")) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getNotPermission()
                                .replace("%player%", p.getName())));
                return true;
            }
        }
        FileConfiguration config = plugin.getConfig();

        ConfigurationSection warpsSection = config.getConfigurationSection("Config.Commands.Warps");

        if (warpsSection != null) {
            Set<String> warpNames = warpsSection.getKeys(false);

            sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpListLineOne")));
            sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpListUpText")));
            for (String warpName : warpNames) {
                String path = "Config.Commands.Warps." + warpName + ".Name";
                String warpDisplayName = config.getString(path);

                TextComponent warpComponent = new TextComponent(MessageUtil.GetColoredMessages(warpDisplayName));
                warpComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/warp " + warpName));
                TextComponent hoverText = new TextComponent(MessageUtil.GetColoredMessages("&3&lTp to " + MessageUtil.GetColoredMessages(warpDisplayName)));
                warpComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (BaseComponent[])new TextComponent[] { hoverText }));

                if (sender instanceof Player) {
                    sender.sendMessage(warpComponent);
                }else {
                    sender.sendMessage(warpDisplayName);
                }

            }
            sender.sendMessage(MessageUtil.GetColoredMessages(config.getString("Messages.Warps.WarpListLineTwo")));
        }
        return true;
    }
}
