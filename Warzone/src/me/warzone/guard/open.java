package me.warzone.guard;

import me.warzone.storage;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by trent on 5/13/2017.
 */
public class open implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) &&
                ((e.getClickedBlock().getType() == Material.ANVIL) ||
                        (e.getClickedBlock().getType() == Material.WORKBENCH) ||
                        (e.getClickedBlock().getType() == Material.FURNACE) ||
                        (e.getClickedBlock().getType() == Material.DROPPER) ||
                        (e.getClickedBlock().getType() == Material.DISPENSER) ||
                        (e.getClickedBlock().getType() == Material.CHEST) ||
                        (e.getClickedBlock().getType() == Material.TRAPPED_CHEST) ||
                        (e.getClickedBlock().getType() == Material.WOOD_BUTTON) ||
                        (e.getClickedBlock().getType() == Material.STONE_BUTTON) ||
                        (e.getClickedBlock().getType() == Material.TRAP_DOOR) ||
                        (e.getClickedBlock().getType() == Material.BREWING_STAND)) &&
                (!storage.build.contains(e.getPlayer()))) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }
}