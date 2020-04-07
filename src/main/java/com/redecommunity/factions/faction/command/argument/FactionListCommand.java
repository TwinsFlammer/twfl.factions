package com.redecommunity.factions.faction.command.argument;

import com.redecommunity.factions.user.data.FUser;
import org.bukkit.command.CommandSender;

public class FactionListCommand extends AbstractFactionArgumentCommand {
    public FactionListCommand() {
        super(
                0,
                "listar"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {

    }
}
