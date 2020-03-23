package com.redecommunity.factions.user.dao;

import com.google.common.collect.Maps;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import com.redecommunity.factions.faction.enums.Role;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.user.manager.FUserManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by @SrGutyerrez
 */
public class FUserDao<T extends FUser> extends Table {
    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`user_id` INTEGER NOT NULL," +
                                "`last_login` LONG," +
                                "`role` VARCHAR(255) NOT NULL," +
                                "`power` DOUBLE NOT NULL," +
                                "`power_max` DOUBLE NOT NULL," +
                                "`map_auto_updating` BOOLEAN NOT NULL," +
                                "`flying` BOOLEAN NOT NULL," +
                                "`seeing_chunks` BOOLEAN NOT NULL," +
                                "`overriding` BOOLEAN NOT NULL," +
                                "`kills_civilian` INTEGER NOT NULL," +
                                "`kills_neutral` INTEGER NOT NULL," +
                                "`kills_enemy` INTEGER NOT NULL," +
                                "`deaths_civilian` INTEGER NOT NULL," +
                                "`deaths_neutral` INTEGER NOT NULL," +
                                "`deaths_enemy` INTEGER NOT NULL," +
                                "`war_wins` INTEGER NOT NULL" +
                                ");",
                        this.getTableName()
                )
        );
    }

    @Override
    public String getDatabaseName() {
        return "server";
    }

    @Override
    public String getTableName() {
        return "server_faction_user";
    }

    public void insert(T fUser) {
        String query = String.format(
                "INSERT INTO %s " +
                        "(" +
                        "`user_id`," +
                        "`last_login`," +
                        "`role`," +
                        "`power`," +
                        "`power_max`," +
                        "`map_auto_updating`," +
                        "`flying`," +
                        "`seeing_chunks`," +
                        "`overriding`," +
                        "`kills_civilian`," +
                        "`kills_neutral`," +
                        "`kills_enemy`," +
                        "`deaths_civilian`," +
                        "`deaths_neutral`," +
                        "`deaths_enemy`," +
                        "`war_wins`" +
                        ")" +
                        " VALUES " +
                        "(" +
                        "%d," +
                        "%d," +
                        "'%s'," +
                        "%g," +
                        "%g," +
                        "%b," +
                        "%b," +
                        "%b," +
                        "%b," +
                        "%d," +
                        "%d," +
                        "%d," +
                        "%d" +
                        "%d," +
                        "%d," +
                        "%d" +
                        ");",
                this.getTableName(),
                fUser.getId(),
                fUser.getLastLogin(),
                fUser.getRole().toString(),
                fUser.getPower(),
                fUser.getPowerMax(),
                fUser.isMapAutoUpdating(),
                fUser.isFlying(),
                fUser.isSeeingChunks(),
                fUser.isOverriding(),
                fUser.getKillsCivilian(),
                fUser.getKillsNeutral(),
                fUser.getKillsEnemy(),
                fUser.getDeathsCivilian(),
                fUser.getDeathsNeutral(),
                fUser.getDeathsEnemy(),
                fUser.getWarWins()
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
                FUser fUser = FUserManager.getFUser(value);

                this.insert(fUser);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public <K, V extends Integer> Object[] findOne(K key, V value) {
        String query = String.format(
                "SELECT * FROM %s WHERE `%s`=%d",
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
                return new Object[] {
                        resultSet.getLong("last_login"),
                        resultSet.getString("role"),
                        resultSet.getDouble("power"),
                        resultSet.getDouble("power_max"),
                        resultSet.getBoolean("map_auto_updating"),
                        resultSet.getBoolean("flying"),
                        resultSet.getBoolean("seeing_chunks"),
                        resultSet.getBoolean("overriding"),
                        resultSet.getInt("kills_civilian"),
                        resultSet.getInt("kills_neutral"),
                        resultSet.getInt("kills_enemy"),
                        resultSet.getInt("deaths_civilian"),
                        resultSet.getInt("deaths_neutral"),
                        resultSet.getInt("deaths_enemy"),
                        resultSet.getInt("war_wins")
                };
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
