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
            case MEMBER:
                return OFFICER;
            case RECRUIT:
                return MEMBER;
            default:
                return this;
        }
    }

    public Role getPrevious() {
        switch (this) {
            case OFFICER:
                return MEMBER;
            case MEMBER:
                return RECRUIT;
            default:
                return null;
        }
    }

    public Boolean isHigherThan(Role role) {
        switch (role) {
            case MEMBER:
                return this == RECRUIT;
            case RECRUIT:
                return this == MEMBER;
            default:
                return true;
        }
    }
}
