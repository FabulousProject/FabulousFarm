package me.alpho320.fabulous.farm.configuration;

import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.farm.FarmPlugin;

public abstract class ConfigurationManager {

    private final FarmPlugin plugin;

    private BukkitConfiguration config;
    private BukkitConfiguration messages;

    public ConfigurationManager(FarmPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract void reload(boolean loadGuis);

    public BukkitConfiguration config() {
        return config;
    }

    public void setConfig(BukkitConfiguration config) {
        this.config = config;
    }

    public BukkitConfiguration messages() {
        return messages;
    }

    public void setMessages(BukkitConfiguration messages) {
        this.messages = messages;
    }

}