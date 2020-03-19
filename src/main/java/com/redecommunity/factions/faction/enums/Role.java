package com.redecommunity.factions.faction.enums;

import lombok.RequiredArgsConstructor;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public enum Role {
    RECRUIT(
            "recruta",
            "Recruta",
            "-"
    ),
    MEMBER(
            "membro",
            "Membro",
            "+"
    ),
    OFFICER(
            "capitão",
            "Capitão",
            "*"
    ),
    LEADER(
            "líder",
            "Líder",
            "#"
    ),
    NEUTRAL(
            "aliada",
            "Aliada",
            null
    ),
    ENEMY(
            "aliada",
            "Aliada",
            null
    ),
    ALLY(
            "aliada",
            "Aliada",
            null
    );

    private final String name, displayName, prefix;
}
