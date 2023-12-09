package me.alpho320.fabulous.farm.util;

import me.alpho320.fabulous.farm.FarmAPI;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class StringUtil {

    public static String getFormattedTime(LocalDateTime time) {
        return getFormattedTime(FarmAPI.getDiff(time));
    }

    public static String getFormattedTime(long time) {
        try {
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
                final BukkitFarmPlugin plugin = BukkitFarmPlugin.instance();
                long seconds = time, minutes = 0, hours = 0, days = 0, weeks = 0, months = 0, years = 0;

                while (seconds >= 60) {
                    seconds -= 60;
                    minutes++;
                }

                while (minutes >= 60) {
                    minutes -= 60;
                    hours++;
                }

                while (hours >= 24) {
                    hours -= 24;
                    days++;
                }

                while (days >= 7) {
                    days -= 7;
                    weeks++;
                }

                while (weeks >= 4) {
                    weeks -= 4;
                    months++;
                }

                while (months >= 12) {
                    months -= 12;
                    years++;
                }

                return "" +
                        (years > 0 ? years + " " + plugin.cache().year + " " : "") +
                        (months > 0 ? months + " " + plugin.cache().month + " " : "") +
                        (weeks > 0 ? weeks + " " + plugin.cache().week + " " : "") +
                        (days > 0 ? days + " " + plugin.cache().day + " " : "") +
                        (hours > 0 ? hours + " " + plugin.cache().hour + " " : "") +
                        (minutes > 0 ? minutes + " " + plugin.cache().minute + " " : "") +
                        (seconds > 0 ? seconds + " " + plugin.cache().second + " " : "");

            });
            return completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }


}