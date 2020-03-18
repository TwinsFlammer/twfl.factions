package com.redecommunity.factions.entity.faction.manager;

import com.google.common.collect.Lists;
import com.redecommunity.api.spigot.util.serialize.LocationSerialize;
import com.redecommunity.factions.entity.faction.data.Faction;
import com.redecommunity.factions.entity.member.data.Member;
import lombok.Getter;
import org.bukkit.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class FactionManager {
    @Getter
    private static final List<Faction> FACTIONS = Lists.newArrayList();

    public static Faction getFaction(Integer id) {
        return FactionManager.FACTIONS.stream()
                .filter(faction -> faction.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static Faction getFaction(String nameOrTag) {
        return FactionManager.FACTIONS.stream()
                .filter(faction -> faction.getTag().equalsIgnoreCase(nameOrTag) || faction.getName().equalsIgnoreCase(nameOrTag))
                .findFirst()
                .orElse(null);
    }

    public static Faction getFaction(Member member) {
        return FactionManager.FACTIONS.stream()
                .filter(faction -> {
                    List<Member> members = faction.getMembers();

                    return members.stream()
                            .anyMatch(member1 -> member1.equals(member));
                })
                .findFirst()
                .orElse(null);
    }

    public static Faction toFaction(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String tag = resultSet.getString("tag");
        String name = resultSet.getString("name");

        String serializedLocation = resultSet.getString("home_location");

        Location homeLocation = serializedLocation == null || serializedLocation.isEmpty() ? null : LocationSerialize.toLocation(serializedLocation);

        Boolean defaultFaction = resultSet.getBoolean("default_faction");

        return new Faction(
                id,
                tag,
                name,
                null,
                null,
                null,
                null,
                homeLocation,
                null,
                defaultFaction,
                null,
                null,
                null,
                null
        );
    }
}
