package com.redecommunity.factions.faction.dao;

import com.google.common.collect.Maps;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.enums.Relation;
import com.redecommunity.factions.faction.manager.FactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by @SrGutyerrez
 */
public class RelationDAO<F extends Faction, R extends Relation> extends Table {
    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`faction_id` INTEGER NOT NULL," +
                                "`target_id` INTEGER NOT NULL," +
                                "`relation` VARCHAR(255)" +
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
        return "server_faction_relation";
    }

    public void insert(F faction, F target, R relation) {
        String query = String.format(
                "INSERT INTO %s " +
                        "(" +
                        "`faction_id`," +
                        "`target_id`," +
                        "`relation`" +
                        ")" +
                        " VALUES " +
                        "(" +
                        "%d," +
                        "%d," +
                        "'%s'" +
                        ");",
                this.getTableName(),
                faction.getId(),
                target.getId(),
                relation.toString()
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

    @Override
    public <K, V> void delete(K key, V value) {
        String query = String.format(
                "DELETE FROM %s WHERE `%s`=%d;",
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
                "UPDATE %s SET %s WHERE `%s`=%d;",
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
                F faction = (F) FactionManager.getFaction(value);
                Integer targetId = (Integer) keys.get("target_id");
                F target = (F) FactionManager.getFaction(targetId);
                R relation = (R) Relation.valueOf((String) keys.get("relation"));

                this.insert(
                        faction,
                        target,
                        relation
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public <K, V extends Integer> HashMap<Integer, Relation> findOne(K key, V value) {
        String query = String.format(
                "SELECT * FROM %s WHERE `%s`=%d;",
                this.getTableName(),
                key,
                value
        );

        HashMap<Integer, Relation> relations = Maps.newHashMap();

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                Integer factionId = resultSet.getInt("faction_id");
                Relation relation = Relation.valueOf(resultSet.getString("relation"));

                relations.put(
                        factionId,
                        relation
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return relations;
    }
}
