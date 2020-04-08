package com.redecommunity.factions.faction.command.arguments;

import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.util.Messages;
import org.bukkit.command.CommandSender;

public class FactionLeaveCommand extends AbstractFactionArgumentCommand {
    public FactionLeaveCommand() {
        super(
                0,
                "sair"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        if (!fUser.hasFaction()) {
            commandSender.sendMessage(Messages.FACTION_NEEDED);
            return;
        }

        if (fUser.isLeader()) {
            commandSender.sendMessage("§cVocê é o líder da sua facção, transfira ela para alguém antes de sair.");
            return;
        }

        // TODO fazer o inventário
    }
}
