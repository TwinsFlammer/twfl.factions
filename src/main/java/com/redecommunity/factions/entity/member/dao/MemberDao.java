package com.redecommunity.factions.entity.member.dao;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.factions.entity.member.data.Member;
import com.redecommunity.factions.entity.member.manager.MemberManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class MemberDao extends Table {
    @Override
    public String getTableName() {
        return "server_faction_members";
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
                                "`user_id` INTEGER NOT NULL PRIMARY KEY," +
                                "`faction_id` INTEGER" +
                                ");",
                        this.getTableName()
                )
        );
    }

    public <K, V, U, I, T> Set<T> findAll(HashMap<K, V> keys) {
        Set<T> members = Sets.newConcurrentHashSet();

        String parameters = this.generateParameters(keys);

        String query = String.format(
                "SELECT * FROM %s WHERE %s;",
                this.getTableName(),
                parameters
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                Member member = MemberManager.toMember(resultSet);

                members.add((T) member);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return members;
    }
}
