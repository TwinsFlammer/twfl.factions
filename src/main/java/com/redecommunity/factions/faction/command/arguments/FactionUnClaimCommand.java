package com.redecommunity.factions.faction.command.arguments;

import com.redecommunity.factions.Factions;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.land.data.Land;
import com.redecommunity.factions.permission.enums.PermissionType;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.util.Messages;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;

import java.util.List;

public class FactionUnClaimCommand extends AbstractFactionArgumentCommand {
    public FactionUnClaimCommand() {
        super(
                0,
                "abandonar"
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

        if (!fUser.hasPermission(PermissionType.UNCLAIM) && !fUser.isOverriding()) {
            commandSender.sendMessage(
                    String.format(
                            Messages.PERMISSION_NEEDED_TO_EXECUTE_THIS_COMMAND,
                            PermissionType.UNCLAIM.getName()
                    )
            );
            return;
        }

        Faction faction = fUser.getFaction();

        if (faction.isUnderAttack()) {
            commandSender.sendMessage("§cVocê não pode abandonar terras enquanto estiver sob ataque.");
            return;
        }

        if (args.length == 0) {
            Chunk chunk = fUser.getChunk();

            Land land = Factions.getLandFactory().getLand(chunk);

            if (!land.getFactionId().equals(faction.getId())) {
                commandSender.sendMessage("§cEsta terra não pertence a sua facção.");
                return;
            }

            List<Land> connectedLands = land.getConnectedLands();

            if (!connectedLands.isEmpty()) {
                commandSender.sendMessage("§cAntes você precisa abandonar todos terrenos conectados a este.");
                return;
            }

            // TODO not implemented yet
        } else if (args.length == 1 && args[0].equalsIgnoreCase("todas")) {
            // TODO not implemented yet
        } else {
            commandSender.sendMessage("§cUtilize /f abandonar.");
            return;
        }
    }
}
