package com.redecommunity.factions.faction.command.arguments;

import com.redecommunity.factions.user.data.FUser;
import org.bukkit.command.CommandSender;

public class FactionRelationCommand extends AbstractFactionArgumentCommand {
    public FactionRelationCommand() {
        super(
                0,
                "relacao"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        // TODO not implemented yet
    }
}
