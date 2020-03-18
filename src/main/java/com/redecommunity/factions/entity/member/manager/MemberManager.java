package com.redecommunity.factions.entity.member.manager;

import com.google.common.collect.Lists;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import com.redecommunity.factions.entity.faction.data.Faction;
import com.redecommunity.factions.entity.faction.manager.FactionManager;
import com.redecommunity.factions.entity.member.data.Member;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by @SrGutyerrez
 */
public class MemberManager {
    @Getter
    private static final List<Member> MEMBERS = Lists.newArrayList();

    public static Member getMember(Integer id) {
        return MemberManager.MEMBERS.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static Member getMember(User user) {
        return MemberManager.getMember(user.getId());
    }

    public static Member getMember(Player player) {
        User user = UserManager.getUser(player.getUniqueId());

        return MemberManager.getMember(user);
    }

    public static Member getMember(UUID uniqueId) {
        User user = UserManager.getUser(uniqueId);

        return MemberManager.getMember(user);
    }

    public static Member getMember(String name) {
        User user = UserManager.getUser(name);

        return MemberManager.getMember(user);
    }

    public static Member toMember(ResultSet resultSet) throws SQLException {
        Integer userId = resultSet.getInt("user_id"),
                factionId = resultSet.getInt("faction_id");


        Faction faction = factionId == null ? null : FactionManager.getFaction(factionId);

        return new Member(
                userId,
                faction
        );
    }
}
