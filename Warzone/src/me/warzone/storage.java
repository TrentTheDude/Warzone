package me.warzone;

import me.warzone.guard.guardManager;
import me.warzone.world.mapSystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class storage {

    //THIS IS WHERE WE'RE STORING OUR HASHMAPS AND LOCAL DATA THAT ISN'T SAVED

    public static boolean started = false;
    public static boolean starting = false;
    public static boolean end_game = false;
    public static boolean lobby = true;

    public static ArrayList<Player> blue = new ArrayList();
    public static ArrayList<Player> red = new ArrayList();
    public static ArrayList<Player> spec = new ArrayList();
    public static ArrayList<Player> build = new ArrayList();

    public static int red_wool = 10;
    public static int blue_wool = 10;

    public static ArrayList<Block>player_placed = new ArrayList<>();
    public static ArrayList<Material>broken_material = new ArrayList<>();
    public static ArrayList<Block>broken_block = new ArrayList<>();
    public static ArrayList<Byte>broken_data = new ArrayList<>();
    public static HashMap<Location,Block> broken_location_block = new HashMap<>();//this will become huge
    public static HashMap<Block,Block> new_old = new HashMap<>();//this will become huge

public static int time = 10;//seconds

    public static void reset(){
        time = 10;
        red_wool=10;
        blue_wool=10;
        blue.clear();
        red.clear();
        spec.clear();
        broken_material.clear();
        broken_data.clear();
        guardManager.blocks.clear();
         started = false;
         starting = false;
         end_game = false;
         lobby = true;
        Bukkit.getScheduler().cancelAllTasks();
    }

    public static void startCountDown(){
        lobby = false;
        starting = true;
        utils.broadcast("&a&lSTARTING THE GAME... IN "+time+" SECONDS");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                if (time == 0){
                    startGame();
                }else{
                    time--;
             //       utils.broadcast(time+"");
                    if (time == 5||time==3||time==2||time==1){
                        utils.broadcast("&AGame starting in: &l"+time+"&a seconds!");
                    }
                }
            }
        },0L, 20L);
    }
    public static int toSeconds(int ticks){
        return Math.multiplyExact(ticks, 20);
    }
    public static int toMinutes(int time){
        return Math.multiplyExact(toSeconds(time), 60);
    }
    public static void startGame(){
        //todo; teleport to game spawn, give kits, etc.
        starting = false;
        started = true;
        Bukkit.getScheduler().cancelAllTasks();
        guardManager.protectBlocks();
        for (Player p: Bukkit.getOnlinePlayers()){
            utils.joinSpec(p);
            utils.teleport(p);
            //todo make kits
        }
        new BukkitRunnable(){
            public void run(){
                if (started) {
                    utils.broadcast("&aTimes up! Game has stopped!");
                    endGame();
                }
            }
        }.runTaskLater(Main.instance, toMinutes(5));//stop game after 5 minutes
    }
    public static void endGame(){
        started = false;
        end_game = true;

        utils.broadcast("&bGoing to lobby in: &l10&b seconds!");

        utils.resetMap();

        new BukkitRunnable(){
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()){

                    p.teleport(lobby());
                    p.getInventory().clear();
                    utils.leaveTeam(p);
                    reset();
                    //add to current
                    mapSystem.setCurrent(mapSystem.nextMap());

                }
            }
        }.runTaskLater(Main.instance, toSeconds(10));
    }
    public static void blueWin(){
        utils.broadcast("&a> &bBlue Team&a wins the round! With &b&l"+storage.blue_wool+"&b wool &aleft!");
        for (Player p: blue){
            utils.addCoins(p.getUniqueId(), 10);//10 for winning
        }
    }
    public static void redWin(){
        utils.broadcast("&a> &cRed Team&a wins the round! With &c&l"+storage.red_wool+"&c wool &aleft!");
        for (Player p: red){
            utils.addCoins(p.getUniqueId(), 10);//10 for winning
        }
    }
    public static Location lobby(){
        Location loc = new Location(Bukkit.getWorld("world"), -2,82,84);
        return loc;
    }

    public static void openTeams(Player p){
        Inventory i = Bukkit.createInventory(null, 27, utils.color("&cPick a team!"));
        i.setItem(9, utils.createItem(Material.WOOL, 14, "&cRed Team", "&7Click to join the &cRed Team&7!", storage.red.size()));
        i.setItem(4, utils.createItem(Material.WOOL, 0, "&dRandom Team", "&7Click to join a &dRandom Team&7!", 1));
        i.setItem(22, utils.createItem(Material.WOOL, 7, "&7Spec Team", "&7Click to join the &7Spec Team&7!", storage.spec.size()));
        i.setItem(17, utils.createItem(Material.WOOL, 11, "&bBlue Team", "&7Click to join the &bBlue Team&7!", storage.blue.size()));
        p.openInventory(i);
    }
}
