package me.thedogsito.je.listeners;

import me.thedogsito.je.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DropOrMoveInInventory implements Listener {
    private Main plugin;

    public DropOrMoveInInventory(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        FileConfiguration items = this.plugin.getItems();
        final Player p = e.getPlayer();
        Item item = e.getItemDrop();
        final ItemStack itemStack = item.getItemStack();
        if (items.getString("ItemsEvents.CancelDropItems").equals("true")) {
            item.remove();
            final int originalSlot = p.getInventory().getHeldItemSlot();
            (new BukkitRunnable() {
                public void run() {
                    p.getInventory().setItem(originalSlot, itemStack);
                }
            }).runTaskLater((Plugin)this.plugin, 10L);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        FileConfiguration items = this.plugin.getItems();
        if (items.getString("ItemsEvents.CancelMoveInventory").equals("true"))
            e.setCancelled(true);
    }
}
