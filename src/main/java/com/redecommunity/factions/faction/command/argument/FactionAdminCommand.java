package com.redecommunity.factions.faction.command.argument;

import com.google.common.collect.Maps;
import com.redecommunity.common.shared.permissions.group.GroupNames;
import com.redecommunity.factions.user.dao.FUserDAO;
import com.redecommunity.factions.user.data.FUser;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class FactionAdminCommand extends AbstractFactionArgumentCommand {
    public FactionAdminCommand() {
        super(
                0,
                "admin",
                GroupNames.MANAGER
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        fUser.setOverriding(!fUser.isOverriding());

        FUserDAO fUserDAO = new FUserDAO();

        HashMap<String, Object> keys = Maps.newHashMap();

        keys.put("overriding", fUser.isOverriding());

        fUserDAO.update(
                keys,
                "user_id",
                fUser.getId()
        );

        commandSender.sendMessage("§aModo administrativo " + (fUser.isOverriding() ? "ativado." : "desativado"));
    }
}
