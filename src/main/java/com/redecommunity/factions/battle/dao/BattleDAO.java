package com.redecommunity.factions.battle.dao;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.factions.battle.data.Battle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class BattleDAO<B extends Battle> extends Table {
    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`challenger_id` INTEGER NOT NULL," +
                                "`challenged_id` INTEGER NOT NULL," +
                                "`time` LONG NOT NULL," +
                                "`duration` LONG," +
                                "`battle_state` VARCHAR(255) NOT NULL," +
                                "`challenger_kills` INTEGER NOT NULL," +
                                "`challenged_kills` INTEGER NOT NULL," +
                                "`challenger_deaths` INTEGER NOT NULL," +
                                "`challenged_deaths` INTEGER NOT NULL" +
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
        return "server_faction_battle";
    }

    public B insert(B battle) {
        String query = String.format(
                "INSERT INTO %s " +
                        "(" +
                        "`challenger_id`," +
                        "`challenged_id`," +
                        "`time`," +
                        "`duration`," +
                        "`battle_state`," +
                        "`challenger_kills`," +
                        "`challenged_kills`," +
                        "`challenger_deaths`," +
                        "`challenged_deaths`" +
                        ")" +
                        " VALUES " +
                        "(" +
                        "%d," +
                        "%d," +
                        "%d," +
                        "%d," +
                        "'%s'," +
                        "%d," +
                        "%d," +
                        "%d," +
                        "%d" +
                        ");",
                this.getTableName(),
                battle.getChallengerId(),
                battle.getChallengedId(),
                battle.getTime(),
                battle.getDuration(),
                battle.getState().toString(),
                battle.getChallengerKills(),
                battle.getChallengedKills(),
                battle.getChallengerDeaths(),
                battle.getChallengedDeaths()
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next())
                return (B) new Battle(
                        generatedKeys.getInt("id"),
                        battle.getChallengerId(),
                        battle.getChallengedId(),
                        battle.getTime(),
                        battle.getDuration(),
                        battle.getState(),
                        battle.getKills(),
                        battle.getDeaths()
                );
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
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

    @Override
    public <K, V, U, I> void update(HashMap<K, V> keys, U key, I value) {
        String parameters = this.generateParameters(keys);

        String query = String.format(
                "UPDATE %s SET %s WHERE `%s`=%d;",
                parameters,
                key,
                value
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public <K, V> Set<B> findAll(K key, V value, K key1, V value1) {
        String query = String.format(
                "SELECT * FROM %s WHERE `%s`=%d OR `%s`=%d",
                this.getTableName(),
                key,
                value,
                key1,
                value1
        );

        Set<B> battles = Sets.newConcurrentHashSet();

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                Battle battle = Battle.toBattle(resultSet);

                battles.add((B) battle);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return battles;
    }
}
