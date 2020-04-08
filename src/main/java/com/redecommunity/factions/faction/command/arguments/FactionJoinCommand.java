package com.redecommunity.factions.faction.command.arguments;

import com.google.common.collect.Maps;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.user.dao.FUserDAO;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.util.Messages;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class FactionJoinCommand extends AbstractFactionArgumentCommand {
    public FactionJoinCommand() {
        super(
                0,
                "entrar"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        if (fUser.hasFaction()) {
            commandSender.sendMessage("§cVocê já está em uma facção.");
            return;
        }

        if (args.length != 1) {
            commandSender.sendMessage("§cUtilize /f entrar <facção>.");
            return;
        }

        String nameOrTag = args[0];

        Faction faction = FactionManager.getFaction(nameOrTag);

        if (faction == null) {
            commandSender.sendMessage(Messages.FACTION_NOT_EXISTS);
            return;
        }

        fUser.setFactionId(faction.getId());

        FUserDAO fUserDAO = new FUserDAO();

        HashMap<String, Object> keys = Maps.newHashMap();

        keys.put("faction_id", faction.getId());

        fUserDAO.update(
                keys,
                "user_id",
                fUser.getId()
        );

        commandSender.sendMessage(
                String.format(
                        "§aVocê entrou na facção %s.",
                        faction.getFullName()
                )
        );
    }
}
