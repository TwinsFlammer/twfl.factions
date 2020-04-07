package com.redecommunity.factions.faction.data;

import com.google.common.collect.Maps;
import com.redecommunity.api.spigot.inventory.item.CustomItem;
import com.redecommunity.factions.battle.data.Battle;
import com.redecommunity.factions.faction.dao.FactionDAO;
import com.redecommunity.factions.faction.enums.Relation;
import com.redecommunity.factions.faction.enums.ResignReason;
import com.redecommunity.factions.faction.enums.Role;
import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.history.data.History;
import com.redecommunity.factions.land.dao.LandDAO;
import com.redecommunity.factions.land.data.Land;
import com.redecommunity.factions.permission.data.Permission;
import com.redecommunity.factions.permission.enums.PermissionType;
import com.redecommunity.factions.user.dao.FUserDAO;
import com.redecommunity.factions.user.data.FUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by @SrGutyerrez
 */
@AllArgsConstructor
@Setter
public class Faction {
    private static final Integer FACTION_MEMBER_LIMIT = 15;

    @Getter
    private final Integer id;

    @Getter
    private String tag, name;

    @Getter
    private final List<FUser> members;
    @Getter
    private final List<Land> lands, protectedLands;
    @Getter
    private final HashMap<Integer, Relation> relationsId;

    @Getter
    private Location homeLocation;

    @Getter
    private final List<Battle> battles;

    private Boolean defaultFaction;

    @Getter
    private final HashMap<EntityType, ItemStack> generators;
    @Getter
    private final HashMap<Relation, Permission> permissions;
    @Getter
    private final List<History> history;
    @Getter
    private final ChatColor tagColor;
    @Getter
    private final Integer warWins;

    public void sendMessage(String message) {
        this.members.forEach(fUser -> fUser.sendMessage(message));
    }

    public void disband() {
        FUserDAO fUserDAO = new FUserDAO();

        HashMap<String, Object> keys = Maps.newHashMap();

        keys.put("faction_id", 1);
        keys.put("role", Role.RECRUIT.toString());

        fUserDAO.update(
                keys,
                "faction_id",
                this.id
        );

        LandDAO landDAO = new LandDAO();

        landDAO.delete(
                "faction_id",
                this.id
        );

        this.members.forEach(fUser -> fUser.resign(ResignReason.DISBAND));
        // fazer para quando deletar a facção remover membros, deletar a facção do mysql
        // trocar a role do membro, deletar as relações, permissões, histórico, geradores e batalhas
    }

    public void kick(FUser fUser) {
        this.members.removeIf(fUser1 -> fUser1.getId().equals(fUser.getId()));

        fUser.resign(ResignReason.KICK);
    }

    public FUser getLeader() {
        return this.members
                .stream()
                .filter(FUser::isLeader)
                .findFirst()
                .orElse(null);
    }

    public Relation getRelation(Faction faction) {
        return this.relationsId.keySet()
                .stream()
                .filter(faction1 -> faction1.equals(faction.getId()))
                .map(this.relationsId::get)
                .findFirst()
                .orElse(
                        Relation.NEUTRAL
                );
    }

    public CustomItem getIcon(DyeColor baseColor, DyeColor letterColor) {
        StringBuilder membersNames = new StringBuilder();

        IntStream.range(0, this.members.size())
                .forEach(index -> {
                    FUser fUser = this.members.get(index);

                    membersNames.append(fUser.getHighestGroup().getFancyPrefix())
                            .append(fUser.isOnline() ? "§a" : "§c")
                            .append(fUser.getRole().getPrefix())
                            .append(fUser.getDisplayName())
                            .append(" ");

                    if ((index % 3) + 1 >= 3)
                        membersNames.append("\n");
                });

        String[] lore = membersNames.toString().split("\\\\n");

        FUser leader = this.getLeader();

        Role role = Role.LEADER;

        String alliesName = this.getAllies().stream()
                .map(Faction::getTag)
                .distinct()
                .collect(Collectors.joining(", "));

        String enemiesName = this.getAllies().stream()
                .map(Faction::getTag)
                .distinct()
                .collect(Collectors.joining(", "));

        return new CustomItem(Material.BANNER)
                .banner(this.tag, baseColor, letterColor)
                .name("§e" + this.getFullName())
                .lore(
                        "§fTerras: §7" + this.lands.stream().filter(land -> !land.isTemporary()).count(),
                        "§fPoder: §7" + this.getPowerRounded(),
                        "§fPoder máximo: §7" + this.getPowerMaxRounded(),
                        "§fRanking de valor: §7" + 1 + "° Lugar",
                        "§fLíder: §7[" + role.getPrefix() + "] " + (leader == null ? "console" : leader.getDisplayName()),
                        "§fMembers: §7(" + this.members.size() + "/" + Faction.FACTION_MEMBER_LIMIT + ") " + this.getOnlinePlayers().size() + " online"
                )
                .lore(
                        true,
                        lore
                )
                .lore(
                        true,
                        "§fKDR: §7" + this.getKDRFormatted(),
                        "§fAbates: §7" + this.getKills() + " " + this.getKillsFormatted(),
                        "§fMortes: §7" + this.getDeaths() + " " + this.getDeathsFormatted(),
                        "§fAliados: §7" + alliesName,
                        "§fInimigos: §7" + enemiesName,
                        "§fGuerras ganhas: §7" + this.warWins
                );
    }

    public List<Player> getOnlinePlayers() {
        return this.members.stream()
                .filter(FUser::isOnline)
                .map(FUser::getPlayer)
                .collect(Collectors.toList());
    }

    public List<Faction> getRelations() {
        return this.relationsId.keySet()
                .stream()
                .map(FactionManager::getFaction)
                .collect(Collectors.toList());
    }

    public List<Faction> getAlliesInvites() {
        return this.relationsId.keySet()
                .stream()
                .filter(factionId -> {
                    Faction faction = FactionManager.getFaction(factionId);

                    return faction.isInvitedAlly(this);
                })
                .map(FactionManager::getFaction)
                .collect(Collectors.toList());
    }

    public List<Faction> getAllies() {
        return this.relationsId.keySet()
                .stream()
                .filter(factionId -> {
                    Faction faction = FactionManager.getFaction(factionId);

                    return this.isAlly(faction);
                })
                .map(FactionManager::getFaction)
                .collect(Collectors.toList());
    }

    public List<Faction> getEnemies() {
        return this.relationsId.keySet()
                .stream()
                .filter(factionId -> {
                    Faction faction = FactionManager.getFaction(factionId);

                    return this.isEnemy(faction);
                })
                .map(FactionManager::getFaction)
                .collect(Collectors.toList());
    }

    public HashMap<EntityType, Integer> loadSpawners() {
        HashMap<EntityType, Integer> spawners = Maps.newHashMap();

        this.lands.forEach(land -> {
            Chunk chunk = land.getChunk();
            World world = chunk.getWorld();

            Integer locationX = chunk.getX() << 4, locationZ = chunk.getZ() << 4;

            for (int x = 0; x < locationX; x++) {
                for (int z = 0; z < locationZ; z++) {
                    for (int y = 0; y < world.getMaxHeight(); y++) {
                        Block block = world.getBlockAt(x, y, z);

                        if (block.getType() == Material.MOB_SPAWNER) {
                            CreatureSpawner creatureSpawner = (CreatureSpawner) block.getState();

                            EntityType spawnedType = creatureSpawner.getSpawnedType();

                            spawners.put(
                                    spawnedType,
                                    spawners.getOrDefault(spawnedType, 0) + 1
                            );
                        }
                    }
                }
            }
        });

        return spawners;
    }

    public String getFullName() {
        return String.format(
                "[%s] %s",
                this.tag,
                this.name
        );
    }

    public String getKDRFormatted() {
        double kdr = this.getKDR();

        DecimalFormat decimalFormat = new DecimalFormat("0.0");

        return decimalFormat.format(kdr);
    }

    public String getKillsFormatted() {
        return String.format(
                "§8[Inimigo §7%d§8 Neutro §7%d§8 Civil §7%d§8]",
                this.getKillsEnemy(),
                this.getKillsNeutral(),
                this.getKillsCivilian()
        );
    }

    public String getDeathsFormatted() {
        return String.format(
                "§8[Inimigo §7%d§8 Neutro §7%d§8 Civil §7%d§8]",
                this.getDeathsEnemy(),
                this.getDeathsNeutral(),
                this.getDeathsCivilian()
        );
    }

    public Integer getKillsCivilian() {
        AtomicInteger killsCivilian = new AtomicInteger(0);

        this.members.forEach(fUser -> {
            killsCivilian.updateAndGet(v -> v + fUser.getKillsCivilian());
        });

        return killsCivilian.get();
    }

    public Integer getKillsNeutral() {
        AtomicInteger killsNeutral = new AtomicInteger(0);

        this.members.forEach(fUser -> {
            killsNeutral.updateAndGet(v -> v + fUser.getKillsNeutral());
        });

        return killsNeutral.get();
    }

    public Integer getKillsEnemy() {
        AtomicInteger killsNeutral = new AtomicInteger(0);

        this.members.forEach(fUser -> {
            killsNeutral.updateAndGet(v -> v + fUser.getKillsEnemy());
        });

        return killsNeutral.get();
    }

    public Integer getKills() {
        return this.getKillsCivilian() + this.getKillsNeutral() + this.getKillsEnemy();
    }

    public Integer getDeathsCivilian() {
        AtomicInteger killsCivilian = new AtomicInteger(0);

        this.members.forEach(fUser -> {
            killsCivilian.updateAndGet(v -> v + fUser.getDeathsCivilian());
        });

        return killsCivilian.get();
    }

    public Integer getDeathsNeutral() {
        AtomicInteger killsNeutral = new AtomicInteger(0);

        this.members.forEach(fUser -> {
            killsNeutral.updateAndGet(v -> v + fUser.getDeathsNeutral());
        });

        return killsNeutral.get();
    }

    public Integer getDeathsEnemy() {
        AtomicInteger killsNeutral = new AtomicInteger(0);

        this.members.forEach(fUser -> {
            killsNeutral.updateAndGet(v -> v + fUser.getDeathsEnemy());
        });

        return killsNeutral.get();
    }

    public Integer getDeaths() {
        return this.getDeathsCivilian() + this.getDeathsNeutral() + this.getDeathsEnemy();
    }

    public Integer getSpawners() {
        return 0;
    }

    public Integer getSpawners(Land land) {
        return 0;
    }

    public double getKDR() {
        int kills = this.getKills();
        int deaths = this.getDeaths() == 0 ? 1 : this.getDeaths();

        return kills / deaths;
    }

    public Double getPower() {
        AtomicReference<Double> power = new AtomicReference<>(0.0);

        this.members.forEach(fUser -> {
            power.updateAndGet(v -> v + fUser.getPower());
        });

        return power.get();
    }

    public Integer getPowerRounded() {
        return (int) Math.round(this.getPower());
    }

    public Double getPowerMax() {
        AtomicReference<Double> powerMax = new AtomicReference<>(0.0);

        this.members.forEach(fUser -> {
            powerMax.updateAndGet(v -> v + fUser.getPowerMax());
        });

        return powerMax.get();
    }

    public Integer getPowerMaxRounded() {
        return (int) Math.round(this.getPowerMaxRounded());
    }

    public Double getPlayersMoney() {
        AtomicReference<Double> money = new AtomicReference<>(0.0);

        this.members.forEach(fUser -> {
            money.updateAndGet(v -> v + fUser.getMoney());
        });

        return money.get();
    }

    public Double getSpawnerMoneyPlaced() {
        AtomicReference<Double> money = new AtomicReference<>(0.0);

        // pegar o dinheiro de spawners no chão

        return money.get();
    }

    public Double getSpawnerMoneyArmazened() {
        AtomicReference<Double> money = new AtomicReference<>(0.0);

        // pegar o dinheiro de spawners armazenados

        return money.get();
    }

    public Boolean isDefault() {
        return this.defaultFaction;
    }

    public Boolean isSimilar(Faction faction) {
        return this.id.equals(faction.getId());
    }

    public Boolean isInvited(Relation relation, Faction faction) {
        return this.relationsId.containsKey(faction.getId()) && faction.getRelationsId().containsKey(this.id)
                && !this.relationsId.get(faction.getId()).equals(relation) || !faction.getRelationsId().get(this.id).equals(relation);
    }

    public Boolean isAlly(Faction faction) {
        return this.relationsId.containsKey(faction.getId()) && faction.getRelationsId().containsKey(this.id)
                && this.relationsId.get(faction.getId()).equals(Relation.ALLY) && faction.getRelationsId().get(this.id).equals(Relation.ALLY);
    }

    public Boolean isEnemy(Faction faction) {
        return this.relationsId.containsKey(faction.getId()) && this.relationsId.get(faction.getId()).equals(Relation.ENEMY);
    }


    public Boolean isInvitedAlly(Faction faction) {
        return this.relationsId.containsKey(faction.getId()) && this.relationsId.get(faction.getId()).equals(Relation.ALLY)
                && !faction.getRelationsId().containsKey(this.id) || !faction.getRelationsId().get(faction.getId()).equals(Relation.ALLY);
    }

    public Boolean isInvited(FUser fUser) {
        return fUser.getInvites().contains(this);
    }

    public Boolean isUnderAttack() {
        return this.lands.stream()
                .filter(Land::isUnderAttack)
                .count() >= 1;
    }

    public Boolean hasPermission(Relation relation, PermissionType permissionType) {
        Permission permission = this.permissions.getOrDefault(
                relation,
                new Permission(Maps.newHashMap())
        );

        return permission.has(permissionType);
    }

    public Boolean hasAnyPermission() {
        return !this.permissions.isEmpty();
    }

    public Boolean hasInvited(FUser fUser) {
        return fUser.getInvites()
                .stream()
                .anyMatch(faction -> faction.getId().equals(this.id));
    }
}
