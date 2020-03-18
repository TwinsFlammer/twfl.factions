package com.redecommunity.factions.permission.data;

import com.redecommunity.factions.permission.enums.PermissionType;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class Permission {
    private final List<PermissionType> permissions;
}
