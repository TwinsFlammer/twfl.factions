package com.redecommunity.factions.entity.permission.data;

import com.redecommunity.factions.entity.permission.enums.PermissionType;
import lombok.AllArgsConstructor;

import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
@AllArgsConstructor
public class Permission {
    private Set<PermissionType> permissions;
}
