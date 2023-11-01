package me.alpho320.fabulous.farm;

import dev.lone.itemsadder.api.CustomStack;
import me.alpho320.fabulous.core.api.util.RoundedNumberFormat;
import me.alpho320.fabulous.core.bukkit.BukkitCore;
import me.alpho320.fabulous.core.bukkit.util.BukkitItemCreator;
import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.configuration.ConfigurationManager;
import me.alpho320.fabulous.farm.data.Cache;
import me.alpho320.fabulous.farm.data.IntData;
import me.alpho320.fabulous.farm.data.PlayerData;
import me.alpho320.fabulous.farm.provider.Provider;
import me.alpho320.fabulous.farm.provider.ProviderManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FarmAPI {

    public static void init(@NotNull FarmPlugin plugin, @Nullable Provider.Callback callback) {
        if (plugin == null) throw new IllegalStateException("init plugin cannot be null!");

        reload(plugin, true, callback);
    }

    public static void reload(@NotNull FarmPlugin plugin, boolean loadAllData, @Nullable Provider.Callback callback) {
        plugin.getServer().getScheduler().cancelTasks(plugin);

        plugin.setConfigurationManager(new ConfigurationManager(plugin));
        plugin.configurationManager().reload(true);
        plugin.setCache(new Cache(plugin.configurationManager()));

        setupFormats(plugin);
        
        ProviderManager.setup(plugin, loadAllData, callback);
        
        if (!loadAllData) checkCallback(callback, true);
    }

    public static @Nullable PlayerData getPlayerData(Player player) {
        if (player == null) return null;
        return ProviderManager.findPlayerData(player);
    }

    public static @Nullable PlayerData getPlayerData(UUID id) {
        if (id == null) return null;
        return ProviderManager.findPlayerData(id);
    }

    public static void getPlayerData(UUID id, boolean load, @Nullable Provider.Callback callback) {
        if (id == null) return;
        PlayerData data = ProviderManager.findPlayerData(id);
        if (data == null && load) {
            ProviderManager.get().loadPlayerData(id, true, c -> {
                checkCallback(callback, c);
            });
        } else {
            checkCallback(callback, true);
        }
    }

    public static void setupFormats(@NotNull FarmPlugin plugin) {
        if (!plugin.getConfig().getBoolean("Numbers.formats.enabled")) return;

        RoundedNumberFormat.setFormats(
                new String[]{
                        plugin.getConfig().getString("Numbers.formats.thousand", "K"),
                        plugin.getConfig().getString("Numbers.formats.million", "M"),
                        plugin.getConfig().getString("Numbers.formats.billion", "B"),
                        plugin.getConfig().getString("Numbers.formats.trillion", "T"),
                        plugin.getConfig().getString("Numbers.formats.quadrillion", "Q")
                }
        );
    }

    public static @NotNull ItemStack getItemFromSection(@NotNull FarmPlugin plugin, ConfigurationSection section) {
        return getItemFromSection(plugin, section, "material");
    }

    public static @NotNull ItemStack getItemFromSection(@NotNull FarmPlugin plugin, ConfigurationSection section, String typeKey) {
        if (section == null) {
            throw new IllegalStateException("GTFS section null!");
        } else {
            String mat = section.getString(typeKey, "AIR");
            if (section.isSet("==")) {
                try {
                    Debug.debug(0, " | CurrentPath: " + section.getCurrentPath());
                    ItemStack itemStack = section.getItemStack(section.getCurrentPath(), new ItemStack(Material.AIR));
                    Debug.debug(0," | ItemStack: " + itemStack);
                    return itemStack;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (mat.startsWith("ITEMSADDER-")) {
                if (plugin.getServer().getPluginManager().isPluginEnabled("ItemsAdder")) {
                    CustomStack stack = CustomStack.getInstance(section.getString(typeKey, "null").split("-")[1]);
                    if (stack == null) {
                        Debug.debug(1, " | ItemError: " + section.getString(typeKey, "null") + " is not itemsadder item!");
                        return new ItemStack(Material.AIR);
                    }

                    ItemStack clone = stack.getItemStack().clone();
                    ItemMeta meta = clone.getItemMeta();

                    if (section.isList("lore"))
                        meta.setLore(BukkitCore.instance().message().colored(section.getStringList("lore")));
                    if (section.isString("name"))
                        meta.setDisplayName(BukkitCore.instance().message().colored(section.getString("name", "")));

                    clone.setItemMeta(meta);
                    clone.setAmount(IntData.of(section).toInt());
                    return clone;
                } else {
                    Debug.debug(1, " | " + mat + " is itemsadder item but itemsadder is not enabled!");
                    return new ItemStack(Material.AIR);
                }
            }  else if (section.getString(typeKey, "AIR").contains("%") || section.getString(typeKey, "AIR").contains(":")) {
                return new BukkitItemCreator()
                        .type("STONE")
                        .name(BukkitCore.instance().message().colored(section.getString("name", "null")))
                        .amount(IntData.of(section).toInt())
                        .lore(BukkitCore.instance().message().colored(section.getStringList("lore")))
                        .damage((short) section.getInt("damage", 0))
                        .enchantFromList(section.getStringList("enchantments"))
                        .modelData(section.getInt("model-data", 0))
                        .create();
            } else {
                return new BukkitItemCreator()
                        .type(section.getString(typeKey, "AIR"))
                        .name(BukkitCore.instance().message().colored(section.getString("name", "null")))
                        .amount(IntData.of(section).toInt())
                        .lore(BukkitCore.instance().message().colored(section.getStringList("lore")))
                        .damage((short) section.getInt("damage", 0))
                        .enchantFromList(section.getStringList("enchantments"))
                        .modelData(section.getInt("model-data", 0))
                        .create();
            }
        }
    }

    public static String format(double i) {
        if (!FarmPlugin.instance().getConfig().getBoolean("Numbers.decimal", false)) {
            return String.format("%.2f", i);
        }

        return FarmPlugin.instance().getConfig().getBoolean("Numbers.formats.enabled", false) ? RoundedNumberFormat.format(i) : String.valueOf(i);
    }

    public static String took(long from) {
        return (System.currentTimeMillis() - from) + "ms";
    }


    public static int getIntFromPerm(Player player, String startsWith, int split) {
        int highest = 0;
        for (PermissionAttachmentInfo permissionAttachmentInfo : player.getEffectivePermissions()) {
            if (permissionAttachmentInfo.getPermission().startsWith(startsWith) && permissionAttachmentInfo.getPermission().contains(".")) {
                int value = Integer.parseInt(permissionAttachmentInfo.getPermission().split("[.]")[split]);
                if (value > highest)
                    highest = value;
            }
        }
        return highest;
    }

    public static String getStringFromPerm(Player player, String startsWith, int split) {
        for (PermissionAttachmentInfo permissionAttachmentInfo : player.getEffectivePermissions()) {
            if (permissionAttachmentInfo.getPermission().startsWith(startsWith) && permissionAttachmentInfo.getPermission().contains(".")) {
                return permissionAttachmentInfo.getPermission().split("[.]")[split];
            }
        }
        return "null";
    }

    public static String getDisplayNameOrMatName(ItemStack item) {
        if (item == null) return "null";
        return (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) ?
                item.getItemMeta().getDisplayName() : item.getType().toString();
    }

    public static List<String> getItemLoreOrNew(ItemStack item) {
        return (item.hasItemMeta() && item.getItemMeta().hasLore()) ?
                item.getItemMeta().getLore() : new ArrayList<>();
    }


    public static long getDiff(Temporal temporal) {
        if (temporal == null) return 1;
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), temporal);
    }

    public static long getDiff(String time, Temporal temporal) {
        return ChronoUnit.valueOf(time).between(LocalDateTime.now(), temporal);
    }

    public static void runSYNC(Runnable runnable) {
        if (FarmPlugin.isDisabled()) return;
        FarmPlugin.instance().getServer().getScheduler().runTask(FarmPlugin.instance(), runnable);
    }

    public static void runASYNC(Runnable runnable) {
        if (FarmPlugin.isDisabled()) return;
        FarmPlugin.instance().getServer().getScheduler().runTaskAsynchronously(FarmPlugin.instance(), runnable);
    }

    public static void executeCommands(Player player, List<String> commands) {
        if (player == null || commands == null) return;
        final FarmPlugin plugin = FarmPlugin.instance();
        plugin.getServer().getScheduler().runTask(plugin, () -> {
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

    public static void checkCallback(@Nullable Provider.Callback callback, boolean state) {
        if (callback != null)
            callback.complete(state);
    }

}