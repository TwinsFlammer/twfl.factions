package com.redecommunity.factions.faction.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Color;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public enum Relation {
    NEUTRAL(
            "neutra",
            "Neutra",
            "Neutro",
            Color.WHITE,
            ChatColor.WHITE
    ),
    ENEMY(
            "inimiga",
            "Inimiga",
            "Inimigo",
            Color.RED,
            ChatColor.RED
    ),
    ALLY(
            "aliada",
            "Aliada",
            "Aliado",
            Color.GREEN,
            ChatColor.GREEN
    );

    @Getter
    private final String name, displayName, sampleName;
    @Getter
    private final Color color;
    @Getter
    private final ChatColor chatColor;
}
