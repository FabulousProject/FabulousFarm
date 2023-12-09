package me.alpho320.fabulous.farm;

import me.alpho320.fabulous.core.api.util.RoundedNumberFormat;
import me.alpho320.fabulous.farm.data.PlayerData;
import me.alpho320.fabulous.farm.provider.Provider;
import me.alpho320.fabulous.farm.provider.ProviderManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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

    public static void setupFormats(@NotNull BukkitFarmPlugin plugin) {
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


    public static String format(double i) {
        if (!BukkitFarmPlugin.instance().getConfig().getBoolean("Numbers.decimal", false)) {
            return String.format("%.2f", i);
        }

        return BukkitFarmPlugin.instance().getConfig().getBoolean("Numbers.formats.enabled", false) ? RoundedNumberFormat.format(i) : String.valueOf(i);
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
        if (BukkitFarmPlugin.isDisabled()) return;
        BukkitFarmPlugin.instance().getServer().getScheduler().runTask(BukkitFarmPlugin.instance(), runnable);
    }

    public static void runASYNC(Runnable runnable) {
        if (BukkitFarmPlugin.isDisabled()) return;
        BukkitFarmPlugin.instance().getServer().getScheduler().runTaskAsynchronously(BukkitFarmPlugin.instance(), runnable);
    }

    public static void executeCommands(Player player, List<String> commands) {
        if (player == null || commands == null) return;
        final BukkitFarmPlugin plugin = BukkitFarmPlugin.instance();
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