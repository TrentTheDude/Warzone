package me.warzone.commands;

import me.warzone.storage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class end implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("end")){
            Player p = (Player)sender;
            if (p.hasPermission("op")){
                storage.endGame();
            }
        }
        return false;
    }
}