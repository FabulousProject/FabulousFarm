package me.alpho320.fabulous.farm.util.updater;

import me.alpho320.fabulous.farm.FarmPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

public class Updater {
    private final @NotNull FarmPlugin plugin;

    private final boolean enabled;
    private final @NotNull String version;
    private @Nullable URL url;

    private final int[] tokenizedCurrentVersion;
    private @NotNull String returnedVersion = "";

    private boolean updateAvailable = false;

    public Updater(@NotNull FarmPlugin plugin, @NotNull String version, boolean enabled) {
        this.plugin = plugin;
        this.enabled = enabled;
        this.version = plugin.version();
        this.tokenizedCurrentVersion = tokenize(version);

        try {
            this.url = new URL("https://raw.githubusercontent.com/FabulousProject/version/main/version.md");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Failed to create URL for Updater!");
            this.url = null;
        }

    }

    public void check() {
        if (!enabled) {
            plugin.logger().warning("Updater is disabled but check() was called!");
            return;
        } else if (url == null) {
            plugin.logger().warning("Updater is enabled but connection URL is null!");
            plugin.logger().warning("Is github down? check the 'https://raw.githubusercontent.com/FabulousProject/version/main/version.md'");
            return;
        }

        try {
            URLConnection connection = url.openConnection();
            this.returnedVersion = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

            plugin.logger().debug("Updater returned version: " + this.returnedVersion + " (current: " + version + ")");

            int[] tokenizedVersion = tokenize(returnedVersion);
            boolean newVersion = false;

            for (int i = 0; i < tokenizedVersion.length; i++) {
                if (tokenizedCurrentVersion.length <= i && tokenizedVersion[i] != 0) {
                    newVersion = true;
                    break;
                }
                if (tokenizedVersion[i] > tokenizedCurrentVersion[i]) {
                    newVersion = true;
                    break;
                } else if (tokenizedVersion[i] < tokenizedVersion[i]) {
                    break;
                }
            }

            if (newVersion) {
                this.updateAvailable = true;

                plugin.logger().warning("A new version " + returnedVersion + " was found! (your version: " + version + "). Please update.");
                plugin.logger().warning("Remember; if you continue to use an old version you will not get any support.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[] tokenize(String s) {
        String[] numericVersion = s.split(Pattern.quote("-"));
        String[] tokenizedVersion = numericVersion[0].split(Pattern.quote("."));
        int[] intTokenizedVersion = new int[tokenizedVersion.length];
        for (int i = 0; i < tokenizedVersion.length; i++) {
            try {
                intTokenizedVersion[i] = Integer.parseInt(tokenizedVersion[i]);
            } catch (NumberFormatException ignored) {
                intTokenizedVersion[i] = 0;
            }
        }

        return intTokenizedVersion;
    }

    public @Nullable URL url() {
        return this.url;
    }

    public @NotNull String returnedVersion() {
        return this.returnedVersion;
    }

    public boolean isUpdateAvailable() {
        return this.updateAvailable;
    }

}