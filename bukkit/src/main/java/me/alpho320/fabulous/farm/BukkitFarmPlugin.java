package me.alpho320.fabulous.farm;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.core.util.inv.smartinventory.SmartInventory;
import me.alpho320.fabulous.core.util.inv.smartinventory.manager.BasicSmartInventory;
import me.alpho320.fabulous.farm.api.FarmManager;
import me.alpho320.fabulous.farm.command.FarmCommand;
import me.alpho320.fabulous.farm.configuration.ConfigurationManager;
import me.alpho320.fabulous.farm.data.Cache;
import me.alpho320.fabulous.farm.gui.GUIManager;
import me.alpho320.fabulous.farm.hook.HookManager;
import me.alpho320.fabulous.farm.hook.impl.MythicMobsHook;
import me.alpho320.fabulous.farm.hook.impl.PAPIHook;
import me.alpho320.fabulous.farm.listener.ItemsAdderLoadListener;
import me.alpho320.fabulous.farm.listener.PlayerJoinListener;
import me.alpho320.fabulous.farm.listener.PlayerQuitListener;
import me.alpho320.fabulous.farm.log.LogHandler;
import me.alpho320.fabulous.farm.provider.Provider;
import me.alpho320.fabulous.farm.provider.ProviderManager;
import me.alpho320.fabulous.farm.task.TaskManager;
import me.alpho320.fabulous.farm.util.logger.FarmPluginLogger;
import me.alpho320.fabulous.farm.util.updater.Updater;
import me.alpho320.fabulous.farm.workload.LoadFarmsWorkloadThread;
import me.alpho320.fabulous.farm.workload.SaveFarmsWorkloadThread;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BukkitFarmPlugin extends JavaPlugin implements FarmPlugin {

    private static BukkitFarmPlugin instance;

    private FarmCommand farmCommand;
    private Cache cache;

    private int versionInt;
    private boolean isLoaded = false;
    private static boolean isDisabled = false;

    private Economy economy;
    private SmartInventory inventory;

    @Override
    public void onLoad() {
        if (instance != null) throw new IllegalStateException("FarmPlugin cannot be started twice!");

        instance = this;
        isDisabled = false;

        CommandAPI.onLoad(new CommandAPIConfig().silentLogs(false).verboseOutput(true));
    }

    @Override
    public void onEnable() {
        long now = System.currentTimeMillis();

        String packageName = getServer().getClass().getPackage().getName();
        this.versionInt = Integer.parseInt(packageName.substring(packageName.lastIndexOf('.') + 1).split("[_]")[1]);

        if (!setupEconomy()) throw new IllegalStateException("Could not connect to Vault");

        this.inventory = new BasicSmartInventory(this);
        this.inventory.init();

        this.farmCommand = new FarmCommand(this);

        registerListeners(
                new PlayerJoinListener(this),
                new PlayerQuitListener(this)
        );


        configurationManager().reload(false);
        LogHandler.init(this);

        getLogger().info(" | You're running on " + versionInt);
        farmCommand().setup();


        CommandAPI.onEnable(this);
    }

    @Override
    public void onDisable() {
        isDisabled = true;
        long now = System.currentTimeMillis();
        Provider provider = ProviderManager.get();

        provider.saveAllData(false, state -> {
            if (state) {
                Debug.debug(0, " | All data successfully saved (" + FarmAPI.took(now) + ")");
            } else {
                Debug.debug(1, " | Failed to save all data!");
            }
            provider.close(null);
        });


        CommandAPI.onDisable();

        Debug.debug(0, "");
        Debug.debug(0, "============ FabulousFarmPlugin ============");
        Debug.debug(0, "");
        Debug.debug(0, "FabulousFarmPlugin Deactive!");
        Debug.debug(0, "Version: " + getDescription().getVersion());
        Debug.debug(0, "Developer: Alpho320#9202");
        Debug.debug(0, "");
        Debug.debug(0, "============ FabulousFarmPlugin ============");
        Debug.debug(0, "");
    }

    public SmartInventory inventory() {
        return this.inventory;
    }

    public @NotNull Economy economy() {
        return this.economy;
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }

    public void setLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            this.economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    public @NotNull FarmCommand farmCommand() {
        return this.farmCommand;
    }

    public static BukkitFarmPlugin instance() {
        return instance;
    }

    @Override
    public @NotNull BukkitConfiguration getConfig() {
        return configurationManager().getConfig();
    }

    public @NotNull BukkitConfiguration config() {
        return configurationManager().getConfig();
    }

    public void setConfig(@NotNull BukkitConfiguration config) {
        configurationManager().setConfig(config);
    }

    public @NotNull BukkitConfiguration messages() {
        return configurationManager().getMessages();
    }

    public void setMessages(@NotNull BukkitConfiguration messages) {
        configurationManager().setMessages(messages);
    }

    public @NotNull Cache cache() {
        return this.cache;
    }

    public void setCache(@NotNull Cache cache) {
        this.cache = cache;
    }


    @Override
    public @NotNull FarmPluginLogger logger() {
        return null;
    }

    @Override
    public @NotNull ConfigurationManager configurationManager() {
        return null;
    }

    @Override
    public @NotNull ProviderManager providerManager() {
        return null;
    }

    @Override
    public @NotNull GUIManager guiManager() {
        return null;
    }

    @Override
    public @NotNull HookManager hookManager() {
        return null;
    }

    @Override
    public @NotNull FarmManager farmManager() {
        return null;
    }

    @Override
    public @NotNull Updater updater() {
        return null;
    }

    @Override
    public @NotNull TaskManager taskManager() {
        return null;
    }

    @Override
    public @NotNull String version() {
        return null;
    }

    public int versionInt() {
        return this.versionInt;
    }

    @Override
    public void reload(boolean async, @Nullable Callback callback) {

    }

    public void registerListeners(Listener...listeners) {
        for (Listener listener : listeners)
            getServer().getPluginManager().registerEvents(listener, this);
    }


    public static boolean isDisabled() {
        return isDisabled;
    }

    public static void setIsDisabled(boolean isDisabled) {
        BukkitFarmPlugin.isDisabled = isDisabled;
    }

}