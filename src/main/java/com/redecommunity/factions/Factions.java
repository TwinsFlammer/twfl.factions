package com.redecommunity.factions;

import com.redecommunity.api.spigot.CommunityPlugin;
import com.redecommunity.factions.land.data.Land;
import com.redecommunity.factions.land.factory.LandFactory;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.user.factory.FUserFactory;
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
    private static final FUserFactory<? extends FUser> F_USER_FACTORY = new FUserFactory<>();

    @Override
    public void onEnablePlugin() {
    }

    @Override
    public void onDisablePlugin() {

    }

    public static LandFactory<? extends Land> getLandFactory() {
        return Factions.LAND_FACTORY;
    }

    public static FUserFactory<? extends FUser> getFUserFactory() {
        return Factions.F_USER_FACTORY;
    }
}
