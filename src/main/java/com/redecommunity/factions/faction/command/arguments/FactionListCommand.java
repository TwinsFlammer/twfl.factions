package com.redecommunity.factions.faction.command.arguments;

import com.redecommunity.common.shared.util.Helper;
import com.redecommunity.factions.faction.data.Faction;
import com.redecommunity.factions.faction.manager.FactionManager;
import com.redecommunity.factions.user.data.FUser;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class FactionListCommand extends AbstractFactionArgumentCommand {
    public FactionListCommand() {
        super(
                0,
                "listar"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        String prePage = args.length == 1 ? args[0] : "1";

        if (!Helper.isInteger(prePage)) {
            commandSender.sendMessage("§cPágina inválida.");
            return;
        }

        List<Faction> factions = FactionManager.getFactions()
                .stream()
                .filter(faction -> !faction.getOnlinePlayers().isEmpty())
                .collect(Collectors.toList());

        final Integer perPage = 10;
        Double pagesNumber = (factions.size() / perPage.doubleValue());

        Integer pages = (int) Math.ceil(pagesNumber);

        Integer page = Integer.parseInt(prePage);

        if (page < 1 || page > pages + 1) {
            commandSender.sendMessage("§cPágina inválida.");
            return;
        }

        commandSender.sendMessage("           §eFacções Online - " + page  + "/" + factions.size());

        if (factions.size() < perPage) {
            factions.forEach(faction -> {
                this.sendMessage(commandSender, faction);
            });
        } else if (!factions.isEmpty()) {
            Integer size = perPage * page;

            Integer count = size - factions.size();

            factions.subList((page == 1 ? 0 : page * 10 - 10), (page == 1 ? 10 : size - count))
                    .forEach(faction -> {
                        this.sendMessage(commandSender, faction);
                    });
        } else {
            commandSender.sendMessage(new String[] {
                    "",
                    "  --/--",
                    ""
            });
        }
    }

    private void sendMessage(CommandSender commandSender, Faction faction) {
        commandSender.sendMessage(
                String.format(
                        " §a%s §8- §7%s §8- §f%d/%d §7online §8- §f%d/%d§7%d",
                        faction.getTag(),
                        faction.getName(),
                        faction.getOnlinePlayers().size(),
                        faction.getMembers().size(),
                        faction.getLandCount(),
                        faction.getPowerRounded(),
                        faction.getPowerMaxRounded()
                )
        );
    }
}
