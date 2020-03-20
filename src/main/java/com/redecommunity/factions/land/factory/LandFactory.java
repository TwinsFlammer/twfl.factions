package com.redecommunity.factions.land.factory;

import com.redecommunity.factions.Factions;
import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.land.data.Land;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

/**
 * Created by @SrGutyerrez
 */
public class LandFactory<L extends Land> {
    public L getLand(Chunk chunk) {
        Land land = FactionManager.getFactions()
                .stream()
                .filter(faction1 -> faction1.getLands()
                        .stream()
                        .anyMatch(land1 -> land1.isSimilar(chunk))
                )
                .findFirst()
                .flatMap(faction1 -> faction1.getLands()
                        .stream()
                        .filter(land1 -> land1.isSimilar(chunk))
                        .findFirst()
                )
                .orElse(
                        new Land(
                                0,
                                Factions.ID_NONE,
                                chunk.getWorld().getName(),
                                chunk.getX(),
                                chunk.getZ(),
                                System.currentTimeMillis(),
                                -1L,
                                null,
                                false
                        )
                );

        return (L) land;
    }

    public L getLand(World world, Integer x, Integer z) {
        Chunk chunk = world.getChunkAt(x, z);

        return this.getLand(chunk);
    }

    public L getLand(String worldName, Integer x, Integer z) {
        World world = Bukkit.getWorld(worldName);

        return this.getLand(world, x, z);
    }
}
