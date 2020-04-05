package com.redecommunity.factions.land.dao;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.factions.Factions;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.land.data.Land;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class LandDAO<L extends Land, F extends Faction> extends Table {
    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`faction_id` INTEGER NOT NULL," +
                                "`world_name` VARCHAR(50) NOT NULL," +
                                "`x` INTEGER NOT NULL," +
                                "`z` INTEGER NOT NULL," +
                                "`temporary` BOOLEAN," +
                                "`claimed_at` LONG NOT NULL" +
                                "`duration` LONG NOT NULL" +
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
        return "server_faction_land";
    }

    public L insert(L land, F faction) {
        String query = String.format(
                "INSERT INTO %s " +
                        "(" +
                        "`faction_id`," +
                        "`world_name`," +
                        "`x`," +
                        "`z`," +
                        "`temporary`," +
                        "`claimed_at`," +
                        "`duration`" +
                        ")" +
                        " VALUES " +
                        "(" +
                        "%d," +
                        "'%s'," +
                        "%d," +
                        "%d," +
                        "%b," +
                        "%d," +
                        "%d" +
                        ")",
                this.getTableName(),
                faction.getId(),
                land.getWorldName(),
                land.getX(),
                land.getZ(),
                land.isTemporary(),
                land.getClaimedAt(),
                land.getDuration()
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next())
                return (L) new Land(
                        resultSet.getInt("id"),
                        land.getFactionId(),
                        land.getWorldName(),
                        land.getX(),
                        land.getZ(),
                        land.getDuration(),
                        land.getClaimedAt(),
                        null,
                        land.isTemporary()
                );
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public <K, V> void delete(K key, V value) {
        String query = String.format(
                "DELETE FROM %s WHERE `%s`=%d",
                this.getTableName(),
                key,
                value
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
                Land land = Factions.getLandFactory().getLand(value);

                if (land != null)
                    this.insert(land);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public <K, V> Set<L> findAll(K key, V value) {
        String query = String.format(
                "SELECT * FROM %s WHERE `%s`=%d;",
                this.getTableName(),
                key,
                value
        );

        Set<L> lands = Sets.newConcurrentHashSet();

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                Land land = Land.toLand(resultSet);

                lands.add((L) land);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return lands;
    }
}
