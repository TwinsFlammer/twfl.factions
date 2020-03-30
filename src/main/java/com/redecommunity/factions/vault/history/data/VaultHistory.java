package com.redecommunity.factions.vault.history.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.EntityType;

import java.util.HashMap;

@RequiredArgsConstructor
@Getter
public class VaultHistory {
    private final Integer faction_id, user_id;
    private final HashMap<EntityType, Integer> added, removed;
    private final Long time;
}
