package com.redecommunity.factions.land.factory;

import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.land.data.Land;
import org.bukkit.Chunk;

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
                .orElse(null);

        return (L) land;
    }
}
