package com.redecommunity.factions.faction.command.arguments;

import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.enums.Role;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.util.Messages;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class FactionSetHome extends AbstractFactionArgumentCommand {
    public FactionSetHome() {
        super(
                0,
                "setbase"
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

        Location location = fUser.getLocation();

        faction.setHomeLocation(location);

        commandSender.sendMessage("§aVocê alterou a localização da sua facção.");
    }
}
