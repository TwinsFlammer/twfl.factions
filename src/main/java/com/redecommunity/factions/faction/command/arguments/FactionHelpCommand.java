package com.redecommunity.factions.faction.command.arguments;

import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.util.Messages;
import org.bukkit.command.CommandSender;

public class FactionHelpCommand extends AbstractFactionArgumentCommand {
    public FactionHelpCommand() {
        super(
                0,
                "ajuda"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        if (fUser.hasFaction()) {
            commandSender.sendMessage(Messages.DEFAULT_HELP_MESSAGE_WITH_FACTION);
        } else {
            commandSender.sendMessage(Messages.DEFAULT_HELP_MESSAGE_WITHOUT_FACTION);
        }
    }
}
