package me.alpho320.fabulous.farm;

import me.alpho320.fabulous.farm.api.FarmManager;
import me.alpho320.fabulous.farm.configuration.ConfigurationManager;
import me.alpho320.fabulous.farm.gui.GUIManager;
import me.alpho320.fabulous.farm.hook.HookManager;
import me.alpho320.fabulous.farm.provider.ProviderManager;
import me.alpho320.fabulous.farm.task.TaskManager;
import me.alpho320.fabulous.farm.util.logger.FarmPluginLogger;
import me.alpho320.fabulous.farm.util.updater.Updater;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface FarmPlugin {

    @NotNull FarmPluginLogger logger();
    @NotNull ConfigurationManager configurationManager();
    @NotNull ProviderManager providerManager();
    @NotNull GUIManager guiManager();
    @NotNull HookManager hookManager();
    @NotNull FarmManager farmManager();
    @NotNull Updater updater();
    @NotNull TaskManager taskManager();

    @NotNull String version();

    int versionInt();
    void reload(boolean async, @Nullable Callback callback);

    void runSYNC(@NotNull Runnable runnable);
    void runASYNC(@NotNull Runnable runnable);

    default void executeCommands(Player player, List<String> commands) {
        if (player == null || commands == null) return;
        runSYNC(() -> {
            for (String command : commands) {
                if (command.contains("null")) return;

                String[] split = command.split(":");
                CommandSender executor;

                if (split[0].equalsIgnoreCase("console"))
                    executor = Bukkit.getConsoleSender();
                else
                    executor = player;
                Bukkit.dispatchCommand(executor, split[1].replaceAll("%player%", player.getName()));
            }
        });
    }

}