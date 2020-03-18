package com.redecommunity.factions.entity.faction.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.SimpleDateFormat;

/**
 * Created by @SrGutyerrez
 */
@AllArgsConstructor
public class NameHistory {
    @Getter
    private final String oldName, newName;
    @Getter
    private final Long time;

    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:ss");

        return simpleDateFormat.format(this.time);
    }
}
