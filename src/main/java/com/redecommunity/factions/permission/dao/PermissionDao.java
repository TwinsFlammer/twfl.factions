package com.redecommunity.factions.permission.dao;

import com.google.common.collect.Maps;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.factions.permission.data.Permission;
import com.redecommunity.factions.permission.enums.PermissionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Created by @SrGutyerrez
 */
public class PermissionDao extends Table {
    @Override
    public void createTable() {
        String permissionsColumns = Arrays.stream(PermissionType.values())
                .map(permissionType -> "`" + permissionType.name() + "`")
                .distinct()
                .collect(Collectors.joining(", "));

        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NO NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`user_id` INTEGER," +
                                "`faction_id` INTEGER," +
                                "%s" +
                                ");",
                        this.getTableName(),
                        permissionsColumns
                )
        );
    }

    @Override
    public String getDatabaseName() {
        return "server";
    }

    @Override
    public String getTableName() {
        return "server_permission";
    }

    public <K, V extends Integer, T extends Permission> void insert(K key, V value, T permission) {
        String permissionsColumns = Arrays.stream(PermissionType.values())
                .map(permissionType -> "`" + permissionType.name() + "`")
                .distinct()
                .collect(Collectors.joining(", "));

        String permissionsValues = Arrays.stream(PermissionType.values())
                .map(permissionType -> permission.has(permissionType).toString())
                .distinct()
                .collect(Collectors.joining(", "));

        String query = String.format(
                "INSERT INTO %s " +
                        "(" +
                        "`%s`," +
                        "%s" +
                        ")" +
                        " VALUES " +
                        "(" +
                        "%d," +
                        "%s" +
                        ")",
                this.getTableName(),
                key,
                permissionsColumns,
                value,
                permissionsValues
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public <K, V, U, I extends Integer> void update(HashMap<K, V> keys, U key, I value) {
        String parameters = this.generateParameters(keys);

        String query = String.format(
                "UPDATE %s SET %s WHERE `%s`=%d",
                this.getTableName(),
                parameters,
                key,
                value
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            Integer affectedRows = preparedStatement.executeUpdate();

            if (affectedRows <= 0) {
                Permission permission = new Permission(
                        Maps.newHashMap()
                );

                keys.forEach((k, v) -> {
                    String permissionTypeName = (String) k;
                    Boolean has = (Boolean) v;

                    Arrays.stream(PermissionType.values())
                            .filter(permissionType1 -> permissionType1.getColumnName().equalsIgnoreCase(permissionTypeName))
                            .findFirst()
                            .ifPresent(permissionType -> permission.addPermission(permissionType, has));
                });

                this.insert(key, value, permission);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public <K, V extends Integer, T> T findOne(K key, V value) {
        String query = String.format(
                "SELECT * FROM %s WHERE `%s`=%d;",
                this.getTableName(),
                key,
                value
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            if (resultSet.next())
                return (T) Permission.toPermission(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return (T) new Permission(
                Maps.newHashMap()
        );
    }
}
