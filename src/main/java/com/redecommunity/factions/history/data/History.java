package com.redecommunity.factions.history.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public class History {
    private final String oldTag, newTag, oldName, newName;
    private final Long time;
}
