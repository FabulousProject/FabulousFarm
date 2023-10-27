package me.alpho320.fabulous.farm;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import me.alpho320.fabulous.core.bukkit.util.BukkitConfiguration;
import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.core.util.inv.smartinventory.SmartInventory;
import me.alpho320.fabulous.core.util.inv.smartinventory.manager.BasicSmartInventory;
import me.alpho320.fabulous.farm.data.Cache;
import me.alpho320.fabulous.farm.hook.Hooks;
import me.alpho320.fabulous.farm.log.LogHandler;
import me.alpho320.fabulous.farm.provider.Provider;
import me.alpho320.fabulous.farm.provider.ProviderManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class FarmPlugin extends JavaPlugin {

    private static FarmPlugin instance;

    private FarmCommand farmCommand;
    private Cache cache;

    private ConfigurationManager configurationManager;

    private LoadFarmsWorkloadThread loadRealmsWorkloadThread;
    private SaveFarmsWorkloadThread saveRealmsWorkloadThread;

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

        ProviderManager.register(
                new YAMLProvider(this)
        );

        Hooks.register(
                new PAPIHook(this, true),
                new MythicMobsHook(this, false)
        );
        CommandAPI.onLoad(new CommandAPIConfig().silentLogs(false).verboseOutput(true));
    }

    @Override
    public void onEnable() {
        long now = System.currentTimeMillis();

        String packageName = getServer().getClass().getPackage().getName();
        this.versionInt = Integer.parseInt(packageName.substring(packageName.lastIndexOf('.') + 1).split("[_]")[1]);

        if (!setupEconomy()) throw new IllegalStateException("Could not connect to Vault");

        FarmAPI.plugin = this;

        this.inventory = new BasicSmartInventory(this);
        this.inventory.init();

        this.farmCommand = new FarmCommand(this);

        FarmAPI.registerListeners(
                this,
                new PlayerJoinListener(this),
                new PlayerQuitListener(),
        );

        setConfigurationManager(new ConfigurationManager(this));
        configurationManager().reload(false);
        LogHandler.init(this);

        getLogger().info(" | You're running on " + versionInt);
        farmCommand().setup();

        if (getConfig().getBoolean("Hooks.itemsadder", false)) {
            getLogger().info(" | Found hook of ItemsAdder, waiting for ItemsadderReadyEvent...");
            FarmAPI.registerListeners(this, new ItemsAdderLoadListener(this, now));
        } else {
            FarmAPI.init(this, state -> {
                if (state) {

                    Hooks.get("PlaceholderAPI").setEnabled(getConfig().getBoolean("Hooks.placeholderapi", true));
                    Hooks.get("MythicMobs").setEnabled(getConfig().getBoolean("Hooks.mythicmobs", false));

                    Hooks.initAll(this);


                    Debug.debug(0, "");
                    Debug.debug(0, " | Successfully loaded! (" + FarmAPI.took(now) + ")");

                    Debug.debug(0, "");
                    Debug.debug(0, "============ FabulousFarmPlugin ============");
                    Debug.debug(0, "");
                    Debug.debug(0, "FabulousFarmPlugin Active!");
                    Debug.debug(0, "Version: " + getDescription().getVersion());
                    Debug.debug(0, "Developer: Alpho320#9202");
                    Debug.debug(0, "");
                    Debug.debug(0, "============ FabulousFarmPlugin ============");
                    Debug.debug(0, "");

                    this.isLoaded = true;
                } else {
                    Debug.debug(1, " | Failed to load! Please check the console.");
                    getServer().getPluginManager().disablePlugin(this);
                }

            });
        }
        CommandAPI.onEnable(this);
    }

    @Override
    public void onDisable() {
        isDisabled = true;
        long now = System.currentTimeMillis();
        Provider provider = ProviderManager.get();

        if (cache().isDataServer) {
            provider.saveAllData(false, state -> {
                if (state) {
                    Debug.debug(0, " | All data successfully saved (" + FarmAPI.took(now) + ")");
                } else {
                    Debug.debug(1, " | Failed to save all data!");
                }
                provider.close(null);
            });
        }

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

    public static FarmPlugin instance() {
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

    public @NotNull ConfigurationManager configurationManager() {
        return this.configurationManager;
    }

    public void setConfigurationManager(@NotNull ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    public int versionInt() {
        return versionInt;
    }

    public LoadFarmsWorkloadThread loadRealmsWorkloadThread() {
        return loadRealmsWorkloadThread;
    }

    public void setLoadFarmsWorkloadThread(LoadFarmsWorkloadThread loadRealmsWorkloadThread) {
        this.loadRealmsWorkloadThread = loadRealmsWorkloadThread;
    }

    public SaveFarmsWorkloadThread saveFarmsWorkloadThread() {
        return saveRealmsWorkloadThread;
    }

    public void setSaveFarmsWorkloadThread(SaveFarmsWorkloadThread saveFarmsWorkloadThread) {
        this.saveRealmsWorkloadThread = saveRealmsWorkloadThread;
    }
    public static boolean isDisabled() {
        return isDisabled;
    }

    public static void setIsDisabled(boolean isDisabled) {
        FarmPlugin.isDisabled = isDisabled;
    }

}