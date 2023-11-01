package me.alpho320.fabulous.farm;

import me.alpho320.fabulous.farm.configuration.ConfigurationManager;
import me.alpho320.fabulous.farm.gui.GUIManager;
import me.alpho320.fabulous.farm.hook.HookManager;
import me.alpho320.fabulous.farm.provider.ProviderManager;
import me.alpho320.fabulous.farm.task.TaskManager;
import me.alpho320.fabulous.farm.util.logger.FarmPluginLogger;
import me.alpho320.fabulous.farm.util.updater.Updater;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

}