package com.redecommunity.factions.faction.dao;

import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.manager.FactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by @SrGutyerrez
 */
public class FactionDao<F extends Faction> extends Table {
    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`tag` VARCHAR(20) NOT NULL," +
                                "`name` VARCHAR(3) NOT NULL" +
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
        return "server_factions";
    }

    public F insert(F faction) {
        String query = String.format(
                "INSERT INTO %s " +
                        "(" +
                        "`tag`," +
                        "`name`," +
                        ")" +
                        " VALUES " +
                        "'%s'," +
                        "'%s';",
                this.getTableName(),
                faction.getTag(),
                faction.getName()
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next())
                return (F) FactionManager.createNewFaction(
                        resultSet.getInt("id"),
                        faction.getTag(),
                        faction.getName(),
                        faction.getLeader(),
                        faction.isDefault()
                );
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
