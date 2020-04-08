package com.redecommunity.factions.faction.command.arguments;

import com.redecommunity.factions.user.data.FUser;
import org.bukkit.command.CommandSender;

public class FactionInvitesCommand extends AbstractFactionArgumentCommand {
    public FactionInvitesCommand() {
        super(
                0,
                "convites"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        // TODO not implemented yet
    }
}
