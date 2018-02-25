package me.warzone;

import me.warzone.commands.*;
import me.warzone.game_listeners.blockEvents;
import me.warzone.game_listeners.chat;
import me.warzone.game_listeners.inventoryClick;
import me.warzone.game_listeners.joinLeave;
import me.warzone.guard.breakGuard;
import me.warzone.guard.buildGuard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

public static Main instance;
    public void onEnable(){
        //todo; setup the config and data seting up here
        instance = this;
        files.setup();//setup the files if they're not existing

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new joinLeave(), this);
        pm.registerEvents(new chat(), this);
        pm.registerEvents(new blockEvents(), this);
        pm.registerEvents(new inventoryClick(), this);
        pm.registerEvents(new breakGuard(), this);
        pm.registerEvents(new buildGuard(), this);

        getCommand("rank").setExecutor(new rank());
        getCommand("map").setExecutor(new map());
        getCommand("start").setExecutor(new start());
        getCommand("end").setExecutor(new end());
        getCommand("build").setExecutor(new build());

        //register events later
    }
    public void onDisable(){
        for (Player p : Bukkit.getOnlinePlayers()){
            utils.leaveTeam(p);
            utils.teleport(p);
        }
    }
}
