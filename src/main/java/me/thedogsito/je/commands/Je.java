package me.thedogsito.je.commands;

import me.thedogsito.je.Main;
import me.thedogsito.je.config.CustomConfig;
import me.thedogsito.je.config.MainConfigManager;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Je implements CommandExecutor {
    private Main plugin;
    public Je(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length >= 1) {
            if(args[0].equalsIgnoreCase("help")) {
                //je help
                help(sender);
            }else if(args[0].equalsIgnoreCase("reload")) {
                //je reload
                reload(sender);
            }else if(args[0].equalsIgnoreCase("get")) {
                //je get <version/author>
                subCommandGet(sender, args);
            }else {
                error(sender);
            }
        }else {
            error(sender);
        }
        return true;
    }

    //Help
    public void help(CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(!p.hasPermission("je.help" + "je.*")) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getNotPermission()
                                .replace("%player%", p.getName())));
                return;
            }

            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l----------JoinEvents commands----------"));
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lje reload"));
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lje get <version/author>"));
            sender.sendMessage(" ");
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lfly"));
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lhub"));
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lsethub"));
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l---------------------------------------"));
        }else{
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l----------JoinEvents commands----------"));
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lje reload"));
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lje get <version/author>"));
            sender.sendMessage(" ");
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lfly"));
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lhub"));
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lsethub"));
            sender.sendMessage(MessageUtil.GetColoredMessages("&3&l---------------------------------------"));
        }
    }

    //Reload
    public void reload(CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(!p.hasPermission("je.reload" + "je.*")) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getNotPermission()
                                .replace("%player%", p.getName())));
                return;
            }
            plugin.getMainConfigManager().reloadConfig();
            sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getReload()
                    .replace("%player%", p.getName())));
        }else{
            plugin.getMainConfigManager().reloadConfig();
            sender.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getReload()));
        }
    }

    //Get
    public void subCommandGet(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(!p.hasPermission("je.get" + "je.*")) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getNotPermission()
                                .replace("%player%", p.getName())));
                return;
            }

            if(args.length == 1) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getErrorArgumentOfGet()
                                .replace("%player%", p.getName())));
                return;
            }

            if(args[1].equalsIgnoreCase("author")) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getPrefix() + "&b&lThis author is:&3&l " + plugin.getDescription().getAuthors())
                );
            }else if(args[1].equalsIgnoreCase("version")) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getPrefix() + "&b&lThis version is:&3&l " + plugin.getDescription().getVersion()));
            }else {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getErrorArgumentOfGet()
                                .replace("%player%", p.getName())));
            }
        }else{
            if(args.length == 1) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getErrorArgumentOfGet()));
                return;
            }

            if(args[1].equalsIgnoreCase("author")) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getPrefix() + "&b&lThis author is:&3&l " + plugin.getDescription().getAuthors())
                );
            }else if(args[1].equalsIgnoreCase("version")) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getPrefix() + "&b&lThis version is:&3&l " + plugin.getDescription().getVersion()));
            }else {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getErrorArgumentOfGet()));
            }
        }
    }

    //Error that command not exist
    public void error(CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(!p.hasPermission("je.error" + "je.*")) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getNotPermission()
                                .replace("%player%", p.getName())));
                return;
            }
            sender.sendMessage(MessageUtil.GetColoredMessages(
                    plugin.getMainConfigManager().getNotExist()
                            .replace("%player%", p.getName())));
        }else{
            sender.sendMessage(MessageUtil.GetColoredMessages(
                    plugin.getMainConfigManager().getNotExist()));
        }
    }
}