package me.warzone.guard;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by trent on 2/11/2017.
 */
public class breakGuard implements Listener {

    @EventHandler
    public void onbreak(BlockBreakEvent e) {
        if (guardManager.blocks.contains(e.getBlock())) {
            e.setCancelled(true);
        }
    }
}