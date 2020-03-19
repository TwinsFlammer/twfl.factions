package com.redecommunity.factions.user.data;

import com.google.common.collect.Lists;
import com.redecommunity.api.spigot.user.data.SpigotUser;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.enums.Role;
import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.permission.data.Permission;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class FUser extends SpigotUser {
    @Setter
    private Integer factionId;

    @Getter
    @Setter
    private Long lastLogin;

    @Getter
    @Setter
    private Role role;

    @Getter
    @Setter
    private Double power, powerMax;

    @Setter
    private Boolean mapAutoUpdating, flying, seeingChunks, overriding;

    @Getter
    @Setter
    private Integer killsCivilian, killsNeutral, killsEnemy, deathsCivilian, deathsNeutral, deathsEnemy;

    private final List<Integer> invites = Lists.newArrayList();

    @Getter
    private final List<Permission> permissions = Lists.newArrayList();

    public FUser(User user) {
        super(user);
    }

    public List<Faction> getInvites() {
        List<Faction> invites = Lists.newArrayList();

        this.invites.forEach(factionId -> {
            Faction faction = FactionManager.getFaction(factionId);

            invites.add(faction);
        });

        return invites;
    }

    public Faction getFaction() {
        return FactionManager.getFaction(this.factionId);
    }

    public String getFactionName() {
        return this.getFaction().getName();
    }

    public String getFactionTag() {
        return this.getFaction().getTag();
    }

    public String getFullFactionName() {
        Faction faction = this.getFaction();

        return faction == null ? null : String.format(
                "[%s] %s",
                faction.getTag(),
                faction.getName()
        );
    }

    public String getKDR() {
        int kills = this.getKills();
        int deaths = this.getDeaths() == 0 ? 1 : this.getDeaths();

        DecimalFormat decimalFormat = new DecimalFormat("0.0");

        return decimalFormat.format(kills / deaths);
    }

    public Integer getKills() {
        return this.killsCivilian + this.killsNeutral + this.killsEnemy;
    }

    public Integer getDeaths() {
        return this.deathsCivilian + this.deathsNeutral + this.deathsEnemy;
    }

    public Boolean isMapAutoUpdating() {
        return this.mapAutoUpdating;
    }

    public Boolean isFlying() {
        return this.flying;
    }

    public Boolean isSeeingChunks() {
        return this.seeingChunks;
    }

    public Boolean isOverriding() {
        return this.overriding;
    }

    public Boolean isLeader() {
        return this.role == Role.LEADER;
    }
}