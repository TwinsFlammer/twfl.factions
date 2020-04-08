package com.redecommunity.factions.faction.command.arguments;

import com.redecommunity.factions.Factions;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.land.dao.LandDAO;
import com.redecommunity.factions.land.data.Land;
import com.redecommunity.factions.permission.enums.PermissionType;
import com.redecommunity.factions.user.data.FUser;
import com.redecommunity.factions.util.Messages;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FactionClaimCommand extends AbstractFactionArgumentCommand {
    public FactionClaimCommand() {
        super(
                0,
                "claim",
                PermissionType.CLAIM
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        if (!fUser.hasFaction()) {
            commandSender.sendMessage(Messages.FACTION_NEEDED);
            return;
        }

        Faction faction = fUser.getFaction();

        Player player = fUser.getPlayer();
        Location location = player.getLocation();
        Chunk chunk = location.getChunk();

        if (fUser.tryClaim(fUser.getFaction(), chunk)) {
            Land land = Factions.getLandFactory().getLand(chunk);

            LandDAO landDAO = new LandDAO();

            Land land1 = landDAO.insert(land, faction);

            faction.getLands().add(land1);

            commandSender.sendMessage("§aVocê dominou esta terra com sucesso!");
        }
    }
}
