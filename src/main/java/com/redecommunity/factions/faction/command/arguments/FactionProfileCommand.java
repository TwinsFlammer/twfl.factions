package com.redecommunity.factions.faction.command.arguments;

import com.redecommunity.factions.user.data.FUser;
import org.bukkit.command.CommandSender;

public class FactionProfileCommand extends AbstractFactionArgumentCommand {
    public FactionProfileCommand() {
        super(
                0,
                "perfil"
        );
    }

    @Override
    public void onCommand(CommandSender commandSender, FUser fUser, String[] args) {
        if (args.length == 0) {

        } else if (args.length == 1) {

        } else {
            commandSender.sendMessage("§cUtilize /f perfil <jogador>");
            return;
        }
    }

    private void sendMessage(CommandSender commandSender, FUser fUser) {
        commandSender.sendMessage(new String[] {
                "",
                "            " + fUser.getPrefix() + "§e" + (fUser.hasFaction() ? fUser.getFactionTag() + fUser.getRolePrefix() : "") + fUser.getDisplayName(),
                "",
                "§fPoder: §7" + fUser.getPowerRounded() + "/" + fUser.getPowerMaxRounded(),
                "§fFacção: §7" + (fUser.hasFaction() ? fUser.getFactionName() : "Nenhuma"),
                "§fCoins: §7" + 0,
                "§fCargo: §7" + fUser.getRoleName(),
                "§fKDR: §7" + fUser.getKDRFormatted(),
                "§fAbates: §7" + fUser.getKills() + " " + fUser.getKillsFormatted(),
                "§fMortes: §7" + fUser.getDeaths() + " " + fUser.getDeathsFormatted(),
                "§fGuerras ganhas: §7" + fUser.getWarWins(),
                "§fStatus: " + (fUser.isOnline() ? "§aOnline" : "§cOffline"),
                "§fÚltimo login: §7" + fUser.getLastLoginFormatted()
        });
    }
}
