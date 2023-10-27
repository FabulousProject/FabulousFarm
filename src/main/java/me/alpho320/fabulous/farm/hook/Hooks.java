package me.alpho320.fabulous.farm.hook;

import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.FarmPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Hooks {

    private static final @NotNull Map<String, Hook> map = new HashMap<>();

    public static void initAll(@NotNull FarmPlugin plugin) {
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            for (Hook hook : map.values()) {
                if (hook.enabled()) {
                    Debug.debug(0, " | Hook of " + hook.name() + " initializing...");
                    hook.init();
                }
            }
        });
    }

    public static void register(Hook...hooks) {
        Arrays.stream(hooks).forEach(Hooks::register);
    }

    public static void register(Hook hook) {
        if (hook == null) return;
        map.put(hook.name(), hook);
    }

    public static @Nullable Hook get(String hook) {
        return map.getOrDefault(hook, null);
    }

    public static boolean remove(String hook) {
        if (hook == null) return false;
        return map.remove(hook) != null;
    }

}