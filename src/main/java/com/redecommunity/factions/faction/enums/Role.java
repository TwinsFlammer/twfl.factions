package com.redecommunity.factions.faction.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public enum Role {
    NONE(
            "nenhum",
            "Nenhum",
            ""
    ),
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
            "neutra",
            "Neutra",
            null
    ),
    ENEMY(
            "inimiga",
            "Inimiga",
            null
    ),
    ALLY(
            "aliada",
            "Aliada",
            null
    );

    @Getter
    private final String name, displayName, prefix;

    public Role getNext() {
        switch (this) {
            case ALLY:
            case ENEMY:
            case NONE:
            case NEUTRAL:
            case LEADER:
            case OFFICER:
                return null;
            case MEMBER:
                return OFFICER;
            case RECRUIT:
                return MEMBER;
        }

        return null;
    }
}
