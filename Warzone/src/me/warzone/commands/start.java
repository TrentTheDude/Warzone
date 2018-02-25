package me.warzone.commands;

import me.warzone.storage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class start implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("start")){
            if (sender.hasPermission("op")){
                storage.startCountDown();
            }
        }
        return false;
    }
}
