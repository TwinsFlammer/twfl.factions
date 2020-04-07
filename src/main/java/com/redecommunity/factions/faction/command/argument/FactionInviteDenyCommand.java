package com.redecommunity.factions.faction.command.argument;

import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.util.Messages;
import org.bukkit.command.CommandSender;

public class FactionInviteDenyCommand extends AbstractFactionArgumentCommand {
    public FactionInviteDenyCommand() {
        super(
                0,
                "recusar"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        if (args.length == 1) {
            String tagOrName = args[0];

            Faction faction = FactionManager.getFaction(tagOrName);

            if (faction == null) {
                commandSender.sendMessage(Messages.FACTION_NOT_EXISTS);
                return;
            }

            if (!faction.hasInvited(fUser)) {
                commandSender.sendMessage("§cEsta facção não enviou um convite para você");
                return;
            }

            fUser.getInvites().removeIf(faction1 -> faction1.getId().equals(faction.getId()));

            commandSender.sendMessage(
                    String.format(
                            "§aO convite da facção %s foi recusado.",
                            faction.getName()
                    )
            );
            return;
        } else {
            commandSender.sendMessage("§cUtilize /f convite aceitar <tag>.");
            return;
        }
    }
}
