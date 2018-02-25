package me.warzone.game_listeners;

import me.warzone.storage;
import me.warzone.utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class inventoryClick implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e){
        Inventory i = e.getClickedInventory();
        ItemStack clicked = e.getCurrentItem();
        Player p = (Player)e.getWhoClicked();
        if (i.getName().equalsIgnoreCase(utils.color("&cPick a team!"))){
            String n = clicked.getItemMeta().getDisplayName();
            if (n.equalsIgnoreCase(utils.color("&cRed Team"))){
                utils.joinRed(p);
                utils.teleport(p);
            }
            if (n.equalsIgnoreCase(utils.color("&bBlue Team"))){
                utils.joinBlue(p);
                utils.teleport(p);
            }
            if (n.equalsIgnoreCase(utils.color("&7Spec Team"))){
                utils.joinSpec(p);
                utils.teleport(p);
            }//&dRandom Team
            if (n.equalsIgnoreCase(utils.color("&dRandom Team"))){
                utils.joinTeam(p);
                utils.teleport(p);
            }
        }
    }
    @EventHandler
    public void interact(PlayerInteractEvent e) {
        try {
            if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(utils.color("&9&lJoin a team!"))) {
                    storage.openTeams((Player) e.getPlayer());
                }
            }
        }catch(NullPointerException ee){         }
    }
}
