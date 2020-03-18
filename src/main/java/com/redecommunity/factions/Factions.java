package com.redecommunity.factions;

import com.redecommunity.api.spigot.CommunityPlugin;
import lombok.Getter;

/**
 * Created by @SrGutyerrez
 */
public class Factions extends CommunityPlugin {
    @Getter
    private static Factions instance;

    public Factions() {
        Factions.instance = this;
    }

    @Override
    public void onEnablePlugin() {

    }

    @Override
    public void onDisablePlugin() {

    }
}
