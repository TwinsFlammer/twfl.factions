package com.redecommunity.factions.relation.enums;

import com.redecommunity.api.spigot.inventory.item.CustomItem;
import com.redecommunity.common.shared.util.Helper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public enum FactionRelation {
    ALLY(
            "aliada",
            "aliadas",
            "aliado",
            Color.LIME,
            ChatColor.GREEN),
    NEUTRAL(
            "neutra",
            "neutras",
            "neutro",
            Color.WHITE,
            ChatColor.WHITE),
    ENEMY(
            "inimiga",
            "inimigas",
            "inimigo",
            Color.RED,
            ChatColor.RED);

    @Getter
    private final String name, pluralName, manlyName;
    @Getter
    private final Color color;
    @Getter
    private final ChatColor chatColor;

    public CustomItem getIcon() {
        return new CustomItem(Material.LEATHER_CHESTPLATE)
                .name(this.chatColor + Helper.capitalize(this.name));
    }
}
