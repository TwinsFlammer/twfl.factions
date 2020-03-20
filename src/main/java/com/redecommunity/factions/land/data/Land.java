package com.redecommunity.factions.land.data;

import com.google.common.collect.Lists;
import com.redecommunity.factions.Factions;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.util.ChunkUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by @SrGutyerrez
 */
@AllArgsConstructor
public class Land {
    private static final Long UNDER_ATTACK_MILLIS = TimeUnit.MINUTES.toMillis(5);

    @Getter
    private final Integer id;
    @Getter
    private Integer factionId;
    @Getter
    private final String worldName;
    @Getter
    private final Integer x, z;
    @Getter
    private final Long claimedAt, duration;

    @Getter
    @Setter
    private Long attackTime;

    @Setter
    private Boolean temporary;

    public Boolean isTemporary() {
        return this.temporary;
    }

    public Boolean isSimilar(Chunk chunk) {
        World world = chunk.getWorld();

        String worldName = world.getName();
        Integer x = chunk.getX(), z = chunk.getZ();

        return this.worldName.equals(worldName) && this.x.equals(x) && this.z.equals(z);
    }

    public Chunk getChunk() {
        World world = Bukkit.getWorld(this.worldName);

        return world.getChunkAt(this.x, this.z);
    }

    public Faction getFaction() {
        return FactionManager.getFaction(this.factionId);
    }

    public List<Faction> getNearbyFactions() {
        return ChunkUtils.getChunksAround(this.getChunk(), 3)
                .stream()
                .map(Factions.getLandFactory()::getLand)
                .filter(land -> !land.isDefaultLand())
                .filter(land -> !land.getFaction().isSimilar(this.getFaction()))
                .filter(land -> !land.isTemporary())
                .map(Land::getFaction)
                .collect(Collectors.toList());
    }

    public List<Land> getConnectedLands() {
        List<Land> lands = Lists.newArrayList();

        Integer[] values = { -1, 1 };

        for (int x = 0; x < values.length; x++) {
            Integer newX = this.x + values[x];
            Integer newZ = this.z + values[x];

            Land land1 = Factions.getLandFactory().getLand(this.worldName, newX, this.z);
            Land land2 = Factions.getLandFactory().getLand(this.worldName, this.x, newZ);

            if (land1 != null && land1.getFactionId().equals(this.factionId))
                lands.add(land1);

            if (land2 != null && land2.getFactionId().equals(this.factionId))
                lands.add(land2);
        }

        return lands;
    }

    public Boolean isDefaultLand() {
        return this.factionId.equals(Factions.ID_NONE) || this.factionId.equals(Factions.ID_SAFEZONE) || this.factionId.equals(Factions.ID_WARZONE);
    }
}
