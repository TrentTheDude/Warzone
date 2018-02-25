package me.warzone;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.MultiverseCoreConfiguration;
import com.onarandombox.MultiverseCore.api.MultiverseCoreConfig;
import com.onarandombox.MultiverseCore.api.MultiversePlugin;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.onarandombox.MultiverseCore.listeners.MultiverseCoreListener;
import me.warzone.commands.rank;
import me.warzone.world.mapSystem;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

public class utils {

    public static String color(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static void broadcast(String message){
        Bukkit.broadcastMessage(color(message));
    }

    public static String getRank(UUID playerUUID){
        String rank = files.d.getString(playerUUID+".rank");
        return rank;
    }
    public static int getCoins(UUID playerUUID){
        int coins = files.d.getInt(playerUUID+".coins");
        return coins;
    }
    public static int getKills(UUID playerUUID){
        int kills = files.d.getInt(playerUUID+".kills");
        return kills;
    }
    public static int getDeaths(UUID playerUUID){
        int deaths = files.d.getInt(playerUUID+".deaths");
        return deaths;
    }
    public static Player getPlayer(UUID playerUUID){
        Player player = Bukkit.getPlayer(playerUUID);
        return player;
    }

    public static void addCoins(UUID playerUUID, int amount){
        int coins = getCoins(playerUUID);
        files.d.set(playerUUID+".coins", Math.addExact(coins, amount));
        files.saveData();
        getPlayer(playerUUID).sendMessage(color("&a     +&l"+amount+"&a Coins"));
    }
    public static void addKills(UUID playerUUID, int amount){
        int kills = getCoins(playerUUID);
        files.d.set(playerUUID+".kills", Math.addExact(kills, amount));
        files.saveData();
    }
    public static void addDeaths(UUID playerUUID, int amount){
        int deaths = getDeaths(playerUUID);
        files.d.set(playerUUID+".deaths", Math.addExact(deaths, amount));
        files.saveData();
    }
    public static void removeCoins(UUID playerUUID, int amount){
        int coins = getCoins(playerUUID);
        files.d.set(playerUUID+".coins", Math.subtractExact(coins, amount));
        files.saveData();
    }
    public static void removeKills(UUID playerUUID, int amount){
        int kills = getCoins(playerUUID);
        files.d.set(playerUUID+".kills", Math.subtractExact(kills, amount));
        files.saveData();
    }
    public static void removeDeaths(UUID playerUUID, int amount){
        int deaths = getCoins(playerUUID);
        files.d.set(playerUUID+".deaths", Math.subtractExact(deaths, amount));
        files.saveData();
    }
    public static void setRank(UUID playerUUID, String rank){
        files.d.set(playerUUID+".rank", rank);
        files.saveData();
    }
    public static void updatePlayer(Player p){
        String r = getRank(p.getUniqueId());
        String prefixRank = rank.getPrefix(r);
        p.setDisplayName(prefixRank+" "+p.getName());
    }


    public static void joinRed(Player p){
        if (storage.red.contains(p)) {
            storage.red.remove(p);
        }
        if (storage.blue.contains(p)) {
            storage.blue.remove(p);
        }
        if (storage.spec.contains(p)) {
            storage.spec.remove(p);
        }
            storage.red.add(p);
            p.sendMessage("Joined Red");
        }
    public static void joinBlue(Player p){
        if (storage.red.contains(p)) {
            storage.red.remove(p);
        }
        if (storage.blue.contains(p)) {
            storage.blue.remove(p);
        }
        if (storage.spec.contains(p)) {
            storage.spec.remove(p);
        }
        storage.blue.add(p);
        p.sendMessage("Joined Blue");
    }
    public static void joinSpec(Player p){
        if (storage.red.contains(p)) {
            storage.red.remove(p);
        }
        if (storage.blue.contains(p)) {
            storage.blue.remove(p);
        }
        if (storage.spec.contains(p)) {
            storage.spec.remove(p);
        }
        storage.spec.add(p);
        p.getInventory().addItem(utils.createItem(Material.WOOL, 0, "&9&lJoin a team!", "&9Right click to join a team!", 1));
        p.sendMessage(utils.color("&7Joined Spec"));
    }

    public static void joinTeam(Player p){
        int r = storage.red.size();
        int b = storage.blue.size();

        if (r == b){
            joinRed(p);
        }
        if (r > b){
            joinBlue(p);
        }
        if (b > r){
            joinRed(p);
        }
    }
    public static void leaveTeam(Player p){
        if (storage.red.contains(p)) {
            storage.red.remove(p);
        }
        if (storage.blue.contains(p)) {
            storage.blue.remove(p);
        }
        if (storage.spec.contains(p)) {
            storage.spec.remove(p);
        }
    }
    public static void teleport(Player p){
        if (isStarted()){
            if (storage.red.contains(p)){
                p.teleport(mapSystem.getRedSpawn());
            }
            if (storage.blue.contains(p)){
                p.teleport(mapSystem.getBlueSpawn());
            }
            if (storage.spec.contains(p)){
                p.teleport(mapSystem.getSpecSpawn());
            }
        }
        if (storage.lobby){
            p.teleport(storage.lobby());
        }
    }
    public static boolean isStarted(){
        return storage.started;
    }

    public static void resetMap(){
        String name = mapSystem.getMapName();
        World w = Bukkit.getWorld(name);
        utils.broadcast("&c&lRESETING MAP: &c"+(storage.broken_block.size()+storage.player_placed.size())+" &cblocks to replace...");

        for (int i = storage.player_placed.size() - 1; i >= 0; i--){
         //   broadcast(storage.player_placed.get(i).getType()+","+storage.player_placed.get(i).getWorld().getName());
            storage.player_placed.get(i).setType(Material.AIR);
            storage.player_placed.remove(i);
        }

        for (int i = storage.broken_block.size() - 1; i >= 0; i--){
            Block block = storage.broken_block.get(i);
            Location loc = block.getLocation();

         //   broadcast(block.getType().name()+", "+storage.broken_material.get(i)+", "+block.getLocation());

            w.getBlockAt(loc).setType(storage.broken_material.get(i));
            w.getBlockAt(loc).setData(storage.broken_data.get(i));
            storage.broken_block.remove(block);
            storage.broken_material.remove(i);
            storage.broken_data.remove(i);
        }
        utils.broadcast("&c&lMAP DONE RESETTING!");
    }

    //we'll add more utils down low


    public static ItemStack createItem(Material material, int data, String name, String lore, int amount){
        ItemStack item = new ItemStack(material, amount, (short)data);
        ItemMeta m = item.getItemMeta();
        m.setDisplayName(utils.color(name));
        m.setLore(Arrays.asList(utils.color(lore)));
        item.setItemMeta(m);
        return item;
    }


}
