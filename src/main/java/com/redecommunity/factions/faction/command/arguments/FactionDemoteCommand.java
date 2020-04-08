package com.redecommunity.factions.faction.command.arguments;

import com.google.common.collect.Maps;
import com.redecommunity.factions.faction.enums.Role;
import com.redecommunity.factions.user.dao.FUserDAO;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.user.manager.FUserManager;
import com.redecommunity.factions.util.Messages;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

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

            FUser fUser1 = FUserManager.getFUser(targetName);

            if (fUser1 == null) {
                commandSender.sendMessage(Messages.USER_NOT_EXISTS);
                return;
            }

            if (!fUser1.isInMyFaction(fUser)) {
                commandSender.sendMessage(Messages.INST_IN_YOUR_FACTION);
                return;
            }

            if (fUser.isOfficer() || fUser.isOverriding()) {
                if (fUser.getRole().equals(fUser1.getRole()) || fUser1.isLeader()) {
                    commandSender.sendMessage("§cO cargo informado não pode ser igual ou superior ao seu.");
                    return;
                }

                Role previousRole = fUser1.getPreviousRole();

                if (previousRole == null) {
                    commandSender.sendMessage("§cNão há mais cargos para serem rebaixados.");
                    return;
                }

                fUser1.setRole(previousRole);

                FUserDAO fUserDAO = new FUserDAO();

                HashMap<String, Object> keys = Maps.newHashMap();

                keys.put("role", fUser1.getRole().toString());

                fUserDAO.update(
                        keys,
                        "user_id",
                        fUser1.getId()
                );

                commandSender.sendMessage(
                        String.format(
                                "§aJogador %s§a rebaixado para %s da facção.",
                                fUser1.getPrefix() + fUser1.getDisplayName(),
                                previousRole.getName()
                        )
                );
                fUser1.sendMessage(
                        String.format(
                                "§cVocê foi rebaixado para %s.",
                                previousRole.getName()
                        )
                );
                return;
            }

            commandSender.sendMessage(
                    String.format(
                            Messages.ROLE_NEEDED_TO_EXECUTE_THIS_COMMAND,
                            Role.OFFICER.getName()
                    )
            );
            return;
        } else {
            commandSender.sendMessage("§cUtilize /f rebaixar <jogador>.");
            return;
        }
    }
}
