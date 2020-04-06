package com.redecommunity.factions.faction.command.argument;

import com.google.common.collect.Maps;
import com.redecommunity.factions.faction.dao.FactionDAO;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.user.dao.FUserDAO;
import com.redecommunity.factions.user.data.FUser;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class FactionCreateCommand extends AbstractFactionArgumentCommand {
    public FactionCreateCommand() {
        super(
                0,
                "criar"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        if (fUser.hasFaction()) {
            commandSender.sendMessage("§cVocê já pertence a uma facção.");
            return;
        }

        if (args.length != 2) {
            commandSender.sendMessage("§cUtilize /f criar <tag> <nome>.");
            return;
        }

        String tag = args[0], name = args[1];

        if (tag.length() != 3) {
            commandSender.sendMessage("§cA tag da facção deve conter 3 caracteres.");
            return;
        }

        if (name.length() > 20 || name.length() < 5) {
            commandSender.sendMessage("§cO nome de sua facção deve conter de 5 a 20 caracteres.");
            return;
        }

        Faction byTag = FactionManager.getFaction(tag), byName = FactionManager.getFaction(name);

        if (byTag != null) {
            commandSender.sendMessage("§cOps! Já existe outra facção utilizando a tag \"" + tag + "\".");
            return;
        }

        if (byName != null) {
            commandSender.sendMessage("§cJá existe outra facção utilizando o nome \"" + name + "\".");
            return;
        }

        Faction faction = FactionManager.createNewFaction(tag, name, fUser, false);

        FactionDAO factionDAO = new FactionDAO<>();

        faction = factionDAO.insert(faction);

        fUser.setFactionId(faction.getId());

        FUserDAO fUserDAO = new FUserDAO();

        HashMap<String, Object> keys = Maps.newHashMap();

        keys.put("faction_id", faction.getId());

        fUserDAO.update(
                keys,
                "user_id",
                fUser.getId()
        );

        commandSender.sendMessage("§eYAY! Sua facção foi criada com sucesso!");
    }
}
