package me.alpho320.fabulous.farm.util.logger;

import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BukkitPluginLogger implements FarmPluginLogger {

    private final @NotNull BukkitFarmPlugin plugin;
    private LoggingLevel serverLoggingLevel = LoggingLevel.INFO;

    private final File logFile;
    private final YamlConfiguration configuration;

    public BukkitPluginLogger(@NotNull BukkitFarmPlugin plugin, LoggingLevel serverLoggingLevel) {
        this.plugin = plugin;
        this.serverLoggingLevel = serverLoggingLevel;
        this.logFile = createFile();
        this.configuration = YamlConfiguration.loadConfiguration(logFile);
    }

    public void save() {
        try {
            configuration.save(logFile);
        } catch (IOException e) {
            e.printStackTrace();
            Debug.debug(1, " | An error occurred while saving the log file.");
        }
    }

    @Override
    public @NotNull LoggingLevel getServerLoggingLevel() {
        return this.serverLoggingLevel;
    }

    @Override
    public void setServerLoggingLevel(@NotNull LoggingLevel serverLoggingLevel) {
        this.serverLoggingLevel = serverLoggingLevel;
    }

    @Override
    public void log(@NotNull String str) {
        if (serverLoggingLevel.equals(LoggingLevel.DEBUG) || serverLoggingLevel.equals(LoggingLevel.INFO))
            plugin.getLogger().info(str);
        configuration.set(new SimpleDateFormat("HH:mm:ss", new Locale("en")).format(new Date()) + "." + System.currentTimeMillis(), str);
    }

    @Override
    public void debug(@NotNull String str) {
        if (serverLoggingLevel.equals(LoggingLevel.DEBUG)) log(str);
    }

    @Override
    public void info(@NotNull String str) {
        plugin.getLogger().info(str);
    }

    @Override
    public void warning(@NotNull String str) {
        plugin.getLogger().warning(str);
    }

    @Override
    public void severe(@NotNull String str) {
        plugin.getLogger().severe(str);
    }

    private @NotNull File createFile() {
        File file = new File(plugin.getDataFolder(), "/logs/log-" + new SimpleDateFormat("dd-MMMM-yyyy", new Locale("en")).format(new Date()) + ".yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
