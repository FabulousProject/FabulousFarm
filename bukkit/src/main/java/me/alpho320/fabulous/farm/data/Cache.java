package me.alpho320.fabulous.farm.data;

import me.alpho320.fabulous.core.api.manager.impl.message.MessageType;
import me.alpho320.fabulous.core.bukkit.BukkitCore;
import me.alpho320.fabulous.core.bukkit.manager.impl.message.BukkitMessageManager;
import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.farm.configuration.BukkitConfi;
import org.jetbrains.annotations.NotNull;

public class Cache {

    public final int maxLoadMS, maxSaveMS;

    public final @NotNull String prefix,
            second,
            minute,
            hour,
            day,
            week,
            month,
            year;

    public final int distanceBetweenRealms,
                deleteRealmAfterDays;

    public final MessageType reloadMessageType,

            insufficientMoney,
            insufficientLevel,
            insufficientItem,
            insufficientPerm,
            insufficientItemMessageType,

            playerHelpMessageType,
            adminHelpMessageType;

    public Cache(@NotNull BukkitConfi manager) {
        BukkitConfiguration config = manager.getConfig();
        BukkitConfiguration messages = manager.getMessages();
        BukkitMessageManager messageManager = BukkitCore.instance().message();

        this.maxLoadMS = config.getInt("Main.load-max-ms-per-tick", 10);
        this.maxSaveMS = config.getInt("Main.save-max-ms-per-tick", 10);
        this.prefix = messageManager.colored(config.getString("Main.prefix", "SERVERNAME"));

        this.second = config.getString("Time.second", "seconds");
        this.minute = config.getString("Time.minute", "minutes");
        this.hour = config.getString("Time.hour", "hours");
        this.day = config.getString("Time.day", "days");
        this.week = config.getString("Time.week", "week");
        this.month = config.getString("Time.month", "months");
        this.year = config.getString("Time.year", "years");

        this.distanceBetweenRealms = config.getInt("Main.distance-between-realms", 16);
        this.deleteRealmAfterDays = config.getInt("Main.delete-realm-after-days", 30);

        this.reloadMessageType = MessageType.getType(messages.getString("Messages.reload.type", "TITLE"));
        this.playerHelpMessageType = MessageType.getType(messages.getString("Messages.player-help-message.type", "TITLE"));
        this.adminHelpMessageType = MessageType.getType(messages.getString("Messages.admin-help-message.type", "TITLE"));

        this.insufficientMoney = MessageType.getType(messages.getString("Messages.insufficient-money.type", "TITLE"));
        this.insufficientLevel = MessageType.getType(messages.getString("Messages.insufficient-level.type", "TITLE"));
        this.insufficientItem = MessageType.getType(messages.getString("Messages.insufficient-item.type", "TITLE"));
        this.insufficientPerm = MessageType.getType(messages.getString("Messages.insufficient-perm.type", "TITLE"));
        this.insufficientItemMessageType = MessageType.getType(messages.getString("Messages.insufficient-item.type", "TITLE"));

    }

}