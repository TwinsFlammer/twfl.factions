package com.redecommunity.factions.faction.data;

import com.redecommunity.factions.battle.data.Battle;
import com.redecommunity.factions.faction.enums.Relation;
import com.redecommunity.factions.generator.data.Generator;
import com.redecommunity.factions.history.data.History;
import com.redecommunity.factions.land.data.Land;
import com.redecommunity.factions.permission.data.Permission;
import com.redecommunity.factions.user.FUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;

/**
 * Created by @SrGutyerrez
 */
@AllArgsConstructor
@Setter
public class Faction {
    @Getter
    private final Integer id;

    @Getter
    private String tag, name;

    @Getter
    private final List<FUser> members;
    @Getter
    private final List<Land> lands, protectedLands;
    @Getter
    private final HashMap<Faction, Relation> relations;

    @Getter
    private Location homeLocation;

    @Getter
    private final List<Battle> battles;

    private Boolean defaultFaction;

    @Getter
    private final List<Generator> generators;
    @Getter
    private final HashMap<Relation, Permission> permissions;
    @Getter
    private final List<History> history;
    @Getter
    private final ChatColor tagColor;
}
