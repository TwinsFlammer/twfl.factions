package com.redecommunity.factions.user.manager;

import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.user.data.FUser;

import java.util.UUID;

/**
 * Created by @SrGutyerrez
 */
public class FUserManager {
    public static FUser getFUser(Integer id) {
        return FactionManager.getFactions()
                .stream()
                .filter(faction -> faction.getMembers()
                        .stream()
                        .anyMatch(fUser -> fUser.getId().equals(id))
                )
                .findFirst()
                .flatMap(faction -> faction.getMembers()
                        .stream()
                        .filter(fUser -> fUser.getId().equals(id))
                        .findFirst()
                )
                .orElse(null);
    }

    public static FUser getFUser(String name) {
        User user = UserManager.getUser(name);

        return FUserManager.getFUser(user.getId());
    }

    public static FUser getFUser(UUID uniqueId) {
        User user = UserManager.getUser(uniqueId);

        return FUserManager.getFUser(user.getId());
    }
}
