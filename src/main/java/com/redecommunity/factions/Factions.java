package com.redecommunity.factions;

import com.redecommunity.api.spigot.CommunityPlugin;
import com.redecommunity.factions.land.data.Land;
import com.redecommunity.factions.land.factory.LandFactory;
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

    public static final Integer ID_NONE = 1;
    public static final Integer ID_SAFEZONE = 2;
    public static final Integer ID_WARZONE = 3;

    private static final LandFactory<? extends Land> LAND_FACTORY = new LandFactory<>();

    @Override
    public void onEnablePlugin() {
    }

    @Override
    public void onDisablePlugin() {

    }

    public static LandFactory<? extends Land> getLandFactory() {
        return Factions.LAND_FACTORY;
    }
}
