package me.warzone.commands;

import me.warzone.utils;
import me.warzone.world.mapSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class map implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("map")){
            if (sender instanceof Player){
                Player p = (Player)sender;
                if (args.length == 0){
                    utils.broadcast(mapSystem.maxMaps()+"");
                    p.sendMessage(utils.color("&a> The current map is: &l"+ mapSystem.getMapName()+"&a, built by: &l"+mapSystem.getMapAuthor()));
                    p.sendMessage(utils.color("&a> Other available maps are:"));
                    for (int a = 0; a < mapSystem.maxMaps(); a++){
                        String n = mapSystem.getMapConfig(a).getString("name");
                        p.sendMessage(utils.color("&a- &b&l"+n));
                    }
                }
            }
        }
        return false;
    }
}
