package com.redecommunity.factions.faction.command.arguments;

import com.google.common.collect.Maps;
import com.redecommunity.factions.faction.enums.Role;
import com.redecommunity.factions.user.dao.FUserDAO;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.user.manager.FUserManager;
import com.redecommunity.factions.util.Messages;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class FactionTransferCommand extends AbstractFactionArgumentCommand {
    public FactionTransferCommand() {
        super(
                0,
                "transferir"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        if (!fUser.hasFaction()) {
            commandSender.sendMessage(
                    Messages.FACTION_NEEDED
            );
            return;
        }

        if (!fUser.isLeader() && !fUser.isOverriding()) {
            commandSender.sendMessage(
                    String.format(
                            Messages.ROLE_NEEDED_TO_EXECUTE_THIS_COMMAND,
                            Role.LEADER.getName()
                    )
            );
            return;
        }

        if (args.length == 1) {
            String targetName = args[0];

            FUser fUser1 = FUserManager.getFUser(targetName);

            if (fUser1 == null) {
                commandSender.sendMessage(
                        Messages.USER_NOT_EXISTS
                );
                return;
            }

            fUser.setRole(Role.OFFICER);
            fUser1.setRole(Role.LEADER);

            FUserDAO fUserDAO = new FUserDAO();

            HashMap<String, Object> keys1 = Maps.newHashMap(), keys2 = Maps.newHashMap();

            keys1.put("role", fUser.getRole().toString());
            keys2.put("role", fUser1.getRole().toString());

            fUserDAO.update(
                    keys1,
                    "user_id",
                    fUser.getId()
            );

            fUserDAO.update(
                    keys2,
                    "user_id",
                    fUser1.getId()
            );

            commandSender.sendMessage(
                    String.format(
                            "§aVocê transferiu a liderança da sua facção para %s§a.",
                            fUser1.getPrefix() + fUser1.getDisplayName()
                    )
            );
            return;
        } else {
            commandSender.sendMessage("§cUtilize /f transferir <jogador>.");
            return;
        }
    }
}
