package com.redecommunity.factions.faction.command.arguments;

import com.google.common.collect.Maps;
import com.redecommunity.common.shared.permissions.group.GroupNames;
import com.redecommunity.factions.user.dao.FUserDAO;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.util.Messages;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class FactionFlyCommand extends AbstractFactionArgumentCommand {
    public FactionFlyCommand() {
        super(
                0,
                "voar",
                GroupNames.ELITE
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        if (!fUser.hasFaction()) {
            commandSender.sendMessage(Messages.FACTION_NEEDED);
            return;
        }

        fUser.setFlying(!fUser.isFlying());

        FUserDAO fUserDAO = new FUserDAO();

        HashMap<String, Object> keys = Maps.newHashMap();

        keys.put("flying", fUser.isFlying());

        fUserDAO.update(
                keys,
                "user_id",
                fUser.getId()
        );

        commandSender.sendMessage(
                String.format(
                        "§aO modo voo foi %s.",
                        fUser.isFlying() ? "ativado" : "desativado"
                )
        );
        return;
    }
}
