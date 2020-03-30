package com.redecommunity.factions.history.dao;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.factions.history.data.History;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class HistoryDAO<H extends History> extends Table {
    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`faction_id` INTEGER NOT NULL," +
                                "`old_tag` VARCHAR(3)," +
                                "`new_tag` VARCHAR(3)," +
                                "`old_name` VARCHAR(20)," +
                                "`new_name` VARCHAR(20)," +
                                "`time` LONG NOT NULL" +
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
        return "server_faction_history";
    }

    public void insert(H history) {
        String query = String.format(
                "INSERT INTO %s " +
                        "(" +
                        "`faction_id`," +
                        "`old_tag`," +
                        "`new_tag`," +
                        "`old_name`," +
                        "`new_name`," +
                        "`time`" +
                        ")" +
                        " VALUES " +
                        "(" +
                        "%d," +
                        "'%s'," +
                        "'%s'," +
                        "'%s'," +
                        "'%s'," +
                        "%d" +
                        ");",
                this.getTableName(),
                history.getFactionId(),
                history.getOldTag(),
                history.getNewTag(),
                history.getOldName(),
                history.getNewName(),
                history.getTime()
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

    public <K, V> Set<H> findAll(K key, V value) {
        String query = String.format(
                "SELECT * FROM %s WHERE `%s`=%d;",
                this.getTableName(),
                key,
                value
        );

        Set<H> histories = Sets.newConcurrentHashSet();

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                History history = History.toHistory(resultSet);

                histories.add((H) history);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return histories;
    }
}
