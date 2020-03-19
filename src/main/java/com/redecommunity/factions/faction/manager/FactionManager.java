package com.redecommunity.factions.faction.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redecommunity.factions.faction.dao.FactionDao;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.enums.Role;
import com.redecommunity.factions.user.data.FUser;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Objects;

/**
 * Created by @SrGutyerrez
 */
public class FactionManager {
    private static final List<Faction> FACTIONS = Lists.newArrayList();

    public static List<Faction> getFactions() {
        return FactionManager.FACTIONS;
    }

    public static Faction getFaction(Integer id) {
        return FactionManager.FACTIONS
                .stream()
                .filter(Objects::nonNull)
                .filter(faction -> faction.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static Faction getFaction(String nameOrTag) {
        return FactionManager.FACTIONS
                .stream()
                .filter(Objects::nonNull)
                .filter(faction -> faction.getTag().equalsIgnoreCase(nameOrTag) || faction.getName().equalsIgnoreCase(nameOrTag))
                .findFirst()
                .orElse(null);
    }

    public static Faction createNewFaction(String tag, String name, FUser leader, Boolean defaultFaction) {
        Faction faction = FactionManager.createNewFaction(
                0,
                tag,
                name,
                leader,
                defaultFaction
        );

        FactionDao factionDao = new FactionDao();

        faction = factionDao.insert(faction);

        leader.setRole(Role.LEADER);
        leader.setFactionId(faction.getId());

        return faction;
    }

    public static Faction createNewFaction(Integer id, String tag, String name, FUser leader, Boolean defaultFaction) {
        return new Faction(
                id,
                tag,
                name,
                Lists.newArrayList(leader),
                Lists.newArrayList(),
                Lists.newArrayList(),
                Maps.newHashMap(),
                null,
                Lists.newArrayList(),
                defaultFaction,
                Lists.newArrayList(),
                Maps.newHashMap(),
                Lists.newArrayList(),
                ChatColor.GRAY
        );
    }
}