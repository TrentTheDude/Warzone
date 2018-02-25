package me.warzone.commands;

import me.warzone.storage;
import me.warzone.utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class build implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("build")){
            Player p = (Player)sender;
            if (p.hasPermission("op")){
                if (storage.build.contains(p)){
                    storage.build.remove(p);
                    p.sendMessage(utils.color("&a> Building mode turned &coff&a."));
                }else{
                    storage.build.add(p);
                    p.sendMessage(utils.color("&a> Building mode turned &bon&a."));
                }
            }
        }
        return false;
    }
}
