package com.redecommunity.factions.permission.data;

import com.google.common.collect.Maps;
import com.redecommunity.factions.permission.enums.PermissionType;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class Permission {
    private final HashMap<PermissionType, Boolean> permissions;

    public Boolean addPermission(PermissionType permissionType, Boolean value) {
        return this.permissions.put(
                permissionType,
                value
        );
    }

    public Boolean has(PermissionType permissionType) {
        return this.permissions.getOrDefault(
                permissionType,
                false
        );
    }

    public static Permission toPermission(ResultSet resultSet) throws SQLException {
        HashMap<PermissionType, Boolean> permissions = Maps.newHashMap();

        for (PermissionType permissionType : PermissionType.values()) {
            permissions.put(
                    permissionType,
                    resultSet.getBoolean(permissionType.getColumnName())
            );
        }

        return new Permission(
                permissions
        );
    }
}
