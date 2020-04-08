package com.redecommunity.factions.faction.command.arguments;

import com.google.common.collect.Maps;
import com.redecommunity.factions.user.dao.FUserDAO;
import com.redecommunity.factions.user.data.FUser;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;

public class FactionMapCommand extends AbstractFactionArgumentCommand {
    private String[] validActions = {
            "on",
            "off"
    };

    public FactionMapCommand() {
        super(
                0,
                "mapa"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        if (args.length == 0) {

        } else if (args.length == 1) {
            String action = args[0];

            if (Arrays.asList(this.validActions).contains(action)) {
                Boolean newState = action.equalsIgnoreCase("on");

                fUser.setMapAutoUpdating(newState);

                FUserDAO fUserDAO = new FUserDAO();

                HashMap<String, Object> keys = Maps.newHashMap();

                keys.put("map_auto_updating", newState);

                fUserDAO.update(
                        keys,
                        "user_id",
                        fUser.getId()
                );

                commandSender.sendMessage(
                        String.format(
                                "O modo mapa foi %s.",
                                newState ? "ativado" : "destivado"
                        )
                );
                return;
            }
        }

        commandSender.sendMessage("§cUtilize /f mapa <on/off>.");
    }
}
