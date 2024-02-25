package me.thedogsito.je.listeners;

import emanondev.itemedit.ItemEdit;
import emanondev.itemedit.storage.PlayerStorage;
import java.util.ArrayList;
import java.util.List;

import me.thedogsito.je.Main;
import me.thedogsito.je.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItems implements Listener {
    private Main plugin;

    private List<String> activePlayers;

    public CustomItems(Main plugin) {
        this.plugin = plugin;
        this.activePlayers = new ArrayList<>();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        FileConfiguration items = this.plugin.getItems();
        Player p = e.getPlayer();
        if (items.getString("ItemsEvents.GiveTheFirstTime").equals("true")) {
            if (!e.getPlayer().hasPlayedBefore()) {
                ConfigurationSection customItemsSection = items.getConfigurationSection("CustomItems");
                if (customItemsSection == null)
                    return;
                for (String itemKey : customItemsSection.getKeys(false)) {
                    Material materialEnum;
                    ConfigurationSection itemSection = customItemsSection.getConfigurationSection(itemKey);
                    if (itemSection == null)
                        continue;
                    String material = itemSection.getString("Material");
                    String displayName = itemSection.getString("DisplayName");
                    List<String> lore = itemSection.getStringList("Lore");
                    List<String> enchantments = itemSection.getStringList("Enchantments");
                    if (material.startsWith("ItemEdit:")) {
                        PlayerStorage playerStorage = ItemEdit.get().getPlayerStorage();
                        String CustomMaterial = material.substring("ItemEdit:".length());
                        int i = itemSection.getInt("Slot");
                        Player player = e.getPlayer();
                        ItemStack itemEditItem = playerStorage.getItem((OfflinePlayer)player, CustomMaterial);
                        if (itemEditItem != null) {
                            PlayerInventory playerInventory = player.getInventory();
                            playerInventory.setItem(i, itemEditItem);
                            player.updateInventory();
                        }
                        continue;
                    }
                    try {
                        materialEnum = Material.valueOf(material);
                    } catch (IllegalArgumentException ex) {
                        Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages("&b&lJoinEvents &3&l>> " + " &c&lInvalid material: " + material + " in items.yml", p));
                        continue;
                    }
                    ItemStack itemStack = new ItemStack(materialEnum);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if (displayName != null)
                        itemMeta.setDisplayName(MessageUtil.GetColoredMessages(displayName, p));
                    if (lore != null && !lore.isEmpty()) {
                        List<String> translatedLore = new ArrayList<>();
                        for (String line : lore)
                            translatedLore.add(MessageUtil.GetColoredMessages(line, p));
                        itemMeta.setLore(translatedLore);
                    }
                    if (enchantments != null && !enchantments.isEmpty())
                        for (String enchant : enchantments) {
                            String[] parts = enchant.split(":");
                            String enchantmentName = parts[0];
                            int enchantmentLevel = Integer.parseInt(parts[1]);
                            Enchantment enchantment = Enchantment.getByName(enchantmentName.toUpperCase());
                            if (enchantment != null)
                                itemMeta.addEnchant(enchantment, enchantmentLevel, true);
                        }
                    itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
                    itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
                    itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });
                    itemStack.setItemMeta(itemMeta);
                    int slot = itemSection.getInt("Slot");
                    PlayerInventory inventory = p.getInventory();
                    inventory.setItem(slot, itemStack);
                    p.updateInventory();
                }
            }
        } else {
            ConfigurationSection customItemsSection = items.getConfigurationSection("CustomItems");
            if (customItemsSection == null)
                return;
            for (String itemKey : customItemsSection.getKeys(false)) {
                Material materialEnum;
                ConfigurationSection itemSection = customItemsSection.getConfigurationSection(itemKey);
                if (itemSection == null)
                    continue;
                String material = itemSection.getString("Material");
                String displayName = itemSection.getString("DisplayName");
                List<String> lore = itemSection.getStringList("Lore");
                List<String> enchantments = itemSection.getStringList("Enchantments");
                if (material.startsWith("ItemEdit:")) {
                    PlayerStorage playerStorage = ItemEdit.get().getPlayerStorage();
                    String CustomMaterial = material.substring("ItemEdit:".length());
                    int i = itemSection.getInt("Slot");
                    Player player = e.getPlayer();
                    ItemStack itemEditItem = playerStorage.getItem((OfflinePlayer)player, CustomMaterial);
                    if (itemEditItem != null) {
                        PlayerInventory playerInventory = player.getInventory();
                        playerInventory.setItem(i, itemEditItem);
                        player.updateInventory();
                    }
                    continue;
                }
                try {
                    materialEnum = Material.valueOf(material);
                } catch (IllegalArgumentException ex) {
                    Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages("&b&lJoinEvents &3&l>> " + " &c&lInvalid material: " + material + " in items.yml", p));
                    continue;
                }
                ItemStack itemStack = new ItemStack(materialEnum);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (displayName != null)
                    itemMeta.setDisplayName(MessageUtil.GetColoredMessages(displayName, p));
                if (lore != null && !lore.isEmpty()) {
                    List<String> translatedLore = new ArrayList<>();
                    for (String line : lore)
                        translatedLore.add(MessageUtil.GetColoredMessages(line, p));
                    itemMeta.setLore(translatedLore);
                }
                if (enchantments != null && !enchantments.isEmpty())
                    for (String enchant : enchantments) {
                        String[] parts = enchant.split(":");
                        String enchantmentName = parts[0];
                        int enchantmentLevel = Integer.parseInt(parts[1]);
                        Enchantment enchantment = Enchantment.getByName(enchantmentName.toUpperCase());
                        if (enchantment != null)
                            itemMeta.addEnchant(enchantment, enchantmentLevel, true);
                    }
                itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
                itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
                itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });
                itemStack.setItemMeta(itemMeta);
                int slot = itemSection.getInt("Slot");
                PlayerInventory inventory = p.getInventory();
                inventory.setItem(slot, itemStack);
                p.updateInventory();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerItemClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInHand();
        if (item == null || item.getType() == Material.AIR)
            return;
        FileConfiguration items = this.plugin.getItems();
        ConfigurationSection customItemsSection = items.getConfigurationSection("CustomItems");
        if (customItemsSection == null)
            return;
        for (String itemKey : customItemsSection.getKeys(false)) {
            ConfigurationSection itemSection = customItemsSection.getConfigurationSection(itemKey);
            if (itemSection == null)
                continue;
            String material = itemSection.getString("Material");
            List<String> commandsClick = itemSection.getStringList("CommandsClick");
            if (material.startsWith("ItemEdit:")) {
                PlayerStorage playerStorage = ItemEdit.get().getPlayerStorage();
                String CustomMaterial = material.substring("ItemEdit:".length());
                ItemStack itemEditItem = playerStorage.getItem((OfflinePlayer)player, CustomMaterial);
                if (itemEditItem != null) {
                    if (!this.activePlayers.contains(player.getName())) {
                        this.activePlayers.add(player.getName());
                        if (commandsClick != null && !commandsClick.isEmpty())
                            for (String command : commandsClick) {
                                command = command.replace("%player%", player.getName());
                                try {
                                    player.performCommand(command);
                                } catch (IllegalArgumentException ex) {
                                    Bukkit.getConsoleSender().sendMessage(MessageUtil.GetColoredMessages("&b&lJoinEvents &3&l>> " + " &c&lFailed to execute click command: " + command, null));
                                }
                            }
                    }
                    continue;
                }
                this.activePlayers.remove(player.getName());
            }
        }
    }
}

