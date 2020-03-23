package com.redecommunity.factions.battle.dao;

import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.factions.battle.data.Battle;

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

    public void insert(B battle) {
    }

    @Override
    public <K, V> void delete(K key, V value) {
    }

    @Override
    public <K, V, U, I> void update(HashMap<K, V> keys, U key, I value) {
    }

    public <K, V, U, I, T> Set<T> findAll(HashMap<K, V> keys) {

        return null;
    }
}
