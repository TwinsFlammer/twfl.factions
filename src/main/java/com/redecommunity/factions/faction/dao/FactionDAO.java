package com.redecommunity.factions.faction.dao;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.factions.battle.dao.BattleDAO;
import com.redecommunity.factions.battle.data.Battle;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.enums.Relation;
import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.history.dao.HistoryDAO;
import com.redecommunity.factions.history.data.History;
import com.redecommunity.factions.land.dao.LandDAO;
import com.redecommunity.factions.land.data.Land;
import com.redecommunity.factions.permission.dao.PermissionDAO;
import com.redecommunity.factions.permission.data.Permission;
import com.redecommunity.factions.user.dao.FUserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by @SrGutyerrez
 */
public class FactionDAO<F extends Faction> extends Table {
    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`tag` VARCHAR(20) NOT NULL," +
                                "`name` VARCHAR(3) NOT NULL," +
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
        return "server_factions";
    }

    public F insert(F faction) {
        String query = String.format(
                "INSERT INTO %s " +
                        "(" +
                        "`tag`," +
                        "`name`," +
                        "`war_wins`" +
                        ")" +
                        " VALUES " +
                        "'%s'," +
                        "'%s'," +
                        "%d;",
                this.getTableName(),
                faction.getTag(),
                faction.getName(),
                faction.getWarWins()
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

    @Override
    public Set<F> findAll() {
        String query = String.format(
                "SELECT * FROM %s;",
                this.getTableName()
        );

        Set<F> factions = Sets.newConcurrentHashSet();

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                Faction faction = FactionManager.toFaction(resultSet);

                FUserDAO fUserDAO = new FUserDAO();

                faction.getMembers().addAll(
                        fUserDAO.findAll(
                                "faction_id",
                                faction.getId()
                        )
                );

                LandDAO landDAO = new LandDAO();

                Set<Land> lands = landDAO.findAll(
                        "faction_id",
                        faction.getId()
                );

                faction.getLands().addAll(
                        lands.stream()
                                .filter(land -> !land.isProtected())
                                .collect(Collectors.toSet())
                );
                faction.getProtectedLands().addAll(
                        lands.stream()
                                .filter(Land::isProtected)
                                .collect(Collectors.toSet())
                );

                PermissionDAO permissionDAO = new PermissionDAO();

                HashMap<Relation, Permission> permissionHashMap = permissionDAO.findOne(
                        "faction_id",
                        faction.getId()
                );

                faction.getPermissions().putAll(permissionHashMap);

                RelationDAO relationDAO = new RelationDAO();

                HashMap<Integer, Relation> relationsId = relationDAO.findOne(
                        "faction_id",
                        faction.getId()
                );

                faction.getRelationsId().putAll(relationsId);

                BattleDAO battleDAO = new BattleDAO();

                Set<Battle> battles = battleDAO.findAll(
                        "challenger_id",
                        faction.getId(),
                        "challenged_id",
                        faction.getId()
                );

                faction.getBattles().addAll(battles);

                HistoryDAO historyDAO = new HistoryDAO();

                Set<History> histories = historyDAO.findAll(
                        "faction_id",
                        faction.getId()
                );

                faction.getHistory().addAll(histories);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return factions;
    }
}
