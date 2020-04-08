package com.redecommunity.factions.faction.command.arguments;

import com.redecommunity.factions.user.data.FUser;
import org.bukkit.command.CommandSender;

public class FactionRankCommand extends AbstractFactionArgumentCommand {
    public FactionRankCommand() {
        super(
                0,
                "ranking"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        // TODO not implemented yet
    }
}
