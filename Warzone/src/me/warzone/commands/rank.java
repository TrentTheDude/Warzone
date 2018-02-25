package me.warzone.commands;

import me.warzone.utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class rank implements CommandExecutor{

    public static String[] prefixes = {"&c[ADMIN]", "&e[MOD]", "&6[SR. MOD]", "&a[VIP]", "&d[MEGA]", "&9[LEGEND]"};

    public static String getPrefix(String rankName){
        String prefix = "";
        for (String p : prefixes){
            if (p.contains(rankName)){
                //this is our prefix, use it
                prefix = p;
            }
        }
        return prefix;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rank")){
            //check for perm, and arg, etc.
            if (sender.hasPermission("staff.admin") && sender instanceof Player){
                Player p = (Player)sender;//the person executing the command, cannot be console
                if (args.length == 2){
                    Player player = Bukkit.getPlayer(args[0]);
                    String rank = args[1];
                    if (player != null) {
                        String[] ranks = {"ADMIN", "MOD", "SR_MOD", "VIP", "MEGA", "LEGEND"};
                        if (Arrays.asList(ranks).contains(rank)) {
                            //set the rank here
                            utils.setRank(player.getUniqueId(), rank);//lol done
                            p.sendMessage(utils.color("Updated " + player.getName() + "'s rank to: " + getPrefix(rank) + "!"));//do that for all chat messages
                            player.sendMessage(utils.color("Your rank has been updated to: " + getPrefix(rank) + "!"));
                        }else{
                            p.sendMessage(utils.color("&aThis rank doesn't exist!"));
                        }
                    }else{
                        p.sendMessage(utils.color("&aCannot find this player!"));
                    }
                    //example: /rank Notch admin, notch is arg[0], admin is arg[1]
                }
            }
        }



        return false;
    }
}
