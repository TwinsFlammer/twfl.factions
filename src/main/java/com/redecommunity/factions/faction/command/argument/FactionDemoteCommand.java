package com.redecommunity.factions.faction.command.argument;

import com.redecommunity.factions.Factions;
import com.redecommunity.factions.faction.enums.Role;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.util.Messages;
import org.bukkit.command.CommandSender;

public class FactionDemoteCommand extends AbstractFactionArgumentCommand {
    public FactionDemoteCommand() {
        super(
                0,
                "rebaixar"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        if (!fUser.hasFaction()) {
            commandSender.sendMessage(Messages.FACTION_NEEDED);
            return;
        }

        if (args.length == 1) {
            String targetName = args[0];

            FUser fUser1 = Factions.getFUserFactory().getUser(targetName);

            if (fUser1 == null) {
                commandSender.sendMessage(Messages.USER_NOT_EXISTS);
                return;
            }

            if (!fUser1.isInMyFaction(fUser)) {
                commandSender.sendMessage(Messages.INST_IN_YOUR_FACTION);
                return;
            }

            if (fUser.isOfficer() || fUser.isOverriding()) {
                Role nextRole = fUser1.getRole().getNext();

                if (nextRole != null && !nextRole.equals(fUser.getRole())) {

                }
            }
        }
    }
}
