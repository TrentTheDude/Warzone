package me.warzone.world;

import me.warzone.files;
import me.warzone.utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class mapSystem {

    public static int maxMaps(){
        int a = 0;
        File dir = new File("plugins/Warzone/maps");
        if (dir.exists()){
            File[] files = dir.listFiles();
            a = files.length;
          //  utils.broadcast(a+" max");
        }
        return a;
    }

    public static void setCurrent(int use){
        files.c.set("current_map", use);
        files.saveConfig();
    }

    public static int currentMap(){
        int current = files.c.getInt("current_map");
        return current;
    }
    public static int nextMap(){
        int current = currentMap();
        int max = maxMaps();
        int next = Math.addExact(current, 1);
        if (next > max){
            //set next to 1
            next = 1;
        }
        return next;
    }

    public static File getMapFile(int index){
        int a = 0;
        File maps = new File("plugins/Warzone/Maps");
        File map = new File("");
        File map_yml = new File("");
        File[] m = maps.listFiles();
       // index = index-1; // so the index can be normal, not in array format (starts with 0; ++)
        if (index != 0){
            index = index-1;
        }
        if (m[index].exists()){
            map = m[index];
            if (new File(map.getAbsolutePath()+"/map.yml").exists()){
                // we have a map.yml file, good
                // don't make map_yml file equal before the if, then it'll throw error if null, not good.
                map_yml = new File(map.getAbsolutePath()+"/map.yml");
            }
        }
        return map_yml;
    }
    public static FileConfiguration getMapConfig(int index){
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(getMapFile(index));
        return yml;
    }

    public static String getMapName(){
        String name = getMapConfig(currentMap()).getString("name");
        return name;
    }
    public static String getMapAuthor(){
        String author = getMapConfig(currentMap()).getString("author");
        return author;
    }
    public static Location decodeLocation(String location){
        String[] loc = location.split(",");
        Location l = new Location(Bukkit.getWorld(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Double.parseDouble(loc[3]));
        l.setYaw(Float.parseFloat(loc[4]));
        return l;
    }
    public static Location getRedSpawn(){
        return decodeLocation(getMapConfig(currentMap()).getString("red_spawn"));
    }
    public static Location getBlueSpawn(){
        return decodeLocation(getMapConfig(currentMap()).getString("blue_spawn"));
    }
    public static Location getSpecSpawn(){
        return decodeLocation(getMapConfig(currentMap()).getString("spec_spawn"));
    }

    public static Location getRedTower1(){
        return decodeLocation(getMapConfig(currentMap()).getString("red_tower1"));
    }
    public static Location getRedTower2(){
        return decodeLocation(getMapConfig(currentMap()).getString("red_tower2"));
    }
    public static Location getBlueTower1(){
        return decodeLocation(getMapConfig(currentMap()).getString("blue_tower1"));
    }
    public static Location getBlueTower2(){
        return decodeLocation(getMapConfig(currentMap()).getString("blue_tower2"));
    }
}