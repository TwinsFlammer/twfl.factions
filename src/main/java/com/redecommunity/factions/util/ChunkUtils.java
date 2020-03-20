package com.redecommunity.factions.util;

import com.google.common.collect.Sets;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.Collection;

/**
 * Created by @SrGutyerrez
 */
public class ChunkUtils {
    public static Collection<Chunk>  getChunksAround(Chunk chunk, Integer radius) {
        Collection<Chunk> chunks = Sets.newHashSet();

        World world = chunk.getWorld();

        if (radius < 1) radius = 1;

        Integer baseX = chunk.getX(), baseZ = chunk.getZ();

        for (int x = baseX - radius; x <= baseX + radius; x++) {
            for (int z = baseZ - radius; z <= baseZ + radius; z++) {
                if (x != baseX || z != baseZ) {
                    Chunk chunk1 = world.getChunkAt(x, z);

                    chunks.add(chunk1);
                }
            }
        }

        return chunks;
    }
}
