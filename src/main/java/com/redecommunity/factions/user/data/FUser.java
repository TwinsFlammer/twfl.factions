package com.redecommunity.factions.user.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redecommunity.api.spigot.inventory.item.CustomItem;
import com.redecommunity.api.spigot.user.data.SpigotUser;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.skin.data.Skin;
import com.redecommunity.common.shared.util.Helper;
import com.redecommunity.common.shared.util.TimeFormatter;
import com.redecommunity.factions.Factions;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.enums.ResignReason;
import com.redecommunity.factions.faction.enums.Role;
import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.land.dao.LandDAO;
import com.redecommunity.factions.land.data.Land;
import com.redecommunity.factions.permission.dao.PermissionDAO;
import com.redecommunity.factions.permission.data.Permission;
import com.redecommunity.factions.permission.enums.PermissionType;
import com.redecommunity.factions.user.dao.FUserDAO;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.Warning;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by @SrGutyerrez
 */
public class FUser extends SpigotUser {
    private static final Double FACTION_USER_POWER_MAX = 5.0, FACTION_USER_POWER = 0.0;

    @Getter
    @Setter
    private Integer factionId = 1;

    @Getter
    @Setter
    private Long lastLogin = -1L;

    @Getter
    @Setter
    private Role role = Role.RECRUIT;

    @Getter
    @Setter
    private Double power = FUser.FACTION_USER_POWER, powerMax = FUser.FACTION_USER_POWER_MAX;

    @Setter
    private Boolean mapAutoUpdating = false, flying = false, seeingChunks = false, overriding = false;

    @Getter
    @Setter
    private Integer killsCivilian = 0, killsNeutral = 0, killsEnemy = 0, deathsCivilian = 0, deathsNeutral = 0, deathsEnemy = 0;

    private final List<Integer> invites = Lists.newArrayList();

    @Getter
    private final Permission permission;

    @Getter
    @Setter
    private Integer warWins = 0;

    public FUser(User user) {
        super(user);

        PermissionDAO permissionDao = new PermissionDAO();

        this.permission = permissionDao.findOne("user_id", this.getId());

        FUserDAO fUserDao = new FUserDAO();

        Object[] objects = fUserDao.findOne("id", this.getId());

        if (objects != null) {
            this.factionId = (Integer) objects[0];
            this.lastLogin = (Long) objects[1];
            this.role = Role.valueOf((String) objects[2]);
            this.power = (Double) objects[3];
            this.powerMax = (Double) objects[4];
            this.mapAutoUpdating = (Boolean) objects[5];
            this.flying = (Boolean) objects[6];
            this.seeingChunks = (Boolean) objects[7];
            this.overriding = (Boolean) objects[8];
            this.killsCivilian = (Integer) objects[9];
            this.killsNeutral = (Integer) objects[10];
            this.killsEnemy = (Integer) objects[11];
            this.deathsCivilian = (Integer) objects[12];
            this.deathsNeutral = (Integer) objects[13];
            this.deathsEnemy = (Integer) objects[14];
            this.warWins = (Integer) objects[15];
        }
    }

    public void sendMessage(String message) {
        if (this.isOnline())
            this.getPlayer().sendMessage(Helper.colorize(message));
    }

    private void resign() {
        this.factionId = Factions.ID_NONE;
        this.role = Role.RECRUIT;

        FUserDAO fUserDAO = new FUserDAO();

        HashMap<String, Object> keys = Maps.newHashMap();

        keys.put("faction_id", 1);

        fUserDAO.update(
                keys,
                "user_id",
                this.getId()
        );

        PermissionDAO permissionDAO = new PermissionDAO();

        permissionDAO.delete(
                "user_id",
                this.getId()
        );
    }

    public void resign(ResignReason resignReason) {
        // chamar o evento e informar o motivo de ter saido

        this.resign();
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
                        "§fAbates: §7" + this.getKills() + " " + this.getKillsFormatted(),
                        "§fMortes: §7" + this.getDeaths() + " " + this.getDeathsFormatted(),
                        "§fGuerras ganhas: §7" + this.warWins,
                        "§fStatus §7" + (this.isOnline() ? "§aOnline" : "§cOffline"),
                        "§fÚltimo login: §7" + this.getLastLoginFormatted()
                )
                .hideAttributes();
    }

    public Role getPreviousRole() {
        return this.role.getPrevious();
    }

    public Role getNextRole() {
        return this.role.getNext();
    }

    public Location getLocation() {
        return this.getPlayer().getLocation();
    }

    public Chunk getChunk() {
        return this.getLocation().getChunk();
    }

    public String getFactionName() {
        return this.getFaction().getName();
    }

    public String getFactionTag() {
        return this.getFaction().getTag();
    }

    public String getFullFactionName() {
        Faction faction = this.getFaction();

        return faction == null ? "" : faction.getFullName();
    }

    public String getKDRFormatted() {
        double kdr = this.getKDR();

        DecimalFormat decimalFormat = new DecimalFormat("0.0");

        return decimalFormat.format(kdr);
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

        String formatted = simpleDateFormat.format(date);

        return formatted.split(" ")[0] + " às " + formatted.split(" ")[1];
    }

    public String getRolePrefix() {
        return this.role.getPrefix();
    }

    public String getRoleName() {
        return this.role.getName();
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

    public Boolean tryClaim(Faction faction, Chunk chunk) {
        Land land = Factions.getLandFactory().getLand(chunk);

        Faction factionLand = land.getFaction();

        if (factionLand != null && factionLand.isDefault()) {
            this.sendMessage("§cVocê não pode dominar " + factionLand.getName() + ".");
            return false;
        }

        if (factionLand != null && !factionLand.isDefault()) {
            if (factionLand.getId().equals(this.factionId)) {
                this.sendMessage("§cEsta terra já está dominada por sua facção!");
                return false;
            }

            if (factionLand.getLands().size() + factionLand.getProtectedLands().size() > factionLand.getPower()) {
                if (factionLand.getSpawners(land) >= 3 ) {
                    this.sendMessage(
                            String.format(
                                    "Ops! A facção %s ainda possui muitos geradores nesta terra.",
                                    factionLand.getName()
                            )
                    );
                }

                this.sendMessage("§eOh yeah! Terra tomada com sucesso!");

                LandDAO landDAO = new LandDAO();

                landDAO.delete(
                        "id",
                        factionLand.getId()
                );

                factionLand.getLands().removeIf(land1 -> land1.getId().equals(land.getId()));
                return false;
            } else {
                this.sendMessage(
                        String.format(
                                "§cOps! A facção %s possui poder suficiente para manter esta %s.",
                                factionLand.getName(),
                                land.isTemporary() ? "proteção. A proteção acaba em " + TimeFormatter.formatMinimized(land.getDuration()) : "terra"
                        )
                );
                return false;
            }
        }

        if (faction.getPower() <= faction.getLands().size() + 1) {
            this.sendMessage("§cSua facção não tem poder suficiente para dominar esta terra.");
            return false;
        }

        if (land.getConnectedLands().stream().anyMatch(land1 -> land.isTemporary() && land1.getFactionId().equals(this.factionId))) {
            this.sendMessage("§cVocê não pode dominar ao lado de terrenos temporários de sua facção.");
            return false;
        }

        if (faction.getLands().stream().anyMatch(land1 -> land1.getWorldName().equals(land.getWorldName()))) {
            List<Land> around = land.getConnectedLands()
                    .stream()
                    .filter(land1 -> land1.getFactionId().equals(faction.getId()))
                    .collect(Collectors.toList());

            if (around.isEmpty()) {
                this.sendMessage("§cVocê só pode dominar terras conectadas às suas.");
                return false;
            }
        }

        List<Faction> factions = land.getNearbyFactions();

        if (factions.size() != 0) {
            String factionsName = factions.stream()
                    .map(Faction::getName)
                    .distinct()
                    .collect(Collectors.joining(", "));

            this.sendMessage("§cAs seguintes facções estão próximas: " + factionsName + ". Portanto, não foi possível dominar esta terra.");
            return false;
        }

        if (!this.hasPermission(PermissionType.CLAIM) && !this.overriding) {
            this.sendMessage("§cVocê não tem permissões para dominar terras.");
            return false;
        }

        return true;
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

    public Boolean isInMyFaction(FUser fUser) {
        return this.factionId.equals(fUser.getFactionId());
    }
}
