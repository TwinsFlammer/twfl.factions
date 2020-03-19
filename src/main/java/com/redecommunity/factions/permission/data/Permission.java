package com.redecommunity.factions.permission.data;

import com.redecommunity.factions.permission.enums.PermissionType;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class Permission {
    private final HashMap<PermissionType, Boolean> permissions;
}
