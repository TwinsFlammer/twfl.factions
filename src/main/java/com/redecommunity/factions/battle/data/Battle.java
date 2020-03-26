package com.redecommunity.factions.battle.data;

import com.google.common.collect.Maps;
import com.redecommunity.factions.battle.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by @SrGutyerrez
 */
@AllArgsConstructor
public class Battle {
    @Getter
    private final Integer id, challengerId, challengedId;
    @Getter
    private final Long time;

    @Getter
    @Setter
    private Long duration;

    @Getter
    @Setter
    private State state;

    @Getter
    private final HashMap<Integer, Integer> kills, deaths;

    public Integer getChallengerKills() {
        return this.kills.getOrDefault(this.challengerId, 0);
    }

    public Integer getChallengedKills() {
        return this.kills.getOrDefault(this.challengedId, 0);
    }

    public Integer getChallengerDeaths() {
        return this.deaths.getOrDefault(this.challengerId, 0);
    }

    public Integer getChallengedDeaths() {
        return this.deaths.getOrDefault(this.challengedId, 0);
    }

    public static Battle toBattle(ResultSet resultSet) throws SQLException {
        Integer challengerId = resultSet.getInt("challenger_id"),
                challengedId = resultSet.getInt("challenged_id");

        HashMap<Integer, Integer> kills = Maps.newHashMap(), deaths = Maps.newHashMap();

        kills.put(
                challengerId,
                resultSet.getInt("challenger_kills")
        );

        kills.put(
                challengedId,
                resultSet.getInt("challenged_kills")
        );

        deaths.put(
                challengerId,
                resultSet.getInt("challenger_deaths")
        );

        deaths.put(
                challengedId,
                resultSet.getInt("challenged_deaths")
        );

        State state = State.valueOf(
                resultSet.getString("state")
        );

        return new Battle(
                resultSet.getInt("id"),
                resultSet.getInt("challenger_id"),
                resultSet.getInt("challenged_id"),
                resultSet.getLong("time"),
                resultSet.getLong("duration"),
                state,
                kills,
                deaths
        );
    }
}