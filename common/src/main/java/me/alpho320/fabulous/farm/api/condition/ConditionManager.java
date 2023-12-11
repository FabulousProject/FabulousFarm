package me.alpho320.fabulous.farm.api.condition;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.TypedManager;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class ConditionManager extends TypedManager<String, Class<?>> {

    public @NotNull List<Condition> conditionsFromSection(@NotNull ConfigurationSection section) {
        List<Condition> conditions = new ArrayList<>();
        for (String key : section.getKeys(false)) {
            final Condition condition = conditionFromSection(key, section.getConfigurationSection(key));
            if (condition != null) conditions.add(condition);
        }
        return conditions;
    }

    public @Nullable Condition conditionFromSection(@NotNull String id, @NotNull ConfigurationSection section) {
        try {
            final Class<?> clazz = find(id);
            if (clazz == null) {
                plugin().logger().severe("   | Condition of " + id + " not found.");
                plugin().logger().severe("   | Available conditions: " + Arrays.toString(map().keySet().toArray()));
                return null;
            }
            return (Condition) clazz.getConstructor(FarmPlugin.class, ConfigurationSection.class).newInstance(plugin(), section);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public @Nullable Class<?> find(String id) {
        if (id == null) return null;
        Class<?> clazz = map().getOrDefault(id, null);
        if (clazz == null) {
            for (Map.Entry<String, Class<?>> entry : map().entrySet()) {
                if (entry.getKey().equalsIgnoreCase(id) || id.toLowerCase().startsWith(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }
        return clazz;
    }

    @Override
    public @Nullable Class<?> fromSection(String id, ConfigurationSection section) {
        return conditionFromSection(id, section).getClass();
    }

}