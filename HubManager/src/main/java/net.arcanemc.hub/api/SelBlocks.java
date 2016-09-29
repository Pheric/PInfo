package net.arcanemc.hub.api;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SelBlocks { // CREDIT TO Quackster FOR THIS CODE!
    public static List<Block> selBlocksCuboid(Location loc1, Location loc2){
        List<Block> blocks = new ArrayList<>();

        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

        for(int x = bottomBlockX; x <= topBlockX; x++)
        {
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
            {
                for(int y = bottomBlockY; y <= topBlockY; y++)
                {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }
    public static List<Block> selBlocksInRaduis(Location loc,int radius){ // CREDIT TO r0306 FOR THIS CODE!
        final Block block = loc.subtract(0,1,0).getBlock();
        List<Block> blocks = new ArrayList<>();
        for (int x = -(radius); x <= radius; x ++)
        {
            for (int y = -(radius); y <= radius; y ++)
            {
                for (int z = -(radius); z <= radius; z ++)
                {
                    blocks.add(block.getRelative(x,y,z));
                }
            }
        }
        return blocks;
    }
}
