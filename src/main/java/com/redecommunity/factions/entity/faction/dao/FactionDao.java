package com.redecommunity.factions.entity.faction.dao;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.factions.entity.faction.data.Faction;
import com.redecommunity.factions.entity.faction.manager.FactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class FactionDao extends Table {
    @Override
    public String getTableName() {
        return "server_factions";
    }

    @Override
    public String getDatabaseName() {
        return "server";
    }

    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`name` VARCHAR(20) NOT NULL," +
                                "`tag` VARCHAR(3) NOT NULL," +
                                "`home_location` VARCHAR(255) NOT NULL," +
                                "`default_faction` BOOLEAN NOT NULL" +
                                ");",
                        this.getTableName()
                )
        );
    }

    @Override
    public <T> Set<T> findAll() {
        Set<T> factions = Sets.newConcurrentHashSet();

        String query = String.format(
                "SELECT * FROM %s;",
                this.getTableName()
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                Faction faction = FactionManager.toFaction(resultSet);

                factions.add((T) faction);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return super.findAll();
    }
}
