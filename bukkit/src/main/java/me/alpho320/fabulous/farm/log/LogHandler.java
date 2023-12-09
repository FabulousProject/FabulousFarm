package me.alpho320.fabulous.farm.log;

import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogHandler {

    private static @NotNull File LOG_FILE;
    private static @NotNull YamlConfiguration CONFIGURATION;

    public static void init(@NotNull BukkitFarmPlugin plugin) {
        LOG_FILE = createFile(plugin);
        CONFIGURATION = YamlConfiguration.loadConfiguration(LOG_FILE);
    }

    public static void log(@NotNull String message) {
        CONFIGURATION.set(new SimpleDateFormat("HH:mm:ss", new Locale("en")).format(new Date()) + "." + System.currentTimeMillis(), message);
    }

    public static void log(boolean debug, @NotNull String message) {
        log(message);
        if (debug) Debug.debug(0, message);
    }

    public static void save() {
        try {
            CONFIGURATION.save(LOG_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            Debug.debug(1, " | An error occurred while saving the log file.");
        }
    }

    private static @NotNull File createFile(@NotNull BukkitFarmPlugin plugin) {
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