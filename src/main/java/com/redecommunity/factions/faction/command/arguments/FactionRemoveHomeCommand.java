package com.redecommunity.factions.faction.command.arguments;

import com.google.common.collect.Maps;
import com.redecommunity.factions.faction.dao.FactionDAO;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.enums.Role;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.util.Messages;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class FactionRemoveHomeCommand extends AbstractFactionArgumentCommand {
    public FactionRemoveHomeCommand() {
        super(
                0,
                "delbase"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        if (!fUser.hasFaction()) {
            commandSender.sendMessage(
                    Messages.FACTION_NEEDED
            );
            return;
        }

        if (!fUser.isOfficer()) {
            commandSender.sendMessage(
                    String.format(
                            Messages.ROLE_NEEDED_TO_EXECUTE_THIS_COMMAND,
                            Role.OFFICER.getName()
                    )
            );
            return;
        }

        Faction faction = fUser.getFaction();

        FactionDAO factionDAO = new FactionDAO();

        HashMap<String, Object> keys = Maps.newHashMap();

        keys.put("home", null);

        factionDAO.update(
                keys,
                "id",
                faction.getId()
        );

        commandSender.sendMessage("§cVocê deletou a base da sua facção.");
    }
}
