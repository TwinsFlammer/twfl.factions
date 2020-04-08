package com.redecommunity.factions.faction.command.arguments;

import com.redecommunity.api.spigot.commands.CustomArgumentCommand;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.group.GroupNames;
import com.redecommunity.common.shared.permissions.group.data.Group;
import com.redecommunity.common.shared.permissions.group.manager.GroupManager;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.factions.Factions;
import com.redecommunity.factions.permission.enums.PermissionType;
import com.redecommunity.factions.user.data.FUser;
import org.bukkit.command.CommandSender;

public abstract class AbstractFactionArgumentCommand extends CustomArgumentCommand {
    private final Group group;
    private final PermissionType permissionType;

    public AbstractFactionArgumentCommand(Integer index, String name) {
        super(index, name);

        this.group = GroupManager.getGroup(GroupNames.DEFAULT);
        this.permissionType = null;
    }


    public AbstractFactionArgumentCommand(Integer index, String name, PermissionType permissionType) {
        super(index, name);

        this.group = GroupManager.getGroup(GroupNames.DEFAULT);
        this.permissionType = permissionType;
    }

    public AbstractFactionArgumentCommand(Integer index, String name, String groupName) {
        super(index, name);

        this.group = GroupManager.getGroup(groupName);
        this.permissionType = null;
    }

    public AbstractFactionArgumentCommand(Integer index, String name, String groupName, PermissionType permissionType) {
        super(index, name);

        this.group = GroupManager.getGroup(groupName);
        this.permissionType = permissionType;
    }

    @Override
    public final void onCommand(CommandSender commandSender, User user, String[] args) {
        Language language = user.getLanguage();

        if (!user.hasGroup(this.group)) {
            commandSender.sendMessage(
                    String.format(
                            language.getMessage("messages.default_commands.invalid_group"),
                            this.group.getFancyPrefix()
                    )
            );
            return;
        }

        FUser fUser = Factions.getFUserFactory().getUser(user.getId());

        if (this.permissionType != null && !fUser.hasPermission(this.permissionType)) {
            commandSender.sendMessage("§cVocê não possui permisssão para executar este comando.");
            return;
        }

        this.onCommand(commandSender, fUser, args);
    }

    public abstract void onCommand(CommandSender commandSender, FUser fUser, String[] args);
}
