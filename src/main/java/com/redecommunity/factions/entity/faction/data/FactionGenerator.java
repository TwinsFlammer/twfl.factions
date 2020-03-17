package com.redecommunity.factions.entity.faction.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class FactionGenerator {
    @Getter
    private final Integer id;
    @Getter
    private final ItemStack itemStack;

    @Getter
    @Setter
    private Location location;

    @Getter
    private final Long time;

    @Setter
    private Long collectedTime;

    public Boolean canCollect() {
        return System.currentTimeMillis() >= this.collectedTime;
    }
}
