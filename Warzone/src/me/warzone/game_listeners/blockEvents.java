package me.warzone.game_listeners;

import me.warzone.storage;
import me.warzone.utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class blockEvents  implements Listener{

    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();
        //  utils.broadcast(block.getType().name());
        if (!e.isCancelled()) {
            if (!storage.build.contains(p)) {
                if (!utils.isStarted()) {
                    e.setCancelled(true);
                    p.sendMessage(utils.color("&a> You don't have build mode enabled!"));
                }
            }
            if (utils.isStarted()) {
                if (!storage.player_placed.contains(block)) {
                    //this is original world block
                    //let's record the block broken so we can replace it before the game ends
                    //  storage.broken_location_block.put(block.getLocation(), block);

                    storage.broken_material.add(block.getType());
                    storage.broken_block.add(block);
                    storage.broken_data.add(block.getData());

                    //todo: make a protection system later to check in here

                    if (block.getType().equals(Material.WOOL)) {
                        if (storage.red.contains(p)) {
                            if (block.getData() == 11) {
                                //blue wool
                                storage.blue_wool--;//remove 1 from the count
                                utils.addCoins(p.getUniqueId(), 5);
                                utils.broadcast("&a> &c" + p.getName() + " &ahas broken a &bBlue Wool&7 (&b" + storage.blue_wool + "&7/&b10&7)");
                                if (storage.blue_wool == 0) {
                                    //end game forcefully
                                    storage.redWin();
                                    storage.endGame();
                                }
                            } else if (block.getData() == 14) {
                                p.sendMessage(utils.color("&aYou can't break your own wool!"));
                                e.setCancelled(true);
                            }
                        }
                        if (storage.blue.contains(p)) {
                            if (block.getData() == 14) {
                                //blue wool
                                storage.red_wool--;//remove 1 from the count
                                utils.addCoins(p.getUniqueId(), 5);
                                utils.broadcast("&a> &b" + p.getName() + " &ahas broken a &cRed Wool&7 (&c" + storage.red_wool + "&7/&c10&7)");
                                if (storage.red_wool == 0) {
                                    //end game forcefully
                                    storage.blueWin();
                                    storage.endGame();
                                }
                            } else if (block.getData() == 11) {
                                p.sendMessage(utils.color("&aYou can't break your own wool!"));
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void placeBlock(BlockPlaceEvent e){
        Player p = e.getPlayer();
        Block block = e.getBlock();
        if (!e.isCancelled()) {
            if (utils.isStarted()) {
                if (!storage.player_placed.contains(block)) {
                    storage.player_placed.add(block);
                }
            }
        }
    }
}