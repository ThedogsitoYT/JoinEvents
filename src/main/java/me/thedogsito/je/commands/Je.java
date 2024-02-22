package me.thedogsito.je.commands;

import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import me.thedogsito.je.Main;
import me.thedogsito.je.utils.MessageUtil;
import me.thedogsito.je.utils.UpdateDownloader;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Je implements CommandExecutor {
    private Main plugin;
    String downloadUrl = "https://hangarcdn.papermc.io/plugins/ThedogsitoYT/JoinEvents/versions/3.0.8-BETA/PAPER/JoinEvents.jar";
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
            }else if (args[0].equalsIgnoreCase("update")) {
                updater(sender);
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
        }
        sender.sendMessage(MessageUtil.GetColoredMessages("&3&l----------JoinEvents commands----------"));
        sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lje reload"));
        sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lje get <version/author>"));
        sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lje update"));
        sender.sendMessage(" ");
        sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lfly (no added)"));
        sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lhub"));
        sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lsethub"));
        sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&ldelhub"));
        sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lwarp"));
        sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lsetwarp"));
        sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&ldelwarp"));
        sender.sendMessage(MessageUtil.GetColoredMessages("&3&l/&b&lwarplist"));
        sender.sendMessage(MessageUtil.GetColoredMessages("&3&l---------------------------------------"));
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

    //Updater
    public void updater(CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission("je.update" + "je.*")) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getNotPermission()
                                .replace("%player%", p.getName())));
                return;
            }

            if (Bukkit.getServer().getVersion().contains("1.8")) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        "&b&lJoinEvents &3&l>> &cFor now it is not for 1.8. you will have to update manually"));
                return;
            }

            if (plugin.isFolia()) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        "&b&lJoinEvents &3&l>> &cFor now it is not for folia. you will have to update manually"));
                return;
            }

            if (plugin.getVersion().equals(plugin.getLatestversion())) {
                p.sendActionBar(MessageUtil.GetColoredMessages("&4&l[&c&lYou are in the latest version&4&l]"));
                p.sendMessage(MessageUtil.GetColoredMessages("&b&lJoinEvents &3&l>> &cYou are in the latest version"));
            }else {
                startUpdateActionBar(p);
            }
        } else {
            sender.sendMessage(MessageUtil.GetColoredMessages("&b&lJoinEvents &3&l>> &cThis command not support on console"));
        }
    }

    // Updater functions
    private void startUpdateActionBar(Player p) {
        p.sendActionBar(MessageUtil.GetColoredMessages("&3&l[&b&lDowloading update&3&l]"));
        p.sendMessage(MessageUtil.GetColoredMessages("&b&lJoinEvents &3&l>>" +
                " &bDowloading update"));

        new BukkitRunnable() {
            @Override
            public void run() {
                UpdateActionBar(p);
            }
        }.runTaskLater(plugin, 1L);
    }

    private void UpdateActionBar(Player p) {
        new BukkitRunnable() {
            double progress = 0.0;

            @Override
            public void run() {
                if (progress <= 1.0) {
                    int percentage = (int) (progress * 100);

                    downloadAndUpdate(p);

                    if (percentage >= 2) {
                        p.sendActionBar(MessageUtil.GetColoredMessages("&3&l[&7----------&3&l] &b&l" + percentage + "&3&l%"));
                    }

                    if (percentage >= 10) {
                        p.sendActionBar(MessageUtil.GetColoredMessages("&3&l[&b&l-&7---------&3&l] &b&l" + percentage + "&3&l%"));
                    }

                    if (percentage >= 20) {
                        p.sendActionBar(MessageUtil.GetColoredMessages("&3&l[&b&l--&7--------&3&l] &b&l" + percentage + "&3&l%"));
                    }

                    if (percentage >= 30) {
                        p.sendActionBar(MessageUtil.GetColoredMessages("&3&l[&b&l---&7-------&3&l] &b&l" + percentage + "&3&l%"));
                    }

                    if (percentage >= 40) {
                        p.sendActionBar(MessageUtil.GetColoredMessages("&3&l[&b&l----&7------&3&l] &b&l" + percentage + "&3&l%"));
                    }

                    if (percentage >= 50) {
                        p.sendActionBar(MessageUtil.GetColoredMessages("&3&l[&b&l-----&7-----&3&l] &b&l" + percentage + "&3&l%"));
                    }

                    if (percentage >= 60) {
                        p.sendActionBar(MessageUtil.GetColoredMessages("&3&l[&b&l------&7----&3&l] &b&l" + percentage + "&3&l%"));
                    }

                    if (percentage >= 70) {
                        p.sendActionBar(MessageUtil.GetColoredMessages("&3&l[&b&l-------&7---&3&l] &b&l" + percentage + "&3&l%"));
                    }

                    if (percentage >= 80) {
                        p.sendActionBar(MessageUtil.GetColoredMessages("&3&l[&b&l--------&7--&3&l] &b&l" + percentage + "&3&l%"));
                    }

                    if (percentage >= 90) {
                        p.sendActionBar(MessageUtil.GetColoredMessages("&3&l[&b&l---------&7-&3&l] &b&l" + percentage + "&3&l%"));
                    }

                    if (percentage >= 100) {
                        stopUpdateActionBar(p);
                        this.cancel();
                        System.out.println("Update completed.");
                    }

                    progress += 0.05;
                    System.out.println("Progress: " + percentage + "%");

                } else {
                    stopUpdateActionBar(p);
                    this.cancel();
                    System.out.println("Update completed.");
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private void downloadAndUpdate(Player player) {
        UpdateDownloader updateDownloader = new UpdateDownloader(plugin);

        new BukkitRunnable() {
            @Override
            public void run() {
                updateDownloader.downloadAndUpdate(downloadUrl, player);
            }
        }.runTaskAsynchronously(plugin);
    }

    private void stopUpdateActionBar(Player p) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            p.sendActionBar(MessageUtil.GetColoredMessages("&3&l[&b&lThe update is downloaded&3&l]"));
            p.sendMessage(MessageUtil.GetColoredMessages(
                    "&b&lJoinEvents &3&l>> &b&lThe update is downloaded. please restart your server"));
        });
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
                        plugin.getMainConfigManager().getPrefix() + " &b&lThis author is:&3&l " + plugin.getDescription().getAuthors())
                );
            }else if(args[1].equalsIgnoreCase("version")) {
                sender.sendMessage(MessageUtil.GetColoredMessages(
                        plugin.getMainConfigManager().getPrefix() + " &b&lThis version is:&3&l " + plugin.getDescription().getVersion()));
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