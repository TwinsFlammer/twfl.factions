package com.redecommunity.factions.faction.command.arguments;

import com.google.common.collect.Maps;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.user.dao.FUserDAO;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.util.Messages;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class FactionInviteAcceptCommand extends AbstractFactionArgumentCommand {
    public FactionInviteAcceptCommand() {
        super(
                0,
                "aceitar"
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

            FUserDAO fUserDAO = new FUserDAO();

            HashMap<String, Object> keys = Maps.newHashMap();

            keys.put("faction_id", faction.getId());

            fUserDAO.update(
                    keys,
                    "user_id",
                    faction.getId()
            );

            faction.getMembers().add(fUser);

            faction.sendMessage(
                    String.format(
                            "§aO jogador %s§a é o mais novo membro da facção.",
                            fUser.getPrefix() + fUser.getDisplayName()
                    )
            );

            commandSender.sendMessage(
                    String.format(
                            "§aVocê juntou-se a facção %s!",
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
