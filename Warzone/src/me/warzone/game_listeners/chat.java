package me.warzone.game_listeners;

import me.warzone.utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class chat implements Listener {

    @EventHandler
    public void preChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        utils.updatePlayer(p);
    }
}