package com.redecommunity.factions.history.data;

import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.manager.FactionManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public class History {
    private final String oldTag, newTag, oldName, newName;
    private final Long time;

    public Integer getFactionId() {
        return FactionManager.getFactions()
                .stream()
                .filter(faction -> faction.getHistory().contains(this))
                .map(Faction::getId)
                .findFirst()
                .orElse(null);
    }

    public static History toHistory(ResultSet resultSet) throws SQLException {
        return new History(
                resultSet.getString("old_tag"),
                resultSet.getString("new_tag"),
                resultSet.getString("old_name"),
                resultSet.getString("new_name"),
                resultSet.getLong("time")
        );
    }
}
