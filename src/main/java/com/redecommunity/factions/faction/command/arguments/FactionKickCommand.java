package com.redecommunity.factions.faction.command.arguments;

import com.redecommunity.factions.faction.enums.ResignReason;
import com.redecommunity.factions.faction.enums.Role;
import com.redecommunity.factions.permission.enums.PermissionType;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.user.manager.FUserManager;
import com.redecommunity.factions.util.Messages;
import org.bukkit.command.CommandSender;

public class FactionKickCommand extends AbstractFactionArgumentCommand {
    public FactionKickCommand() {
        super(
                0,
                "expulsar"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        if (!fUser.hasFaction()) {
            commandSender.sendMessage(Messages.FACTION_NEEDED);
            return;
        }

        if (args.length != 1) {
            commandSender.sendMessage("§cUtilize /f expulsar <jogador>.");
            return;
        }

        if (fUser.hasPermission(PermissionType.KICK) || fUser.isOfficer() || fUser.isLeader() || fUser.isOverriding()) {
            String targetName = args[0];

            FUser fUser1 = FUserManager.getFUser(targetName);

            if (fUser1 == null) {
                commandSender.sendMessage(Messages.USER_NOT_EXISTS);
                return;
            }

            if (fUser.equals(fUser1)) {
                commandSender.sendMessage("§cVocê não pode expulsar a si mesmo.");
                return;
            }

            if (fUser.getRole().equals(fUser1.getRole()) || fUser1.isLeader()) {
                commandSender.sendMessage("§cVocê não pode expulsar alguém com o cargo maior ou igual ao seu.");
                return;
            }

            fUser1.resign(ResignReason.KICK);

            fUser1.sendMessage(
                    String.format(
                            "§cVocê foi expulso da sua facção por %s§c.",
                            fUser.getPrefix() + fUser.getDisplayName()
                    )
            );

            commandSender.sendMessage(
                    String.format(
                            "§aVocê expulsou %s §ada sua facção com sucesso.",
                            fUser1.getPrefix() + fUser1.getDisplayName()
                    )
            );
            return;
        } else {
            commandSender.sendMessage(
                    String.format(
                            Messages.ROLE_NEEDED_TO_EXECUTE_THIS_COMMAND,
                            Role.OFFICER.getName()
                    )
            );
            return;
        }
    }
}
