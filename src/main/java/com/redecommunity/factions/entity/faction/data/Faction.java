package com.redecommunity.factions.entity.faction.data;

import com.google.common.collect.Multimap;
import com.redecommunity.factions.relation.enums.FactionRelation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.*;

/**
 * Created by @SrGutyerrez
 */
@AllArgsConstructor
public class Faction {
    @Getter
    private final Integer id;
    @Getter
    private String tag, name;
    @Getter
    private final Set<Integer> membersId;
    @Getter
    private final Set<FactionLand> lands, protectedLands;
    @Getter
    private final Multimap<FactionRelation, Integer> relationsId;
    @Getter
    private Location homeLocation;

    private final Set<Battle> battles;

    private Boolean defaultFaction;

    @Getter
    private final Set<FactionGenerator> generators;
    @Getter
    private final HashMap<?, ?> permissions;
    @Getter
    private final TreeSet<?> nameHistory;
    @Getter
    private ChatColor tagColor;

    public Boolean isDefault() {
        return this.defaultFaction;
    }
}
