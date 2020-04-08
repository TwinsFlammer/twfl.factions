package com.redecommunity.factions.faction.command.arguments;

import com.redecommunity.factions.user.data.FUser;
import org.bukkit.command.CommandSender;

public class FactionMembersCommand extends AbstractFactionArgumentCommand {
    public FactionMembersCommand() {
        super(
                0,
                "membros"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        // TODO not implemented yet
    }
}
