package com.redecommunity.factions.entity.faction.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.factions.entity.faction.manager.FactionManager;
import com.redecommunity.factions.entity.member.data.Member;
import com.redecommunity.factions.entity.member.manager.MemberManager;
import com.redecommunity.factions.entity.permission.data.Permission;
import com.redecommunity.factions.relation.enums.FactionRelation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.*;

/**
 * Created by @SrGutyerrez
 */
@AllArgsConstructor
public class Faction {
    @Getter
    private final Integer id;
    @Getter
    private String tag, name;
    @Getter
    private final Set<Integer> membersId;
    @Getter
    private final Set<FactionLand> lands, protectedLands;
    @Getter
    private final HashMap<FactionRelation, Integer> relationsId;
    @Getter
    private Location homeLocation;

    @Getter
    private final Set<Battle> battles;

    private Boolean defaultFaction;

    @Getter
    private final Set<FactionGenerator> generators;
    @Getter
    private final HashMap<Integer, Permission> permissions;
    @Getter
    private final TreeSet<NameHistory> nameHistory;
    @Getter
    private ChatColor tagColor;

    public Boolean isDefault() {
        return this.defaultFaction;
    }

    public HashMap<Faction, FactionRelation> getRelations() {
        HashMap<Faction, FactionRelation> relations = Maps.newHashMap();

        for (Map.Entry<FactionRelation, Integer> entry : this.relationsId.entrySet()) {
            FactionRelation factionRelation = entry.getKey();
            Integer factionId = entry.getValue();

            Faction faction = FactionManager.getFaction(factionId);

            relations.put(
                    faction,
                    factionRelation
            );
        }

        return relations;
    }

    public List<Faction> getAllies() {
        List<Faction> allies = Lists.newArrayList();

        for (Map.Entry<FactionRelation, Integer> entry : this.relationsId.entrySet()) {
            FactionRelation factionRelation = entry.getKey();
            Integer factionId = entry.getValue();

            Faction faction = FactionManager.getFaction(factionId);

            if (factionRelation == FactionRelation.ALLY)
                allies.add(faction);
        }

        return allies;
    }

    public List<Faction> getEnemies() {
        List<Faction> allies = Lists.newArrayList();

        for (Map.Entry<FactionRelation, Integer> entry : this.relationsId.entrySet()) {
            FactionRelation factionRelation = entry.getKey();
            Integer factionId = entry.getValue();

            Faction faction = FactionManager.getFaction(factionId);

            if (factionRelation == FactionRelation.ENEMY)
                allies.add(faction);
        }

        return allies;
    }

    public List<Member> getMembers() {
        List<Member> members = Lists.newArrayList();

        for (Integer integer : this.membersId) {
            Member member = MemberManager.getMember(integer);

            members.add(member);
        }

        return null;
    }
}
