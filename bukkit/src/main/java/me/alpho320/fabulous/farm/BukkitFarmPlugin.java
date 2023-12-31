package me.alpho320.fabulous.farm;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.core.util.inv.smartinventory.SmartInventory;
import me.alpho320.fabulous.core.util.inv.smartinventory.manager.BasicSmartInventory;
import me.alpho320.fabulous.farm.api.FarmManager;
import me.alpho320.fabulous.farm.api.scheduler.BukkitSchedulerManager;
import me.alpho320.fabulous.farm.api.scheduler.SchedulerManager;
import me.alpho320.fabulous.farm.command.FarmCommand;
import me.alpho320.fabulous.farm.configuration.BukkitConfigurationManager;
import me.alpho320.fabulous.farm.configuration.BukkitGUIManager;
import me.alpho320.fabulous.farm.data.Cache;
import me.alpho320.fabulous.farm.gui.GUIManager;
import me.alpho320.fabulous.farm.hook.BukkitHookManager;
import me.alpho320.fabulous.farm.hook.HookManager;
import me.alpho320.fabulous.farm.listener.PlayerJoinListener;
import me.alpho320.fabulous.farm.listener.PlayerQuitListener;
import me.alpho320.fabulous.farm.provider.BukkitProviderManager;
import me.alpho320.fabulous.farm.provider.ProviderManager;
import me.alpho320.fabulous.farm.task.BukkitTaskManager;
import me.alpho320.fabulous.farm.util.logger.BukkitPluginLogger;
import me.alpho320.fabulous.farm.util.logger.FarmPluginLogger;
import me.alpho320.fabulous.farm.util.updater.Updater;
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
    private Updater updater;

    private BukkitPluginLogger logger;
    private BukkitFarmManager farmManager;
    private BukkitGUIManager guiManager;
    private BukkitConfigurationManager configurationManager;
    private BukkitProviderManager providerManager;
    private BukkitHookManager hookManager;

    private BukkitTaskManager taskManager;
    private BukkitSchedulerManager schedulerManager;

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
        new BukkitFarmAPI(this);

        this.logger = new BukkitPluginLogger(this, FarmPluginLogger.LoggingLevel.DEBUG);
        this.taskManager = new BukkitTaskManager(this);
        this.schedulerManager = new BukkitSchedulerManager(this);
        this.guiManager = new BukkitGUIManager(this);
        this.configurationManager = new BukkitConfigurationManager(this);
        this.providerManager = new BukkitProviderManager(this);
        this.hookManager = new BukkitHookManager(this);
        this.farmManager = new BukkitFarmManager(this);

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
        logger.setServerLoggingLevel(getConfig().getBoolean("Main.debug", false) ? FarmPluginLogger.LoggingLevel.DEBUG : FarmPluginLogger.LoggingLevel.INFO);

        this.updater = new Updater(this, getDescription().getVersion(), getConfig().getBoolean("Main.updater", false));
        getServer().getScheduler().runTaskAsynchronously(this, updater::check);

        hookManager.init(hookState -> {
            if (hookState) {
                logger.info(" | All hooks successfully loaded. " + BukkitFarmAPI.took(now));
                providerManager.setup(ProviderManager.ProviderLoadType.ALL_FARMS, state -> {
                    if (state) {

                        guiManager.setup();
                        farmManager.setup();
                        taskManager.startTasks();

                        setLoaded(true);
                        logger.info(" | Plugin successfully loaded. " + BukkitFarmAPI.took(now));
                    } else {
                        logger.severe(" | Failed to load all data!");
                    }
                });
            } else {
                logger.severe(" | Failed to load all hooks!");
            }
        });


        farmCommand().setup();
        CommandAPI.onEnable(this);
    }

    @Override
    public void onDisable() {
        isDisabled = true;
        long now = System.currentTimeMillis();

        providerManager().provider().saveAllData(false, state -> {
            if (state) {
                Debug.debug(0, " | All data successfully saved (" + BukkitFarmAPI.took(now) + ")");
            } else {
                Debug.debug(1, " | Failed to save all data!");
            }
            providerManager().provider().close(null);
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
        return configurationManager().config();
    }

    public void setConfig(@NotNull BukkitConfiguration config) {
        configurationManager().setConfig(config);
    }

    public @NotNull BukkitConfiguration messages() {
        return configurationManager().messages();
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
        return this.logger;
    }

    @Override
    public @NotNull BukkitConfigurationManager configurationManager() {
        return this.configurationManager;
    }

    @Override
    public @NotNull BukkitProviderManager providerManager() {
        return this.providerManager;
    }

    @Override
    public @NotNull GUIManager guiManager() {
        return this.guiManager;
    }

    @Override
    public @NotNull HookManager hookManager() {
        return this.hookManager;
    }

    @Override
    public @NotNull FarmManager farmManager() {
        return this.farmManager;
    }

    @Override
    public @NotNull Updater updater() {
        return this.updater;
    }

    @Override
    public @NotNull SchedulerManager schedulerManager() {
        return this.schedulerManager;
    }

    @Override
    public @NotNull BukkitTaskManager taskManager() {
        return this.taskManager;
    }

    @Override
    public @NotNull String version() {
        return getDescription().getVersion();
    }

    public int versionInt() {
        return this.versionInt;
    }

    @Override
    public void reload(boolean async, @Nullable Callback callback) {

    }

    @Override
    public void runSYNC(@NotNull Runnable runnable) {
        getServer().getScheduler().runTask(this, runnable);
    }

    @Override
    public void runASYNC(@NotNull Runnable runnable) {
        getServer().getScheduler().runTaskAsynchronously(this, runnable);
    }

    public void registerListeners(Listener...listeners) {
        for (Listener listener : listeners)
            getServer().getPluginManager().registerEvents(listener, this);
    }

    public void checkCallback(@Nullable Callback callback, boolean state) {
        if (callback != null) callback.complete(state);
    }

    public static boolean isDisabled() {
        return isDisabled;
    }

    public static void setIsDisabled(boolean isDisabled) {
        BukkitFarmPlugin.isDisabled = isDisabled;
    }

}