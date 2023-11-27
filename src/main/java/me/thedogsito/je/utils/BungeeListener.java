package me.thedogsito.je.utils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.thedogsito.je.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeeListener implements PluginMessageListener {
    private static Main plugin;
    private static String lastServerName;

    public BungeeListener(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();

        if (subChannel.equals("GetServer")) {
            String serverName = in.readUTF();

            plugin.getConfig().set("Config.Commands.Hub.Server", serverName);
            player.sendMessage(MessageUtil.GetColoredMessages(plugin.getMainConfigManager().getProxyHub()
                    .replace("%sv%", serverName)));
            plugin.saveConfig();
        }
    }

    public static String getLastServerName() {
        return lastServerName;
    }

    public static void sendBungeeMessage(String playerName, String subChannel) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subChannel);
        out.writeUTF(playerName);

        Player player = plugin.getServer().getPlayer(playerName);
        if (player != null) {
            player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        }
    }
}