package me.warzone.game_listeners;

import me.warzone.files;
import me.warzone.storage;
import me.warzone.utils;
import me.warzone.world.mapSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class joinLeave implements Listener{

    @EventHandler
    public void join(PlayerJoinEvent e){
        Player p = e.getPlayer();
        //check if their data is there or not
        if (utils.getRank(p.getUniqueId())==null || utils.getRank(p.getUniqueId()).equalsIgnoreCase("")){
            //means they're either new or something got fucked up
            //setup the player for the first time
            files.d.set(p.getUniqueId()+".username", p.getName());
            files.d.set(p.getUniqueId()+".rank", "Default");
            files.d.set(p.getUniqueId()+".coins", 0);
            files.d.set(p.getUniqueId()+".kills", 0);
            files.d.set(p.getUniqueId()+".deaths", 0);
            files.saveData();
        }
        if (!p.hasPlayedBefore()){
            //new, say new join message
            e.setJoinMessage(utils.color("&a> &7"+p.getName()+" &d(new)"));
        }else{
            e.setJoinMessage(utils.color("&a> &7"+p.getName()));
        }
        //if they changed their name, update the username in their data
        if (!files.d.getString(p.getUniqueId()+".username").equalsIgnoreCase(p.getName())){
            files.d.set(p.getUniqueId()+".username", p.getName());
            files.saveData();
        }
        if (utils.isStarted()){
            utils.joinSpec(p);
            utils.teleport(p);
        }
    }
    @EventHandler
    public void leave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        utils.leaveTeam(p);
    }

    @EventHandler
    public void move(PlayerMoveEvent e){
        if (storage.lobby){
            if (!storage.starting){
                // kk let's check for players then start
                if (Bukkit.getOnlinePlayers().size() >= 2){
                    utils.broadcast("&a> &bFound enough players to start a match!");
                    storage.startCountDown();
                }
            }
        }
    }
}
