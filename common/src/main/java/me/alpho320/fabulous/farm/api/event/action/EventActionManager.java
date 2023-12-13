package me.alpho320.fabulous.farm.api.event.action;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.TypedManager;
import me.alpho320.fabulous.farm.api.event.EventType;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EventActionManager extends TypedManager<String, Class<?>> {

    public void checkActions(@NotNull Map<EventType, List<EventAction>> map, @NotNull EventType eventType, @NotNull Location location, @Nullable Event event, @Nullable Entity entity, @Nullable String extraInfo) {
        List<EventAction> actions = map.getOrDefault(eventType, new ArrayList<>());
        for (EventAction action : actions) {
            action.execute(location, event, entity, extraInfo);
        }
    }

    public void checkActions(@NotNull Map<EventType, List<EventAction>> map, @NotNull EventType eventType, @NotNull Location location, @Nullable Event event, @Nullable Entity entity) {
        checkActions(map, eventType, location, event, entity, null);
    }

    public void checkActions(@NotNull Map<EventType, List<EventAction>> map, @NotNull EventType eventType, @NotNull Location location, @Nullable Event event) {
        checkActions(map, eventType, location, event, null, null);
    }

    public void checkActions(@NotNull Map<EventType, List<EventAction>> map, @NotNull EventType eventType, @NotNull Location location) {
        checkActions(map, eventType, location, null, null, null);
    }

    public @NotNull Map<EventType, List<EventAction>> actionMapFromSection(ConfigurationSection section) {
        Map<EventType, List<EventAction>> map = new HashMap<>();
        if (section == null) return map;
        for (String key : section.getKeys(false)) {
            final EventType eventType = EventType.match(key);
            if (eventType == null) {
                plugin().logger().severe("   | " + key + " is not a valid event type.");
                continue;
            }
            final List<EventAction> actions = actionListFromSection(section.getConfigurationSection(key));

            plugin().logger().info("   | Amount of " + actions.size() + " actions loaded for " + eventType.type() + " event.");
            map.put(eventType, actions);
        }
        return map;
    }

    public @NotNull List<EventAction> actionListFromSection(ConfigurationSection section) {
        List<EventAction> list = new ArrayList<>();
        if (section == null) return list;
        for (String key : section.getKeys(false)) {
            EventAction action = actionFromSection(key, section.getConfigurationSection(key));
            if (action == null) continue;
            list.add(action);
        }
        return list;
    }

    public @Nullable EventAction actionFromSection(@NotNull String id, @NotNull ConfigurationSection section) {
        try {
            final Class<?> clazz = find(id);
            if (clazz == null) {
                plugin().logger().severe("   | EventAction of " + id + " not found.");
                plugin().logger().severe("   | Available actions: " + Arrays.toString(map().keySet().toArray()));
                return null;
            }

            EventAction action = (EventAction) clazz.getConstructor(FarmPlugin.class, ConfigurationSection.class).newInstance(plugin(), section);
            action.register();

            return action;
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
        return actionFromSection(id, section).getClass();
    }

}