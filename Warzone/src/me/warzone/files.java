package me.warzone;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class files {

    public static File folder = new File("plugins/Warzone");
    public static File mapFolder = new File("plugins/Warzone/Maps");

    public static File config = new File(folder.getAbsoluteFile()+"/config.yml");
    public static FileConfiguration c = YamlConfiguration.loadConfiguration(config);
    public static File data = new File(folder.getAbsoluteFile()+"/data.yml");
    public static FileConfiguration d = YamlConfiguration.loadConfiguration(data);

    public static void setup(){
        if (!folder.exists()){
            folder.mkdir();//create folder as a folder
        }
        if (!mapFolder.exists()){
            mapFolder.mkdir();//create folder as a folder
        }
        if (!config.exists()){
            try{
                config.createNewFile();

                c.set("current_map", 1);//default starting map we're using, we log this because if the server goes down mid-game, it knows what map to continue on.

                saveConfig();//we save it because it was edited from the actual program we're writing rn.

            }catch(IOException e){
                e.printStackTrace();//catch error if directory is not found.
            }
        }
        if (!data.exists()){
            try{
                data.createNewFile();
            }catch(IOException e){
                e.printStackTrace();//catch error if directory is not found.
            }
        }
    }
    public static void saveConfig(){
        try{
            c.save(config);
        }catch(IOException e){
            e.printStackTrace();//catch error if file isn't there
        }
    }
    public static void saveData(){
        try{
            d.save(data);
        }catch(IOException e){
            e.printStackTrace();//catch error if file isn't there
        }
    }
    public static void loadConfig(){
        //only running this when we manually modify the file directly
        c = YamlConfiguration.loadConfiguration(config);
    }
    public static void loadData(){
        //only running this when we manually modify the file directly
        d = YamlConfiguration.loadConfiguration(data);
    }

    public static List<File> getMaps(){
        List<File> maps = new ArrayList<>();
        File folder = mapFolder;
        if (folder != null){
            for (File file : folder.listFiles()){
                if (file.isDirectory()){
                    //this is good we want this
                    maps.add(file);
                }
            }
        }
        return maps;
    }

}
