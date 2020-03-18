package com.redecommunity.factions.entity.member.data;

import com.redecommunity.factions.entity.faction.data.Faction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by @SrGutyerrez
 */
@AllArgsConstructor
public class Member {
    @Getter
    private final Integer id;

    @Getter
    @Setter
    private Faction faction;
}
