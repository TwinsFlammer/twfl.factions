package com.redecommunity.factions.entity.faction.data;

import com.redecommunity.factions.entity.faction.enums.BattleState;
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
    private final Integer id;
    @Getter
    private final Faction challenger, challenged;
    @Getter
    private final Long time;

    @Getter
    @Setter
    private Long duration;

    @Getter
    @Setter
    private BattleState battleState;

    @Getter
    private final HashMap<?, ?> kills, deaths;
}
