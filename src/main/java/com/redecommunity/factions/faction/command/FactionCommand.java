package com.redecommunity.factions.faction.command;

import com.redecommunity.api.spigot.commands.CustomCommand;
import com.redecommunity.api.spigot.commands.enums.CommandRestriction;
import com.redecommunity.common.shared.permissions.group.GroupNames;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.factions.faction.command.arguments.*;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.user.manager.FUserManager;
import org.bukkit.command.CommandSender;

/**
 * Created by @SrGutyerrez
 */
public class FactionCommand extends CustomCommand {
    public FactionCommand() {
        super(
                "f",
                CommandRestriction.IN_GAME,
                GroupNames.DEFAULT
        );

        this.addArgument(
                new FactionAdminCommand(),
                new FactionClaimCommand(),
                new FactionCreateCommand(),
                new FactionDemoteCommand(),
                new FactionDisbandCommand(),
                new FactionFlyCommand(),
                new FactionHelpCommand(),
                new FactionInviteCommand()
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, User user, String[] args) {
        String arg = args[0];

        Faction faction = FactionManager.getFaction(arg);

        if (faction != null) {
            // TODO not implemented yet
            return;
        }

        FUser fUser = FUserManager.getFUser(arg);

        if (fUser != null) {
            // TODO not implemented yet
            return;
        }

        if (arg.length() <= 3) {
            // TODO not implemented yet
            return;
        } else {
            // TODO not implemented yet
            return;
        }
    }
}
