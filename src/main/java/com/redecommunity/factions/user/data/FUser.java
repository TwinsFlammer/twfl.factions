package com.redecommunity.factions.user.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redecommunity.api.spigot.inventory.item.CustomItem;
import com.redecommunity.api.spigot.user.data.SpigotUser;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.skin.data.Skin;
import com.redecommunity.common.shared.util.Helper;
import com.redecommunity.factions.Factions;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.enums.Role;
import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.land.data.Land;
import com.redecommunity.factions.permission.data.Permission;
import com.redecommunity.factions.permission.enums.PermissionType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Warning;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private final Permission permission;

    @Getter
    private Integer warWins;

    public FUser(User user) {
        super(user);

        // create this value
        this.permission = new Permission(Maps.newHashMap());
    }

    public void sendMessage(String message) {
        if (this.isOnline())
            this.getPlayer().sendMessage(Helper.colorize(message));
    }

    public void tryClaim(Faction faction, Chunk chunk) {
        Land land = Factions.getLandFactory().getLand(chunk);

        if (land == null) {
            this.sendMessage("§cOcorreu um erro ao tentar comprar esta terra");
            return;
        }

        // continuar
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

    public CustomItem getSkull() {
        Skin skin = this.getSkin();
        Role role = this.getRole();


        return new CustomItem(Material.SKULL_ITEM)
                .name(this.getHighestGroup().getFancyPrefix() + this.getDisplayName())
                .data(3)
                .owner(skin == null ? this.getDisplayName() : skin.getValue())
                .lore(
                        "§fPoder: §7" + this.getPowerRounded() + "/" + this.getPowerMaxRounded(),
                        "§fCoins: §7" + this.getMoneyFormatted(),
                        "§fCargo: §7" + role.getPrefix() + role.getDisplayName(),
                        "§fKDR: §7" + this.getKDRFormatted(),
                        "§fAbates: §7" + this.getKillsFormatted(),
                        "§fMortes: §7" + this.getDeathsFormatted(),
                        "§fGuerras ganhas: §7" + this.warWins,
                        "§fStatus §7" + (this.isOnline() ? "§aOnline" : "§cOffline"),
                        "§fÚltimo login: §7" + this.getLastLoginFormatted()
                )
                .hideAttributes();
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

    public String getKDRFormatted() {
        int kills = this.getKills();
        int deaths = this.getDeaths() == 0 ? 1 : this.getDeaths();

        DecimalFormat decimalFormat = new DecimalFormat("0.0");

        return decimalFormat.format(kills / deaths);
    }

    public String getKillsFormatted() {
        return String.format(
                "§8[Inimigo §7%d§8 Neutro §7%d§8 Civil §7%d§8]",
                this.killsEnemy,
                this.killsNeutral,
                this.killsCivilian
        );
    }

    public String getDeathsFormatted() {
        return String.format(
                "§8[Inimigo §7%d§8 Neutro §7%d§8 Civil §7%d§8]",
                this.deathsEnemy,
                this.deathsNeutral,
                this.deathsCivilian
        );
    }

    public String getMoneyFormatted() {
        return null;
    }

    public String getLastLoginFormatted(){
        Date date = new Date(this.lastLogin);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return simpleDateFormat.format(date);
    }

    public double getKDR() {
        int kills = this.getKills();
        int deaths = this.getDeaths() == 0 ? 1 : this.getDeaths();

        return kills / deaths;
    }

    public Integer getKills() {
        return this.killsCivilian + this.killsNeutral + this.killsEnemy;
    }

    public Integer getDeaths() {
        return this.deathsCivilian + this.deathsNeutral + this.deathsEnemy;
    }

    public Integer getPowerRounded() {
        return (int) Math.round(this.power);
    }

    public Integer getPowerMaxRounded() {
        return (int) Math.round(this.powerMax);
    }

    @Warning
    public Double getMoney() {
        return null;
    }

    @Warning
    public void withdraw(Double value) {
        // TODO not implemented-yet
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

    public Boolean isOfficer() {
        return this.isLeader() || this.role == Role.OFFICER;
    }

    public Boolean isMember() {
        return this.isLeader() || this.isOfficer() || this.role == Role.MEMBER;
    }

    public Boolean hasPermission(PermissionType permissionType) {
        return this.permission.has(permissionType) || permissionType.getDefault(this.role);
    }

    public Boolean isOnline() {
        return this.getPlayer() != null;
    }

    public Boolean hasFaction() {
        return !(this.factionId.equals(Factions.ID_NONE) && this.factionId.equals(Factions.ID_SAFEZONE) && this.factionId.equals(Factions.ID_WARZONE));
    }
}
