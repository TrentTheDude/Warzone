package me.warzone.guard;

import me.warzone.utils;
import me.warzone.world.mapSystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class guardManager
        implements Listener
{
    static int r = 5;

    public static void protectBlocks()
    {
        protectNearbyblocks(mapSystem.getRedSpawn(), r);
        protectNearbyblocks(mapSystem.getBlueSpawn(), r);

        protectNearbyblocks(mapSystem.getRedTower1(), r);
        protectNearbyblocks(mapSystem.getRedTower2(), r);

        protectNearbyblocks(mapSystem.getBlueTower1(), r);
        protectNearbyblocks(mapSystem.getBlueTower2(), r);


        utils.broadcast("&a> &cSetup protected zones.");
    }

    public static List<Block> blocks = new ArrayList();

    public static List<Block> protectNearbyblocks(Location location, int radius)
    {
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }
}