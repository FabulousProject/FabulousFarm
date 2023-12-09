package me.alpho320.fabulous.farm.configuration;

import me.alpho320.fabulous.core.bukkit.BukkitCore;
import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class BukkitConfigurationManager extends ConfigurationManager {

    private final @NotNull BukkitFarmPlugin plugin;
    private BukkitCore core;

    private BukkitConfiguration config;
    private BukkitConfiguration messages;

    public BukkitConfigurationManager(BukkitFarmPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    public void reload(boolean loadGuis) {
        setConfig(new BukkitConfiguration("config", plugin));
        setMessages(new BukkitConfiguration("messages", plugin));

        this.core = new BukkitCore(plugin);
        this.core.init(plugin, ChatColor.translateAlternateColorCodes('&', config().getString("Main.prefix", "ServerName")), messages());

        Debug.setDebug(config().getBoolean("Main.debug", false));
        if (loadGuis) BukkitGUIManager.reload(plugin);
    }

}