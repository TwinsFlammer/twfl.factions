package com.redecommunity.factions.faction.command.arguments;

import com.redecommunity.api.spigot.util.JSONText;
import com.redecommunity.common.shared.util.Helper;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.permission.enums.PermissionType;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.user.manager.FUserManager;
import com.redecommunity.factions.util.Messages;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class FactionInviteCommand extends AbstractFactionArgumentCommand {
    private AbstractFactionArgumentCommand[] abstractFactionArgumentCommands;

    public FactionInviteCommand() {
        super(
                0,
                "convite"
        );

        abstractFactionArgumentCommands = new AbstractFactionArgumentCommand[]{
                new FactionInviteAcceptCommand(),
                new FactionInviteDenyCommand()
        };
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        if (fUser.hasFaction()) {
            commandSender.sendMessage("§cVocê já pertence a uma facção.");
            return;
        }

        if (args.length == 2) {
            String targetName = args[0];

            AbstractFactionArgumentCommand abstractFactionArgumentCommand = Arrays.stream(abstractFactionArgumentCommands)
                    .filter(abstractFactionArgumentCommand1 -> abstractFactionArgumentCommand1.getName().equalsIgnoreCase(targetName))
                    .findFirst()
                    .orElse(null);

            if (abstractFactionArgumentCommand != null) {
                abstractFactionArgumentCommand.onCommand(
                        commandSender,
                        fUser,
                        Helper.removeFirst(args)
                );
                return;
            }
        } else if (args.length == 1) {
            if (!fUser.hasPermission(PermissionType.INVITE)) {
                commandSender.sendMessage(
                        String.format(
                                Messages.PERMISSION_NEEDED_TO_EXECUTE_THIS_COMMAND,
                                PermissionType.INVITE.getName()
                        )
                );
                return;
            }

            String targetName = args[0];

            FUser fUser1 = FUserManager.getFUser(targetName);

            if (fUser1 == null) {
                commandSender.sendMessage(Messages.USER_NOT_EXISTS);
                return;
            }

            Faction faction = fUser.getFaction();

            fUser1.getInvites().add(faction);

            new JSONText()
                    .next()
                    .text(String.format("§aVocê foi convidado para entra na facção §f%s §a.", faction.getFullName()))
                    .next()
                    .text("\n")
                    .next()
                    .text("§aPara ver sua lista de convites clique ")
                    .next()
                    .text("§lAQUI")
                    .clickRunCommand("/f convites")
                    .next()
                    .text("§r§a.")
                    .send(fUser1.getPlayer());

            new JSONText()
                    .next()
                    .text(String.format("§aVocê convidou %s §apara sua facção.", fUser1.getPrefix() + fUser1.getDisplayName()))
                    .next()
                    .text("\n")
                    .next()
                    .text("§aPara cancelar o convite clique ")
                    .next()
                    .text("§lAQUI")
                    .clickRunCommand("/f convite cancelar " + fUser1.getName())
                    .next()
                    .text("§r§a.")
                    .send(commandSender);
            return;
        } else {
            commandSender.sendMessage("§cUtilize /f convidar <jogador>.");
            return;
        }
    }
}
