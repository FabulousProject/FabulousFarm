package me.alpho320.fabulous.farm;

import me.alpho320.fabulous.core.api.util.RoundedNumberFormat;
import me.alpho320.fabulous.farm.api.FarmAPI;
import me.alpho320.fabulous.farm.api.crop.Crop;
import me.alpho320.fabulous.farm.api.crop.CropHolder;
import me.alpho320.fabulous.farm.api.fertilizer.Fertilizer;
import me.alpho320.fabulous.farm.api.greenhouse.Greenhouse;
import me.alpho320.fabulous.farm.api.greenhouse.GreenhouseHolder;
import me.alpho320.fabulous.farm.api.pot.Pot;
import me.alpho320.fabulous.farm.api.pot.PotHolder;
import me.alpho320.fabulous.farm.api.scarecrow.Scarecrow;
import me.alpho320.fabulous.farm.api.scarecrow.ScarecrowHolder;
import me.alpho320.fabulous.farm.api.season.Season;
import me.alpho320.fabulous.farm.api.vermin.Vermin;
import me.alpho320.fabulous.farm.data.PlayerData;
import me.alpho320.fabulous.farm.hook.Hook;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class BukkitFarmAPI implements FarmAPI {

    private final @NotNull BukkitFarmPlugin plugin;
    private static BukkitFarmAPI instance;

    public BukkitFarmAPI(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
        instance = this;
    }

    public static @NotNull BukkitFarmAPI instance() {
        if (instance == null)
            throw new IllegalStateException("BukkitFarmAPI is not initialized yet!");
        return instance;
    }


    @Override
    public @Nullable PotHolder findPotHolder(@NotNull Location location) {
        return plugin.farmManager().potManager().findHolder(location);
    }

    @Override
    public @Nullable CropHolder findCropHolder(@NotNull Location location) {
        return plugin.farmManager().cropManager().findHolder(location);
    }

    @Override
    public @Nullable ScarecrowHolder findScarecrowHolder(@NotNull Location location) {
        return plugin.farmManager().scarecrowManager().findHolder(location);
    }

    @Override
    public @Nullable GreenhouseHolder findGreenhouseHolder(@NotNull Location location) {
        return plugin.farmManager().greenhouseManager().findHolder(location);
    }

    @Override
    public @NotNull Season currentSeason() {
        return plugin.farmManager().seasonManager().currentSeason();
    }

    @Override
    public @Nullable Pot findPot(@NotNull String id) {
        return plugin.farmManager().potManager().find(id);
    }

    @Override
    public @Nullable Crop findCrop(@NotNull String id) {
        return plugin.farmManager().cropManager().find(id);
    }

    @Override
    public @Nullable Vermin findCrow(@NotNull String id) {
        return plugin.farmManager().crowManager().find(id);
    }

    @Override
    public @Nullable Season findSeason(@NotNull String id) {
        return plugin.farmManager().seasonManager().find(id);
    }

    @Override
    public @Nullable Scarecrow findScarecrow(@NotNull String id) {
        return plugin.farmManager().scarecrowManager().find(id);
    }

    @Override
    public @Nullable Fertilizer findFertilizer(@NotNull String id) {
        return plugin.farmManager().fertilizerManager().find(id);
    }

    @Override
    public @Nullable Greenhouse findGreenhouse(@NotNull String id) {
        return plugin.farmManager().greenhouseManager().find(id);
    }

    @Override
    public @Nullable Hook findHook(@NotNull String id) {
        return plugin.hookManager().find(id);
    }

    @Override
    public @Nullable PlayerData findPlayerData(String name) {
        return plugin.providerManager().provider().findPlayerData(Bukkit.getOfflinePlayer(name).getUniqueId());
    }

    @Override
    public @Nullable PlayerData findPlayerData(UUID id) {
        return plugin.providerManager().provider().findPlayerData(id);
    }

    @Override
    public @NotNull CompletableFuture<@Nullable PlayerData> findPlayerData(@NotNull UUID id, boolean load) {
        return plugin.providerManager().provider().findPlayerDataAsync(id);
    }

    @Override
    public @NotNull String format(double i) {
        if (!plugin.getConfig().getBoolean("Numbers.decimal", false)) {
            return String.format("%.2f", i);
        }

        return plugin.getConfig().getBoolean("Numbers.formats.enabled", false) ? RoundedNumberFormat.format(i) : String.valueOf(i);
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

    public static String took(long from) {
        return (System.currentTimeMillis() - from) + "ms";
    }

    public static boolean validateConfigurationSection(@NotNull BukkitFarmPlugin plugin, @NotNull ConfigurationSection section, String path, @NotNull String id) {
        if (section.isConfigurationSection(path)) return true;

        plugin.logger().warning(id + " has no '" + path + "' section, please check your file.");
        return false;
    }

    public static boolean validateConfigurationKeyIsSet(@NotNull BukkitFarmPlugin plugin, @NotNull ConfigurationSection section, String path, @NotNull String id) {
        if (section.isSet(path)) return true;

        plugin.logger().warning(id + " has no '" + path + "' value, please check your file.");
        return false;
    }

    public static void extractDefaultFolderFilesFromJarIfNoExist(@NotNull BukkitFarmPlugin plugin, @NotNull String path) {
        try {
            File[] folder = new File(plugin.getDataFolder(), path).listFiles();
            if (folder == null || folder.length == 0) {
                plugin.getClass().getClassLoader().getResources(path).asIterator().forEachRemaining(url -> {
                    String[] split = url.getPath().split("/");
                    String fileName = split[split.length - 1];

                    plugin.logger().debug(" | Extracting default file: " + fileName);
                    plugin.saveResource(path + "/" + fileName, false);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            plugin.logger().severe(" | Could not extract default files! (FolderPath: " + path + ")");
        }
    }

}