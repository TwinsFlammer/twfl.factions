package com.redecommunity.factions.battle.data;

import com.redecommunity.factions.battle.enums.BattleState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
    private BattleState battleState;

    @Getter
    private final HashMap<Integer, Integer> kills, deaths;
}