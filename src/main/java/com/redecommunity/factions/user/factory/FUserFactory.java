package com.redecommunity.factions.user.factory;

import com.google.common.collect.Lists;
import com.redecommunity.api.spigot.SpigotAPI;
import com.redecommunity.common.shared.permissions.user.factory.AbstractUserFactory;
import com.redecommunity.factions.user.data.FUser;

import java.util.List;
import java.util.UUID;

/**
 * Created by @SrGutyerrez
 */
public class FUserFactory<U extends FUser> extends AbstractUserFactory<U> {
    private final List<U> users = Lists.newArrayList();

    @Override
    public U getUser(Integer id) {
        return this.users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseGet(() -> {
                    U user = (U) SpigotAPI.getSpigotUserFactory().getUser(id);

                    this.users.add(user);

                    return user;
                });
    }

    @Override
    public U getUser(String username) {
        return this.users.stream()
                .filter(u -> u.getName().equalsIgnoreCase(username))
                .findFirst()
                .orElseGet(() -> {
                    U user = (U) SpigotAPI.getSpigotUserFactory().getUser(username);

                    this.users.add(user);

                    return user;
                });
    }

    @Override
    public U getUser(UUID uniqueId) {
        return this.users.stream()
                .filter(u -> u.getUniqueId().equals(uniqueId))
                .findFirst()
                .orElseGet(() -> {
                    U user = (U) SpigotAPI.getSpigotUserFactory().getUser(uniqueId);

                    this.users.add(user);

                    return user;
                });
    }
}
