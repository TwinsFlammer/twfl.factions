package com.redecommunity.factions.faction.command.arguments;

import com.redecommunity.common.shared.permissions.group.GroupNames;
import com.redecommunity.common.shared.util.Helper;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.user.manager.FUserManager;
import com.redecommunity.factions.util.Messages;
import org.bukkit.command.CommandSender;

public class FactionPowerCommand extends AbstractFactionArgumentCommand {
    public FactionPowerCommand() {
        super(
                0,
                "poder",
                GroupNames.MANAGER
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        if (args.length == 2) {
            String targetName = args[0];
            String prePower = args[1];

            FUser fUser1 = FUserManager.getFUser(targetName);

            if (fUser1 == null) {
                commandSender.sendMessage(Messages.USER_NOT_EXISTS);
                return;
            }

            if (!Helper.isInteger(prePower)) {
                commandSender.sendMessage("§cPoder inválido.");
                return;
            }

            Double power = Double.valueOf(prePower);

            fUser1.setPower(power);

            commandSender.sendMessage(
                    String.format(
                            "§aO poder de %s§a foi atualizado para %g",
                            fUser1.getPrefix() + fUser1.getDisplayName(),
                            power
                    )
            );
        } else {
            commandSender.sendMessage("§cUtilize /f poder <jogador> <poder>.");
            return;
        }
    }
}
