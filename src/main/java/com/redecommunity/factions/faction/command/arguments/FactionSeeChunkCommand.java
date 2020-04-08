package com.redecommunity.factions.faction.command.arguments;

import com.google.common.collect.Maps;
import com.redecommunity.factions.user.dao.FUserDAO;
import com.redecommunity.factions.user.data.FUser;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class FactionSeeChunkCommand extends AbstractFactionArgumentCommand {
    public FactionSeeChunkCommand() {
        super(
                0,
                "verterras"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        fUser.setSeeingChunks(!fUser.isSeeingChunks());

        FUserDAO fUserDAO = new FUserDAO();

        HashMap<String, Object> keys = Maps.newHashMap();

        keys.put("seeing_chunks", fUser.isSeeingChunks());

        fUserDAO.update(
                keys,
                "user_id",
                fUser.getId()
        );

        commandSender.sendMessage(
                String.format(
                        "§aModo ver terras foi %s.",
                        fUser.isSeeingChunks() ? "ativado" : "desativado"
                )
        );
    }
}
