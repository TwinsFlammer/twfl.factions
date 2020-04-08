package com.redecommunity.factions.faction.command.arguments;

import com.redecommunity.factions.user.data.FUser;
import org.bukkit.command.CommandSender;

public class FactionPermissionsCommand extends AbstractFactionArgumentCommand {
    public FactionPermissionsCommand() {
        super(
                0,
                "permissoes"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        // TODO not implemented yet
    }
}
