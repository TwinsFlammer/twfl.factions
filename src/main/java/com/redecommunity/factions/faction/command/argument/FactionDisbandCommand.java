package com.redecommunity.factions.faction.command.argument;

import com.redecommunity.factions.faction.dao.FactionDAO;
import com.redecommunity.factions.faction.enums.Role;
import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.util.Messages;
import org.bukkit.command.CommandSender;

public class FactionDisbandCommand extends AbstractFactionArgumentCommand {
    public FactionDisbandCommand() {
        super(
                0,
                "desfazer"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        if (!fUser.hasFaction()) {
            commandSender.sendMessage(Messages.FACTION_NEEDED);
            return;
        }

        if (!fUser.isLeader()) {
            fUser.sendMessage(
                    String.format(
                            Messages.ROLE_NEEDED_TO_EXECUTE_THIS_COMMAND,
                            Role.LEADER.getName()
                    )
            );
            return;
        }

        // TODO fazer o inventário

        commandSender.sendMessage("Não foi implementado ainda");
    }
}
