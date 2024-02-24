package me.thedogsito.je.listeners;

import me.thedogsito.je.Main;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireworksJoinListener implements Listener {
    private Main plugin;
    public FireworksJoinListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        List<String> types = plugin.getConfig().getStringList("Config.Join.Fireworks.Types");
        List<String> colors = plugin.getConfig().getStringList("Config.Join.Fireworks.Colors");

        FireworkEffect.Type type = getValidType(types);
        Color color = getValidColor(colors);

        if (type != null && colors != null && !colors.isEmpty()) {
            List<Color> colorList = new ArrayList<>();

            for (String colorName : colors) {
                try {
                    java.awt.Color awtColor = java.awt.Color.decode(colorName);
                    colorList.add(Color.fromRGB(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue()));
                } catch (NumberFormatException e) {
                    plugin.getLogger().warning("Invalid firework color: " + colorName);
                }
            }

            if (!colorList.isEmpty()) {
                spawnCombinedFirework(event.getPlayer().getLocation(), type, colorList);
            }
        }

    }

    private void spawnCombinedFirework(org.bukkit.Location location, FireworkEffect.Type type, List<Color> colors) {
        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta meta = firework.getFireworkMeta();

        if (type != null && !colors.isEmpty()) {
            FireworkEffect.Builder combinedEffectBuilder = FireworkEffect.builder();
            for (Color color : colors) {
                combinedEffectBuilder.withColor(color);
            }
            combinedEffectBuilder.with(type).trail(true).flicker(false);

            meta.addEffect(combinedEffectBuilder.build());

            meta.setPower(1);
        }

        firework.setFireworkMeta(meta);
    }


    private <T> T getRandomElement(List<T> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    private Color getValidColor(List<String> colors) {
        String colorName = getRandomElement(colors);

        try {
            return (Color) Color.class.getField(colorName.toUpperCase()).get(null);
        } catch (Exception ignored) {
        }

        try {
            java.awt.Color awtColor = java.awt.Color.decode(colorName);
            return Color.fromRGB(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
        } catch (NumberFormatException e) {
            Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages("&b&lJoinEvents &3&l>> " +
                    "&c&lInvalid firework color: " + colorName + " does not exist", null));
        }
        return null;
    }


    private FireworkEffect.Type getValidType(List<String> types) {
        String typeName = getRandomElement(types);
        try {
            return FireworkEffect.Type.valueOf(typeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages("&b&lJoinEvents &3&l>> " +
                    "&c&lThis effect: " + typeName + " does not exist", null));
        }
        return null;
    }
}
