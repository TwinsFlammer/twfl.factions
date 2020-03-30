package com.redecommunity.factions.manager;

import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.user.manager.FUserManager;

public class StartManager {
    public StartManager() {
        new DataManager();

        new TableManager();

        new ListenerManager();
        new CommandManager();
    }
}

class DataManager {
    DataManager() {
        new FactionManager();
        new FUserManager();
    }
}

class TableManager {
    TableManager() {

    }
}

class ListenerManager {
    ListenerManager() {

    }
}

class CommandManager {
    CommandManager() {

    }
}