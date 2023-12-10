package me.alpho320.fabulous.farm.provider;

import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.Callback;
import me.alpho320.fabulous.farm.BukkitFarmAPI;
import me.alpho320.fabulous.farm.provider.impl.MySQLProvider;
import me.alpho320.fabulous.farm.provider.impl.SQLiteProvider;
import me.alpho320.fabulous.farm.provider.impl.YAMLProvider;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

public class BukkitProviderManager extends ProviderManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitProviderManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup(@NotNull ProviderLoadType type, @Nullable Callback callback) {
        AtomicLong baseNow = new AtomicLong(System.currentTimeMillis());
        AtomicLong now = new AtomicLong(System.currentTimeMillis());
        plugin.logger().info(" | Registering providers...");

        register("mysql", new MySQLProvider(plugin));
        register("sqlite", new SQLiteProvider(plugin));
        register("yaml", new YAMLProvider(plugin));

        String key = plugin.configurationManager().config().getString("Data.provider", "null").toLowerCase();

        Provider provider = find(key);
        plugin.logger().info(" | Selected Provider: " + (provider != null ? provider.name() : "null!"));

        if (provider != null) {
            setProvider(provider);
            provider.init(c -> {
                Callback callback2 = (state) -> {
                    if (state) {
                        plugin.logger().info(" | Provider successfully loaded. (" + (System.currentTimeMillis() - now.get()) + "ms)");
                        plugin.taskManager().startTasks();
                        plugin.checkCallback(callback, true);
                    } else {
                        plugin.logger().severe(" | Failed to load provider, plugin disabling!");
                        plugin.checkCallback(callback, false);
                        Bukkit.getServer().getPluginManager().disablePlugin((BukkitFarmPlugin) plugin);
                    }
                };
                switch (type) {
                    case ALL:
                        provider.loadAllData(true, callback2);
                        break;
                    case ALL_FARMS:
                        plugin.logger().info(" | Loading all pot data...");
                        provider.loadAllPotData(true, c1 -> {
                            if (c1) {
                                plugin.logger().info(" | Amount of " + plugin.farmManager().potManager().map().keySet().size() + " pot data loaded in " + BukkitFarmAPI.took(now.get()) + ".");
                                now.set(System.currentTimeMillis());
                                provider.loadAllCropData(true, c2 -> {
                                    if (c2) {
                                        plugin.logger().info(" | Amount of " + plugin.farmManager().cropManager().map().keySet().size() + " crop data loaded in " + BukkitFarmAPI.took(now.get()) + ".");
                                        now.set(System.currentTimeMillis());
                                        provider.loadAllGreenhouseData(true, c3 -> {
                                            if (c3) {
                                                plugin.logger().info(" | Amount of " + plugin.farmManager().greenhouseManager().map().keySet().size() + " greenhouse data loaded in " + BukkitFarmAPI.took(now.get()) + ".");
                                                now.set(System.currentTimeMillis());
                                                provider.loadAllScarecrowData(true, c4 -> {
                                                    if (c4) {
                                                        plugin.logger().info(" | Amount of " + plugin.farmManager().scarecrowManager().map().keySet().size() + " scarecrow data loaded in " + BukkitFarmAPI.took(now.get()) + ".");
                                                    } else {
                                                        plugin.logger().severe(" | Failed to load all scarecrow data!");
                                                    }

                                                    plugin.logger().info(" | All farm data loaded in " + BukkitFarmAPI.took(baseNow.get()));
                                                    callback2.complete(c4);
                                                });
                                            } else {
                                                plugin.logger().severe(" | Failed to load all greenhouse data!");
                                                callback2.complete(false);
                                            }
                                        });
                                    } else {
                                        plugin.logger().severe(" | Failed to load all crop data!");
                                        callback2.complete(false);
                                    }
                                });
                            } else {
                                plugin.logger().severe(" | Failed to load all pot data!");
                                callback2.complete(false);
                            }
                        });
                        break;
                    case PLAYER:
                        provider.loadAllPlayerData(true, callback);
                        break;
                }
            });
        } else {
            plugin.logger().severe(" | Failed to load provider! Please check your config.");
            plugin.logger().severe(" | Tried to load provider of " + key + " but something went wrong!");
            plugin.logger().severe(" | Probably you have a typo in your config, there is no provider named " + key + "!");
            plugin.logger().severe(" | Available providers: " + Arrays.toString(map().keySet().toArray()));
            plugin.logger().severe(" | Disabling plugin...");
            Bukkit.getServer().getPluginManager().disablePlugin((BukkitFarmPlugin) plugin);
        }

    }


}